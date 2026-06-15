package org.games.plataforma.ascii;

class Jogador {

    public int          x;
    public int          y;

    private int         velocidadeY = 0;
    private boolean     noChao = false;

    private final int   FORCA_PULO = -3;
    private final int   GRAVIDADE = 1;
    private final int   VELOCIDADE_QUEDA_MAX = 3;

    public Jogador(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moverEsquerda() {
        if (x > 1) {
            x--;
        }
    }

    public void moverDireita() {
        if (x < 20) {
            x++;
        }
    }

    public void pular() {
        if (noChao) {
            velocidadeY = FORCA_PULO;
            noChao = false;
        }
    }

    public void aplicarGravidade() {
        if (!noChao) {
            velocidadeY += GRAVIDADE;
            velocidadeY = Math.min(velocidadeY, VELOCIDADE_QUEDA_MAX);
            y += velocidadeY;
        }
    }

    public void aterrissar() {
        velocidadeY = 0;
        noChao = true;
    }
}
