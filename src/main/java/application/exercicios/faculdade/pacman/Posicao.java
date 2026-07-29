package application.exercicios.faculdade.pacman;

import java.util.Objects;

public final class Posicao {

    private final int x;
    private final int y;

    public Posicao(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Posicao mover(Direcao direcao) {
        return new Posicao(x + direcao.getDx(), y + direcao.getDy());
    }

    public int distanciaManhattan(Posicao outra) {
        return Math.abs(x - outra.x) + Math.abs(y - outra.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Posicao)) {
            return false;
        }
        Posicao p = (Posicao) o;
        return x == p.x && y == p.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

