package br.com.vemrankser.ranqueamento.service;

import br.com.vemrankser.ranqueamento.dto.*;
import br.com.vemrankser.ranqueamento.entity.CargoEntity;
import br.com.vemrankser.ranqueamento.entity.UsuarioEntity;
import br.com.vemrankser.ranqueamento.enums.TipoPerfil;
import br.com.vemrankser.ranqueamento.exceptions.RegraDeNegocioException;
import br.com.vemrankser.ranqueamento.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private static final int USUARIO_ATIVO = 1;
    private static final int PONTUACAO_INICIAL = 0;

    private final UsuarioRepository usuarioRepository;

    private final CargoService cargoService;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;


    public Optional<UsuarioEntity> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public List<UsuarioDTO> findByNome(String nome) {
        if (nome != null) {
            return usuarioRepository.findByNomeIgnoreCase(nome.trim().replaceAll("\\s+", " ")).stream()
                    .map(usuarioEntity -> objectMapper.convertValue(usuarioEntity, UsuarioDTO.class))
                    .toList();
        }
        return usuarioRepository.findAll().stream()
                .map(usuarioEntity -> objectMapper.convertValue(usuarioEntity, UsuarioDTO.class))
                .toList();

    }

    public List<UsuarioDTO> list() {
        return usuarioRepository.findAll().stream()
                .map(pessoa -> objectMapper.convertValue(pessoa, UsuarioDTO.class))
                .toList();
    }


    public UsuarioDTO pegarLogin(String login) throws RegraDeNegocioException {
        UsuarioEntity usuarioEncontrado = usuarioRepository.findByLoginIgnoreCase(login);
        if (!Objects.equals(usuarioEncontrado.getTipoPerfil(), TipoPerfil.ALUNO.getCargo())) {
            throw new RegraDeNegocioException("Este usuário não é um aluno");
        }

        return objectMapper.convertValue(usuarioEncontrado, UsuarioDTO.class);

    }

    public UsuarioDTO pegarIdUsuario(Integer id) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = findById(id);
        return objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
    }

    public UsuarioDTO pegarLoginInstrutor(String login) throws RegraDeNegocioException {
        UsuarioEntity usuarioEncontrado = usuarioRepository.findByLoginIgnoreCase(login);
        if (!Objects.equals(usuarioEncontrado.getTipoPerfil(), TipoPerfil.INSTRUTOR.getCargo())) {
            throw new RegraDeNegocioException("Este usuário não é um instrutor");
        }

        return objectMapper.convertValue(usuarioEncontrado, UsuarioDTO.class);

    }

    public UsuarioDTO editar(Integer id, UsuarioAtualizarDTO usuarioAtualizar) throws RegraDeNegocioException {
        UsuarioEntity usuarioEncontrado = findById(id);
        usuarioEncontrado.setNome(usuarioAtualizar.getNome());
        usuarioEncontrado.setLogin(usuarioAtualizar.getLogin());
        usuarioEncontrado.setEmail(usuarioAtualizar.getEmail());
        usuarioEncontrado.setCidade(usuarioAtualizar.getCidade());
        usuarioEncontrado.setEspecialidade(usuarioAtualizar.getEspecialidade());
        usuarioEncontrado.setSenha(passwordEncoder.encode(usuarioAtualizar.getSenha()));
        usuarioEncontrado.setStatusUsuario(usuarioAtualizar.getStatusUsuario());

        usuarioRepository.save(usuarioEncontrado);
        return objectMapper.convertValue(usuarioEncontrado, UsuarioDTO.class);
    }

    public PageDTO<UsuarioDTO> listarUsuarios(Integer pagina, Integer tamanho, String sort) {
        Sort ordenacao = Sort.by(sort.toLowerCase());
        PageRequest pageRequest = PageRequest.of(pagina, tamanho, ordenacao);
        Page<UsuarioEntity> allAlunos = usuarioRepository.findAll(pageRequest);
        List<UsuarioDTO> usuarioDTOS = allAlunos.getContent().stream()
                .map(usuarioEntity -> objectMapper.convertValue(usuarioEntity, UsuarioDTO.class))
                .toList();

        return new PageDTO<>(allAlunos.getTotalElements(),
                allAlunos.getTotalPages(),
                pagina,
                tamanho,
                usuarioDTOS);
    }

    public PageDTO<UsuarioDTO> listarAlunos(Integer pagina, Integer tamanho) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<UsuarioEntity> allAlunos = usuarioRepository.findAllByTipoPerfil(TipoPerfil.ALUNO.getCargo(), pageRequest);
        List<UsuarioDTO> usuarioDTOS = allAlunos.getContent().stream()
                .map(usuarioEntity -> objectMapper.convertValue(usuarioEntity, UsuarioDTO.class))
                .toList();

        return new PageDTO<>(allAlunos.getTotalElements(),
                allAlunos.getTotalPages(),
                pagina,
                tamanho,
                usuarioDTOS);
    }

    public PageDTO<AlunoTrilhaDTO> listarAlunosTrilhaGeral(Integer pagina, Integer tamanho, String nome) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<UsuarioEntity> allAlunos = usuarioRepository.findAllByTipoPerfilAndNomeContainingIgnoreCase(TipoPerfil.ALUNO.getCargo(), nome, pageRequest);
        List<AlunoTrilhaDTO> usuarioDTOS = allAlunos.getContent().stream()
                .map(usuarioEntity -> {
                    AlunoTrilhaDTO alunoTrilhaDTO = objectMapper.convertValue(usuarioEntity, AlunoTrilhaDTO.class);
                    alunoTrilhaDTO.setTrilhas(usuarioEntity.getTrilhas().stream()
                            .map(trilhaEntity -> objectMapper.convertValue(trilhaEntity, TrilhaDTO.class)).toList());

                    return alunoTrilhaDTO;
                }).toList();
        return new PageDTO<>(allAlunos.getTotalElements(),
                allAlunos.getTotalPages(),
                pagina,
                tamanho,
                usuarioDTOS);
    }

    public Integer getIdLoggedUser() {
        return Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }

    public UsuarioLogadoDTO getLoggedUser() throws RegraDeNegocioException {
        return objectMapper.convertValue(findById(getIdLoggedUser()), UsuarioLogadoDTO.class);
    }

    public UsuarioLogadoDTO getLoggedUserPersonalizado() throws RegraDeNegocioException {
        return objectMapper.convertValue(findById(getIdLoggedUser()), UsuarioLogadoDTO.class);
    }

    public UsuarioDTO cadastrar(UsuarioCreateDTO usuario, TipoPerfil tipoPerfil) throws RegraDeNegocioException {
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        UsuarioEntity usuarioEntity = objectMapper.convertValue(usuario, UsuarioEntity.class);
        CargoEntity cargo = cargoService.findById(tipoPerfil.getCargo());
        usuarioEntity.setCargos(Set.of(cargo));
        usuarioEntity.setStatusUsuario(USUARIO_ATIVO);
        usuarioEntity.setSenha(senhaCriptografada);
        usuarioEntity.setTipoPerfil(tipoPerfil.getCargo());
        usuarioEntity.setPontuacaoAluno(PONTUACAO_INICIAL);

        return objectMapper.convertValue(usuarioRepository.save(usuarioEntity), UsuarioDTO.class);
    }

    public UsuarioDTO uploadImagem(MultipartFile imagem, Integer idUsuario) throws RegraDeNegocioException, IOException {
        UsuarioEntity usuarioEncontrado = findById(idUsuario);
        usuarioEncontrado.setFoto(imagem.getBytes());
        return objectMapper.convertValue(usuarioRepository.save(usuarioEncontrado), UsuarioDTO.class);
    }

    public UsuarioFotoDTO pegarImagemUsuario(Integer idUsuario) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = findById(idUsuario);

        return objectMapper.convertValue(usuarioEntity, UsuarioFotoDTO.class);
    }


    public UsuarioEntity findById(Integer id) throws RegraDeNegocioException {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado!"));
    }


    public PageDTO<AlunoTrilhaPersonalizadoDTO> listAlunoTrilhaQuery(Integer pagina, Integer tamanho, String nome, Integer idTrilha) {

        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<AlunoTrilhaPersonalizadoDTO> page = usuarioRepository.listAlunoTrilhaQuery(pageRequest, nome, idTrilha);
        List<AlunoTrilhaPersonalizadoDTO> alunoTrilhaPersonalizadoDTOS = page.getContent().stream().toList();
        return new PageDTO<>(page.getTotalElements(),
                page.getTotalPages(),
                pagina,
                tamanho,
                alunoTrilhaPersonalizadoDTOS
        );
    }
}
