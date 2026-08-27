package games.puzzle.nonogram;

public enum Dificuldade {
    FACIL(5, 5, 0.45),
    MEDIO(10, 10, 0.50),
    DIFICIL(15, 15, 0.55),
    ESPECIALISTA(20, 20, 0.60);

    private final int quantidadeLinhas;
    private final int quantidadeColunas;
    private final double probabilidadePreenchimento;

    Dificuldade(int quantidadeLinhas, int quantidadeColunas, double probabilidadePreenchimento) {
        this.quantidadeLinhas = quantidadeLinhas;
        this.quantidadeColunas = quantidadeColunas;
        this.probabilidadePreenchimento = probabilidadePreenchimento;
    }

    // --- RETORNA A QUANTIDADE DE LINHAS DO NÍVEL ---
    public int getQuantidadeLinhas() {
        return quantidadeLinhas;
    }

    // --- RETORNA A QUANTIDADE DE COLUNAS DO NÍVEL ---
    public int getQuantidadeColunas() {
        return quantidadeColunas;
    }

    // --- RETORNA A PROBABILIDADE DE UMA CÉLULA SER PREENCHIDA ---
    public double getProbabilidadePreenchimento() {
        return probabilidadePreenchimento;
    }

    // --- RETORNA O NOME FORMATADO DA DIFICULDADE ---
    public String getNomeFormatado() {
        switch (this) {
            case FACIL:
                return "Fácil";
            case MEDIO:
                return "Médio";
            case DIFICIL:
                return "Difícil";
            case ESPECIALISTA:
                return "Especialista";
            default:
                return name();
        }
    }
}

