package br.com.vemrankser.ranqueamento.service;

import br.com.vemrankser.ranqueamento.dto.AtividadeCreateDTO;
import br.com.vemrankser.ranqueamento.dto.AtividadeDTO;
import br.com.vemrankser.ranqueamento.dto.AtividadePaginacaoDTO;
import br.com.vemrankser.ranqueamento.entity.AtividadeEntity;
import br.com.vemrankser.ranqueamento.exceptions.RegraDeNegocioException;
import br.com.vemrankser.ranqueamento.repository.AtividadeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public AtividadePaginacaoDTO<AtividadeDTO> listarAtividades(Integer pagina, Integer tamanho) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);

        Page<AtividadeEntity> atividadeEntity = atividadeRepository.findAll(pageRequest);
        List<AtividadeDTO> atividadeDTOList = atividadeEntity.getContent()
                .stream()
                .map(atividade -> {
                    AtividadeDTO atividadeDTO = objectMapper.convertValue(atividade, AtividadeDTO.class);
                    return atividadeDTO;
                })
                .toList();
        return new AtividadePaginacaoDTO<>(atividadeEntity.getTotalElements(), atividadeEntity.getTotalPages(), pagina, tamanho, atividadeDTOList);
    }

    public List<AtividadeDTO> listarTodasAtividades() {
        return atividadeRepository.findAll()
                .stream()
                .map(atividade -> objectMapper.convertValue(atividade, AtividadeDTO.class))
                .toList();
    }

    public AtividadeEntity buscarPorIdAtividade(Integer idAtividade) throws RegraDeNegocioException {
        return atividadeRepository.findById(idAtividade)
                .orElseThrow(() -> new RegraDeNegocioException("Atividade não encontrada."));
    }
}
