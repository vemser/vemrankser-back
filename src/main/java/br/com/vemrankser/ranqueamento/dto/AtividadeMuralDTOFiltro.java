package br.com.vemrankser.ranqueamento.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AtividadeMuralDTOFiltro {

    private String nome;
    private LocalDateTime dataEntrega;

}
