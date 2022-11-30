package br.com.vemrankser.ranqueamento.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UsuarioAtualizarDTO {
//    @Schema(description = "Sua foto", example = "foto")
//    private Byte[] foto;

    @NotNull
    @Schema(description = "Seu nome", example = "diego")
    private String nome;

    @NotNull
    @Schema(description = "Seu login", example = "diego.jose")
    private String login;

    @NotNull
    @Schema(description = "Seu email", example = "diego@hotmail.com")
    private String email;

    @NotNull
    @Schema(description = "Sua senha", example = "123")
    private String senha;

    @NotNull
    @Schema(description = "Seu status", example = "1")
    private Integer statusUsuario;

//    @NotNull
//    @Schema(description = "Sua trilha", example = "back-end")
//    private String atuacao;


//    @NotNull
//    @Schema(description = "Tipo do perfil",example = "instrutor")
//    private Integer tipoPerfil;

    @NotNull
    @Schema(description = "Sua cidade", example = "Recife")
    private String cidade;


    @Schema(description = "Especialidade do instrutor", example = "QA")
    private String especialidade;


}
