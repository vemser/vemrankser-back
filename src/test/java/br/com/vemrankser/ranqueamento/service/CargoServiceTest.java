package br.com.vemrankser.ranqueamento.service;

import br.com.vemrankser.ranqueamento.entity.CargoEntity;
import br.com.vemrankser.ranqueamento.exceptions.RegraDeNegocioException;
import br.com.vemrankser.ranqueamento.repository.CargoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CargoServiceTest {
    @InjectMocks
    private CargoService cargoService;

    @Mock
    private CargoRepository cargoRepository;


    @Test
    public void deveTestarFindByIdComSucesso() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer busca = 10;

        CargoEntity cargoEntity = getCargoEntity();
        cargoEntity.setIdCargo(1);
        when(cargoRepository.findById(anyInt())).thenReturn(Optional.of(cargoEntity));

        // Ação (ACT)
        CargoEntity cargo = cargoService.findById(busca);

        // Verificação (ASSERT)
        assertNotNull(cargo);
        assertEquals(1, cargo.getIdCargo());
    }

    @Test(expected = RegraDeNegocioException.class)
    public void deveTestarFindByIdComErro() throws RegraDeNegocioException {
        // Criar variaveis (SETUP)
        Integer busca = 10;
        when(cargoRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Ação (ACT)
        cargoService.findById(busca);
    }

    private static CargoEntity getCargoEntity() {
        CargoEntity cargoEntity = new CargoEntity();
        cargoEntity.setNome("ROLE_ADMIN");
        return cargoEntity;
    }

}
