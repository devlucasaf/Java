package application.simuladores.particulas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class SistemaParticulas extends JPanel {

    private final List<Particula>   particulas = new ArrayList<>();
    private final Random            r = new Random();
    private String                  modo = "fogo";
    private int                     mx = 500;
    private int                     my = 350;

    public SistemaParticulas() {
        setBackground(Color.BLACK);
        setPreferredSize(new java.awt.Dimension(1000, 700));
        setFocusable(true);

        addMouseMotionListener(new MouseAdapter() {
            @Override public void mouseMoved(MouseEvent e) { mx = e.getX(); my = e.getY(); }
            @Override public void mouseDragged(MouseEvent e) { mx = e.getX(); my = e.getY(); }
        });
        addMouseListener(new MouseAdapter() {
            @Override public void mousePressed(MouseEvent e) {
                if (modo.equals("explosao")) explosao(e.getX(), e.getY());
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                switch (e.getKeyChar()) {
                    case '1' -> modo = "fogo";
                    case '2' -> modo = "fumaca";
                    case '3' -> modo = "chuva";
                    case '4' -> modo = "explosao";
                }
                System.out.println("Modo: " + modo);
            }
        });

        Timer t = new Timer(16, e -> { emitir(); atualizar(); repaint(); });
        t.start();
    }

    private void emitir() {
        switch (modo) {
            case "fogo" -> {
                for (int i = 0; i < 5; i++) {
                    double vx = (r.nextDouble() - 0.5) * 1.5;
                    double vy = -2 - r.nextDouble() * 2;
                    Color c = new Color(255, 100 + r.nextInt(155), 0, 200);
                    particulas.add(new Particula(mx, my, vx, vy, 60, c, 4 + r.nextDouble() * 4));
                }
            }
            case "fumaca" -> {
                for (int i = 0; i < 3; i++) {
                    double vx = (r.nextDouble() - 0.5) * 0.8;
                    double vy = -1 - r.nextDouble();
                    int cinza = 100 + r.nextInt(80);
                    particulas.add(new Particula(mx, my, vx, vy, 120, new Color(cinza, cinza, cinza, 150), 8));
                }
            }
            case "chuva" -> {
                for (int i = 0; i < 8; i++) {
                    double x = r.nextDouble() * getWidth();
                    particulas.add(new Particula(x, 0, 0.5, 8 + r.nextDouble() * 4, 120, new Color(150, 200, 255), 2));
                }
            }
            default -> { }
        }
    }

    private void explosao(int cx, int cy) {
        for (int i = 0; i < 100; i++) {
            double a = r.nextDouble() * Math.PI * 2;
            double v = 3 + r.nextDouble() * 4;
            Color c = new Color(255, 100 + r.nextInt(155), r.nextInt(80));
            particulas.add(new Particula(cx, cy, Math.cos(a) * v, Math.sin(a) * v, 50, c, 3 + r.nextDouble() * 3));
        }
    }

    private void atualizar() {
        Iterator<Particula> it = particulas.iterator();
        while (it.hasNext()) {
            Particula p = it.next();
            p.x += p.vx;
            p.y += p.vy;
            if (modo.equals("fumaca")) p.vy -= 0.02;
            else p.vy += 0.05;
            p.vida--;
            if (p.vida <= 0) it.remove();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (Particula p : particulas) {
            int alpha = Math.max(0, Math.min(255, (int) (255 * p.vida / p.vidaMax)));
            g2.setColor(new Color(p.cor.getRed(), p.cor.getGreen(), p.cor.getBlue(), alpha));
            int s = (int) p.tamanho;
            g2.fillOval((int) p.x - s / 2, (int) p.y - s / 2, s, s);
        }
        g2.setColor(Color.WHITE);
        g2.drawString("Modo: " + modo + " (teclas 1=fogo 2=fumaca 3=chuva 4=explosao)", 10, 20);
        g2.drawString("Particulas: " + particulas.size(), 10, 35);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Sistema de Particulas");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            SistemaParticulas s = new SistemaParticulas();
            f.add(s);
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
            s.requestFocus();
        });
    }
}

