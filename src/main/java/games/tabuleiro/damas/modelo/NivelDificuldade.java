package games.tabuleiro.damas.modelo;

/**
 * Níveis de dificuldade do BOT.
 * A profundidade define quantos lances à frente o Minimax analisa; a chance de erro
 * faz o BOT escolher, às vezes, uma jogada válida aleatória (deixando-o mais "humano").
 */
public enum NivelDificuldade {

    FACIL("Fácil", 2, 0.30),
    MEDIO("Médio", 4, 0.05),
    DIFICIL("Difícil", 6, 0.0);

    private final String descricao;
    private final int profundidade;
    private final double chanceErro;

    NivelDificuldade(String descricao, int profundidade, double chanceErro) {
        this.descricao = descricao;
        this.profundidade = profundidade;
        this.chanceErro = chanceErro;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getProfundidade() {
        return profundidade;
    }

    public double getChanceErro() {
        return chanceErro;
    }
}
