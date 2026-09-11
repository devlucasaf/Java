package math.numerico.raizes;

public enum SituacaoResultado {
    CONVERGIU,
    MAXIMO_ITERACOES,
    INTERVALO_INVALIDO,
    DERIVADA_PROXIMA_DE_ZERO,
    DENOMINADOR_PROXIMO_DE_ZERO,
    VALOR_NAO_FINITO,
    ERRO_CALCULO;

    // --- RETORNA O NOME FORMATADO DA SITUAÇÃO ---
    public String getNomeFormatado() {
        return switch (this) {
            case CONVERGIU -> "Convergiu";
            case MAXIMO_ITERACOES -> "Máximo de iterações atingido";
            case INTERVALO_INVALIDO -> "Intervalo inválido";
            case DERIVADA_PROXIMA_DE_ZERO -> "Derivada próxima de zero";
            case DENOMINADOR_PROXIMO_DE_ZERO -> "Denominador próximo de zero";
            case VALOR_NAO_FINITO -> "Valor numérico não finito";
            case ERRO_CALCULO -> "Erro durante o cálculo";
        };
    }
}

