package application.simuladores.particulas;

import java.awt.Color;

public class Particula {

    public double   x, y;
    public double   vx, vy;
    public double   vida;
    public double   vidaMax;
    public Color    cor;
    public double   tamanho;

    public Particula(double x, double y, double vx, double vy, double vida, Color cor, double tamanho) {
        this.x = x; this.y = y;
        this.vx = vx; this.vy = vy;
        this.vida = vida;
        this.vidaMax = vida;
        this.cor = cor;
        this.tamanho = tamanho;
    }
}

