package br.com.vemrankser.ranqueamento.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AtividadeMuralDTO {

    private String nome;
    private LocalDateTime dataEntrega;

}
