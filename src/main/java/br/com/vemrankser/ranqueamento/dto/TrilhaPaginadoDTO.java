package br.com.vemrankser.ranqueamento.dto;

import lombok.Data;

import java.util.List;

@Data
public class TrilhaPaginadoDTO extends TrilhaCreateDTO{

    private Integer idTrilha;
    private List<UsuarioDTO> usuarios;

}
