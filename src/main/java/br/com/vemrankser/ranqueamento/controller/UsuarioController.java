package br.com.vemrankser.ranqueamento.controller;

import br.com.vemrankser.ranqueamento.dto.LoginDTO;
import br.com.vemrankser.ranqueamento.service.AuthService;
import br.com.vemrankser.ranqueamento.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/usuario")
public class UsuarioController {

    private final AuthService authService;
    private final UsuarioService usuarioService;

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
}
