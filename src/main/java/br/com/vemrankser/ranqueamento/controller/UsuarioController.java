package br.com.vemrankser.ranqueamento.controller;

import br.com.vemrankser.ranqueamento.dto.*;
import br.com.vemrankser.ranqueamento.enums.TipoPerfil;
import br.com.vemrankser.ranqueamento.exceptions.RegraDeNegocioException;
import br.com.vemrankser.ranqueamento.service.AuthService;
import br.com.vemrankser.ranqueamento.service.TrilhaService;
import br.com.vemrankser.ranqueamento.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {

    private final AuthService authService;
    private final UsuarioService usuarioService;
    private final TrilhaService trilhaService;

    @Operation(summary = "Logar na sua conta", description = "Autentificar usuário no aplicativo")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Usuário autenticado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/login")
    public String auth(@RequestBody @Valid LoginDTO loginDTO) {
        return authService.auth(loginDTO);
    }

    @Operation(summary = "Cadastrar usuário", description = "Cadastra um novo aluno no banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Aluno cadastrado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioDTO> cadastrar(@Valid @RequestBody UsuarioCreateDTO usuario, TipoPerfil tipoPerfil) throws RegraDeNegocioException, RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.cadastrar(usuario, tipoPerfil), HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar conta do usuário", description = "Atualizar sua conta do aplicativo")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Conta atualizada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/atualizar/{idUsuario}")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@PathVariable(name = "idUsuario") Integer idUsuario, @RequestBody UsuarioAtualizarDTO usuario) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.editar(idUsuario, usuario), HttpStatus.OK);

    }

    @Operation(summary = "Pega a lista de usuários", description = "Resgata a lista de usuários do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foi resgatado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/lista-usuarios")
    public ResponseEntity<PageDTO<UsuarioDTO>> listUsuarios(@RequestParam(required = false, defaultValue = "0") Integer pagina, @RequestParam(required = false, defaultValue = "5") Integer tamanho, @RequestParam(value = "sort", required = false, defaultValue = "nome") String sort) {
        return new ResponseEntity<>(usuarioService.listarUsuarios(pagina, tamanho, sort), HttpStatus.OK);
    }

    @Operation(summary = "Pega a lista de alunos", description = "Resgata a lista de alunos do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foi resgatado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/lista-alunos")
    public ResponseEntity<PageDTO<UsuarioDTO>> listAlunos(@RequestParam(required = false, defaultValue = "0") Integer pagina, @RequestParam(required = false, defaultValue = "5") Integer tamanho) {
        return new ResponseEntity<>(usuarioService.listarAlunos(pagina, tamanho), HttpStatus.OK);
    }

    @Operation(summary = "Pega a lista de alunos e trilha", description = "Resgata a lista de alunos e trilha do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foi resgatado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/lista-alunos-trilha")
    public ResponseEntity<PageDTO<AlunoTrilhaPersonalizadoDTO>> listAlunosTrilha(@RequestParam(required = false, defaultValue = "0") Integer pagina, @RequestParam(required = false, defaultValue = "5") Integer tamanho, @RequestParam(defaultValue = "") String nome, @RequestParam(required = false) Integer idTrilha) {
        return new ResponseEntity<>(usuarioService.listAlunoTrilhaQuery(pagina, tamanho, nome, idTrilha), HttpStatus.OK);
    }

    @Operation(summary = "Pega a lista de alunos e trilha geral", description = "Resgata a lista de alunos e trilha geral do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foi resgatado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/lista-alunos-trilha-geral")
    public ResponseEntity<PageDTO<AlunoTrilhaDTO>> listAlunosTrilhaGeral(@RequestParam(required = false, defaultValue = "0") Integer pagina, @RequestParam(required = false, defaultValue = "5") Integer tamanho, @RequestParam(defaultValue = "") String nome) {
        return new ResponseEntity<>(usuarioService.listarAlunosTrilhaGeral(pagina, tamanho, nome), HttpStatus.OK);
    }

    @Operation(summary = "Pega aluno pelo login", description = "Resgata o aluno pelo login banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foi resgatado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/pegar-aluno")
    public ResponseEntity<UsuarioDTO> pegarAluno(String login) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.pegarLogin(login), HttpStatus.OK);
    }


    @Operation(summary = "Pega um usuário pelo nome", description = "Resgata um usuário pelo nome do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foi resgatado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/find-nome")
    public ResponseEntity<List<UsuarioDTO>> findByNome(@RequestParam(value = "nome", required = false) String nome) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.findByNome(nome), HttpStatus.OK);
    }

    @Operation(summary = "Pega um usuário pelo id", description = "Resgata um usuário pelo id do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foi resgatado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/find-id-usuario")
    public ResponseEntity<UsuarioDTO> findById(Integer id) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.pegarIdUsuario(id), HttpStatus.OK);
    }


    @Operation(summary = "Coloca a foto no usuário", description = "Coloca a foto no usuário do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foi atualizado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping(value = "/upload-imagem/{idUsuario}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UsuarioDTO> uploadImagem(@RequestPart(value = "file") MultipartFile file, @PathVariable(value = "idUsuario") Integer idUsuario) throws RegraDeNegocioException, IOException {
        return new ResponseEntity<>(usuarioService.uploadImagem(file, idUsuario), HttpStatus.OK);
    }


    @Operation(summary = "Pega foto do usuário", description = "Resgata a foto do usuário pelo nome do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foi resgatado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/find-foto/{idUsuario}")
    public ResponseEntity<UsuarioFotoDTO> pegarFotoUsuario(@PathVariable(name = "idUsuario") Integer idUsuario) throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.pegarImagemUsuario(idUsuario), HttpStatus.OK);
    }

    @Operation(summary = "Pegar usuário logado", description = "Resgata o usuário logado do banco de dados")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Foi resgatado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/pegar-usuario-logado")
    public ResponseEntity<UsuarioLogadoDTO> pegarUsuarioLogado() throws RegraDeNegocioException {
        return new ResponseEntity<>(usuarioService.getLoggedUserPersonalizado(), HttpStatus.OK);
    }

}
