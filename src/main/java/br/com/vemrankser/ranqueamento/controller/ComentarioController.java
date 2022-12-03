package br.com.vemrankser.ranqueamento.controller;

import br.com.vemrankser.ranqueamento.dto.AtividadeComentarioAvaliacaoCreateDTO;
import br.com.vemrankser.ranqueamento.dto.AtividadeComentarioAvaliacaoDTO;
import br.com.vemrankser.ranqueamento.dto.ComentarioCreateDTO;
import br.com.vemrankser.ranqueamento.dto.ComentarioDTO;
import br.com.vemrankser.ranqueamento.enums.AtividadeStatus;
import br.com.vemrankser.ranqueamento.enums.TipoFeedback;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    @PutMapping("/avaliar-comentar-atividade")
    public ResponseEntity<AtividadeComentarioAvaliacaoDTO> adicionarComentarioAvaliar(@RequestBody @Valid AtividadeComentarioAvaliacaoCreateDTO atividadeComentarioAvaliacaoCreateDTO, @RequestParam(required = false) Integer idAtividade, AtividadeStatus atividadeStatus, TipoFeedback tipoFeedback) throws RegraDeNegocioException {

        log.info("Criando novo comentario....");
        AtividadeComentarioAvaliacaoDTO comentarioAvaliacaoDTO = comentarioService.adicionarComentarioAvaliar(atividadeComentarioAvaliacaoCreateDTO, idAtividade, atividadeStatus, tipoFeedback);
        log.info("Comentario criado com sucesso!");

        return new ResponseEntity<>(comentarioAvaliacaoDTO, HttpStatus.OK);
    }

    @Operation(summary = "Comentario de atividade", description = "Cadastrar comentario para atividade")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cadastro de comentario com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<ComentarioCreateDTO> criarComentario(@RequestBody @Valid ComentarioCreateDTO comentarioCreateDTO, Integer idAtividade) throws RegraDeNegocioException {

        log.info("Criando novo comentario....");
        ComentarioDTO comentarioDTO = comentarioService.adicionar(comentarioCreateDTO, idAtividade);
        log.info("Comentario criado com sucesso!");

        return new ResponseEntity<>(comentarioDTO, HttpStatus.OK);
    }

    @Operation(summary = "Listar comentários por id atividade", description = "Listar comentários por atividade")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Listar comentário, êxito"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-comentario")
    public ResponseEntity<List<ComentarioDTO>> listarComentarioPorAtividade(Integer idAtividade) throws RegraDeNegocioException {
        return ResponseEntity.ok(comentarioService.listarComentarioPorAtividade(idAtividade));
    }


    @Operation(summary = "Listar comentários por feedback positivo e negativo", description = "Listar comentários")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Listar comentário, êxito"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-por-feedback")
    public ResponseEntity<List<ComentarioDTO>> listarComentarioPorFeedback(TipoFeedback tipoFeedback) {
        return ResponseEntity.ok(comentarioService.listarComentarioPorFeedback(tipoFeedback));
    }
}
