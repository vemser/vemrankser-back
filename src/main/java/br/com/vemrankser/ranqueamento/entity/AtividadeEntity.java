package br.com.vemrankser.ranqueamento.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "link")
    private String link;

    @Column(name = "status_Atividade")
    private Integer statusAtividade;

    @Column(name = "nome_instrutor")
    private String nomeInstrutor;

    @JsonIgnore
    @OneToMany(mappedBy = "atividade", fetch = FetchType.LAZY)
    private Set<ComentarioEntity> comentarios = new HashSet<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_modulo", referencedColumnName = "id_modulo")
    private ModuloEntity modulo;

}
