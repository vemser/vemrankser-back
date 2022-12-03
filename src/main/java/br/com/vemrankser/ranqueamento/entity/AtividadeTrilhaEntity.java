package br.com.vemrankser.ranqueamento.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "ATIVIDADE_TRILHA")
public class AtividadeTrilhaEntity {

    @EmbeddedId
    private AtividadeTrilhaPkEntity id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idTrilha")
    @JoinColumn(name = "ID_TRILHA")
    private TrilhaEntity trilha;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idAtividade")
    @JoinColumn(name = "ID_ATIVIDADE")
    private AtividadeEntity atividade;

}
