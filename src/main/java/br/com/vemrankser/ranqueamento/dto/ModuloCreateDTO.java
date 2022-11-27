package br.com.vemrankser.ranqueamento.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class ModuloCreateDTO {

    @NotNull
    private String nome;

    @Schema(description = "Data inicial do modulo ", example = "26/11/2022")
    private LocalDateTime dataInicio;

    @FutureOrPresent
    @Schema(description = "Data para o fim do modulo ", example = "30/11/2022")
    private LocalDateTime dataFim;

}
