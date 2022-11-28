package br.com.vemrankser.ranqueamento.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
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

    @FutureOrPresent
    @Schema(description = "Data de início da atividade", example = "15/02/2023")
    private LocalDateTime dataCriacao;

    @FutureOrPresent
    @Schema(description = "Data final para entrega da atividade", example = "16/02/2023")
    private LocalDateTime dataEntrega;

    @Schema(description = "Envio de link para correção", example = "www.github.com")
    private String link;

    @Schema(description = "Instrutor", example = "Rafael Lazari")
    private String nomeInstrutor;
}
