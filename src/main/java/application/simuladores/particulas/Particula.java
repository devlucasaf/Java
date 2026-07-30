package application.simuladores.particulas;

import java.awt.Color;

public class Particula {

    public double   x;
    public double   y;
    public double   vx;
    public double   vy;
    public double   vida;
    public double   vidaMax;
    public double   tamanho;
    public Color    cor;

    public Particula(double x, double y, double vx, double vy, double vida, Color cor, double tamanho) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.vida = vida;
        this.vidaMax = vida;
        this.cor = cor;
        this.tamanho = tamanho;
    }
}

