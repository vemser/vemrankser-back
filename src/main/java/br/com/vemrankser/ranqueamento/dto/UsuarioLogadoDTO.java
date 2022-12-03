package br.com.vemrankser.ranqueamento.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UsuarioLogadoDTO {
    private Integer idUsuario;
    private String nome;
    private Integer tipoPerfil;
    private String email;
    private String login;
    private String especialidade;
    private Set<TrilhaDTO> trilhas;
    private byte[] foto;

}
