package br.com.vemrankser.ranqueamento.service;

import br.com.vemrankser.ranqueamento.dto.ComentarioCreateDTO;
import br.com.vemrankser.ranqueamento.dto.ComentarioDTO;
import br.com.vemrankser.ranqueamento.entity.AtividadeEntity;
import br.com.vemrankser.ranqueamento.entity.ComentarioEntity;
import br.com.vemrankser.ranqueamento.entity.UsuarioEntity;
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
        Set<ComentarioEntity> comentarioEntitySet = new HashSet<>();
        comentarioEntitySet.add(comentarioEntity);
        atividadeEntity.setComentarios(comentarioEntitySet);
        atividadeService.save(atividadeEntity);

        comentarioEntity.setAtividade(atividadeEntity);
        comentarioRepository.save(comentarioEntity);

        return objectMapper.convertValue(comentarioEntity, ComentarioDTO.class);
    }

    //    public AtividadeAvaliarDTO avaliarAtividade(AtividadeAvaliarDTO atividadeAvaliarDTO, Integer idAtividade) throws RegraDeNegocioException {
//        AtividadeEntity atividadeAvaliacao = buscarPorIdAtividade(idAtividade);
//        atividadeAvaliacao.setStatusAtividade(AtividadeStatus.CONCLUIDA);
//        atividadeAvaliacao.setPontuacao(atividadeAvaliarDTO.getPontuacao());
//        atividadeAvaliacao.getAlunos().forEach(aluno -> aluno.setPontuacaoAluno(calcularPontuacao(aluno, atividadeAvaliacao)));
//        atividadeRepository.save(atividadeAvaliacao);
//
//
//        return objectMapper.convertValue(atividadeAvaliacao, AtividadeAvaliarDTO.class);
//    }
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
