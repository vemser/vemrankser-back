package br.com.vemrankser.ranqueamento.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AlunoTrilhaPersonalizadoDTO {
    private Integer idUsuario;
    @Lob
    private byte[] foto;
    private String nome;
    private String email;
    private String login;
    private Integer statusUsuario;
    private Integer tipoPerfil;
   // private List<TrilhaDTO> trilhas;
    private String nomeTrilha;
    private Integer edicao;
    private LocalDate anoEdicao;
    private Integer idTrilha;
}
