package br.com.vemrankser.ranqueamento.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class UsuarioEntity {

    @Id
    private Integer idUsuario;

    private Integer idTrilha;

    private String nome;

    private String login;

    private String email;

    @Column(name = "senha")
    private String senha;

    @Column(name = "status")
    private Integer statusUsuario;

    @Column(name = "tipo_perfil")
    private Integer tipoPerfil;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "atuacao")
    private String atuacao;

    @Column(name = "especialidade")
    private String especialidade;

}
