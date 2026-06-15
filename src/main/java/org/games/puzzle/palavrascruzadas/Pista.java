package org.games.puzzle.palavrascruzadas;

public class Pista {

    private final int numero;
    private final String direcao;
    private final String dica;
    private final int linhaInicial;
    private final int colunaInicial;
    private final int tamanho;

    public Pista(int numero, String direcao, String dica, int linhaInicial, int colunaInicial, int tamanho) {
        this.numero = numero;
        this.direcao = direcao;
        this.dica = dica;
        this.linhaInicial = linhaInicial;
        this.colunaInicial = colunaInicial;
        this.tamanho = tamanho;
    }

    public int getNumero() {
        return numero;
    }

    public String getDirecao() {
        return direcao;
    }

    public String getDica() {
        return dica;
    }

    public int getLinhaInicial() {
        return linhaInicial;
    }

    public int getColunaInicial() {
        return colunaInicial;
    }

    public int getTamanho() {
        return tamanho;
    }
}

