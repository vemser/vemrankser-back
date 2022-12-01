package br.com.vemrankser.ranqueamento.service;

import br.com.vemrankser.ranqueamento.dto.*;
import br.com.vemrankser.ranqueamento.entity.TrilhaEntity;
import br.com.vemrankser.ranqueamento.entity.UsuarioEntity;
import br.com.vemrankser.ranqueamento.exceptions.RegraDeNegocioException;
import br.com.vemrankser.ranqueamento.repository.TrilhaRepository;
import br.com.vemrankser.ranqueamento.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class TrilhaService {

    private final TrilhaRepository trilhaRepository;
    private final UsuarioService usuarioService;


    private final ObjectMapper objectMapper;


    public TrilhaDTO adicionar(TrilhaCreateDTO trilhaNova) throws RegraDeNegocioException {
        TrilhaEntity trilha = objectMapper.convertValue(trilhaNova, TrilhaEntity.class);
        trilhaRepository.save(trilha);
        return objectMapper.convertValue(trilha, TrilhaDTO.class);
    }

    //    public TrilhaDTO adicionarAlunoTrilha(Integer idTrilha, Integer idAluno) throws RegraDeNegocioException {
//        UsuarioEntity alunoEncontrado = usuarioService.findById(idAluno);
//        TrilhaEntity trilhaEntity = buscarPorIdTrilha(idTrilha);
//        trilhaEntity.getUsuarios().add(alunoEncontrado);
//        trilhaRepository.save(trilhaEntity);
//        return objectMapper.convertValue(trilhaEntity, TrilhaDTO.class);
//    }


    public void adicionarAlunoTrilha(String login, List<Integer> listTrilha, LoginTrilhaDTO loginTrilhaDTO) throws RegraDeNegocioException {
        for (Integer number : listTrilha) {
            UsuarioDTO alunoDTO = usuarioService.pegarLogin(login);
            UsuarioEntity alunoEncontrado = objectMapper.convertValue(alunoDTO, UsuarioEntity.class);

            Set<UsuarioEntity> usuarioEntities = new HashSet<>();
            usuarioEntities.add(alunoEncontrado);
            TrilhaEntity trilhaEntity = findById(number);


            trilhaEntity.setUsuarios(new HashSet<>(usuarioEntities));

            trilhaEntity.getUsuarios().add(alunoEncontrado);
            trilhaRepository.save(trilhaEntity);
        }
    }

    public void adicionarIntrustorTrilha(String login, List<Integer> listTrilha, LoginTrilhaDTO loginTrilhaDTO) throws RegraDeNegocioException {
        for (Integer number : listTrilha) {
            UsuarioDTO alunoDTO = usuarioService.pegarLoginInstrutor(login);
            UsuarioEntity alunoEncontrado = objectMapper.convertValue(alunoDTO, UsuarioEntity.class);

            Set<UsuarioEntity> usuarioEntities = new HashSet<>();
            usuarioEntities.add(alunoEncontrado);
            TrilhaEntity trilhaEntity = findById(number);


            trilhaEntity.setUsuarios(new HashSet<>(usuarioEntities));

            trilhaEntity.getUsuarios().add(alunoEncontrado);
            trilhaRepository.save(trilhaEntity);
        }
    }
//  //
//    public TrilhaDTO adicionarAlunoTrilha2(List<Integer> idTrilha, Integer edicao, String login) throws RegraDeNegocioException {
//        UsuarioDTO alunoDTO = usuarioService.pegarLogin(login);
//        UsuarioEntity alunoEncontrado = objectMapper.convertValue(alunoDTO, UsuarioEntity.class);
//        List<TrilhaEntity> trilhaEntities = new ArrayList<>();
//        TrilhaEntity trilhaEntity = buscarPorNomeTrilha(nomeTrilha);
//        if (!Objects.equals(trilhaEntity.getEdicao(), edicao)) {
//            throw new RegraDeNegocioException("Esta edição de trilha não existe!");
//        }
//        for (Integer number : idTrilha) {
//            TrilhaEntity trilhaEntity = findById(number);
//            trilhaEntities.add(trilhaEntity);
//            trilhaEntity.getUsuarios().add(alunoEncontrado);
//            tr
//        }
//
//        trilhaRepository.save(trilhaEntity);
//        return objectMapper.convertValue(trilhaEntity, TrilhaDTO.class);
//    }

//    public TrilhaDTO adicionarIntrustorTrilha(String nomeTrilha, Integer edicao, String login) throws RegraDeNegocioException {
//        UsuarioDTO instrutorDTO = usuarioService.pegarLoginInstrutor(login);
//        UsuarioEntity instrutorEncontrado = objectMapper.convertValue(instrutorDTO, UsuarioEntity.class);
//        TrilhaEntity trilhaEntity = buscarPorNomeTrilha(nomeTrilha);
//        if (!Objects.equals(trilhaEntity.getEdicao(), edicao)) {
//            throw new RegraDeNegocioException("Esta edição de trilha não existe!");
//        }
//        trilhaEntity.getUsuarios().add(instrutorEncontrado);
//        trilhaRepository.save(trilhaEntity);
//        return objectMapper.convertValue(trilhaEntity, TrilhaDTO.class);
//    }

    public PageDTO<TrilhaPaginadoDTO> listarUsuariosNaTrilha(Integer pagina, Integer tamanho, Integer idTrilha) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<TrilhaEntity> allAlunos = trilhaRepository.findAllByIdTrilha(idTrilha, pageRequest);

        List<TrilhaPaginadoDTO> trilhaDTOS = allAlunos.getContent().stream()
                .map(trilha -> {
                    TrilhaPaginadoDTO trilhaDTO = objectMapper.convertValue(trilha, TrilhaPaginadoDTO.class);
                    trilhaDTO.setUsuarios(trilha.getUsuarios().stream()
                            .map(usuarioDTO -> objectMapper.convertValue(usuarioDTO, UsuarioDTO.class)).toList());

                    return trilhaDTO;
                }).toList();

        return new PageDTO<>(allAlunos.getTotalElements(),
                allAlunos.getTotalPages(),
                pagina,
                tamanho,
                trilhaDTOS);
    }



//    private List<TrilhaDTO> listarTrilhaq() {
//        return trilhaRepository.findAll()
//                .stream()
//                .map(trilha -> objectMapper.convertValue(trilha, TrilhaDTO.class))
//                .toList();
//    }

    //    public List<TrilhaDTO> listarTrilha2() {
//        return trilhaRepository.findAll()
//                .stream()
//                .map(trilha -> {
//                    TrilhaDTO trilhaDTO = objectMapper.convertValue(trilha, TrilhaDTO.class);
//                    trilhaDTO.setUsuarios(trilha.getUsuarios().stream()
//                            .map(usuarioDTO -> objectMapper.convertValue(usuarioDTO,UsuarioDTO.class)).toList());
//
//                    return trilhaDTO;
//                }).toList();
//    }
//    public List<TrilhaDTO> listarTrilha2() {
//        return trilhaRepository.findAll()
//                .stream()
//                .map(trilha -> {
//                    TrilhaDTO trilhaDTO = objectMapper.convertValue(trilha, TrilhaDTO.class);
//                    trilhaDTO.getUsuarios().stream()
//                            .map(usuarioDTO -> objectMapper.convertValue(usuarioDTO,TrilhaDTO.class))
//                            .toList();
//                    return trilhaDTO;
//                }).toList();
//    }
    public TrilhaDTO pegarIdTrilha(Integer id) throws RegraDeNegocioException {
        TrilhaEntity trilhaEntity = findById(id);
        return objectMapper.convertValue(trilhaEntity, TrilhaDTO.class);
    }

    public TrilhaEntity findById(Integer idTrilha) throws RegraDeNegocioException {
        return trilhaRepository.findById(idTrilha)
                .orElseThrow(() -> new RegraDeNegocioException("Trilha não encontrada."));
    }

    public TrilhaEntity buscarPorNomeTrilha(String nomeTrilha) throws RegraDeNegocioException {
        return trilhaRepository.findByNomeIgnoreCase(nomeTrilha.trim().replaceAll("\\s+", " "))
                .orElseThrow(() -> new RegraDeNegocioException("Nome da trilha não encontrada."));
    }

    public List<TrilhaDTO> findTrilhaByNome(String nomeTrilha) {
        if (nomeTrilha != null) {
            return trilhaRepository.findAllByNomeContainingIgnoreCase(nomeTrilha.trim().replaceAll("\\s+", " "))
                    .stream()
                    .map(trilha -> objectMapper.convertValue(trilha, TrilhaDTO.class))
                    .toList();
        }
        return trilhaRepository.findAll().stream()
                .map(trilhaEntity -> objectMapper.convertValue(trilhaEntity, TrilhaDTO.class))
                .toList();
    }

    public List<TrilhaDTO> findAllEdicao(Integer edicao) {
        if (edicao != null) {
            return trilhaRepository.findAllByEdicao(edicao)
                    .stream()
                    .map(trilha -> objectMapper.convertValue(trilha, TrilhaDTO.class))
                    .toList();
        }
        return trilhaRepository.findAll().stream()
                .map(trilhaEntity -> objectMapper.convertValue(trilhaEntity, TrilhaDTO.class))
                .toList();
    }

    public TrilhaEntity buscarTrilhaPorEdicao(Integer edicao) throws RegraDeNegocioException {
        return trilhaRepository.findByEdicao(edicao)
                .orElseThrow(() -> new RegraDeNegocioException("Edição da trilha não encontrada."));
    }


    public List<RankingDTO> rankingtrilha(Integer idTrilha) throws RegraDeNegocioException {
        TrilhaEntity trilha = findById(idTrilha);
        List<RankingDTO> ListUsuarios = trilha.getUsuarios()
                .stream()
                .sorted(Comparator.comparing(UsuarioEntity::getPontuacaoAluno)
                        .reversed())
                .limit(5L)
                .map(this::mapRankingDTO)
                .collect(Collectors
                        .toList());
        return ListUsuarios;
    }

    private RankingDTO mapRankingDTO(UsuarioEntity usuarioEntity) {
        return new RankingDTO(usuarioEntity.getNome(), usuarioEntity.getPontuacaoAluno());
    }
}
