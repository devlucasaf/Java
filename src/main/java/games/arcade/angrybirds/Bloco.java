package games.arcade.angrybirds;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Bloco {

    public double   x, y;
    public double   largura, altura;
    public int      vida = 1;

    public Bloco(double x, double y, double largura, double altura) {
        this.x = x; this.y = y;
        this.largura = largura; this.altura = altura;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) largura, (int) altura);
    }

    public void desenhar(Graphics2D g) {
        g.setColor(new Color(139, 69, 19));
        g.fillRect((int) x, (int) y, (int) largura, (int) altura);
        g.setColor(Color.BLACK);
        g.drawRect((int) x, (int) y, (int) largura, (int) altura);
    }
}

