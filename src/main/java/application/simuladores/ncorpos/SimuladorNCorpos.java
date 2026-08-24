package application.simuladores.ncorpos;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimuladorNCorpos extends JPanel {

    private static final double G = 1.0;
    private static final double DT = 0.5;

    private final List<Corpo> corpos = new ArrayList<>();

    // --- INICIALIZA OS CORPOS E INICIA A ATUALIZACAO DA SIMULACAO ---
    public SimuladorNCorpos() {
        setBackground(Color.BLACK);
        setPreferredSize(new java.awt.Dimension(1000, 700));

        corpos.add(new Corpo(500, 350, 0, 0, 5000, 15, Color.YELLOW));

        Random random = new Random(1);

        for (int i = 0; i < 30; i++) {
            double angulo = random.nextDouble() * Math.PI * 2;
            double raio = 100 + random.nextDouble() * 250;
            double x = 500 + raio * Math.cos(angulo);
            double y = 350 + raio * Math.sin(angulo);
            double v = Math.sqrt(G * 5000 / raio);
            double vx = -v * Math.sin(angulo);
            double vy = v * Math.cos(angulo);

            Color cor = new Color(random.nextInt(200) + 55, random.nextInt(200) + 55, random.nextInt(200) + 55);
            corpos.add(new Corpo(x, y, vx, vy, 5 + random.nextDouble() * 20, 3, cor));
        }

        Timer timer = new Timer(16, e -> { atualizar(); repaint(); });
        timer.start();
    }

    // --- CALCULA AS FORCAS GRAVITACIONAIS E ATUALIZA OS CORPOS ---
    private void atualizar() {
        int n = corpos.size();
        double[] fx = new double[n];
        double[] fy = new double[n];

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                Corpo a = corpos.get(i);
                Corpo b = corpos.get(j);

                double dx = b.x - a.x;
                double dy = b.y - a.y;
                double dist2 = dx * dx + dy * dy + 25;
                double dist = Math.sqrt(dist2);
                double f = G * a.massa * b.massa / dist2;

                fx[i] += f * dx / dist;
                fy[i] += f * dy / dist;
                fx[j] -= f * dx / dist;
                fy[j] -= f * dy / dist;
            }
        }

        for (int i = 0; i < n; i++) {
            Corpo c = corpos.get(i);

            c.vx += fx[i] / c.massa * DT;
            c.vy += fy[i] / c.massa * DT;
            c.x += c.vx * DT;
            c.y += c.vy * DT;
        }
    }

    // --- DESENHA OS CORPOS NA INTERFACE GRAFICA ---
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Corpo c : corpos) {
            g2.setColor(c.cor);
            g2.fillOval((int) (c.x - c.raio), (int) (c.y - c.raio), (int) (c.raio * 2), (int) (c.raio * 2));
        }
    }

    // --- CRIA E EXIBE A JANELA DA SIMULACAO ---
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Simulador N-Corpos");

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new SimuladorNCorpos());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

