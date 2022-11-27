package br.com.vemrankser.ranqueamento.service;

import br.com.vemrankser.ranqueamento.dto.ComentarioCreateDTO;
import br.com.vemrankser.ranqueamento.dto.ComentarioDTO;
import br.com.vemrankser.ranqueamento.entity.AtividadeEntity;
import br.com.vemrankser.ranqueamento.entity.ComentarioEntity;
import br.com.vemrankser.ranqueamento.exceptions.RegraDeNegocioException;
import br.com.vemrankser.ranqueamento.repository.ComentarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;

    private final ObjectMapper objectMapper;

    public ComentarioDTO adicionar(ComentarioCreateDTO comentarioCreateDTO) throws RegraDeNegocioException {
        ComentarioEntity comentarioEntity = objectMapper.convertValue(comentarioCreateDTO, ComentarioEntity.class);

        AtividadeEntity atividadeEntity = new AtividadeEntity();
        atividadeEntity.setIdAtividade(atividadeEntity.getIdAtividade());
        comentarioRepository.save(comentarioEntity);

        ComentarioDTO comentarioDTO = objectMapper.convertValue(comentarioEntity, ComentarioDTO.class);

        return comentarioDTO;
    }

    public ComentarioEntity buscarPorIdComentario(Integer idComentario) throws RegraDeNegocioException{
        return  comentarioRepository.findById(idComentario)
                .orElseThrow(() -> new RegraDeNegocioException("Comentario não encontrado"));
    }

}
