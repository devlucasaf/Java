package application.simuladores.ncorpos;

public class Corpo {

    public double x, y;
    public double vx, vy;
    public double massa;
    public double raio;
    public java.awt.Color cor;

    public Corpo(double x, double y, double vx, double vy, double massa, double raio, java.awt.Color cor) {
        this.x = x; this.y = y;
        this.vx = vx; this.vy = vy;
        this.massa = massa; this.raio = raio;
        this.cor = cor;
    }
}

