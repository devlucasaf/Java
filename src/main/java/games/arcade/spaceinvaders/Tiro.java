package games.arcade.spaceinvaders;

public class Tiro extends Entidade {

    private final boolean doJogador;

    public Tiro(double x, double y, double velY, boolean doJogador) {
        super(x, y, 4, 12);
        this.velocidadeY = velY;
        this.doJogador = doJogador;
    }

    public boolean isDoJogador() {
        return doJogador;
    }

    @Override
    public void atualizar(double dt) {
        super.atualizar(dt);
        if (y < -20 || y > 700) {
            destruir();
        }
    }
}

