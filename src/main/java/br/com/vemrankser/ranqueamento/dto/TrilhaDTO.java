package br.com.vemrankser.ranqueamento.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class TrilhaDTO  extends TrilhaCreateDTO{

    private Integer idTrilha;
    private UsuarioDTO usuarios;

}
