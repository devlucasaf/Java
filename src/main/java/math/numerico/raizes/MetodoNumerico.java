package math.numerico.raizes;

public enum MetodoNumerico {
    BISSECAO,
    NEWTON_RAPHSON,
    SECANTE;

    // --- RETORNA O NOME FORMATADO DO MÉTODO ---
    public String getNomeFormatado() {
        return switch (this) {
            case BISSECAO -> "Bisseção";
            case NEWTON_RAPHSON -> "Newton-Raphson";
            case SECANTE -> "Secante";
        };
    }
}

