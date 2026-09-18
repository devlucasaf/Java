package games.text.debate;

public enum LadoDebate {
    FAVORAVEL,
    CONTRARIO;

    // --- RETORNA O NOME FORMATADO DO LADO ---
    public String getNomeFormatado() {
        return switch (this) {
            case FAVORAVEL -> "Favorável";
            case CONTRARIO -> "Contrário";
        };
    }

    // --- RETORNA O LADO OPOSTO ---
    public LadoDebate getOposto() {
        return this == FAVORAVEL ? CONTRARIO : FAVORAVEL;
    }
}

