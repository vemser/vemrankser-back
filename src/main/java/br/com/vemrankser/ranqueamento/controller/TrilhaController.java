package br.com.vemrankser.ranqueamento.controller;

import br.com.vemrankser.ranqueamento.dto.*;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/trilha")
public class TrilhaController {

    private final TrilhaService trilhaService;

    @Operation(summary = "Adicionar Trilha", description = "Adicionar uma nova trilha ")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Trilha adicionada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<TrilhaDTO> adicionarTrilha(@RequestBody @Valid TrilhaCreateDTO trilhaCreateDTO) throws RegraDeNegocioException {
        log.info("Criando Trilha...");
        TrilhaDTO trilhaDTO = trilhaService.adicionar(trilhaCreateDTO);
        log.info("Trilha Criada com sucesso!!");
        return new ResponseEntity<>(trilhaDTO, HttpStatus.OK);
    }

    @Operation(summary = "Adicionar aluno a trilha", description = "Adicionar aluno a uma nova trilha")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "aluno adicionado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/adicionar-aluno-trilha")
    public ResponseEntity<TrilhaDTO> adicionarAluno(String nomeTrilha, Integer edicao, String login) throws RegraDeNegocioException {
        TrilhaDTO trilhaDTO = trilhaService.adicionarAlunoTrilha(nomeTrilha, edicao, login);
        return new ResponseEntity<>(trilhaDTO, HttpStatus.OK);
    }

    @Operation(summary = "Pega a lista de usuários na trilha", description = "Resgata a lista de usuários na trilha do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foi resgatado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/lista-usuarios")
    public ResponseEntity<PageDTO<TrilhaPaginadoDTO>> listUsuarios(Integer pagina, Integer tamanho, @RequestParam(defaultValue = "") String nome) {
        return new ResponseEntity<>(trilhaService.listarUsuariosNaTrilha(pagina, tamanho, nome), HttpStatus.OK);
    }

    @Operation(summary = "Pega a lista de usuários na trilha pela pontuação", description = "Resgata a lista de usuários na trilha pela pontuacao no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foi resgatado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/lista-ranking")
    public ResponseEntity<List<RankingDTO>> listranking(Integer idTrilha) throws RegraDeNegocioException {
        return new ResponseEntity<>(trilhaService.rankingtrilha(idTrilha), HttpStatus.OK);
    }

    @Operation(summary = "Pega o nome da trilha ou lista", description = "Resgata o nome da trilha ou lista do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foi resgatado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/lista-trilha-nome")
    public ResponseEntity<List<TrilhaDTO>> findTrilhaByNome(@RequestParam(required = false) String nome) {
        return new ResponseEntity<>(trilhaService.findTrilhaByNome(nome), HttpStatus.OK);
    }


    @Operation(summary = "Pega a edicao da trilha ou a lista", description = "Resgata a edicao da trilha ou lista do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foi resgatado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/find-edicao")
    public ResponseEntity<List<TrilhaDTO>> findEdicao(@RequestParam(required = false) Integer edicao) {
        return new ResponseEntity<>(trilhaService.findAllEdicao(edicao), HttpStatus.OK);
    }

    @Operation(summary = "Adicionar instrutor a trilha", description = "Adicionar instrutor a uma nova trilha")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "aluno adicionado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/adicionar-instrutor-trilha")
    public ResponseEntity<TrilhaDTO> adicionarInstrutor(String nomeTrilha, Integer edicao, String login) throws RegraDeNegocioException {
        TrilhaDTO trilhaDTO = trilhaService.adicionarIntrustorTrilha(nomeTrilha, edicao, login);
        return new ResponseEntity<>(trilhaDTO, HttpStatus.OK);
    }

    @Operation(summary = "Pega trilha pelo id", description = "Resgata a trilha pelo id do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foi resgatado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/find-id-trilha")
    public ResponseEntity<TrilhaDTO> findById(Integer idTrilha) throws RegraDeNegocioException {
        return new ResponseEntity<>(trilhaService.pegarIdTrilha(idTrilha), HttpStatus.OK);
    }
}
