package games.narrativo.escape;

public enum Direcao {
    NORTE,
    SUL,
    LESTE,
    OESTE;

    // --- RETORNA A DIREÇÃO CORRESPONDENTE AO TEXTO INFORMADO ---
    public static Direcao converter(String texto) {
        if (texto == null) {
            return null;
        }

        switch (texto.trim().toLowerCase()) {
            case "norte":
            case "n":
                return NORTE;
            case "sul":
            case "s":
                return SUL;
            case "leste":
            case "l":
                return LESTE;
            case "oeste":
            case "o":
                return OESTE;
            default:
                return null;
        }
    }

    // --- RETORNA O NOME FORMATADO DA DIREÇÃO ---
    public String getNomeFormatado() {
        return name().toLowerCase();
    }
}

