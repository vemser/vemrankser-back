package br.com.vemrankser.ranqueamento.service;

import br.com.vemrankser.ranqueamento.entity.CargoEntity;
import br.com.vemrankser.ranqueamento.exceptions.RegraDeNegocioException;
import br.com.vemrankser.ranqueamento.repository.CargoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CargoService {
    private final CargoRepository cargoRepository;
    private final ObjectMapper objectMapper;

    public CargoEntity findById(Integer id) throws RegraDeNegocioException {
        return cargoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Cargo não encontrado"));
    }
}
