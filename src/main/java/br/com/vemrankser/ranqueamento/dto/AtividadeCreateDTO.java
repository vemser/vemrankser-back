package br.com.vemrankser.ranqueamento.dto;

import br.com.vemrankser.ranqueamento.entity.ModuloEntity;
import br.com.vemrankser.ranqueamento.entity.TrilhaEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

//    @NotNull
//    @FutureOrPresent
//    @Schema(description = "Data de início da atividade", example = "15/02/2023")
//    private LocalDateTime dataCriacao;

    @NotNull
    @FutureOrPresent
    @Schema(description = "Data final para entrega da atividade", example = "16/02/2023")
    private LocalDateTime dataEntrega;

    @Schema(description = "Instrutor", example = "Rafael Lazari")
    private String nomeInstrutor;

    @NotEmpty
    @Schema(description = "Tipos de trilha", example = "QA")
    private List<TrilhaCreateDTO> trilhas;

    @NotEmpty
    @Schema(description = "Tipo do módulo", example = "Java OO")
    private List<ModuloCreateDTO> modulos;
}
