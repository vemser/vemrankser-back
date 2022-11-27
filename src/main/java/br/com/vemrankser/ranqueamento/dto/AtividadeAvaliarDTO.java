package br.com.vemrankser.ranqueamento.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class AtividadeAvaliarDTO {

    @Min(value = 0, message = "nota não pode ser menor que zero")
    @Max(value = 100, message = "nota não pode ser maior que 100")
    @Schema(description = "Pontução da atividade", example = "90")
    private Integer pontuacao;
}
