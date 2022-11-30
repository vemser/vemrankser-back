package br.com.vemrankser.ranqueamento.dto;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class AtividadeDTO {
    private Integer idAtividade;
    private String nomeInstrutor;
    private String titulo;
    private Integer pesoAtividade;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataEntrega;
}
