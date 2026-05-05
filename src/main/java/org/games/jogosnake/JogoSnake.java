package org.games.jogosnake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class JogoSnake extends JPanel implements ActionListener {

    private final int               largura = 1200;
    private final int               altura = 800;
    private final int               tamanhoQuadrado = 20;
    private final int               velocidade = 15;

    private final ArrayList<Point>  pixels = new ArrayList<>();
    private int                     tamanhoCobra = 1;
    private int                     x;
    private int                     y;
    private int                     velocidadeX = 0;
    private int                     velocidadeY = 0;
    private Point                   comida;

    private boolean                 fimDeJogo = false;
    private Timer                   timer;

    public JogoSnake() {
        this.setPreferredSize(new Dimension(largura, altura));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);

        this.addKeyListener(new ControleTeclado(this));

        iniciarJogo();
    }

    private void iniciarJogo() {
        x = largura / 2;
        y = altura / 2;
        gerarComida();

        timer = new Timer(1000 / velocidade, this);
        timer.start();
    }

    private void gerarComida() {
        Random random = new Random();
        int rX = random.nextInt(largura / tamanhoQuadrado) * tamanhoQuadrado;
        int rY = random.nextInt(altura / tamanhoQuadrado) * tamanhoQuadrado;
        comida = new Point(rX, rY);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        if (!fimDeJogo) {
            graphics.setColor(Color.RED);
            graphics.fillRect(comida.x, comida.y, tamanhoQuadrado, tamanhoQuadrado);

            graphics.setColor(Color.GREEN);
            for (Point p : pixels) {
                graphics.fillRect(p.x, p.y, tamanhoQuadrado, tamanhoQuadrado);
            }

            graphics.setColor(Color.RED);
            graphics.setFont(new Font("Helvetica", Font.BOLD, 35));
            graphics.drawString("Pontos: " + (tamanhoCobra - 1), 10, 35);
        } else {
            mostrarFimDeJogo(graphics);
        }
    }

    private void mostrarFimDeJogo(Graphics graphics) {
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("Helvetica", Font.BOLD, 50));
        graphics.drawString("Fim de Jogo!", largura / 3, altura / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!fimDeJogo) {
            x += velocidadeX;
            y += velocidadeY;

            if (x < 0 || x >= largura || y < 0 || y >= altura) {
                fimDeJogo = true;
            }

            pixels.add(new Point(x, y));
            if (pixels.size() > tamanhoCobra) {
                pixels.remove(0);
            }

            for (int i = 0; i < pixels.size() - 1; i++) {
                if (pixels.get(i).equals(new Point(x, y))) {
                    fimDeJogo = true;
                }
            }

            if (x == comida.x && y == comida.y) {
                tamanhoCobra++;
                gerarComida();
            }
        }
        repaint();
    }

    public int getVelocidadeX() {
        return velocidadeX;
    }

    public int getVelocidadeY() {
        return velocidadeY;
    }

    public void setVelocidadeX(int velocidadeX) {
        this.velocidadeX = velocidadeX;
    }

    public void setVelocidadeY(int velocidadeY) {
        this.velocidadeY = velocidadeY;
    }

    public int getTamanhoQuadrado() {
        return tamanhoQuadrado;
    }
}