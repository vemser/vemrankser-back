package br.com.vemrankser.ranqueamento.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AtividadeDTO extends AtividadeCreateDTO {

    @NotNull
    @Schema(example = "1")
    private Integer idAtividade;
}
