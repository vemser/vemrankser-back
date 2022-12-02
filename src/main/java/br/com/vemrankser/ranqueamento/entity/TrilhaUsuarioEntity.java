package br.com.vemrankser.ranqueamento.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "TRILHA_USUARIO")
public class TrilhaUsuarioEntity {

    @EmbeddedId
    private TrilhaUsuarioIdEntity trilhaUsuarioIdEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idTrilha")
    @JoinColumn(name = "ID_TRILHA")
    private TrilhaEntity trilha;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idUsuario")
    @JoinColumn(name = "ID_USUARIO", insertable = false, updatable = false)
    private UsuarioEntity usuarioEntity;
}
