package br.com.vemrankser.ranqueamento.enums;

public enum AtividadeStatus {
    PENDENTE(0), CONCLUIDA(1);

    private Integer atividadeStatus;

    AtividadeStatus(Integer atividadeStatus) {
        this.atividadeStatus = atividadeStatus;
    }

    public Integer getAtividadeStatus() {
        return atividadeStatus;
    }
}
