package application.system.inventario;

public enum CategoriaProduto {
    ALIMENTOS,
    BEBIDAS,
    ELETRONICOS,
    ESCRITORIO,
    HIGIENE,
    LIMPEZA,
    VESTUARIO,
    OUTROS;

    // --- RETORNA O NOME FORMATADO DA CATEGORIA ---
    public String getNomeFormatado() {
        return switch (this) {
            case ALIMENTOS -> "Alimentos";
            case BEBIDAS -> "Bebidas";
            case ELETRONICOS -> "Eletrônicos";
            case ESCRITORIO -> "Escritório";
            case HIGIENE -> "Higiene";
            case LIMPEZA -> "Limpeza";
            case VESTUARIO -> "Vestuário";
            case OUTROS -> "Outros";
        };
    }
}

