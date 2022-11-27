package br.com.vemrankser.ranqueamento.service;

import br.com.vemrankser.ranqueamento.dto.ModuloCreateDTO;
import br.com.vemrankser.ranqueamento.dto.ModuloDTO;
import br.com.vemrankser.ranqueamento.entity.ModuloEntity;
import br.com.vemrankser.ranqueamento.entity.TrilhaEntity;
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

    private final TrilhaService trilhaService;
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

    public ModuloDTO vincularModuloTrilha(ModuloCreateDTO modulo, Integer id) throws RegraDeNegocioException {
        ModuloEntity moduloEntity = objectMapper.convertValue(modulo, ModuloEntity.class);
        moduloEntity.setIdModulo(moduloEntity.getIdModulo());
        modulo.getTrilhas().forEach(moduloTrilha -> {
            TrilhaEntity trilha = null;
            try {
                trilha = trilhaService.buscarPorIdTrilha(moduloTrilha.getIdTrilha());
                moduloEntity.getTrilhas().add(trilha);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        moduloEntity.setStatusModulo(StatusModulo.S);
        moduloRepository.save(moduloEntity);
        return objectMapper.convertValue(moduloEntity, ModuloDTO.class);
    }
}
