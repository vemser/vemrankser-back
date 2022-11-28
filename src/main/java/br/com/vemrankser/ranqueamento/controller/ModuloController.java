package br.com.vemrankser.ranqueamento.controller;

import br.com.vemrankser.ranqueamento.dto.ModuloCreateDTO;
import br.com.vemrankser.ranqueamento.dto.ModuloDTO;
import br.com.vemrankser.ranqueamento.exceptions.RegraDeNegocioException;
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

    @Operation(summary = "Adicionar novo Modulo", description = "Adicionar novos modulos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Modulo adicionado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/adicionar-modulo")
    public ResponseEntity<ModuloDTO> adicionar(@RequestBody @Valid ModuloCreateDTO modulo) {
        log.info("Criando modulo....");
        ModuloDTO moduloDTO = moduloService.adicionar(modulo);
        log.info("Modulo Criado com sucesso....");
        return new ResponseEntity<>(moduloDTO, HttpStatus.OK);
    }
    @PostMapping("/vincular-modulo-trilha")
    public ResponseEntity<ModuloDTO> vincular(@RequestBody @Valid ModuloCreateDTO modulo, Integer id) throws RegraDeNegocioException {
        log.info("Criando modulo....");
        ModuloDTO moduloDTO = moduloService.vincularModuloTrilha(modulo,id);
        log.info("Modulo Criado com sucesso....");
        return new ResponseEntity<>(moduloDTO, HttpStatus.OK);
    }
}
