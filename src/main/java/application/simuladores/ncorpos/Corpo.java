package application.simuladores.ncorpos;

import java.awt.*;

public class Corpo {

    public double   x;
    public double   y;
    public double   vx;
    public double   vy;
    public double   massa;
    public double   raio;
    public Color    cor;

    public Corpo(double x, double y, double vx, double vy, double massa, double raio, java.awt.Color cor) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.massa = massa;
        this.raio = raio;
        this.cor = cor;
    }
}

