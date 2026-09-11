package games.strategy.cardbattle;

public enum TipoEfeito {
    AUMENTO_ATAQUE,
    REDUCAO_ATAQUE,
    AUMENTO_DEFESA,
    REDUCAO_DEFESA,
    VENENO,
    REGENERACAO;

    // --- RETORNA O NOME FORMATADO DO EFEITO ---
    public String getNomeFormatado() {
        return switch (this) {
            case AUMENTO_ATAQUE -> "Aumento de ataque";
            case REDUCAO_ATAQUE -> "Redução de ataque";
            case AUMENTO_DEFESA -> "Aumento de defesa";
            case REDUCAO_DEFESA -> "Redução de defesa";
            case VENENO -> "Veneno";
            case REGENERACAO -> "Regeneração";
        };
    }

    // --- VERIFICA SE O EFEITO É POSITIVO ---
    public boolean isPositivo() {
        return this == AUMENTO_ATAQUE || this == AUMENTO_DEFESA || this == REGENERACAO;
    }

    // --- VERIFICA SE O EFEITO É NEGATIVO ---
    public boolean isNegativo() {
        return !isPositivo();
    }
}

