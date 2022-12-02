package br.com.vemrankser.ranqueamento.service;

import br.com.vemrankser.ranqueamento.dto.UsuarioCreateDTO;
import br.com.vemrankser.ranqueamento.dto.UsuarioDTO;
import br.com.vemrankser.ranqueamento.dto.UsuarioLogadoDTO;
import br.com.vemrankser.ranqueamento.entity.UsuarioEntity;
import br.com.vemrankser.ranqueamento.enums.TipoPerfil;
import br.com.vemrankser.ranqueamento.exceptions.RegraDeNegocioException;
import br.com.vemrankser.ranqueamento.repository.UsuarioRepository;
import br.com.vemrankser.ranqueamento.security.TokenService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioServiceTest {
    @InjectMocks
    private UsuarioService usuarioService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private UsuarioRepository usuarioRepository;


    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CargoService cargoService;

    @Mock
    private TokenService tokenService;

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(usuarioService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestargetIdLoggedUser(){

        // Criar variaveis (SETUP)
        UsernamePasswordAuthenticationToken dto
                = new UsernamePasswordAuthenticationToken(1, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(dto);

        // Ação (ACT)
        Integer idLoggedUser = usuarioService.getIdLoggedUser();


        // Verificação (ASSERT)
        assertEquals(1,idLoggedUser);
    }

    @Test
    public void deveTestargetLoggedUser() throws RegraDeNegocioException {

        // Criar variaveis (SETUP)
        UsernamePasswordAuthenticationToken dto
                = new UsernamePasswordAuthenticationToken(1, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(dto);
        UsuarioEntity usuarioEntity = getUsuarioEntity();
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntity));

        // Ação (ACT)
        UsuarioLogadoDTO loggedUser = usuarioService.getLoggedUser();
        loggedUser.setLogin("alison.ailson");

        // Verificação (ASSERT)
        assertEquals("alison.ailson",loggedUser.getLogin());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer busca = 10;
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Ação (ACT)
        usuarioService.findById(busca);
    }

    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer busca = 10;

        UsuarioEntity usuarioEntity = getUsuarioEntity();
        usuarioEntity.setIdUsuario(1);
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntity));

        // Ação (ACT)
        UsuarioEntity usuarioRecuperado = usuarioService.findById(busca);

        // Verificação (ASSERT)
        assertNotNull(usuarioRecuperado);
        assertEquals(1, usuarioRecuperado.getIdUsuario());
    }


    public static UsuarioEntity getUsuarioEntity() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setIdUsuario(1);
        usuarioEntity.setFoto(null);
        usuarioEntity.setStatusUsuario(1);
        usuarioEntity.setPontuacaoAluno(0);
        usuarioEntity.setLogin("alison.ailson");
        usuarioEntity.setNome("alison");
        usuarioEntity.setSenha("123");
        usuarioEntity.setCidade("recife");
        usuarioEntity.setEspecialidade("backend");
        usuarioEntity.setEmail("alison@hotmail.com");
        usuarioEntity.setCargos(new HashSet<>());
        usuarioEntity.setTipoPerfil(TipoPerfil.ALUNO.getCargo());

        return usuarioEntity;
    }

    public static UsuarioCreateDTO getUsuarioCreteDTO() {
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO();
        usuarioCreateDTO.setCidade("recife");
        usuarioCreateDTO.setEmail("alison@hotmail.com");
        usuarioCreateDTO.setNome("alison");
        usuarioCreateDTO.setFoto(null);
        usuarioCreateDTO.setTipoPerfil(TipoPerfil.ALUNO.getCargo());
        usuarioCreateDTO.setLogin("alison.ailson");
        usuarioCreateDTO.setSenha("123");
        usuarioCreateDTO.setEspecialidade("backend");

        return usuarioCreateDTO;
    }
}
