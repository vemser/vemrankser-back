package br.com.vemrankser.ranqueamento.service;

import br.com.vemrankser.ranqueamento.dto.PageDTO;
import br.com.vemrankser.ranqueamento.dto.UsuarioCreateDTO;
import br.com.vemrankser.ranqueamento.dto.UsuarioDTO;
import br.com.vemrankser.ranqueamento.dto.UsuarioFotoDTO;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private static final int USUARIO_ATIVO = 1;
    private static final int USUARIO_INATIVO = 0;

    private final UsuarioRepository usuarioRepository;

    private final CargoService cargoService;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;


    public Optional<UsuarioEntity> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public UsuarioDTO findByNome(String nome) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = usuarioRepository.findByNomeContainingIgnoreCase(nome)
                .orElseThrow(() -> new RegraDeNegocioException("Nome não encontrado!"));
        return objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
    }

    public PageDTO<UsuarioDTO> listarUsuarios(Integer pagina, Integer tamanho, String sort, String nome) {
        Sort ordenacao = Sort.by(sort.toLowerCase());
        PageRequest pageRequest = PageRequest.of(pagina, tamanho, ordenacao);
        Page<UsuarioEntity> allAlunos = usuarioRepository.findByNomeIgnoreCaseContaining(nome, pageRequest);

        List<UsuarioDTO> usuarioDTOS = allAlunos.getContent().stream()
                .map(usuarioEntity -> objectMapper.convertValue(usuarioEntity, UsuarioDTO.class))
                .toList();

        return new PageDTO<>(allAlunos.getTotalElements(),
                allAlunos.getTotalPages(),
                pagina,
                tamanho,
                usuarioDTOS);
    }

    public UsuarioDTO cadastrar(UsuarioCreateDTO usuario, TipoPerfil tipoPerfil) throws RegraDeNegocioException {
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        UsuarioEntity usuarioEntity = objectMapper.convertValue(usuario, UsuarioEntity.class);
        CargoEntity cargo = cargoService.findById(tipoPerfil.getCargo());
        usuarioEntity.setCargos(Set.of(cargo));
        usuarioEntity.setStatusUsuario(USUARIO_ATIVO);
        usuarioEntity.setSenha(senhaCriptografada);
        // usuarioEntity.setTipoPerfil(TipoPerfil.ALUNO.getCargo());

        return objectMapper.convertValue(usuarioRepository.save(usuarioEntity), UsuarioDTO.class);
    }

    public UsuarioDTO uploadImagem(MultipartFile imagem,Integer idUsuario) throws RegraDeNegocioException, IOException {
        UsuarioEntity usuarioEncontrado = findById(idUsuario);
        usuarioEncontrado.setFoto(imagem.getBytes());
        return objectMapper.convertValue(usuarioRepository.save(usuarioEncontrado), UsuarioDTO.class);
    }

    public UsuarioFotoDTO pegarImagemUsuario(Integer idUsuario) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = findById(idUsuario);

        return objectMapper.convertValue(usuarioEntity,UsuarioFotoDTO.class);
    }


    //
//    public UsuarioDTO desativarConta(Integer idUsuario) throws RegraDeNegocioException {
//        UsuarioEntity usuarioEncontrado = findById(idUsuario);
//        usuarioEncontrado.setStatusUsuario(USUARIO_INATIVO);
//        usuarioRepository.save(usuarioEncontrado);
//
//        return objectMapper.convertValue(usuarioEncontrado, UsuarioDTO.class);
//    }
    public UsuarioDTO editar(Integer id, UsuarioCreateDTO usuarioAtualizar) throws RegraDeNegocioException {
        UsuarioEntity usuarioEncontrado = findById(id);
        usuarioEncontrado.setFoto(usuarioEncontrado.getFoto());
        usuarioEncontrado.setNome(usuarioAtualizar.getNome());
        usuarioEncontrado.setEmail(usuarioAtualizar.getEmail());
        usuarioEncontrado.setSenha(passwordEncoder.encode(usuarioAtualizar.getSenha()));
        usuarioEncontrado.setStatusUsuario(usuarioAtualizar.getStatusUsuario());
        usuarioEncontrado.setAtuacao(usuarioEncontrado.getAtuacao());

        //  usuarioEncontrado.setTipoPerfil(usuarioEncontrado.getTipoPerfil());

        usuarioRepository.save(usuarioEncontrado);

        return objectMapper.convertValue(usuarioEncontrado, UsuarioDTO.class);
    }

    public UsuarioEntity findById(Integer id) throws RegraDeNegocioException {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado!"));
    }

}
