package br.com.vemrankser.ranqueamento.controller;

import br.com.vemrankser.ranqueamento.dto.*;
import br.com.vemrankser.ranqueamento.entity.AtividadeEntity;
import br.com.vemrankser.ranqueamento.enums.AtividadeStatus;
import br.com.vemrankser.ranqueamento.exceptions.RegraDeNegocioException;
import br.com.vemrankser.ranqueamento.service.AtividadeService;
import br.com.vemrankser.ranqueamento.service.UsuarioService;
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

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/atividade")
public class AtividadeController {

    private final AtividadeService atividadeService;

    //    @Override
    @Operation(summary = "Cadastro de atividade", description = "Cadastrar atividade para os módulos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cadastro de atividade com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<AtividadeCreateDTO> create(@RequestBody @Valid AtividadeCreateDTO atividadeCreateDTO, Integer idModulo, Integer idTrilha, String login) throws RegraDeNegocioException {

        log.info("Criando nova atidade....");
        AtividadeDTO atividadeDTO = atividadeService.adicionar(atividadeCreateDTO, idModulo, idTrilha, login);
        log.info("Atividade criada com sucesso!");

        return new ResponseEntity<>(atividadeDTO, HttpStatus.CREATED);
    }


    @Operation(summary = "Listar atividade com paginação", description = "Listar atividade com paginação")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Listar de atividade com paginação, êxito"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-paginado")
    public ResponseEntity<AtividadePaginacaoDTO<AtividadeDTO>> listarAtividadePaginado(Integer pagina, Integer tamanho) throws RegraDeNegocioException {
        return ResponseEntity.ok(atividadeService.listarAtividades(pagina, tamanho));
    }

    @Operation(summary = "Avaliar atividade do aluno", description = "Avaliar atividade do aluno")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Atividade avalida com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/avaliar/{idAtividade}")
    public ResponseEntity<AtividadeAvaliarDTO> avaliarAtividade(@PathVariable(name = "idAtividade") Integer idAtividade, @RequestBody @Valid AtividadeAvaliarDTO atividadeAvaliarDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(atividadeService.avaliarAtividade(atividadeAvaliarDTO, idAtividade), HttpStatus.OK);

    }

    @Operation(summary = "Listar atividade por status", description = "Listar atividade por status")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Listar atividade por status com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-status")
    public ResponseEntity<List<AtividadeEntity>> buscarAtividadePorStatusvidadePaginado(AtividadeStatus atividadeStatus) throws RegraDeNegocioException {
        return ResponseEntity.ok(atividadeService.buscarAtividadePorStatus(atividadeStatus));
    }

    @Operation(summary = "Listar atividade no mural", description = "Listar atividade no mural")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Listar atividade no mural com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-mural")
    public ResponseEntity<PageDTO<AtividadeMuralDTO>> listarAtividadeMural(Integer pagina, Integer tamanho) throws RegraDeNegocioException {
        return new ResponseEntity<>(atividadeService.listarAtividadeMural(pagina, tamanho), HttpStatus.OK);
    }

    @Operation(summary = "Listar atividade por nota", description = "Listar atividade por nota")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Listar atividade por nota com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-nota")
    public ResponseEntity<PageDTO<AtividadeNotaDTO>> listarAtividadePorNota(Integer pagina, Integer tamanho) throws RegraDeNegocioException {
        return new ResponseEntity<>(atividadeService.listarAtividadePorNota(pagina, tamanho), HttpStatus.OK);
    }

    @Operation(summary = "Entregar atividade", description = "Entregar atividade")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Entregar atividade com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/entregar/{idAtividade}")
    public ResponseEntity<AtividadeAlunoEnviarDTO> entregarAtividade(@PathVariable(name = "idAtividade")Integer idAtividade, AtividadeAlunoEnviarDTO atividadeAlunoEnviarDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(atividadeService.entregarAtividade(atividadeAlunoEnviarDTO, idAtividade), HttpStatus.OK);

    }


}
