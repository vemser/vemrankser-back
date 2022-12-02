package br.com.vemrankser.ranqueamento.dto;

import lombok.Data;

import java.util.List;

@Data
public class AlunoTrilhaDTO {
    private Integer idUsuario;
    private byte[] foto;
    private String nome;
    private String email;
    private String login;
    private Integer statusUsuario;
    private Integer tipoPerfil;
    private List<TrilhaDTO> trilhas;
}
