package application.system.condominio;

public enum TipoMorador {
    PROPRIETARIO,
    INQUILINO,
    DEPENDENTE;

    // --- RETORNA O NOME FORMATADO DO TIPO DE MORADOR ---
    public String getNomeFormatado() {
        return switch (this) {
            case PROPRIETARIO -> "Proprietário";
            case INQUILINO -> "Inquilino";
            case DEPENDENTE -> "Dependente";
        };
    }
}

