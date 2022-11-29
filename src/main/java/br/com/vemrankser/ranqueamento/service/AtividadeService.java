package br.com.vemrankser.ranqueamento.service;

import br.com.vemrankser.ranqueamento.dto.*;
import br.com.vemrankser.ranqueamento.entity.AtividadeEntity;
import br.com.vemrankser.ranqueamento.entity.ModuloEntity;
import br.com.vemrankser.ranqueamento.entity.TrilhaEntity;
import br.com.vemrankser.ranqueamento.entity.UsuarioEntity;
import br.com.vemrankser.ranqueamento.enums.AtividadeStatus;
import br.com.vemrankser.ranqueamento.exceptions.RegraDeNegocioException;
import br.com.vemrankser.ranqueamento.repository.AtividadeRepository;
import br.com.vemrankser.ranqueamento.repository.ModuloRepository;
import br.com.vemrankser.ranqueamento.repository.TrilhaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AtividadeService {

    private final AtividadeRepository atividadeRepository;
    private final TrilhaRepository trilhaRepository;
    private final ModuloRepository moduloRepository;
    private final ModuloService moduloService;
    private final UsuarioService usuarioService;
    private final TrilhaService trilhaService;
    private final ObjectMapper objectMapper;


//    public AtividadeDTO adicionar(AtividadeCreateDTO atividadeCreateDTO, Integer idModulo, Integer idTrilha, String login) throws RegraDeNegocioException {
//        UsuarioDTO alunoDTO = usuarioService.pegarLogin(login);
//        UsuarioEntity alunoEncontrado = objectMapper.convertValue(alunoDTO, UsuarioEntity.class);
//        ModuloEntity moduloEntity = moduloService.buscarPorIdModulo(idModulo);
//
//        return null;
//
//    }

    public AtividadePaginacaoDTO<AtividadeDTO> listarAtividades(Integer pagina, Integer tamanho) throws RegraDeNegocioException {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);

        Page<AtividadeEntity> atividadeEntity = atividadeRepository.findAll(pageRequest);
        List<AtividadeDTO> atividadeDTOList = atividadeEntity.getContent()
                .stream()
                .map(atividade -> {
                    AtividadeDTO atividadeDTO = objectMapper.convertValue(atividade, AtividadeDTO.class);
                    return atividadeDTO;
                })
                .toList();
        if(atividadeDTOList.isEmpty()) {
            throw new RegraDeNegocioException("Não foi encontrada nenhuma atividade");
        }

        return new AtividadePaginacaoDTO<>(atividadeEntity.getTotalElements(), atividadeEntity.getTotalPages(), pagina, tamanho, atividadeDTOList);
    }

    public AtividadeAvaliarDTO avaliarAtividade(AtividadeAvaliarDTO atividadeAvaliarDTO, Integer idAtividade) throws RegraDeNegocioException {
        AtividadeEntity atividadeAvaliacao = buscarPorIdAtividade(idAtividade);

        atividadeAvaliacao.setStatusAtividade(AtividadeStatus.CONCLUIDA.getAtividadeStatus());
        atividadeAvaliacao.setPontuacao(atividadeAvaliarDTO.getPontuacao());
        atividadeRepository.save(atividadeAvaliacao);

        return objectMapper.convertValue(atividadeAvaliacao, AtividadeAvaliarDTO.class);
    }

    public List<AtividadeEntity> buscarAtividadePorStatus(AtividadeStatus atividadeStatus) throws RegraDeNegocioException {

        List<AtividadeEntity> atividadeEntity = atividadeRepository.findByStatusAtividade(atividadeStatus.getAtividadeStatus())
                .stream()
                .toList();
        if (atividadeEntity.isEmpty()) {
            throw new RegraDeNegocioException("Atividade não cadastrada");
        }

        return atividadeEntity;
    }

    public PageDTO<AtividadeMuralDTO> listarAtividadeMural(Integer pagina, Integer tamanho) throws RegraDeNegocioException {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<AtividadeMuralDTO> atividadeEntity = atividadeRepository.listarAtividadeMural(pageRequest);

        List<AtividadeMuralDTO> atividadeMuralDTOList = atividadeEntity.getContent()
                .stream()
                .map(atividade -> {
                    AtividadeMuralDTO atividadeMuralDTO1 = objectMapper.convertValue(atividade, AtividadeMuralDTO.class);
                    return atividadeMuralDTO1;
                })
                .toList();

        if(atividadeMuralDTOList.isEmpty()) {
            throw new RegraDeNegocioException("Sem atividades no mural.");
        }

        return new PageDTO<>(atividadeEntity.getTotalElements(),
                atividadeEntity.getTotalPages(),
                pagina,
                tamanho,
                atividadeMuralDTOList);
    }

    public PageDTO<AtividadeNotaDTO> listarAtividadePorNota(Integer pagina, Integer tamanho) throws RegraDeNegocioException {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<AtividadeNotaDTO> atividadeEntity = atividadeRepository.listarAtividadePorNota(pageRequest);

        List<AtividadeNotaDTO> atividadeNotaDTOList = atividadeEntity.getContent()
                .stream()
                .map(atividade -> {
                    AtividadeNotaDTO atividadeNotaDTO1 = objectMapper.convertValue(atividade, AtividadeNotaDTO.class);
                    return atividadeNotaDTO1;
                })
                .toList();

        if(atividadeNotaDTOList.isEmpty()) {
            throw new RegraDeNegocioException("Sem cadastro de notas.");
        }

        return new PageDTO<>(atividadeEntity.getTotalElements(),
                atividadeEntity.getTotalPages(),
                pagina,
                tamanho,
                atividadeNotaDTOList);
    }

    public AtividadeAlunoEnviarDTO entregarAtividade(AtividadeAlunoEnviarDTO atividadeAlunoEnviarDTO, Integer idAtividade) throws RegraDeNegocioException {
        AtividadeEntity atividadeEntity = buscarPorIdAtividade(idAtividade);
        AtividadeEntity atividadeEntityRecuperado = objectMapper.convertValue(atividadeAlunoEnviarDTO, AtividadeEntity.class);

        atividadeEntityRecuperado.setIdAtividade(idAtividade);
        atividadeEntityRecuperado.setIdModulo(atividadeEntity.getIdModulo());
        atividadeEntityRecuperado.setTitulo(atividadeEntity.getTitulo());
        atividadeEntityRecuperado.setInstrucoes(atividadeEntity.getInstrucoes());
        atividadeEntityRecuperado.setPesoAtividade(atividadeEntity.getPesoAtividade());
        atividadeEntityRecuperado.setDataCriacao(atividadeEntity.getDataCriacao());
        atividadeEntityRecuperado.setDataEntrega(atividadeEntity.getDataEntrega());
        atividadeEntityRecuperado.setPontuacao(atividadeEntity.getPontuacao());
        atividadeEntityRecuperado.setLink(atividadeAlunoEnviarDTO.getLink());
        atividadeEntityRecuperado.setStatusAtividade(AtividadeStatus.CONCLUIDA.getAtividadeStatus());
        atividadeEntityRecuperado.setNomeInstrutor(atividadeEntity.getNomeInstrutor());

        atividadeRepository.save(atividadeEntityRecuperado);

        return objectMapper.convertValue(atividadeEntityRecuperado, AtividadeAlunoEnviarDTO.class);
    }

    public AtividadeEntity buscarPorIdAtividade(Integer idAtividade) throws RegraDeNegocioException {
        return atividadeRepository.findById(idAtividade)
                .orElseThrow(() -> new RegraDeNegocioException("Atividade não encontrada."));
    }

}
