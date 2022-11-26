package br.com.vemrankser.ranqueamento.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class AtividadeCreateDTO {

    @NotEmpty
    @Schema(description = "título da atividade", example = "POO")
    private String Titulo;

    @NotEmpty
    @Schema(description = "Instruções para a atividade", example = "Exercício de orientação à objeto")
    private String instrucoes;

    @NotNull
    @Schema(description = "Peso da atividade", example = "2")
    private Integer pesoAtividade;

    @NotEmpty
    @Schema(description = "Data de início da atividade", example = "15/02/2023")
    private LocalDateTime dataCriacao;

    @NotEmpty
    @Schema(description = "Data final para entrega da atividade", example = "16/02/2023")
    private LocalDateTime dataEntrega;

    @Schema(description = "Pontução da atividade", example = "90")
    private Integer pontuacao;

    @Schema(description = "Envio de link para correção", example = "www.github.com")
    private String link;

    @NotNull
    @Schema(description = "Atividade ativada ou desativada", example = "ATIVO")
    private Integer statusAtividade;
}
