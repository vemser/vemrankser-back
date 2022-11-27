package br.com.vemrankser.ranqueamento.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

@Data
public class ModuloCreateDTO {

    @NotNull
    private String nome;
    @NotNull
    private LocalDateTime dataInicio;
    @PastOrPresent
    private LocalDateTime dataFim;

    @NotNull
    private boolean statusModulo;
}
