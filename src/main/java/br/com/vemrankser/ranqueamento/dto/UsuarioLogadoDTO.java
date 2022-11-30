package br.com.vemrankser.ranqueamento.dto;

import lombok.Data;

@Data
public class UsuarioLogadoDTO {
    private String nome;
    private Integer tipoPerfil;
    private byte[] foto;
}
