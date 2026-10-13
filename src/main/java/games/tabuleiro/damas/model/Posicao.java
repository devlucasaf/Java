package games.tabuleiro.damas.model;

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

    public String notacao() {
        return String.valueOf((char) ('a' + coluna)) + (Tabuleiro.TAMANHO - linha);
    }

    @Override
    public String toString() {
        return notacao();
    }
}
