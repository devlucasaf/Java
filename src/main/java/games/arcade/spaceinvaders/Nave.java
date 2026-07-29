package games.arcade.spaceinvaders;

public class Nave extends Entidade {

    private int vidas = 3;

    public Nave(double x, double y) {
        super(x, y, 40, 20);
    }

    public void moverEsquerda() {
        velocidadeX = -220;
    }

    public void moverDireita() {
        velocidadeX = 220;
    }

    public void parar() {
        velocidadeX = 0;
    }

    public Tiro atirar() {
        return new Tiro(x + largura / 2 - 2, y - 10, -400, true);
    }

    public int getVidas() {
        return vidas;
    }

    public void perderVida() {
        vidas--;
        if (vidas <= 0) {
            destruir();
        }
    }

    @Override
    public void atualizar(double dt) {
        super.atualizar(dt);
        if (x < 0) {
            x = 0;
        }

        if (x + largura > 800) {
            x = 800 - largura;
        }
    }
}

