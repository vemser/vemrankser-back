package br.com.vemrankser.ranqueamento.enums;

public enum TipoFeedback {
    POSITIVO(1), NEGATIVO(2);


    private Integer tipoFeedback;

    TipoFeedback(Integer tipoFeedback) {
        this.tipoFeedback = tipoFeedback;
    }

    public Integer getTipoFeedback() {
        return tipoFeedback;
    }
}
