package br.com.vemrankser.ranqueamento.service;

import br.com.vemrankser.ranqueamento.dto.TrilhaCreateDTO;
import br.com.vemrankser.ranqueamento.dto.TrilhaDTO;
import br.com.vemrankser.ranqueamento.entity.TrilhaEntity;
import br.com.vemrankser.ranqueamento.exceptions.RegraDeNegocioException;
import br.com.vemrankser.ranqueamento.repository.TrilhaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class TrilhaService {

    private final TrilhaRepository trilhaRepository;

    private final ObjectMapper objectMapper;

    public TrilhaDTO adiocionar(TrilhaCreateDTO trilhaNova) throws RegraDeNegocioException {
        TrilhaEntity trilha = objectMapper.convertValue(trilhaNova, TrilhaEntity.class);
        trilhaRepository.save(trilha);
        TrilhaDTO trilhaDTO = objectMapper.convertValue(trilha, TrilhaDTO.class);
        return trilhaDTO;

    }

    private List<TrilhaDTO> listarTrilha() {
        return trilhaRepository.findAll()
                .stream()
                .map(trilha -> objectMapper.convertValue(trilha, TrilhaDTO.class))
                .toList();
    }

    public TrilhaEntity buscarPorIdTrilha(Integer idTrilha) throws RegraDeNegocioException {
        return trilhaRepository.findById(idTrilha)
                .orElseThrow(() -> new RegraDeNegocioException("Trilha não encontrada."));
    }


}
