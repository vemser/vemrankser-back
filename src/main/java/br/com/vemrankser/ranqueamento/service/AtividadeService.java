package br.com.vemrankser.ranqueamento.service;

import br.com.vemrankser.ranqueamento.dto.AtividadeCreateDTO;
import br.com.vemrankser.ranqueamento.dto.AtividadeDTO;
import br.com.vemrankser.ranqueamento.entity.AtividadeEntity;
import br.com.vemrankser.ranqueamento.exceptions.RegraDeNegocioException;
import br.com.vemrankser.ranqueamento.repository.AtividadeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AtividadeService {

    private final AtividadeRepository atividadeRepository;

    private final ObjectMapper objectMapper;


    public AtividadeDTO adicionar(AtividadeCreateDTO atividadeCreateDTO) throws RegraDeNegocioException {
        AtividadeEntity atividadeEntity = objectMapper.convertValue(atividadeCreateDTO, AtividadeEntity.class);
//        atividadeEntity.setUsuario();
        atividadeRepository.save(atividadeEntity);

        return objectMapper.convertValue(atividadeEntity, AtividadeDTO.class);
    }

    public List<AtividadeDTO> listarAtividades() {
        return atividadeRepository.findAll()
                .stream()
                .map(atividade -> objectMapper.convertValue(atividade, AtividadeDTO.class))
                .toList();
    }

}
