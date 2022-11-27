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
    @Column(name = "id_atividade")
    private Integer idAtividade;

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

    @JsonIgnore
    @OneToMany(mappedBy = "comentario", fetch = FetchType.LAZY)
    private Set<ComentarioEntity> comentarios = new HashSet<>();
}
