package br.com.vemrankser.ranqueamento.controller;

import br.com.vemrankser.ranqueamento.dto.TrilhaCreateDTO;
import br.com.vemrankser.ranqueamento.dto.TrilhaDTO;
import br.com.vemrankser.ranqueamento.exceptions.RegraDeNegocioException;
import br.com.vemrankser.ranqueamento.service.TrilhaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/trilha")
public class TrilhaController {

    private final TrilhaService trilhaService;
    @Operation(summary = "Cadastro de Trilha", description = "Cadastrar Trilha para os módulos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cadastro de Trilha realizado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<TrilhaDTO> create(@RequestBody @Valid TrilhaCreateDTO trilhaCreateDTO) throws RegraDeNegocioException {
        log.info("Criando Trilha...");
        TrilhaDTO e = trilhaService.create(trilhaCreateDTO);
        log.info("Trilha Criada com sucesso!!");
        return new ResponseEntity<>(e, HttpStatus.OK);
    }
}
