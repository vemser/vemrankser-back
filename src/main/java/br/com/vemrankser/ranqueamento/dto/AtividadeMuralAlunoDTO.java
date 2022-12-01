package br.com.vemrankser.ranqueamento.dto;

import br.com.vemrankser.ranqueamento.enums.AtividadeStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AtividadeMuralAlunoDTO {

    private Integer idAtividade;

    private String titulo;

    private String instrucoes;

    private Integer pesoAtividade;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataEntrega;

    private Integer idModulo;

    private AtividadeStatus statusAtividade;

    private String nomeModulo;

    private String trilhaNome;

    private Integer edicao;
}
