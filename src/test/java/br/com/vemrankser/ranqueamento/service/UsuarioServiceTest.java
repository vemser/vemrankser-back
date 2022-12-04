package br.com.vemrankser.ranqueamento.service;

import br.com.vemrankser.ranqueamento.dto.*;
import br.com.vemrankser.ranqueamento.entity.CargoEntity;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
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
    public void deveTestargetIdLoggedUser() {

        // Criar variaveis (SETUP)
        UsernamePasswordAuthenticationToken dto
                = new UsernamePasswordAuthenticationToken(1, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(dto);

        // Ação (ACT)
        Integer idLoggedUser = usuarioService.getIdLoggedUser();


        // Verificação (ASSERT)
        assertEquals(1, idLoggedUser);
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
        assertEquals("alison.ailson", loggedUser.getLogin());
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

    @Test
    public void deveTestarFindEmailComSucesso() {
        // Criar variaveis (SETUP)
        UsuarioEntity usuarioEntity = getUsuarioEntity();
        when(usuarioRepository.findByEmail(any())).thenReturn(Optional.of(usuarioEntity));

        // Ação (ACT)
        Optional<UsuarioEntity> usuarioServiceByEmail = usuarioService.findByEmail(usuarioEntity.getEmail());

        // Verificação (ASSERT)
        assertNotNull(usuarioServiceByEmail);
        assertEquals("alison@hotmail.com", usuarioServiceByEmail.get().getEmail());
    }

    @Test
    public void deveTestarFindByNomeComSucesso() {
        // Criar variaveis (SETUP)
        List<UsuarioEntity> usuarioEntities = new ArrayList<>();
        UsuarioEntity usuarioEntity = getUsuarioEntity();
        usuarioEntities.add(usuarioEntity);
        when(usuarioRepository.findByNomeIgnoreCase(any())).thenReturn(usuarioEntities);
        //  when(usuarioRepository.findAll()).thenReturn(usuarioEntities);

        // Ação (ACT)
        List<UsuarioDTO> usuarioDTOList = usuarioService.findByNome(usuarioEntity.getNome());

        // Verificação (ASSERT)
        assertNotNull(usuarioDTOList);
        assertEquals(1, usuarioDTOList.size());
    }

    @Test
    public void deveTestarFindAllByNomeComSucesso() {
        // Criar variaveis (SETUP)
        List<UsuarioEntity> usuarioEntities = new ArrayList<>();
        UsuarioEntity usuarioEntity = getUsuarioEntity();
        usuarioEntities.add(usuarioEntity);
        // when(usuarioRepository.findByNomeIgnoreCase(any())).thenReturn(usuarioEntities);
        when(usuarioRepository.findAll()).thenReturn(usuarioEntities);
        usuarioEntity.setNome(null);
        // Ação (ACT)
        List<UsuarioDTO> usuarioDTOList = usuarioService.findByNome(usuarioEntity.getNome());

        // Verificação (ASSERT)
        assertNotNull(usuarioDTOList);
        assertEquals(1, usuarioDTOList.size());
    }

    @Test
    public void deveTestarFindAllComSucesso() {
        // Criar variaveis (SETUP)
        UsuarioEntity usuarioEntity = getUsuarioEntity();
        List<UsuarioEntity> list = new ArrayList<>();
        list.add(usuarioEntity);
        when(usuarioRepository.findAll()).thenReturn(list);

        // Ação (ACT)
        List<UsuarioDTO> list1 = usuarioService.list();

        // Verificação (ASSERT)
        assertNotNull(list1);

    }

    @Test
    public void deveTestarPegarLoginComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        UsuarioEntity usuarioEntity = getUsuarioEntity();

        when(usuarioRepository.findByLoginIgnoreCase(any())).thenReturn(usuarioEntity);

        // Ação (ACT)
        UsuarioDTO usuarioDTO = usuarioService.pegarLogin(usuarioEntity.getLogin());

        // Verificação (ASSERT)
        assertNotNull(usuarioDTO);
        assertEquals("alison.ailson", usuarioDTO.getLogin());

    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarPegarLoginComErro() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        UsuarioEntity usuarioEntity = getUsuarioEntity();
        usuarioEntity.setTipoPerfil(1);

        when(usuarioRepository.findByLoginIgnoreCase(any())).thenReturn(usuarioEntity);

        // Ação (ACT)
        usuarioService.pegarLogin(usuarioEntity.getLogin());
    }

    @Test
    public void deveTestarFindIdComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        UsuarioEntity usuarioEntity = getUsuarioEntity();

        when(usuarioRepository.findById(any())).thenReturn(Optional.of(usuarioEntity));

        // Ação (ACT)
        UsuarioEntity usuario = usuarioService.findById(usuarioEntity.getIdUsuario());

        // Verificação (ASSERT)
        assertNotNull(usuario);
        assertEquals(1, usuario.getIdUsuario());

    }

    @Test
    public void deveTestarPegarIdUsuarioComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        UsuarioEntity usuarioEntity = getUsuarioEntity();

        when(usuarioRepository.findById(any())).thenReturn(Optional.of(usuarioEntity));

        // Ação (ACT)
        UsuarioDTO usuario = usuarioService.pegarIdUsuario(usuarioEntity.getIdUsuario());

        // Verificação (ASSERT)
        assertNotNull(usuario);
        assertEquals(1, usuario.getIdUsuario());

    }

    @Test
    public void deveTestarPegarLoginInstrutorComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        UsuarioEntity usuarioEntity = getUsuarioEntity();
        usuarioEntity.setTipoPerfil(3);

        when(usuarioRepository.findByLoginIgnoreCase(any())).thenReturn(usuarioEntity);

        // Ação (ACT)
        UsuarioDTO usuarioDTO = usuarioService.pegarLoginInstrutor(usuarioEntity.getLogin());

        // Verificação (ASSERT)
        assertNotNull(usuarioDTO);
        assertEquals("alison.ailson", usuarioDTO.getLogin());

    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarPegarLoginInstrutorComErro() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        UsuarioEntity usuarioEntity = getUsuarioEntity();
        usuarioEntity.setTipoPerfil(1);

        when(usuarioRepository.findByLoginIgnoreCase(any())).thenReturn(usuarioEntity);

        // Ação (ACT)
        usuarioService.pegarLoginInstrutor(usuarioEntity.getLogin());
    }

    @Test
    public void deveTestarEditarComSucesso() throws RegraDeNegocioException {
        // SETUP
        Integer id = 10;
        UsuarioCreateDTO usuarioCreateDTO = getUsuarioCreateDTO();
        UsuarioAtualizarDTO usuarioAtualizarDTO = objectMapper.convertValue(usuarioCreateDTO, UsuarioAtualizarDTO.class);
        usuarioAtualizarDTO.setStatusUsuario(1);
        UsuarioEntity usuarioEntity = getUsuarioEntity();
        usuarioEntity.setNome("Eduardo Sedrez");
        usuarioEntity.setIdUsuario(1);
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntity));

        String senhaCriptografa = "j183nsur74bd83gr7";
        when(passwordEncoder.encode(anyString())).thenReturn(senhaCriptografa);

        UsuarioEntity usuario = getUsuarioEntity();
        when(usuarioRepository.save(any())).thenReturn(usuario);

        // Ação (ACT)
        UsuarioDTO usuarioDTO = usuarioService.editar(id, usuarioAtualizarDTO);

        // Verificação (ASSERT)
        assertNotNull(usuarioDTO);
        assertNotEquals("Eduardo Sedrez", usuarioDTO.getNome());

    }

    @Test
    public void deveTestarListarUsuariosPaginadoComSucesso() {
        // SETUP
        Integer pagina = 5;
        Integer quantidade = 3;

        UsuarioEntity usuarioEntity = getUsuarioEntity();
        Page<UsuarioEntity> paginaMock = new PageImpl<>(List.of(usuarioEntity));
        when(usuarioRepository.findAll(any(Pageable.class))).thenReturn(paginaMock);

        // ACT
        PageDTO<UsuarioDTO> listarUsuarios = usuarioService.listarUsuarios(pagina, quantidade, "nome");

        // ASSERT
        assertNotNull(listarUsuarios);
        assertEquals(1, listarUsuarios.getQuantidadePaginas());
        assertEquals(1, listarUsuarios.getTotalElementos());
    }

    @Test
    public void deveTestarListarAlunosPaginadoComSucesso() {
        // SETUP
        Integer pagina = 5;
        Integer quantidade = 3;

        UsuarioEntity usuarioEntity = getUsuarioEntity();
        Page<UsuarioEntity> paginaMock = new PageImpl<>(List.of(usuarioEntity));
        when(usuarioRepository.findAllByTipoPerfil(anyInt(), any(Pageable.class))).thenReturn(paginaMock);

        // ACT
        PageDTO<UsuarioDTO> listarUsuarios = usuarioService.listarAlunos(pagina, quantidade);

        // ASSERT
        assertNotNull(listarUsuarios);
        assertEquals(1, listarUsuarios.getQuantidadePaginas());
        assertEquals(1, listarUsuarios.getTotalElementos());
    }

    @Test
    public void deveTestarListarAlunosTrilhaPaginadoComSucesso() {
        // SETUP
        Integer pagina = 5;
        Integer quantidade = 3;

        UsuarioEntity usuarioEntity = getUsuarioEntity();
        usuarioEntity.setTrilhas(new HashSet<>());
        Page<UsuarioEntity> paginaMock = new PageImpl<>(List.of(usuarioEntity));
        when(usuarioRepository.findAllByTipoPerfilAndNomeContainingIgnoreCase(anyInt(), anyString(), any(Pageable.class))).thenReturn(paginaMock);

        // ACT
        PageDTO<AlunoTrilhaDTO> listarUsuarios = usuarioService.listarAlunosTrilhaGeral(pagina, quantidade, "alison");


        // ASSERT
        assertNotNull(listarUsuarios);
        assertEquals(1, listarUsuarios.getQuantidadePaginas());
        assertEquals(1, listarUsuarios.getTotalElementos());
    }

    @Test
    public void deveTestargetLoggedUserPersonalizado() throws RegraDeNegocioException {

        // Criar variaveis (SETUP)
        UsernamePasswordAuthenticationToken dto
                = new UsernamePasswordAuthenticationToken(1, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(dto);
        UsuarioEntity usuarioEntity = getUsuarioEntity();
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuarioEntity));

        // Ação (ACT)
        UsuarioLogadoDTO loggedUser = usuarioService.getLoggedUserPersonalizado();
        loggedUser.setLogin("alison.ailson");

        // Verificação (ASSERT)
        assertEquals("alison.ailson", loggedUser.getLogin());
    }

    @Test
    public void deveTestarCadastrarComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        UsuarioCreateDTO usuarioCreateDTO = getUsuarioCreateDTO();

        UsuarioEntity usuarioEntity = getUsuarioEntity();

        String senhaCriptografada = "j183nsur74bd83gr7";
        when(passwordEncoder.encode(anyString())).thenReturn(senhaCriptografada);

        when(cargoService.findById(anyInt())).thenReturn(getCargoEntity());

        usuarioEntity.setIdUsuario(1);
        when(usuarioRepository.save(any())).thenReturn(usuarioEntity);

        // Ação (ACT)
        UsuarioDTO usuarioDTO = usuarioService.cadastrar(usuarioCreateDTO, TipoPerfil.ALUNO);

        // Verificação (ASSERT)
        assertNotNull(usuarioDTO);
        assertNotNull(usuarioDTO.getIdUsuario());
        assertEquals("alison@hotmail.com", usuarioDTO.getEmail());
    }

    @Test
    public void deveTestarUploadImagemComSucesso() throws RegraDeNegocioException, IOException {
        UsuarioEntity usuario = getUsuarioEntity();
        byte[] imagemBytes = new byte[10 * 1024];
        MultipartFile imagem = new MockMultipartFile("imagem", imagemBytes);
        Integer idUsuario = 1;

        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any())).thenReturn(usuario);

        UsuarioDTO usuarioDTO = usuarioService.uploadImagem(imagem, idUsuario);
        UsuarioEntity usuarioEntity = objectMapper.convertValue(usuarioDTO, UsuarioEntity.class);


        assertNotEquals(usuario.getFoto(), usuarioEntity.getFoto());
        assertNotNull(usuarioDTO);

    }

    @Test
    public void deveTestarPegarImagemUsuarioComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        UsuarioEntity usuarioEntity = getUsuarioEntity();

        when(usuarioRepository.findById(any())).thenReturn(Optional.of(usuarioEntity));

        // Ação (ACT)
        UsuarioFotoDTO usuario = usuarioService.pegarImagemUsuario(usuarioEntity.getIdUsuario());

        // Verificação (ASSERT)
        assertNotNull(usuario);

    }

    @Test
    public void deveTestarListarUsuariosPersonalizadoQueryPaginadoComSucesso() {
        // SETUP
        Integer pagina = 5;
        Integer quantidade = 3;
        Integer idTrilha = 1;

        UsuarioEntity usuarioEntity = getUsuarioEntity();
        AlunoTrilhaPersonalizadoDTO alunoTrilhaPersonalizadoDTO = objectMapper.convertValue(usuarioEntity, AlunoTrilhaPersonalizadoDTO.class);
        Page<AlunoTrilhaPersonalizadoDTO> paginaMock = new PageImpl<>(List.of(alunoTrilhaPersonalizadoDTO));
        when(usuarioRepository.listAlunoTrilhaQuery(any(), anyString(), anyInt())).thenReturn(paginaMock);

        // ACT
        PageDTO<AlunoTrilhaPersonalizadoDTO> listarUsuarios = usuarioService.listAlunoTrilhaQuery(pagina, quantidade, "nome", idTrilha);

        // ASSERT
        assertNotNull(listarUsuarios);
        assertEquals(1, listarUsuarios.getQuantidadePaginas());
        assertEquals(1, listarUsuarios.getTotalElementos());
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

    public static UsuarioCreateDTO getUsuarioCreateDTO() {
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

    private static CargoEntity getCargoEntity() {
        CargoEntity cargoEntity = new CargoEntity();
        cargoEntity.setNome("ROLE_ADMIN");
        return cargoEntity;
    }
}
