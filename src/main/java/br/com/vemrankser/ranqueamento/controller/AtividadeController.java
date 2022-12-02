package br.com.vemrankser.ranqueamento.controller;

import br.com.vemrankser.ranqueamento.dto.*;
import br.com.vemrankser.ranqueamento.enums.AtividadeStatus;
import br.com.vemrankser.ranqueamento.exceptions.RegraDeNegocioException;
import br.com.vemrankser.ranqueamento.service.AtividadeService;
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


    @Operation(summary = "Cadastro de atividade", description = "Cadastrar atividade para os módulos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cadastro de atividade com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<AtividadeDTO> create(@RequestBody @Valid AtividadeCreateDTO atividadeCreateDTO, Integer idModulo, @RequestParam List<Integer> idTrilha) throws RegraDeNegocioException {

        log.info("Criando nova atidade....");
        AtividadeDTO atividadeDTO = atividadeService.createAtividade(atividadeCreateDTO, idModulo, idTrilha);
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
    public ResponseEntity<AtividadePaginacaoDTO<AtividadeDTO>> listarAtividadePaginado(@RequestParam(required = false, defaultValue = "0") Integer pagina, @RequestParam(required = false, defaultValue = "5") Integer tamanho) throws RegraDeNegocioException {
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
    @GetMapping("/listar-trilha-status")
    public ResponseEntity<PageDTO<AtividadeTrilhaDTO>> listarAtividadePorStatus(@RequestParam(required = false, defaultValue = "0") Integer pagina, @RequestParam(required = false, defaultValue = "10") Integer tamanho, @RequestParam(required = false) Integer idTrilha, @RequestParam(required = false) AtividadeStatus atividadeStatus) throws RegraDeNegocioException {
        return new ResponseEntity<>(atividadeService.listarAtividadePorStatus(pagina, tamanho, idTrilha, atividadeStatus), HttpStatus.OK);
    }

    @Operation(summary = "Listar atividade no mural instrutor", description = "Listar atividade no mural instrutor")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Listar atividade no mural do instrutor com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-mural-instrutor")
    public ResponseEntity<PageDTO<AtividadeMuralDTO>> listarAtividadeMural(@RequestParam(required = false, defaultValue = "0") Integer pagina, @RequestParam(required = false, defaultValue = "5") Integer tamanho,@RequestParam(required = false) Integer idTrilha) throws RegraDeNegocioException {
        return new ResponseEntity<>(atividadeService.listarAtividadeMuralInstrutor(pagina, tamanho, idTrilha), HttpStatus.OK);
    }

    @Operation(summary = "Listar atividade no mural do aluno", description = "Listar atividade no mural do aluno")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Listar atividade no mural do aluno com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-mural-aluno")
    public ResponseEntity<PageDTO<AtividadeMuralAlunoDTO>> listarAtividadeMuralAluno(@RequestParam(required = false) Integer pargina, @RequestParam(required = false) Integer tamanho, @RequestParam(required = false) AtividadeStatus atividadeStatus, @RequestParam(required = false) Integer idUsuario) throws RegraDeNegocioException {
        return new ResponseEntity<>(atividadeService.listarAtividadeMuralAluno(pargina, tamanho, idUsuario, atividadeStatus), HttpStatus.OK);
    }

    @Operation(summary = "Listar atividade por trilha e modulo", description = "Listar atividade por trilha e modulo")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Listar atividade por trilha e modulo com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar-trilha-modulo")
    public ResponseEntity<PageDTO<AtividadeNotaDTO>> listarAtividadePorNota(@RequestParam(required = false, defaultValue = "0") Integer pagina, @RequestParam(required = false, defaultValue = "5") Integer tamanho, @RequestParam(required = false, defaultValue = "2") Integer idTrilha, @RequestParam(required = false, defaultValue = "1") Integer idModulo, @RequestParam(required = false) AtividadeStatus atividadeStatus) throws RegraDeNegocioException {
        return new ResponseEntity<>(atividadeService.listarAtividadePorIdTrilhaIdModulo(pagina, tamanho, idTrilha, idModulo, atividadeStatus), HttpStatus.OK);
    }

    @Operation(summary = "Entregar atividade", description = "Entregar atividade")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Entregar atividade com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/entregar-aluno/{idAtividade}")
    public ResponseEntity<AtividadeAlunoEnviarDTO> entregarAtividade(@PathVariable(name = "idAtividade") Integer idAtividade, AtividadeAlunoEnviarDTO atividadeAlunoEnviarDTO) throws RegraDeNegocioException {
        return new ResponseEntity<>(atividadeService.entregarAtividade(atividadeAlunoEnviarDTO, idAtividade), HttpStatus.OK);

    }

    @Operation(summary = "Pega a atividade pelo id", description = "Resgata a atividade pelo id do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foi resgatado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/find-id-atividade")
    public ResponseEntity<AtividadeDTO> findById(Integer idAtividade) throws RegraDeNegocioException {
        return new ResponseEntity<>(atividadeService.findById(idAtividade), HttpStatus.OK);
    }

}
