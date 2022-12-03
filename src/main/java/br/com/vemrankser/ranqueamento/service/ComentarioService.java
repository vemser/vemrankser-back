package br.com.vemrankser.ranqueamento.service;

import br.com.vemrankser.ranqueamento.dto.AtividadeComentarioAvaliacaoCreateDTO;
import br.com.vemrankser.ranqueamento.dto.AtividadeComentarioAvaliacaoDTO;
import br.com.vemrankser.ranqueamento.dto.ComentarioDTO;
import br.com.vemrankser.ranqueamento.entity.AtividadeEntity;
import br.com.vemrankser.ranqueamento.entity.ComentarioEntity;
import br.com.vemrankser.ranqueamento.entity.UsuarioEntity;
import br.com.vemrankser.ranqueamento.enums.TipoFeedback;
import br.com.vemrankser.ranqueamento.exceptions.RegraDeNegocioException;
import br.com.vemrankser.ranqueamento.repository.ComentarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final AtividadeService atividadeService;
    private final UsuarioService usuarioService;

    private final ObjectMapper objectMapper;

//    public ComentarioDTO adicionar(ComentarioCreateDTO comentarioCreateDTO, Integer idAtividade,TipoFeedback tipoFeedback) throws RegraDeNegocioException {
//        AtividadeEntity atividadeEntity = atividadeService.buscarPorIdAtividade(idAtividade);
//
//        ComentarioEntity comentarioEntity = objectMapper.convertValue(comentarioCreateDTO, ComentarioEntity.class);
//        comentarioEntity.setStatusComentario(tipoFeedback.getTipoFeedback());
//        comentarioEntity.setIdAtividade(atividadeEntity.getIdAtividade());
//
//        comentarioEntity.setAtividade(atividadeEntity);
//        comentarioRepository.save(comentarioEntity);
//
//        ComentarioDTO comentarioDTO = objectMapper.convertValue(comentarioEntity, ComentarioDTO.class);
//
//        return comentarioDTO;
//    }

    public AtividadeComentarioAvaliacaoDTO adicionarComentarioAvaliar(AtividadeComentarioAvaliacaoCreateDTO atividadeComentarioAvaliacaoCreateDTO, Integer idAluno, Integer idAtividade, TipoFeedback tipoFeedback) throws RegraDeNegocioException {
        AtividadeEntity atividadeEntity = atividadeService.buscarPorIdAtividade(idAtividade);
        UsuarioEntity usuarioEntity = usuarioService.findById(idAluno);

        ComentarioEntity comentarioEntity = objectMapper.convertValue(atividadeComentarioAvaliacaoCreateDTO, ComentarioEntity.class);
        comentarioEntity.setStatusComentario(tipoFeedback.getTipoFeedback());
        comentarioEntity.setIdAtividade(atividadeEntity.getIdAtividade());
        comentarioEntity.setAtividade(atividadeEntity);
        comentarioEntity.setIdUsuario(usuarioEntity.getIdUsuario());
        comentarioEntity.setUsuario(usuarioEntity);
//        Set<ComentarioEntity> comentarioEntitySet = new HashSet<>();
//        comentarioEntitySet.add(comentarioEntity);
//        atividadeEntity.setComentarios(comentarioEntitySet);
        comentarioEntity.setComentario(comentarioEntity.getComentario());
        atividadeEntity.setPontuacao(atividadeComentarioAvaliacaoCreateDTO.getNotaAvalicao());
        //  atividadeEntity.getAlunos().forEach(aluno -> aluno.setPontuacaoAluno(calcularPontuacao(aluno, atividadeEntity)));
        atividadeEntity.getAlunos().stream()
                .filter(usuarioEntity1 -> usuarioEntity1.getIdUsuario().equals(usuarioEntity.getIdUsuario()))
                .forEach(aluno -> aluno.setPontuacaoAluno(calcularPontuacao(aluno, atividadeEntity)));


//        if (atividadeStatus.equals(AtividadeStatus.CONCLUIDA)) {
//            atividadeEntity.setStatusAtividade(AtividadeStatus.CONCLUIDA);
//        } else {
//            atividadeEntity.setStatusAtividade(AtividadeStatus.PENDENTE);
//        }

        // comentarioEntity.setAtividade(atividadeEntity);
        comentarioEntity.setComentario(atividadeComentarioAvaliacaoCreateDTO.getComentario());
        comentarioRepository.save(comentarioEntity);
        atividadeService.save(atividadeEntity);

        return objectMapper.convertValue(atividadeComentarioAvaliacaoCreateDTO, AtividadeComentarioAvaliacaoDTO.class);
    }

    public List<ComentarioDTO> comentariosDoAluno(Integer idAluno) {
        return comentarioRepository.findAllByIdUsuario(idAluno).stream()
                .map(comentarioEntity -> objectMapper.convertValue(comentarioEntity, ComentarioDTO.class))
                .toList();
    }

    private Integer calcularPontuacao(UsuarioEntity usuarioEntity, AtividadeEntity atividadeEntity) {
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
        ComentarioEntity contatoEntity = objectMapper.convertValue(comentarioRecuperado, ComentarioEntity.class);
        comentarioRepository.delete(contatoEntity);

    }

    public ComentarioEntity findById(Integer id) throws RegraDeNegocioException {
        return comentarioRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("o comentario nao foi encontrado"));
    }
}
