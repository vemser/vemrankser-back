package br.com.vemrankser.ranqueamento.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "USUARIO")
public class UsuarioEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_SEQUENCIA")
    @SequenceGenerator(name = "USUARIO_SEQUENCIA", sequenceName = "SEQ_USUARIO", allocationSize = 1)
    @Column(name = "id_usuario")
    private Integer idUsuario;
    // alteracao nova
//    @Column(name = "id_atividade", insertable = false, updatable = false)
//    private Integer idAtividade;

    @Column(name = "foto")
    @Lob
    private byte[] foto;

    @Column(name = "nome")
    private String nome;

    @Column(name = "login")
    private String login;

    @Column(name = "email")
    private String email;
    @JsonIgnore
    @Column(name = "senha")
    private String senha;

    @Column(name = "pontuacao_aluno")
    private Integer pontuacaoAluno;

    @Column(name = "status")
    private Integer statusUsuario;
    // alteracao nova
    @Column(name = "tipo_perfil")
    private Integer tipoPerfil;

    @Column(name = "cidade")
    private String cidade;

//    @Column(name = "atuacao")
//    private String atuacao;

    @Column(name = "especialidade")
    private String especialidade;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "USUARIO_CARGO",
            joinColumns = @JoinColumn(name = "ID_USUARIO"),
            inverseJoinColumns = @JoinColumn(name = "ID_CARGO")
    )
    private Set<CargoEntity> cargos;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "TRILHA_USUARIO",
            joinColumns = @JoinColumn(name = "ID_USUARIO"),
            inverseJoinColumns = @JoinColumn(name = "ID_TRILHA")
    )
    private Set<TrilhaEntity> trilhas;
    // alteracao nova
//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "ID_ATIVIDADE", referencedColumnName = "ID_ATIVIDADE")
//    private AtividadeEntity atividade;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "ATIVIDADE_USUARIO",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_atividade")
    )
    private Set<AtividadeEntity> atividades;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return cargos;
    }
    @JsonIgnore
    @Override
    public String getPassword() {
        return senha;
    }
    @JsonIgnore
    @Override
    public String getUsername() {
        return email;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return statusUsuario == 1;
    }
}
