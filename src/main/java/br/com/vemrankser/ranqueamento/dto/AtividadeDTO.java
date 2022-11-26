package br.com.vemrankser.ranqueamento.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AtividadeDTO extends AtividadeCreateDTO {

    @NotNull
    @Schema(example = "1")
    private Integer idAtividade;
}
