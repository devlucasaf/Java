package application.simuladores.particulas;

import java.awt.Color;

public class Particula {

    public double   x;
    public double   y;
    public double   vx;
    public double   vy;
    public double   vida;
    public double   vidaMaxima;
    public double   tamanho;
    public Color    cor;

    public Particula(double x, double y, double vx, double vy, double vida, Color cor, double tamanho) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.vida = vida;
        this.vidaMaxima = vida;
        this.cor = cor;
        this.tamanho = tamanho;
    }
}

