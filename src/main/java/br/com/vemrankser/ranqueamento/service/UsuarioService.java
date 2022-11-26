package br.com.vemrankser.ranqueamento.service;

import br.com.vemrankser.ranqueamento.entity.UsuarioEntity;
import br.com.vemrankser.ranqueamento.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private static final int USUARIO_ATIVO = 1;
    private static final int USUARIO_INATIVO = 0;

    private final UsuarioRepository usuarioRepository;

    private final CargoService cargoService;

    private final ObjectMapper objectMapper;


    public Optional<UsuarioEntity> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

}
