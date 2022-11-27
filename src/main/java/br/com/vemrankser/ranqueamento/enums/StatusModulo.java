package br.com.vemrankser.ranqueamento.enums;

public enum StatusModulo {

    S("S"),
    N("N");

    private final String descricao;

    StatusModulo(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
