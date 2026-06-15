package org.application.system.cinema;

public enum ClassificacaoIndicativa {
    LIVRE(0),
    DEZ_ANOS(10),
    DOZE_ANOS(12),
    QUATORZE_ANOS(14),
    DEZESSEIS_ANOS(16),
    DEZOITO_ANOS(18);

    private int idadeMinima;

    ClassificacaoIndicativa(int idadeMinima) {
        this.idadeMinima = idadeMinima;
    }

    public int getIdadeMinima() {
        return idadeMinima;
    }
}
