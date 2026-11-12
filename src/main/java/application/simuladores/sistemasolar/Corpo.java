package application.simuladores.sistemasolar;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;

public class Corpo {
    public final String    nome;
    public final double    massa;
    public final Color     cor;
    public final int       raio;
    public double          x;
    public double          y;
    public double          vx;
    public double          vy;
    public double          ax;
    public double          ay;
    public final Deque<double[]> trilha = new ArrayDeque<>();
    public final boolean cometa;

    public Corpo(String nome, double massa, Color cor, int raio, boolean cometa) {
        this.nome = nome;
        this.massa = massa;
        this.cor = cor;
        this.raio = raio;
        this.cometa = cometa;
    }
}
