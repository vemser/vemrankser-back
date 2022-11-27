package br.com.vemrankser.ranqueamento.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ComentarioDTO extends ComentarioCreateDTO {

//    @NotNull
    @Schema(example = "1")
    private Integer idComentario;
}
