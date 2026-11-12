package application.simuladores.sistemasolar;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SistemaSolar extends JPanel {

    private static final double G = 4 * Math.PI * Math.PI;
    private static final double SUAVIZACAO = 0.01;
    private static final double PASSO = 0.0005;
    private static final int MAX_TRILHA = 600;

    private final List<Corpo> corpos = new ArrayList<>();
    private final Random random = new Random();
    private double tempo;
    private double velocidade = 0.5;
    private double zoom = 1;
    private boolean pausado;
    private boolean mostrarTrilhas = true;

    public SistemaSolar() {
        setBackground(Color.BLACK);
        resetar();
        addMouseWheelListener(e -> {
            zoom *= e.getWheelRotation() < 0 ? 1.15 : 1 / 1.15;
            zoom = Math.max(0.3, Math.min(zoom, 20));
        });

        new Timer(16, e -> {
            if (!pausado) {
                avancar(velocidade * 0.016);
            }
            repaint();
        }).start();
    }

    // ----------------------------------------------------------------- modelo
    private void resetar() {
        corpos.clear();
        tempo = 0;
        corpos.add(new Corpo("Sol", 1, new Color(255, 204, 0), 14, false));
        adicionarPlaneta("Mercurio", 1.66e-7, 0.387, new Color(170, 170, 170), 3);
        adicionarPlaneta("Venus", 2.45e-6, 0.723, new Color(230, 190, 120), 5);
        adicionarPlaneta("Terra", 3.0e-6, 1.0, new Color(70, 130, 255), 5);
        adicionarPlaneta("Marte", 3.2e-7, 1.524, new Color(220, 80, 50), 4);
        adicionarPlaneta("Jupiter", 9.55e-4, 5.203, new Color(210, 160, 110), 10);
        adicionarPlaneta("Saturno", 2.86e-4, 9.537, new Color(230, 210, 150), 9);
        adicionarPlaneta("Urano", 4.4e-5, 19.19, new Color(130, 220, 230), 7);
        adicionarPlaneta("Netuno", 5.15e-5, 30.07, new Color(60, 90, 220), 7);
    }

    private void adicionarPlaneta(String nome, double massa, double r, Color cor, int raio) {
        Corpo corpo = new Corpo(nome, massa, cor, raio, false);
        orbitar(corpo, r, 1.0);
        corpos.add(corpo);
    }

    private void orbitar(Corpo c, double r, double fator) {
        double ang = random.nextDouble() * 2 * Math.PI;
        double v = fator * Math.sqrt(G / r);
        c.x = r * Math.cos(ang);
        c.y = r * Math.sin(ang);
        c.vx = -v * Math.sin(ang);
        c.vy = v * Math.cos(ang);
    }

    private void lancarCometa() {
        if (corpos.stream().filter(c -> c.cometa).count() >= 6) {
            return;
        }

        Corpo corpo = new Corpo("Cometa", 0, Color.WHITE, 2, true);
        orbitar(corpo, 25 + random.nextDouble() * 10, 0.2 + random.nextDouble() * 0.3);
        corpos.add(corpo);
    }

    private void avancar(double anos) {
        int passos = Math.max(1, (int) Math.ceil(anos / PASSO));
        double dt = anos / passos;

        for (int i = 0; i < passos; i++) {
            calcularAceleracoes();
            for (Corpo c : corpos) {
                c.vx += c.ax * dt / 2;
                c.vy += c.ay * dt / 2;
                c.x += c.vx * dt;
                c.y += c.vy * dt;
            }

            calcularAceleracoes();
            for (Corpo c : corpos) {
                c.vx += c.ax * dt / 2;
                c.vy += c.ay * dt / 2;
            }
        }
        tempo += anos;

        corpos.removeIf(c -> c.cometa && (Math.hypot(c.x, c.y) < 0.05 || Math.hypot(c.x, c.y) > 200));

        for (Corpo c : corpos) {
            c.trilha.addLast(new double[]{c.x, c.y});
            if (c.trilha.size() > MAX_TRILHA) {
                c.trilha.removeFirst();
            }
        }
    }

    private void calcularAceleracoes() {
        for (Corpo a : corpos) {
            a.ax = 0;
            a.ay = 0;
            for (Corpo b : corpos) {
                if (a == b || b.massa == 0) {
                    continue;
                }
                double dx = b.x - a.x;
                double dy = b.y - a.y;
                double d2 = dx * dx + dy * dy + SUAVIZACAO * SUAVIZACAO;
                double inv = G * b.massa / (d2 * Math.sqrt(d2));
                a.ax += dx * inv;
                a.ay += dy * inv;
            }
        }
    }

    // --------------------------------------------------------------- desenho
    private double escala() {
        return Math.min(getWidth(), getHeight()) / 2.0 / Math.sqrt(35) * zoom;
    }

    private Point paraTela(double x, double y) {
        double d = Math.hypot(x, y);
        if (d == 0) {
            return new Point(getWidth() / 2, getHeight() / 2);
        }
        double dTela = Math.sqrt(d) * escala();
        return new Point((int) (getWidth() / 2 + x / d * dTela), (int) (getHeight() / 2 + y / d * dTela));
    }

    @Override
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D graphics2D = (Graphics2D) g0;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (Corpo c : corpos) {
            if (mostrarTrilhas) {
                graphics2D.setColor(new Color(c.cor.getRed(), c.cor.getGreen(), c.cor.getBlue(), 90));
                Point anterior = null;
                for (double[] p : c.trilha) {
                    Point t = paraTela(p[0], p[1]);
                    if (anterior != null) {
                        graphics2D.drawLine(anterior.x, anterior.y, t.x, t.y);
                    }
                    anterior = t;
                }
            }

            Point point = paraTela(c.x, c.y);
            graphics2D.setColor(c.cor);
            graphics2D.fillOval(point.x - c.raio, point.y - c.raio, c.raio * 2, c.raio * 2);
            if (!c.cometa) {
                graphics2D.setColor(Color.LIGHT_GRAY);
                graphics2D.setFont(graphics2D.getFont().deriveFont(11f));
                graphics2D.drawString(c.nome, point.x + c.raio + 3, point.y - c.raio);
            }
        }

        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(String.format("Tempo: %.2f anos   Velocidade: %.2f anos/s   Zoom: %.2fx   (roda do mouse = zoom)",
                tempo, velocidade, zoom), 10, 18);
    }

    // ------------------------------------------------------------------ main

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SistemaSolar sistemaSolar = new SistemaSolar();

            JSlider slider = new JSlider(1, 100, 25);
            slider.addChangeListener(e -> sistemaSolar.velocidade = slider.getValue() / 50.0);
            JButton btnPausar = new JButton("Pausar");
            btnPausar.addActionListener(e -> {
                sistemaSolar.pausado = !sistemaSolar.pausado;
                btnPausar.setText(sistemaSolar.pausado ? "Continuar" : "Pausar");
            });

            JButton btnCometa = new JButton("Lancar btnCometa");
            btnCometa.addActionListener(e -> sistemaSolar.lancarCometa());

            JButton btnResetar = new JButton("Resetar");
            btnResetar.addActionListener(e -> sistemaSolar.resetar());

            JCheckBox checkBoxTrilhas = new JCheckBox("Trilhas", true);
            checkBoxTrilhas.addActionListener(e -> sistemaSolar.mostrarTrilhas = checkBoxTrilhas.isSelected());

            JPanel panelControles = new JPanel();

            panelControles.add(new JLabel("Velocidade"));
            panelControles.add(slider);
            panelControles.add(btnPausar);
            panelControles.add(btnCometa);
            panelControles.add(btnResetar);
            panelControles.add(checkBoxTrilhas);

            JFrame frame = new JFrame("Sistema Solar - Simulador N-corpos");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.add(sistemaSolar, BorderLayout.CENTER);
            frame.add(panelControles, BorderLayout.SOUTH);
            frame.setSize(900, 800);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
