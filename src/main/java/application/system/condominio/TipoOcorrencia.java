package application.system.condominio;

public enum TipoOcorrencia {
    BARULHO,
    DANOS,
    SEGURANCA,
    LIMPEZA,
    ESTACIONAMENTO,
    DESCUMPRIMENTO_REGRA,
    OUTRO;

    // --- RETORNA O NOME FORMATADO DO TIPO DE OCORRÊNCIA ---
    public String getNomeFormatado() {
        return switch (this) {
            case BARULHO -> "Barulho";
            case DANOS -> "Danos";
            case SEGURANCA -> "Segurança";
            case LIMPEZA -> "Limpeza";
            case ESTACIONAMENTO -> "Estacionamento";
            case DESCUMPRIMENTO_REGRA -> "Descumprimento de regra";
            case OUTRO -> "Outro";
        };
    }
}

