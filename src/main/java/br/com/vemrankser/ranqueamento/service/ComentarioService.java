package br.com.vemrankser.ranqueamento.service;

import br.com.vemrankser.ranqueamento.dto.*;
import br.com.vemrankser.ranqueamento.entity.AtividadeEntity;
import br.com.vemrankser.ranqueamento.entity.ComentarioEntity;
import br.com.vemrankser.ranqueamento.entity.UsuarioEntity;
import br.com.vemrankser.ranqueamento.enums.AtividadeStatus;
import br.com.vemrankser.ranqueamento.exceptions.RegraDeNegocioException;
import br.com.vemrankser.ranqueamento.repository.ComentarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final AtividadeService atividadeService;

    private final ObjectMapper objectMapper;

    public ComentarioDTO adicionar(ComentarioCreateDTO comentarioCreateDTO, Integer idAtividade) throws RegraDeNegocioException {
        AtividadeEntity atividadeEntity = atividadeService.buscarPorIdAtividade(idAtividade);

        ComentarioEntity comentarioEntity = objectMapper.convertValue(comentarioCreateDTO, ComentarioEntity.class);

        comentarioEntity.setIdAtividade(atividadeEntity.getIdAtividade());

        comentarioEntity.setAtividade(atividadeEntity);
        comentarioRepository.save(comentarioEntity);

        ComentarioDTO comentarioDTO = objectMapper.convertValue(comentarioEntity, ComentarioDTO.class);

        return comentarioDTO;
    }

    public AtividadeComentarioAvaliacaoDTO adicionarComentarioAvaliar(AtividadeComentarioAvaliacaoDTO atividadeComentarioAvaliacaoDTO, Integer idAtividade, AtividadeStatus atividadeStatus) throws RegraDeNegocioException {
        AtividadeEntity atividadeEntity = atividadeService.buscarPorIdAtividade(idAtividade);

        ComentarioEntity comentarioEntity = objectMapper.convertValue(atividadeComentarioAvaliacaoDTO, ComentarioEntity.class);
        comentarioEntity.setIdAtividade(atividadeEntity.getIdAtividade());
        Set<ComentarioEntity> comentarioEntitySet = new HashSet<>();
        comentarioEntitySet.add(comentarioEntity);
        atividadeEntity.setComentarios(comentarioEntitySet);
        atividadeEntity.setPontuacao(atividadeComentarioAvaliacaoDTO.getNotaAvalicao());
        atividadeEntity.getAlunos().forEach(aluno -> aluno.setPontuacaoAluno(calcularPontuacao(aluno, atividadeEntity)));

        if(atividadeStatus.equals(AtividadeStatus.CONCLUIDA)) {
            atividadeEntity.setStatusAtividade(AtividadeStatus.CONCLUIDA);
        } else  {
            atividadeEntity.setStatusAtividade(AtividadeStatus.PENDENTE);
        }

        comentarioEntity.setAtividade(atividadeEntity);
        comentarioEntity.setComentario(atividadeComentarioAvaliacaoDTO.getComentario());
        comentarioRepository.save(comentarioEntity);
        atividadeService.save(atividadeEntity);

        return atividadeComentarioAvaliacaoDTO;
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
