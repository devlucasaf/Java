package games.tabuleiro.damas.modelo;

/**
 * Coordenada de uma casa do tabuleiro.
 * <p>
 * A linha 0 fica no topo da tela e a coluna 0 à esquerda. Na notação exibida ao usuário,
 * as colunas vão de "a" a "h" e as linhas de 1 (base) a 8 (topo).
 *
 * @param linha  índice da linha (0 a 7)
 * @param coluna índice da coluna (0 a 7)
 */
public record Posicao(int linha, int coluna) {

    public boolean dentroDoTabuleiro() {
        return linha >= 0 && linha < Tabuleiro.TAMANHO && coluna >= 0 && coluna < Tabuleiro.TAMANHO;
    }

    public boolean isCasaEscura() {
        return (linha + coluna) % 2 == 1;
    }

    public Posicao deslocar(int deltaLinha, int deltaColuna) {
        return new Posicao(linha + deltaLinha, coluna + deltaColuna);
    }

    /** Notação no estilo "c3". */
    public String notacao() {
        return String.valueOf((char) ('a' + coluna)) + (Tabuleiro.TAMANHO - linha);
    }

    @Override
    public String toString() {
        return notacao();
    }
}
