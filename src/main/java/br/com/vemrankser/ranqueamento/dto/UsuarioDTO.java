package br.com.vemrankser.ranqueamento.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Integer idUsuario;
    private String nome;
    private String email;
    private Integer statusUsuario;
}
