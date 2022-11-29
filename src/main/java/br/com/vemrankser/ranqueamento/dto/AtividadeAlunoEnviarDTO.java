package br.com.vemrankser.ranqueamento.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AtividadeAlunoEnviarDTO {

    @Schema(description = "Link do repositório", example = "github.com")
    private String link;
}
