package br.com.vemrankser.ranqueamento.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RankingDTO {

    private String nome;
    private Integer pontuacaoAluno;

}

