package games.text.debate;

public enum CriterioAvaliacao {
    COERENCIA,
    RELEVANCIA,
    CLAREZA,
    EVIDENCIAS,
    ESTRUTURA,
    VOCABULARIO;

    // --- RETORNA O NOME FORMATADO DO CRITÉRIO ---
    public String getNomeFormatado() {
        return switch (this) {
            case COERENCIA -> "Coerência";
            case RELEVANCIA -> "Relevância";
            case CLAREZA -> "Clareza";
            case EVIDENCIAS -> "Evidências";
            case ESTRUTURA -> "Estrutura";
            case VOCABULARIO -> "Vocabulário";
        };
    }
}

