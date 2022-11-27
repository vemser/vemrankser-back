package br.com.vemrankser.ranqueamento.controller;

import br.com.vemrankser.ranqueamento.dto.ModuloCreateDTO;
import br.com.vemrankser.ranqueamento.dto.ModuloDTO;
import br.com.vemrankser.ranqueamento.service.ModuloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping("/modulo")
public class ModuloController {

    private final ModuloService moduloService;

    @Operation(summary = "Cadastro de Modulo", description = "Cadastrar modulos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cadastro de modulo realizado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/cadastrar-modulo")
    public ResponseEntity<ModuloDTO> create(@RequestBody @Valid ModuloCreateDTO modulo) {
        log.info("Criando modulo....");
        ModuloDTO moduloDTO = moduloService.create(modulo);
        log.info("Criando modulo....");
        return new ResponseEntity<>(moduloDTO, HttpStatus.OK);
    }
}
