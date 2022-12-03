package br.com.vemrankser.ranqueamento.service;

import br.com.vemrankser.ranqueamento.dto.*;
import br.com.vemrankser.ranqueamento.entity.AtividadeEntity;
import br.com.vemrankser.ranqueamento.entity.ComentarioEntity;
import br.com.vemrankser.ranqueamento.entity.UsuarioEntity;
import br.com.vemrankser.ranqueamento.enums.AtividadeStatus;
import br.com.vemrankser.ranqueamento.exceptions.RegraDeNegocioException;
import br.com.vemrankser.ranqueamento.repository.ComentarioRepository;
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
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ComentarioServiceTest {

    @InjectMocks
    private ComentarioService comentarioService;

    @Mock
    private AtividadeService atividadeService;

    @Mock
    private ComentarioRepository comentarioRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(comentarioService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarAdicionarComentario() throws RegraDeNegocioException {
        // SETUP
        Integer idAtividade = 22;
        AtividadeEntity atividade = getAtividadeEntity();
        ComentarioCreateDTO comentarioCreateDTO = getComentarioCreateDTO();

        when(atividadeService.buscarPorIdAtividade(anyInt())).thenReturn(atividade);
        //  when(comentarioRepository.save(any())).thenReturn(atividade);

        // ACT
        ComentarioCreateDTO comentarioCreateDTO1 = comentarioService.adicionar(comentarioCreateDTO, idAtividade);

        // ASSERT
        assertNotNull(comentarioCreateDTO1);
//        assertEquals("Pontos de melhoria...", comentarioCreateDTO1.getComentario());
    }


    @Test
    public void deveTestarlistarComentarioPorAtividadeComSucesso() throws RegraDeNegocioException {

        // SETUP
        Integer id = 3;
        AtividadeEntity atividadeEntity = getAtividadeEntity();
        atividadeEntity.setIdAtividade(12);

        ComentarioDTO comentarioDTO = getComentarioDTO();
        comentarioDTO.setComentario("Muito bom!");

        when(atividadeService.buscarPorIdAtividade(anyInt())).thenReturn(atividadeEntity);
        when(comentarioRepository.findAllByIdAtividade(anyInt())).thenReturn(List.of(getComentarioEntity()));
//        when(objectMapper.convertValue())

        // ACT
        List<ComentarioDTO> comentarioDTOList = comentarioService.listarComentarioPorAtividade(id);

        // ASSERT
        assertNotNull(comentarioDTO);
        assertEquals(1, comentarioDTOList.size());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarBuscarComentarioPorIdComFalha() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer idComentario = 4;

        when(comentarioRepository.findById(anyInt())).thenReturn(Optional.empty());
        // Ação (ACT)
        ComentarioEntity comentarioEntity = comentarioService.buscarPorIdComentario(idComentario);

        // Verificação (ASSERT)

    }

    @Test
    public void deveTestarBuscarComentarioPorIdComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer idComentario = 4;

        ComentarioEntity comentario = getComentarioEntity();
        comentario.setIdComentario(idComentario);
        when(comentarioRepository.findById(anyInt())).thenReturn(Optional.of(comentario));
        // Ação (ACT)
        ComentarioEntity comentarioEntity = comentarioService.buscarPorIdComentario(idComentario);

        // Verificação (ASSERT)
        assertEquals(4, comentarioEntity.getIdComentario());
    }

    @Test
    public void deveTestarRemoverComentarioComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer idComentario = 4;

        ComentarioEntity comentario = getComentarioEntity();
        comentario.setIdComentario(idComentario);
        when(comentarioRepository.findById(anyInt())).thenReturn(Optional.of(comentario));
        // Ação (ACT)
        comentarioService.delete(idComentario);

        // Verificação (ASSERT)
        verify(comentarioRepository, times(1)).delete(any());

    }

    @Test
    public void deveTestarFindById() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer idComentario = 11;
        ComentarioEntity comentarioEntity = getComentarioEntity();
        comentarioEntity.setIdComentario(idComentario);
        when(comentarioRepository.findById(anyInt())).thenReturn(Optional.of(comentarioEntity));

        // Ação (ACT)
        ComentarioEntity comentario = comentarioService.findById(idComentario);

        // Verificação (ASSERT)
        assertNotNull(comentarioEntity);
        assertEquals(11, comentarioEntity.getIdComentario());

    }

    public static ComentarioEntity getComentarioEntity() {
        ComentarioEntity comentarioEntity = new ComentarioEntity();

        comentarioEntity.setIdComentario(11);
        comentarioEntity.setIdAtividade(44);
        comentarioEntity.setComentario("Pontos de melhoria...");

        return comentarioEntity;
    }

    public static ComentarioAvaliacaoDTO getComentarioAvaliacaoDTO() {
        ComentarioAvaliacaoDTO comentarioAvaliacaoDTO = new ComentarioAvaliacaoDTO();
        comentarioAvaliacaoDTO.setComentario("Parabéns . . .");

        return comentarioAvaliacaoDTO;
    }

    public static ComentarioDTO getComentarioDTO() {
        ComentarioDTO comentarioDTO = new ComentarioDTO();

        comentarioDTO.setComentario("Pontos de melhoria...");

        return comentarioDTO;
    }

    public static ComentarioCreateDTO getComentarioCreateDTO() {
        ComentarioDTO comentarioDTO = new ComentarioDTO();

        comentarioDTO.setComentario("Pontos de melhoria...");

        return comentarioDTO;
    }

    public static AtividadeEntity getAtividadeEntity() {
        AtividadeEntity atividadeEntity = new AtividadeEntity();

        atividadeEntity.setIdAtividade(1);
        atividadeEntity.setStatusAtividade(AtividadeStatus.PENDENTE);
        atividadeEntity.setPesoAtividade(3);

        return atividadeEntity;
    }

    public static AtividadeAvaliarDTO getAtividadeAvaliarDTO() {
        AtividadeAvaliarDTO atividadeAvaliarDTO = new AtividadeAvaliarDTO();

        atividadeAvaliarDTO.setPontuacao(90);
        atividadeAvaliarDTO.setLink("www.githu.com");

        return atividadeAvaliarDTO;
    }

    public static AtividadeAvaliacaoDTO getAtividadeAvaliacaoDTO() {
        AtividadeAvaliacaoDTO atividadeAvaliacaoDTO = new AtividadeAvaliacaoDTO();
        atividadeAvaliacaoDTO.setPontuacao(100);

        return atividadeAvaliacaoDTO;
    }

    public static UsuarioEntity getUsuarioEntity() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();

        usuarioEntity.setIdUsuario(22);
        usuarioEntity.setNome("Robert Plant");
        usuarioEntity.setPontuacaoAluno(100);

        return usuarioEntity;
    }
}
