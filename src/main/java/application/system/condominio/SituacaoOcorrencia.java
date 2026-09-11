package application.system.condominio;

public enum SituacaoOcorrencia {
    ABERTA,
    EM_ANALISE,
    RESOLVIDA,
    ARQUIVADA;

    // --- RETORNA O NOME FORMATADO DA SITUAÇÃO ---
    public String getNomeFormatado() {
        return switch (this) {
            case ABERTA -> "Aberta";
            case EM_ANALISE -> "Em análise";
            case RESOLVIDA -> "Resolvida";
            case ARQUIVADA -> "Arquivada";
        };
    }
}

