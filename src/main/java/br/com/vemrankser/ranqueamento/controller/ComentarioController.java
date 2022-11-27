package br.com.vemrankser.ranqueamento.controller;

import br.com.vemrankser.ranqueamento.dto.ComentarioCreateDTO;
import br.com.vemrankser.ranqueamento.dto.ComentarioDTO;
import br.com.vemrankser.ranqueamento.exceptions.RegraDeNegocioException;
import br.com.vemrankser.ranqueamento.service.ComentarioService;
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

@Validated
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/comentario")
public class ComentarioController {

    private final ComentarioService comentarioService;

    @Operation(summary = "Comentario de atividade", description = "Cadastrar comentario para atividade")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cadastro de comentario com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<ComentarioCreateDTO> create(@RequestBody @Valid ComentarioCreateDTO comentarioCreateDTO, Integer idAtividade) throws RegraDeNegocioException {

        log.info("Criando novo comentario....");
        ComentarioDTO comentarioDTO = comentarioService.adicionar(comentarioCreateDTO, idAtividade);
        log.info("Comentario criado com sucesso!");

        return new ResponseEntity<>(comentarioDTO, HttpStatus.OK);
    }
}
