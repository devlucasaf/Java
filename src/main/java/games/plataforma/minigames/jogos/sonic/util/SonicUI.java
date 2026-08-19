package games.plataforma.minigames.jogos.sonic.util;

import games.plataforma.minigames.gui.JanelaJogo;
import games.plataforma.minigames.jogos.sonic.model.Sonic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SonicUI extends JanelaJogo {

    private Sonic   model;
    private JPanel  gamePanel;
    private JLabel  infoLabel;
    private Timer   timer;

    public SonicUI() {
        super("Sonic - O Ouriço");
        inicializarComponentes();
    }

    @Override
    protected void inicializarComponentes() {
        model = new Sonic();
        setLayout(new BorderLayout());

        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                desenharJogo(g);
            }
        };

        gamePanel.setPreferredSize(new Dimension(Sonic.LARGURA, Sonic.ALTURA));
        gamePanel.setBackground(new Color(30, 40, 60));
        gamePanel.setFocusable(true);
        gamePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        model.moverEsquerda();
                        break;
                    case KeyEvent.VK_RIGHT:
                        model.moverDireita();
                        break;
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_SPACE:
                        model.pular();
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    model.pararHorizontal();
                }
            }
        });

        infoLabel = new JLabel("Vidas: 3  |  Anéis: 0");
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        topPanel.add(infoLabel);

        JPanel botoesPanel = criarPainelBotoesVoltar();
        JButton reiniciar = new JButton("Reiniciar");
        reiniciar.setBackground(new Color(64,64,64));
        reiniciar.setForeground(Color.WHITE);
        reiniciar.setFocusPainted(false);
        reiniciar.addActionListener(e -> reiniciarJogo());
        botoesPanel.add(reiniciar);

        add(topPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);
        add(botoesPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);

        timer = new Timer(16, e -> {
            model.update();
            atualizarInfo();
            gamePanel.repaint();
            if (model.isGameOver()) {
                timer.stop();
                if (model.isVenceu()) {
                    JOptionPane.showMessageDialog(this, "Parabéns! Você chegou ao final com " + model.getAneis() + " anéis!");
                } else {
                    JOptionPane.showMessageDialog(this, "Game Over! Vidas: 0");
                }
            }
        });
        timer.start();

        gamePanel.requestFocusInWindow();
    }

    private void desenharJogo(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int desl = model.getDeslocamentoX();

        g2d.setColor(new Color(50, 150, 50));
        for (Rectangle plat : model.getPlataformas()) {
            int x = plat.x - desl;
            if (x > -50 && x < Sonic.LARGURA + 50) {
                g2d.fillRect(x, plat.y, plat.width, plat.height);
                g2d.setColor(new Color(30, 120, 30));
                g2d.drawRect(x, plat.y, plat.width, plat.height);
                g2d.setColor(new Color(50, 150, 50));
            }
        }

        g2d.setColor(Color.YELLOW);
        for (Rectangle anel : model.getAneisList()) {
            int x = anel.x - desl;
            if (x > -20 && x < Sonic.LARGURA + 20) {
                g2d.fillOval(x, anel.y, anel.width, anel.height);
                g2d.setColor(Color.ORANGE);
                g2d.drawOval(x, anel.y, anel.width, anel.height);
                g2d.setColor(Color.YELLOW);
            }
        }

        for (Rectangle ini : model.getInimigos()) {
            int x = ini.x - desl;
            if (x > -30 && x < Sonic.LARGURA + 30) {
                g2d.setColor(new Color(180, 50, 50));
                g2d.fillRoundRect(x, ini.y, ini.width, ini.height, 8, 8);
                g2d.setColor(Color.WHITE);
                g2d.fillOval(x + 5, ini.y + 5, 8, 8);
                g2d.fillOval(x + 17, ini.y + 5, 8, 8);
                g2d.setColor(Color.BLACK);
                g2d.fillOval(x + 7, ini.y + 8, 4, 4);
                g2d.fillOval(x + 19, ini.y + 8, 4, 4);
                g2d.setColor(Color.GRAY);
                g2d.drawLine(x + 15, ini.y, x + 15, ini.y - 10);
                g2d.fillOval(x + 13, ini.y - 12, 4, 4);
            }
        }

        Rectangle bandeira = model.getBandeira();
        int bx = bandeira.x - desl;
        if (bx > -30 && bx < Sonic.LARGURA + 30) {
            g2d.setColor(Color.GRAY);
            g2d.fillRect(bx, bandeira.y, 5, bandeira.height);
            g2d.setColor(new Color(0, 200, 255));
            g2d.fillRect(bx + 5, bandeira.y, 25, 20);
            g2d.setColor(Color.WHITE);
            g2d.drawString("⚡", bx + 12, bandeira.y + 16);
        }

        Rectangle sonic = model.getSonic();
        int sx = sonic.x - desl;
        if (sx > -40 && sx < Sonic.LARGURA + 40) {
            if (model.isInvencivel() && (System.currentTimeMillis() / 100) % 2 == 0) {
                return;
            }

            g2d.setColor(new Color(0, 100, 255));
            g2d.fillRoundRect(sx, sonic.y, sonic.width, sonic.height, 12, 12);
            g2d.setColor(new Color(0, 100, 255));
            g2d.fillOval(sx + 5, sonic.y - 4, 20, 14);
            g2d.setColor(Color.WHITE);

            if (model.isViradoDireita()) {
                g2d.fillOval(sx + 16, sonic.y + 4, 8, 8);
                g2d.fillOval(sx + 6, sonic.y + 4, 8, 8);
                g2d.setColor(Color.BLACK);
                g2d.fillOval(sx + 19, sonic.y + 7, 3, 3);
                g2d.fillOval(sx + 9, sonic.y + 7, 3, 3);
            } else {
                g2d.fillOval(sx + 6, sonic.y + 4, 8, 8);
                g2d.fillOval(sx + 16, sonic.y + 4, 8, 8);
                g2d.setColor(Color.BLACK);
                g2d.fillOval(sx + 8, sonic.y + 7, 3, 3);
                g2d.fillOval(sx + 18, sonic.y + 7, 3, 3);
            }

            g2d.setColor(Color.BLACK);
            g2d.drawArc(sx + 8, sonic.y + 12, 14, 8, 0, -180);
            g2d.setColor(Color.RED);
            g2d.fillRect(sx + 2, sonic.y + sonic.height - 4, 10, 4);
            g2d.fillRect(sx + 18, sonic.y + sonic.height - 4, 10, 4);
            g2d.setColor(new Color(0, 80, 200));

            int[] xPoints = {sx, sx - 6, sx - 4, sx - 10, sx - 6, sx - 12, sx - 4, sx - 6, sx};
            int[] yPoints = {sonic.y + 8, sonic.y + 4, sonic.y, sonic.y + 2, sonic.y - 2, sonic.y + 2, sonic.y + 4, sonic.y + 8, sonic.y + 10};

            if (model.isViradoDireita()) {
                g2d.fillPolygon(xPoints, yPoints, 9);
            } else {
                int[] xPointsInv = new int[9];
                for (int i = 0; i < 9; i++) {
                    xPointsInv[i] = sx + sonic.width - (xPoints[i] - sx);
                }
                g2d.fillPolygon(xPointsInv, yPoints, 9);
            }
        }
    }

    private void atualizarInfo() {
        infoLabel.setText("Vidas: " + model.getVidas() + "  |  Anéis: " + model.getAneis());
    }

    private void reiniciarJogo() {
        model = new Sonic();
        timer.restart();
        gamePanel.requestFocusInWindow();
        atualizarInfo();
        gamePanel.repaint();
    }
}
