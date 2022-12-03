package br.com.vemrankser.ranqueamento.service;

import br.com.vemrankser.ranqueamento.dto.*;
import br.com.vemrankser.ranqueamento.entity.AtividadeEntity;
import br.com.vemrankser.ranqueamento.entity.ModuloEntity;
import br.com.vemrankser.ranqueamento.entity.TrilhaEntity;
import br.com.vemrankser.ranqueamento.entity.UsuarioEntity;
import br.com.vemrankser.ranqueamento.enums.AtividadeStatus;
import br.com.vemrankser.ranqueamento.exceptions.RegraDeNegocioException;
import br.com.vemrankser.ranqueamento.repository.AtividadeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AtividadeService {

    private final AtividadeRepository atividadeRepository;
    private final ModuloService moduloService;
    private final UsuarioService usuarioService;
    private final TrilhaService trilhaService;
    private final ObjectMapper objectMapper;

    private static final Integer INICIAL_PONTUACAO = 0;


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
        if (atividadeDTOList.isEmpty()) {
            throw new RegraDeNegocioException("Não foi encontrada nenhuma atividade");
        }

        return new AtividadePaginacaoDTO<>(atividadeEntity.getTotalElements(), atividadeEntity.getTotalPages(), pagina, tamanho, atividadeDTOList);
    }

    public AtividadeDTO createAtividade(AtividadeCreateDTO atividadeCreateDTO, Integer idModulo, List<Integer> idTrilha) throws RegraDeNegocioException {
        UsuarioLogadoDTO loggedUser = usuarioService.getLoggedUser();
        ModuloEntity moduloEntity = moduloService.buscarPorIdModulo(idModulo);
        List<TrilhaEntity> trilhaEntities = new ArrayList<>();
        AtividadeEntity atividadeEntity = objectMapper.convertValue(atividadeCreateDTO, AtividadeEntity.class);
        atividadeEntity.setNomeInstrutor(loggedUser.getNome());
        atividadeEntity.setPontuacao(INICIAL_PONTUACAO);

        LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
        atividadeEntity.setDataCriacao(now);

        for (Integer number : idTrilha) {
            TrilhaEntity trilhaEntity = trilhaService.findById(number);
            atividadeEntity.getAlunos().addAll(trilhaEntity.getUsuarios());
            trilhaEntities.add(trilhaEntity);

        }
        atividadeEntity.setStatusAtividade(AtividadeStatus.PENDENTE);
        atividadeEntity.setTrilhas(new HashSet<>(trilhaEntities));
        atividadeEntity.setModulo(moduloEntity);

        atividadeRepository.save(atividadeEntity);

        return objectMapper.convertValue(atividadeEntity, AtividadeDTO.class);


    }

    public AtividadeDTO findById(Integer idAtividade) throws RegraDeNegocioException {
        AtividadeEntity atividadeEntity = buscarPorIdAtividade(idAtividade);
        return objectMapper.convertValue(atividadeEntity, AtividadeDTO.class);

    }

    public AtividadeAvaliarDTO avaliarAtividade(AtividadeAvaliarDTO atividadeAvaliarDTO, Integer idAtividade) throws RegraDeNegocioException {
        AtividadeEntity atividadeAvaliacao = buscarPorIdAtividade(idAtividade);
        atividadeAvaliacao.setStatusAtividade(AtividadeStatus.CONCLUIDA);
        atividadeAvaliacao.setPontuacao(atividadeAvaliarDTO.getPontuacao());
        atividadeAvaliacao.getAlunos().forEach(aluno -> aluno.setPontuacaoAluno(calcularPontuacao(aluno, atividadeAvaliacao)));
        atividadeRepository.save(atividadeAvaliacao);


        return objectMapper.convertValue(atividadeAvaliacao, AtividadeAvaliarDTO.class);
    }

    private Integer calcularPontuacao(UsuarioEntity usuarioEntity, AtividadeEntity atividadeEntity) {
        return usuarioEntity.getPontuacaoAluno() + atividadeEntity.getPontuacao();
    }

    public AtividadeDTO colocarAtividadeComoConcluida(Integer idAtividade) throws RegraDeNegocioException {
        AtividadeEntity atividadeEntity = buscarPorIdAtividade(idAtividade);
        atividadeEntity.setStatusAtividade(AtividadeStatus.CONCLUIDA);
        atividadeRepository.save(atividadeEntity);

        return objectMapper.convertValue(atividadeEntity, AtividadeDTO.class);

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
        //  atividadeEntityRecuperado.setLink(atividadeAlunoEnviarDTO.getLink());
        atividadeEntityRecuperado.setStatusAtividade(AtividadeStatus.CONCLUIDA);
        atividadeEntityRecuperado.setNomeInstrutor(atividadeEntity.getNomeInstrutor());

        atividadeRepository.save(atividadeEntityRecuperado);

        return objectMapper.convertValue(atividadeEntityRecuperado, AtividadeAlunoEnviarDTO.class);
    }

    public PageDTO<AtividadeTrilhaDTO> listarAtividadePorStatus(Integer pagina, Integer tamanho, Integer idTrilha, AtividadeStatus atividadeStatus) throws RegraDeNegocioException {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<AtividadeTrilhaDTO> atividadeTrilha = atividadeRepository.listarAtividadePorStatus(pageRequest, idTrilha, atividadeStatus);

        List<AtividadeTrilhaDTO> atividadeTrilhaDTOS = atividadeTrilha.getContent()
                .stream()
                .map(atividadeTrilhaDTO -> {
                    objectMapper.convertValue(atividadeTrilhaDTO, AtividadeTrilhaDTO.class);
                    return atividadeTrilhaDTO;
                })
                .toList();
        if (atividadeTrilhaDTOS.isEmpty()) {
            throw new RegraDeNegocioException("Atividade não cadastrada");
        }

        return new PageDTO<>(atividadeTrilha.getTotalElements(),
                atividadeTrilha.getTotalPages(),
                pagina,
                tamanho,
                atividadeTrilhaDTOS);
    }

    public PageDTO<AtividadeMuralDTO> listarAtividadeMuralInstrutor(Integer pagina, Integer tamanho, Integer idTrilha) throws RegraDeNegocioException {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<AtividadeMuralDTO> atividadeEntity = atividadeRepository.listarAtividadeMuralInstrutor(pageRequest, idTrilha);

        List<AtividadeMuralDTO> atividadeMuralDTOList = atividadeEntity.getContent()
                .stream()
                .map(atividade -> objectMapper.convertValue(atividade, AtividadeMuralDTO.class))
                .toList();

        if (atividadeMuralDTOList.isEmpty()) {
            throw new RegraDeNegocioException("Sem atividades no mural do instrutor");
        }

        return new PageDTO<>(atividadeEntity.getTotalElements(),
                atividadeEntity.getTotalPages(),
                pagina,
                tamanho,
                atividadeMuralDTOList);
    }

    public PageDTO<AtividadeMuralAlunoDTO> listarAtividadeMuralAluno(Integer pagina, Integer tamanho, Integer idAluno, AtividadeStatus atividadeStatus) throws RegraDeNegocioException {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<AtividadeMuralAlunoDTO> atividadeEntity = atividadeRepository.listarAtividadeMuralAluno(idAluno, atividadeStatus, pageRequest);

        List<AtividadeMuralAlunoDTO> atividadeMuralDTOList = atividadeEntity.getContent()
                .stream()
                .map(atividade -> objectMapper.convertValue(atividade, AtividadeMuralAlunoDTO.class))
                .toList();

        if (atividadeMuralDTOList.isEmpty()) {
            throw new RegraDeNegocioException("Sem atividades no mural do aluno");
        }

        return new PageDTO<>(atividadeEntity.getTotalElements(),
                atividadeEntity.getTotalPages(),
                pagina,
                tamanho,
                atividadeMuralDTOList);
    }
    // ESSE PAGINADO
//    public List<AtividadeMuralAlunoDTO> listarAtividadeMuralAluno(AtividadeStatus atividadeStatus, Integer idUsuario){
//        return atividadeRepository.listarAtividadeMuralAluno(idUsuario, atividadeStatus);
//    }

    public PageDTO<AtividadeNotaDTO> listarAtividadePorIdTrilhaIdModulo(Integer pagina, Integer tamanho, Integer idTrilha, Integer idModulo, AtividadeStatus atividadeStatus) throws RegraDeNegocioException {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<AtividadeNotaDTO> atividadeEntity = atividadeRepository.listarAtividadePorIdTrilhaIdModulo(pageRequest, idTrilha, idModulo, atividadeStatus);

        List<AtividadeNotaDTO> atividadeNotaDTOList = atividadeEntity.getContent()
                .stream()
                .map(atividade -> {
                    AtividadeNotaDTO atividadeNotaDTO1 = objectMapper.convertValue(atividade, AtividadeNotaDTO.class);
                    return atividadeNotaDTO1;
                })
                .toList();

        if (atividadeNotaDTOList.isEmpty()) {
            throw new RegraDeNegocioException("Sem cadastro de notas.");
        }

        return new PageDTO<>(atividadeEntity.getTotalElements(),
                atividadeEntity.getTotalPages(),
                pagina,
                tamanho,
                atividadeNotaDTOList);
    }

    public AtividadeEntity buscarPorIdAtividade(Integer idAtividade) throws RegraDeNegocioException {
        return atividadeRepository.findById(idAtividade)
                .orElseThrow(() -> new RegraDeNegocioException("Atividade não encontrada."));
    }

    public AtividadeDTO save(AtividadeEntity atividadeEntity) {
        return objectMapper.convertValue(atividadeRepository.save(atividadeEntity), AtividadeDTO.class);
    }

}
