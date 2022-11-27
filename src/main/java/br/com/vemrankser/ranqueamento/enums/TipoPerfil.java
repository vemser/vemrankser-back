package br.com.vemrankser.ranqueamento.enums;


public enum TipoPerfil {
    COORDENADOR(1), ALUNO(2), INSTRUTOR(3), ADMINISTRADOR(4), GESTAO(5);

    private Integer tipoPerfil;

    TipoPerfil(Integer role) {
        this.tipoPerfil = role;
    }

    public Integer getCargo() {
        return tipoPerfil;
    }
}
