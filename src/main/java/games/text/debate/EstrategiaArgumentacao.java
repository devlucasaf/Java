package games.text.debate;

public enum EstrategiaArgumentacao {
    CAUSA_E_EFEITO,
    EXEMPLO_PRATICO,
    COMPARACAO,
    REFUTACAO,
    PROPOSTA_SOLUCAO,
    PRINCIPIO_GERAL;

    // --- RETORNA O NOME FORMATADO DA ESTRATÉGIA ---
    public String getNomeFormatado() {
        return switch (this) {
            case CAUSA_E_EFEITO -> "Causa e efeito";
            case EXEMPLO_PRATICO -> "Exemplo prático";
            case COMPARACAO -> "Comparação";
            case REFUTACAO -> "Refutação";
            case PROPOSTA_SOLUCAO -> "Proposta de solução";
            case PRINCIPIO_GERAL -> "Princípio geral";
        };
    }

    // --- RETORNA UMA ORIENTAÇÃO PARA O JOGADOR ---
    public String getOrientacao() {
        return switch (this) {
            case CAUSA_E_EFEITO -> "Explique como uma ação produz determinada consequência.";
            case EXEMPLO_PRATICO -> "Apresente uma situação concreta que sustente sua posição.";
            case COMPARACAO -> "Compare duas situações e destaque diferenças relevantes.";
            case REFUTACAO -> "Responda a uma ideia contrária e explique por que ela é insuficiente.";
            case PROPOSTA_SOLUCAO -> "Apresente uma solução e explique como ela poderia funcionar.";
            case PRINCIPIO_GERAL -> "Defenda sua posição com base em um princípio ou valor geral.";
        };
    }
}

