package br.com.vemrankser.ranqueamento.service;

import br.com.vemrankser.ranqueamento.dto.AtividadeDTO;
import br.com.vemrankser.ranqueamento.dto.ComentarioDTO;
import br.com.vemrankser.ranqueamento.entity.AtividadeEntity;
import br.com.vemrankser.ranqueamento.exceptions.RegraDeNegocioException;
import br.com.vemrankser.ranqueamento.repository.AtividadeRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AtividadeServiceTest {

    @InjectMocks
    private AtividadeService atividadeService;

    @Mock
    private AtividadeRepository atividadeRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(atividadeService, "objectMapper", objectMapper);
    }

    @Test
    public void deveTestarSaveAtividadeComSucesso() throws RegraDeNegocioException {

        // SETUP
        Integer id = 3;
        AtividadeDTO atividadeDTO = getAtividadeDTO();
        atividadeDTO.setIdAtividade(id);

        AtividadeEntity atividadeEntity = getAtividadeEntity();

        when(atividadeRepository.save(any())).thenReturn(atividadeEntity);

        // ACT
        atividadeService.save(atividadeEntity);

        // ASSERT
        assertNotNull(atividadeEntity);
//        assertEquals(1, comentarioDTOList.size());
    }


    public static AtividadeDTO getAtividadeDTO() {
        AtividadeDTO atividadeDTO = new AtividadeDTO();

        atividadeDTO.setIdAtividade(11);
        atividadeDTO.setPesoAtividade(2);
        atividadeDTO.setTitulo("Java OO");
        atividadeDTO.setNomeInstrutor("Robert Plant");

        return atividadeDTO;
    }

    public static AtividadeEntity getAtividadeEntity() {
        AtividadeEntity atividadeEntity = new AtividadeEntity();

        atividadeEntity.setIdAtividade(11);
        atividadeEntity.setPesoAtividade(2);
        atividadeEntity.setTitulo("Java OO");
        atividadeEntity.setNomeInstrutor("Robert Plant");

        return atividadeEntity;
    }
}
