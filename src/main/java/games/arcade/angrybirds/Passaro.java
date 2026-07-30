package games.arcade.angrybirds;

import java.awt.Color;
import java.awt.Graphics2D;

public class Passaro {

    public double   x, y;
    public double   vx, vy;
    public double   raio = 12;
    public boolean  ativo = false;

    public void reset(double x, double y) {
        this.x = x; this.y = y;
        this.vx = 0; this.vy = 0;
        this.ativo = false;
    }

    public void lancar(double vx, double vy) {
        this.vx = vx; this.vy = vy;
        this.ativo = true;
    }

    public void atualizar(double dt) {
        if (!ativo) return;
        vy += 500 * dt;
        x += vx * dt;
        y += vy * dt;
    }

    public void desenhar(Graphics2D g) {
        g.setColor(Color.RED);
        g.fillOval((int) (x - raio), (int) (y - raio), (int) (raio * 2), (int) (raio * 2));
        g.setColor(Color.BLACK);
        g.drawOval((int) (x - raio), (int) (y - raio), (int) (raio * 2), (int) (raio * 2));
    }
}

