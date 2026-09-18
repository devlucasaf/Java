package application.system.inventario;

public enum ClassificacaoABC {
    A,
    B,
    C;

    // --- RETORNA A DESCRIÇÃO DA CLASSIFICAÇÃO ---
    public String getDescricao() {
        return switch (this) {
            case A -> "Alta importância";
            case B -> "Importância intermediária";
            case C -> "Menor importância";
        };
    }
}

