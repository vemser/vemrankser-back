package br.com.vemrankser.ranqueamento.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class AtividadeTrilhaIdEntity implements Serializable {


    @Column(name = "ID_TRILHA")
    private Integer idTrilha;


    @Column(name = "ID_ATIVIDADE")
    private Integer idAtividade;
}
