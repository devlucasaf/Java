package application.system.inventario;

public enum TipoMovimentacao {
    ENTRADA,
    SAIDA,
    AJUSTE_POSITIVO,
    AJUSTE_NEGATIVO;

    // --- RETORNA O NOME FORMATADO DO TIPO DE MOVIMENTAÇÃO ---
    public String getNomeFormatado() {
        return switch (this) {
            case ENTRADA -> "Entrada";
            case SAIDA -> "Saída";
            case AJUSTE_POSITIVO -> "Ajuste positivo";
            case AJUSTE_NEGATIVO -> "Ajuste negativo";
        };
    }

    // --- VERIFICA SE A MOVIMENTAÇÃO AUMENTA O ESTOQUE ---
    public boolean isEntrada() {
        return this == ENTRADA || this == AJUSTE_POSITIVO;
    }

    // --- VERIFICA SE A MOVIMENTAÇÃO DIMINUI O ESTOQUE ---
    public boolean isSaida() {
        return this == SAIDA || this == AJUSTE_NEGATIVO;
    }
}

