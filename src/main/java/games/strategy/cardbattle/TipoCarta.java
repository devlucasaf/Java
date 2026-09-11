package games.strategy.cardbattle;

public enum TipoCarta {
    ATAQUE,
    DEFESA,
    CURA,
    SUPORTE;

    // --- RETORNA O NOME FORMATADO DO TIPO DA CARTA ---
    public String getNomeFormatado() {
        return switch (this) {
            case ATAQUE -> "Ataque";
            case DEFESA -> "Defesa";
            case CURA -> "Cura";
            case SUPORTE -> "Suporte";
        };
    }
}

