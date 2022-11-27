package br.com.vemrankser.ranqueamento.service;

import br.com.vemrankser.ranqueamento.dto.AtividadeAvaliarDTO;
import br.com.vemrankser.ranqueamento.dto.AtividadeCreateDTO;
import br.com.vemrankser.ranqueamento.dto.AtividadeDTO;
import br.com.vemrankser.ranqueamento.dto.AtividadePaginacaoDTO;
import br.com.vemrankser.ranqueamento.entity.AtividadeEntity;
import br.com.vemrankser.ranqueamento.entity.ModuloEntity;
import br.com.vemrankser.ranqueamento.enums.AtividadeStatus;
import br.com.vemrankser.ranqueamento.exceptions.RegraDeNegocioException;
import br.com.vemrankser.ranqueamento.repository.AtividadeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AtividadeService {

    private final AtividadeRepository atividadeRepository;
    private final ModuloService moduloService;
    private final ObjectMapper objectMapper;


    public AtividadeDTO adicionar(AtividadeCreateDTO atividadeCreateDTO, Integer idModulo) throws RegraDeNegocioException {
        ModuloEntity moduloEntity = moduloService.buscarPorIdModulo(idModulo);
        AtividadeEntity atividadeEntity = objectMapper.convertValue(atividadeCreateDTO, AtividadeEntity.class);

        atividadeEntity.setStatusAtividade(AtividadeStatus.PENDENTE.getAtividadeStatus());
        atividadeEntity.setIdModulo(moduloEntity.getIdModulo());
        atividadeEntity.setModulo(moduloEntity);
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

    public AtividadeAvaliarDTO avaliarAtividade(AtividadeAvaliarDTO atividadeAvaliarDTO, Integer idAtividade) throws RegraDeNegocioException {
        AtividadeEntity atividadeAvaliacao = buscarPorIdAtividade(idAtividade);

        atividadeAvaliacao.setStatusAtividade(AtividadeStatus.CONCLUIDA.getAtividadeStatus());
        atividadeAvaliacao.setPontuacao(atividadeAvaliarDTO.getPontuacao());
        atividadeRepository.save(atividadeAvaliacao);

        return objectMapper.convertValue(atividadeAvaliacao, AtividadeAvaliarDTO.class);
    }

    public List<Optional<AtividadeEntity>> buscarAtividadePorStatus(Integer atividadeStatus) {
        return atividadeRepository.findByStatusAtividade(atividadeStatus);
    }

    public AtividadeEntity buscarPorIdAtividade(Integer idAtividade) throws RegraDeNegocioException {
        return atividadeRepository.findById(idAtividade)
                .orElseThrow(() -> new RegraDeNegocioException("Atividade não encontrada."));
    }


}
