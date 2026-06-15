package org.games.plataforma.doodlejump;

public class Plataforma {

    private int         x;
    private int         y;
    private final int   largura;

    public Plataforma(int x, int y, int largura) {
        this.x = x;
        this.y = y;
        this.largura = largura;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLargura() {
        return largura;
    }

    public void descer(int quantidade) {
        y += quantidade;
    }

    public boolean colide(int jogadorX, int yAtual, int yProximo) {
        if (jogadorX < x || jogadorX >= x + largura) {
            return false;
        }
        return yAtual <= y && yProximo >= y;
    }
}

