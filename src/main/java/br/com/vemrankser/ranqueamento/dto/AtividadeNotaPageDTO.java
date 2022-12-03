package br.com.vemrankser.ranqueamento.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AtividadeNotaPageDTO {

    private String nome;
    private Integer idAtividade;
    private Integer nota;
    private String link;
}
