package games.arcade.angrybirds;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AngryBirds extends JPanel {

    private static final int LARGURA = 1000;
    private static final int ALTURA  = 600;
    private static final int SOLO    = 500;

    private final Passaro       passaro = new Passaro();
    private final List<Bloco>   blocos = new ArrayList<>();
    private int                 pontos = 0;
    private int                 tentativas = 5;
    private boolean             mirando = false;
    private int                 mouseX, mouseY;
    private final int           estilinguX = 120;
    private final int           estilinguY = SOLO - 30;

    public AngryBirds() {
        setBackground(new Color(135, 206, 235));
        setPreferredSize(new java.awt.Dimension(LARGURA, ALTURA));
        montarNivel();
        passaro.reset(estilinguX, estilinguY);

        MouseAdapter m = new MouseAdapter() {
            @Override public void mousePressed(MouseEvent e) {
                if (!passaro.ativo && tentativas > 0) mirando = true;
                mouseX = e.getX(); mouseY = e.getY();
            }
            @Override public void mouseDragged(MouseEvent e) { mouseX = e.getX(); mouseY = e.getY(); }
            @Override public void mouseReleased(MouseEvent e) {
                if (mirando) {
                    double vx = (estilinguX - mouseX) * 3;
                    double vy = (estilinguY - mouseY) * 3;
                    passaro.lancar(vx, vy);
                    tentativas--;
                    mirando = false;
                }
            }
        };
        addMouseListener(m);
        addMouseMotionListener(m);

        Timer t = new Timer(16, e -> { atualizar(0.016); repaint(); });
        t.start();
    }

    private void montarNivel() {
        for (int c = 0; c < 3; c++) {
            for (int l = 0; l < 5; l++) {
                blocos.add(new Bloco(700 + c * 45, SOLO - (l + 1) * 40, 40, 40));
            }
        }
    }

    private void atualizar(double dt) {
        passaro.atualizar(dt);

        if (passaro.ativo) {
            Iterator<Bloco> it = blocos.iterator();
            while (it.hasNext()) {
                Bloco b = it.next();
                double px = passaro.x, py = passaro.y;
                if (px + passaro.raio > b.x && px - passaro.raio < b.x + b.largura
                        && py + passaro.raio > b.y && py - passaro.raio < b.y + b.altura) {
                    it.remove();
                    pontos += 100;
                    passaro.vy *= 0.5;
                    passaro.vx *= 0.7;
                    break;
                }
            }

            if (passaro.y >= SOLO - passaro.raio || passaro.x > LARGURA || passaro.x < 0) {
                if (tentativas > 0) passaro.reset(estilinguX, estilinguY);
                else passaro.ativo = false;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(60, 140, 60));
        g2.fillRect(0, SOLO, LARGURA, ALTURA - SOLO);

        g2.setColor(new Color(90, 50, 20));
        g2.fillRect(estilinguX - 5, estilinguY, 10, 40);

        for (Bloco b : blocos) b.desenhar(g2);

        if (mirando) {
            g2.setColor(Color.GRAY);
            g2.drawLine(estilinguX, estilinguY, mouseX, mouseY);
        }

        passaro.desenhar(g2);

        g2.setColor(Color.BLACK);
        g2.drawString("Pontos: " + pontos, 20, 20);
        g2.drawString("Tentativas: " + tentativas, 20, 40);
        g2.drawString("Blocos restantes: " + blocos.size(), 20, 60);
        if (blocos.isEmpty()) g2.drawString("VITORIA!", LARGURA / 2 - 30, 300);
        else if (tentativas == 0 && !passaro.ativo) g2.drawString("FIM DE JOGO", LARGURA / 2 - 40, 300);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Angry Birds clone");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.add(new AngryBirds());
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}

