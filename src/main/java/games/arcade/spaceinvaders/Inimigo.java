package games.arcade.spaceinvaders;

public class Inimigo extends Entidade {

    private final int pontos;

    public Inimigo(double x, double y, int pontos) {
        super(x, y, 30, 24);
        this.pontos = pontos;
    }

    public int getPontos() {
        return pontos;
    }

    public Tiro atirar() {
        return new Tiro(x + largura / 2 - 2, y + altura, 260, false);
    }
}

