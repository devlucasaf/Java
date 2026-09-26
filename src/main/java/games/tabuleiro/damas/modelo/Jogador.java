package games.tabuleiro.damas.modelo;

/**
 * Representa os dois participantes de uma partida de damas.
 * <p>
 * O Jogador 1 usa as peças claras, ocupa a parte inferior do tabuleiro e sempre inicia a partida.
 * O Jogador 2 usa as peças vermelhas e ocupa a parte superior do tabuleiro.
 */
public enum Jogador {

    JOGADOR_1("Jogador 1", "Peças claras", -1, 0),
    JOGADOR_2("Jogador 2", "Peças vermelhas", 1, Tabuleiro.TAMANHO - 1);

    private final String nome;
    private final String descricaoPecas;
    private final int direcaoAvanco;
    private final int linhaPromocao;

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

    /** Variação de linha que representa "andar para frente" para este jogador. */
    public int getDirecaoAvanco() {
        return direcaoAvanco;
    }

    /** Linha em que as peças comuns deste jogador são promovidas a dama. */
    public int getLinhaPromocao() {
        return linhaPromocao;
    }

    public Jogador getAdversario() {
        return this == JOGADOR_1 ? JOGADOR_2 : JOGADOR_1;
    }
}
