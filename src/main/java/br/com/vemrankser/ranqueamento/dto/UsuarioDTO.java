package br.com.vemrankser.ranqueamento.dto;

import br.com.vemrankser.ranqueamento.entity.UsuarioEntity;
import lombok.Data;

import java.util.Set;

@Data
public class UsuarioDTO {
    private Integer idUsuario;
    private String nome;
    private String email;
    private Integer statusUsuario;
    private Integer tipoPerfil;
    private String login;
    private String cidade;
    private String especialidade;

}
