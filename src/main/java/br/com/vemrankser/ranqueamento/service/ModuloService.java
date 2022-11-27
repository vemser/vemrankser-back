package br.com.vemrankser.ranqueamento.service;

import br.com.vemrankser.ranqueamento.dto.ModuloCreateDTO;
import br.com.vemrankser.ranqueamento.dto.ModuloDTO;
import br.com.vemrankser.ranqueamento.entity.ModuloEntity;
import br.com.vemrankser.ranqueamento.enums.StatusModulo;
import br.com.vemrankser.ranqueamento.exceptions.RegraDeNegocioException;
import br.com.vemrankser.ranqueamento.repository.ModuloRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ModuloService {

    private final ModuloRepository moduloRepository;
    private final ObjectMapper objectMapper;


    public ModuloDTO adicionar(ModuloCreateDTO modulo) {
        ModuloEntity moduloEntityNovo = objectMapper.convertValue(modulo, ModuloEntity.class);
        moduloEntityNovo.setStatusModulo(StatusModulo.S);
        moduloRepository.save(moduloEntityNovo);
        ModuloDTO moduloDTO = objectMapper.convertValue(moduloEntityNovo, ModuloDTO.class);
        return moduloDTO;

    }

    public ModuloEntity buscarPorIdModulo(Integer idModulo) throws RegraDeNegocioException {
        return moduloRepository.findById(idModulo)
                .orElseThrow(() -> new RegraDeNegocioException("Modulo não encontrado."));
    }

    private List<ModuloDTO> listarModulo() {
        return moduloRepository.findAll()
                .stream()
                .map(modulo -> objectMapper.convertValue(modulo, ModuloDTO.class))
                .toList();
    }
}
