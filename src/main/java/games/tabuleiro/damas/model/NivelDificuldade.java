package games.tabuleiro.damas.model;

public enum NivelDificuldade {

    FACIL("Fácil", 2, 0.30),
    MEDIO("Médio", 4, 0.05),
    DIFICIL("Difícil", 6, 0.0);

    private final String    descricao;
    private final int       profundidade;
    private final double    chanceErro;

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
