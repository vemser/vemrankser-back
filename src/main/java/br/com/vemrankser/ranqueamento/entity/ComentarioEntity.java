package br.com.vemrankser.ranqueamento.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ComentarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMENTARIO_SEQUENCIA")
    @SequenceGenerator(name = "COMENTARIO_SEQUENCIA", sequenceName = "SEQ_COMENTARIO", allocationSize = 1)
    @Column(name = "id_comentario")
    private Integer idComentario;

    @Column(name = "comentario")
    private String comentario;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "COMENTARIO_ATIVIDADE",
            joinColumns = @JoinColumn(name = "id_comentario"),
            inverseJoinColumns = @JoinColumn(name = "id_atividade")
    )
    private Set<AtividadeEntity> atividades = new HashSet<>();
}
