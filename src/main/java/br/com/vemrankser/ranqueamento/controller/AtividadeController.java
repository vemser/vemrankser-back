package br.com.vemrankser.ranqueamento.controller;

import br.com.vemrankser.ranqueamento.dto.AtividadeAvaliarDTO;
import br.com.vemrankser.ranqueamento.dto.AtividadeCreateDTO;
import br.com.vemrankser.ranqueamento.dto.AtividadeDTO;
import br.com.vemrankser.ranqueamento.dto.AtividadePaginacaoDTO;
import br.com.vemrankser.ranqueamento.entity.AtividadeEntity;
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
import java.util.Optional;

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
    public ResponseEntity<AtividadeCreateDTO> create(@RequestBody @Valid AtividadeCreateDTO atividadeCreateDTO, Integer idModulo) throws RegraDeNegocioException {

        log.info("Criando nova atidade....");
        AtividadeDTO atividadeDTO = atividadeService.adicionar(atividadeCreateDTO, idModulo);
        log.info("Atividade criada com sucesso!");

        return new ResponseEntity<>(atividadeDTO, HttpStatus.OK);
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
    public ResponseEntity<AtividadeAvaliarDTO> avaliarAtividade(@PathVariable(name = "idAtividade") Integer idAtividade, @RequestBody AtividadeAvaliarDTO atividadeAvaliarDTO) throws RegraDeNegocioException {
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
    public ResponseEntity<List<Optional<AtividadeEntity>>> listarAtibuscarAtividadePorStatusvidadePaginado(Integer atividadeStatus) throws RegraDeNegocioException {
        return ResponseEntity.ok(atividadeService.buscarAtividadePorStatus(atividadeStatus));
    }
}
