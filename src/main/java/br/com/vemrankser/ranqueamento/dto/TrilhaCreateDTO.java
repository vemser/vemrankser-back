package br.com.vemrankser.ranqueamento.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TrilhaCreateDTO {

    @Schema(description = "Nome", example = "Maicon gerardi")
    private String nome;

    @Schema(description = "Edição do vem ser", example = "11")
    private Integer edicao;

    @Schema(description = "Ano da edição", example = "2023")
    private Integer anoEdicao;

}
