package games.arcade.spaceinvaders;

public class Entidade {

    protected double x;
    protected double y;
    protected double largura;
    protected double altura;
    protected double velocidadeX;
    protected double velocidadeY;
    protected boolean ativo = true;

    public Entidade(double x, double y, double largura, double altura) {
        this.x = x;
        this.y = y;
        this.largura = largura;
        this.altura = altura;
    }

    public void atualizar(double dt) {
        x += velocidadeX * dt;
        y += velocidadeY * dt;
    }

    public boolean colideCom(Entidade outra) {
        return ativo && outra.ativo
                && x < outra.x + outra.largura
                && x + largura > outra.x
                && y < outra.y + outra.altura
                && y + altura > outra.y;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getLargura() { return largura; }
    public double getAltura() { return altura; }
    public boolean isAtivo() { return ativo; }
    public void destruir() { this.ativo = false; }
}

