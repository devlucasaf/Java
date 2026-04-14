package org.games.adivinhacao;

public class IA {

    private int minimo;
    private int maximo;

    public IA(int minimo, int maximo) {
        this.minimo = minimo;
        this.maximo = maximo;
    }

    public int gerarPalpite() {
        return (minimo + maximo) / 2;
    }

    public void diminuirLimite(int palpite) {
        maximo = palpite - 1;
    }

    public void aumentarLimite(int palpite) {
        minimo = palpite + 1;
    }
}
