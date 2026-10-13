package games.tabuleiro.damas.model;

public enum Jogador {

    JOGADOR_1("Jogador 1", "Peças claras", -1, 0),
    JOGADOR_2("Jogador 2", "Peças vermelhas", 1, Tabuleiro.TAMANHO - 1);

    private final String    nome;
    private final String    descricaoPecas;
    private final int       direcaoAvanco;
    private final int       linhaPromocao;

    Jogador(String nome, String descricaoPecas, int direcaoAvanco, int linhaPromocao) {
        this.nome = nome;
        this.descricaoPecas = descricaoPecas;
        this.direcaoAvanco = direcaoAvanco;
        this.linhaPromocao = linhaPromocao;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricaoPecas() {
        return descricaoPecas;
    }

    public int getDirecaoAvanco() {
        return direcaoAvanco;
    }

    public int getLinhaPromocao() {
        return linhaPromocao;
    }

    public Jogador getAdversario() {
        return this == JOGADOR_1 ? JOGADOR_2 : JOGADOR_1;
    }
}
