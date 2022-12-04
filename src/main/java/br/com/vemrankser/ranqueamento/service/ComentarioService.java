package br.com.vemrankser.ranqueamento.service;

import br.com.vemrankser.ranqueamento.dto.AtividadeComentarioAvaliacaoCreateDTO;
import br.com.vemrankser.ranqueamento.dto.AtividadeComentarioAvaliacaoDTO;
import br.com.vemrankser.ranqueamento.dto.ComentarioDTO;
import br.com.vemrankser.ranqueamento.dto.PageDTO;
import br.com.vemrankser.ranqueamento.entity.AtividadeEntity;
import br.com.vemrankser.ranqueamento.entity.ComentarioEntity;
import br.com.vemrankser.ranqueamento.entity.UsuarioEntity;
import br.com.vemrankser.ranqueamento.enums.TipoFeedback;
import br.com.vemrankser.ranqueamento.exceptions.RegraDeNegocioException;
import br.com.vemrankser.ranqueamento.repository.ComentarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final AtividadeService atividadeService;
    private final UsuarioService usuarioService;

    private final ObjectMapper objectMapper;

    public AtividadeComentarioAvaliacaoDTO adicionarComentarioAvaliar(AtividadeComentarioAvaliacaoCreateDTO atividadeComentarioAvaliacaoCreateDTO, Integer idAluno, Integer idAtividade) throws RegraDeNegocioException {
        AtividadeEntity atividadeEntity = atividadeService.buscarPorIdAtividade(idAtividade);
        UsuarioEntity usuarioEntity = usuarioService.findById(idAluno);

        ComentarioEntity comentarioEntity = objectMapper.convertValue(atividadeComentarioAvaliacaoCreateDTO, ComentarioEntity.class);
        comentarioEntity.setIdAtividade(atividadeEntity.getIdAtividade());
        comentarioEntity.setAtividade(atividadeEntity);
        comentarioEntity.setIdUsuario(usuarioEntity.getIdUsuario());
        comentarioEntity.setUsuario(usuarioEntity);
        comentarioEntity.setComentario(comentarioEntity.getComentario());
        atividadeEntity.setPontuacao(atividadeComentarioAvaliacaoCreateDTO.getNotaAvalicao());
        atividadeEntity.getAlunos().stream()
                .filter(usuarioEntity1 -> usuarioEntity1.getIdUsuario().equals(usuarioEntity.getIdUsuario()))
                .forEach(aluno -> aluno.setPontuacaoAluno(calcularPontuacao(aluno, atividadeEntity)));

        comentarioEntity.setComentario(atividadeComentarioAvaliacaoCreateDTO.getComentario());
        comentarioRepository.save(comentarioEntity);
        atividadeService.save(atividadeEntity);

        return objectMapper.convertValue(atividadeComentarioAvaliacaoCreateDTO, AtividadeComentarioAvaliacaoDTO.class);
    }

    public ComentarioDTO adicionarFeedback(ComentarioDTO comentarioDTO, Integer idAluno, TipoFeedback tipoFeedback) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = usuarioService.findById(idAluno);
        ComentarioEntity comentarioEntity = objectMapper.convertValue(comentarioDTO, ComentarioEntity.class);
        comentarioEntity.setStatusComentario(tipoFeedback.getTipoFeedback());
        comentarioEntity.setIdUsuario(usuarioEntity.getIdUsuario());
        comentarioEntity.setUsuario(usuarioEntity);
        comentarioRepository.save(comentarioEntity);
        return comentarioDTO;
    }

    public PageDTO<ComentarioDTO> comentariosDoAluno(Integer pagina, Integer tamanho, Integer idAluno) {

        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<ComentarioEntity> page = comentarioRepository.findAllByIdUsuario(pageRequest, idAluno);
        List<ComentarioDTO> pessoasDaPagina = page.getContent().stream()
                .map(itemEntretenimentoEntity -> objectMapper.convertValue(itemEntretenimentoEntity, ComentarioDTO.class))
                .toList();
        return new PageDTO<>(page.getTotalElements(),
                page.getTotalPages(),
                pagina,
                tamanho,
                pessoasDaPagina
        );
    }

    public Integer calcularPontuacao(UsuarioEntity usuarioEntity, AtividadeEntity atividadeEntity) {
        return usuarioEntity.getPontuacaoAluno() + atividadeEntity.getPontuacao();
    }

    public List<ComentarioDTO> listarComentarioPorAtividade(Integer idAtividade) throws RegraDeNegocioException {
        AtividadeEntity atividade = atividadeService.buscarPorIdAtividade(idAtividade);

        return comentarioRepository.findAllByIdAtividade(atividade.getIdAtividade())
                .stream()
                .map(atividadeDto -> objectMapper.convertValue(atividadeDto, ComentarioDTO.class))
                .toList();
    }

    public List<ComentarioDTO> listarComentarioPorFeedback(TipoFeedback tipoFeedback) {
        return comentarioRepository.findAllByStatusComentario(tipoFeedback.getTipoFeedback())
                .stream()
                .map(comentarioEntity -> objectMapper.convertValue(comentarioEntity, ComentarioDTO.class))
                .toList();
    }

    public ComentarioEntity buscarPorIdComentario(Integer idComentario) throws RegraDeNegocioException {
        return comentarioRepository.findById(idComentario)
                .orElseThrow(() -> new RegraDeNegocioException("Comentario não encontrado"));
    }

    public void delete(Integer idComentario) throws RegraDeNegocioException {

        ComentarioEntity comentarioRecuperado = findById(idComentario);
        ComentarioEntity comentarioEntity = objectMapper.convertValue(comentarioRecuperado, ComentarioEntity.class);
        comentarioRepository.delete(comentarioEntity);

    }

    public ComentarioEntity findById(Integer id) throws RegraDeNegocioException {
        return comentarioRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("o comentario nao foi encontrado"));
    }
}
