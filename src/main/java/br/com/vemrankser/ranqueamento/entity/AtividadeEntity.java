package br.com.vemrankser.ranqueamento.entity;

import br.com.vemrankser.ranqueamento.enums.AtividadeStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "ATIVIDADE")
public class AtividadeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ATIVIDADE_SEQUENCIA")
    @SequenceGenerator(name = "ATIVIDADE_SEQUENCIA", sequenceName = "SEQ_ATIVIDADE", allocationSize = 1)
    @Column(name = "ID_ATIVIDADE")
    private Integer idAtividade;

    @Column(name = "id_modulo", insertable = false, updatable = false)
    private Integer idModulo;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "instrucoes")
    private String instrucoes;

    @Column(name = "peso_Atividade")
    private Integer pesoAtividade;
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
    @Column(name = "data_entrega")
    private LocalDateTime dataEntrega;

    @Column(name = "pontuacao")
    private Integer pontuacao;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status_Atividade")
    private AtividadeStatus statusAtividade;

    @Column(name = "nome_instrutor")
    private String nomeInstrutor;

    @JsonIgnore
    @OneToMany(mappedBy = "atividade", fetch = FetchType.LAZY)
    private Set<ComentarioEntity> comentarios;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_modulo", referencedColumnName = "id_modulo")
    private ModuloEntity modulo;


    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "ATIVIDADE_USUARIO",
            joinColumns = @JoinColumn(name = "id_atividade"),
            inverseJoinColumns = @JoinColumn(name = "id_usuario")
    )
    private Set<UsuarioEntity> alunos = new HashSet<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "ATIVIDADE_TRILHA",
            joinColumns = @JoinColumn(name = "id_atividade"),
            inverseJoinColumns = @JoinColumn(name = "id_trilha")
    )
    private Set<TrilhaEntity> trilhas = new HashSet<>();


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "atividade")
    private Set<LinkEntity> links;

}
