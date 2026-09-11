package application.system.condominio;

public enum SituacaoBoleto {
    PENDENTE,
    PAGO,
    VENCIDO,
    CANCELADO;

    // --- RETORNA O NOME FORMATADO DA SITUAÇÃO ---
    public String getNomeFormatado() {
        return switch (this) {
            case PENDENTE -> "Pendente";
            case PAGO -> "Pago";
            case VENCIDO -> "Vencido";
            case CANCELADO -> "Cancelado";
        };
    }
}

