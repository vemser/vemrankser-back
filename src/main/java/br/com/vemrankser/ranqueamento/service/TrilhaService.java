package br.com.vemrankser.ranqueamento.service;

import br.com.vemrankser.ranqueamento.dto.PageDTO;
import br.com.vemrankser.ranqueamento.dto.TrilhaCreateDTO;
import br.com.vemrankser.ranqueamento.dto.TrilhaDTO;
import br.com.vemrankser.ranqueamento.entity.TrilhaEntity;
import br.com.vemrankser.ranqueamento.entity.UsuarioEntity;
import br.com.vemrankser.ranqueamento.exceptions.RegraDeNegocioException;
import br.com.vemrankser.ranqueamento.repository.TrilhaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public TrilhaDTO adicionarAlunoTrilha(Integer idTrilha, Integer idAluno) throws RegraDeNegocioException {
        UsuarioEntity alunoEncontrado = usuarioService.findById(idAluno);
        TrilhaEntity trilhaEntity = buscarPorIdTrilha(idTrilha);
        trilhaEntity.getUsuarios().add(alunoEncontrado);
        trilhaRepository.save(trilhaEntity);
        return objectMapper.convertValue(trilhaEntity, TrilhaDTO.class);
    }

    public PageDTO<TrilhaDTO> listarUsuariosNaTrilha(Integer pagina, Integer tamanho, String nome) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<TrilhaEntity> allAlunos = trilhaRepository.findAllByNomeContaining(nome, pageRequest);

        List<TrilhaDTO> trilhaDTOS = allAlunos.getContent().stream()
                .map(trilha -> objectMapper.convertValue(trilha, TrilhaDTO.class))
                .toList();

        return new PageDTO<>(allAlunos.getTotalElements(),
                allAlunos.getTotalPages(),
                pagina,
                tamanho,
                trilhaDTOS);
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
