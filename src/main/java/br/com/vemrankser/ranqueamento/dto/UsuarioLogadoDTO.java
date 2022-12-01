package br.com.vemrankser.ranqueamento.dto;

import lombok.Data;

import java.util.List;

@Data
public class UsuarioLogadoDTO {
    private String nome;
    private Integer tipoPerfil;
    private String email;
    private String login;
    private byte[] foto;
}
