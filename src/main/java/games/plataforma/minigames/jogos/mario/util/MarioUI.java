package games.plataforma.minigames.jogos.mario.util;

import games.plataforma.minigames.gui.JanelaJogo;
import games.plataforma.minigames.jogos.mario.model.Mario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MarioUI extends JanelaJogo {

    private Mario       model;
    private JPanel      gamePanel;
    private JLabel      infoLabel;
    private Timer       timer;

    public MarioUI() {
        super("Super Mario Bros");
        inicializarComponentes();
    }

    @Override
    protected void inicializarComponentes() {
        model = new Mario();
        setLayout(new BorderLayout());

        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                desenharJogo(g);
            }
        };

        gamePanel.setPreferredSize(new Dimension(Mario.LARGURA, Mario.ALTURA));
        gamePanel.setBackground(new Color(30, 30, 40));
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

        infoLabel = new JLabel("Vidas: 3  |  Moedas: 0");
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
                    JOptionPane.showMessageDialog(this, "Parabéns! Você chegou ao final com " + model.getMoedas() + " moedas!");
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

        g2d.setColor(new Color(100, 70, 40));
        for (Rectangle plat : model.getPlataformas()) {
            int x = plat.x - desl;
            if (x > -50 && x < Mario.LARGURA + 50) {
                g2d.fillRect(x, plat.y, plat.width, plat.height);
                g2d.setColor(new Color(80, 60, 30));
                g2d.drawRect(x, plat.y, plat.width, plat.height);
                g2d.setColor(new Color(100, 70, 40));
            }
        }

        g2d.setColor(Color.YELLOW);
        for (Rectangle moeda : model.getMoedasList()) {
            int x = moeda.x - desl;
            if (x > -20 && x < Mario.LARGURA + 20) {
                g2d.fillOval(x, moeda.y, moeda.width, moeda.height);
                g2d.setColor(Color.ORANGE);
                g2d.drawOval(x, moeda.y, moeda.width, moeda.height);
                g2d.setColor(Color.YELLOW);
            }
        }

        // Desenha inimigos
        for (Rectangle ini : model.getInimigos()) {
            int x = ini.x - desl;
            if (x > -30 && x < Mario.LARGURA + 30) {
                g2d.setColor(new Color(150, 50, 50));
                g2d.fillRoundRect(x, ini.y, ini.width, ini.height, 10, 10);
                g2d.setColor(Color.WHITE);
                g2d.fillOval(x + 5, ini.y + 5, 8, 8);
                g2d.fillOval(x + 17, ini.y + 5, 8, 8);
                g2d.setColor(Color.BLACK);
                g2d.fillOval(x + 7, ini.y + 8, 4, 4);
                g2d.fillOval(x + 19, ini.y + 8, 4, 4);
            }
        }

        // Desenha bandeira
        Rectangle bandeira = model.getBandeira();
        int bx = bandeira.x - desl;
        if (bx > -30 && bx < Mario.LARGURA + 30) {
            g2d.setColor(Color.GRAY);
            g2d.fillRect(bx, bandeira.y, 5, bandeira.height);
            g2d.setColor(new Color(0, 200, 0));
            g2d.fillRect(bx + 5, bandeira.y, 25, 20);
            g2d.setColor(Color.WHITE);
            g2d.drawString("★", bx + 12, bandeira.y + 16);
        }

        Rectangle mario = model.getMario();
        int mx = mario.x - desl;
        if (mx > -40 && mx < Mario.LARGURA + 40) {
            if (model.isInvencivel() && (System.currentTimeMillis() / 100) % 2 == 0) {
                return;
            }

            g2d.setColor(Color.RED);
            g2d.fillRect(mx, mario.y, mario.width, mario.height);
            g2d.setColor(Color.BLUE);
            g2d.fillRect(mx + 2, mario.y - 6, mario.width - 4, 8);
            g2d.setColor(new Color(255, 200, 150));
            g2d.fillRect(mx + 5, mario.y + 5, 20, 15);
            g2d.setColor(Color.WHITE);

            if (model.isViradoDireita()) {
                g2d.fillOval(mx + 18, mario.y + 7, 6, 6);
                g2d.fillOval(mx + 8, mario.y + 7, 6, 6);
                g2d.setColor(Color.BLACK);
                g2d.fillOval(mx + 20, mario.y + 9, 3, 3);
                g2d.fillOval(mx + 10, mario.y + 9, 3, 3);
            } else {
                g2d.fillOval(mx + 6, mario.y + 7, 6, 6);
                g2d.fillOval(mx + 16, mario.y + 7, 6, 6);
                g2d.setColor(Color.BLACK);
                g2d.fillOval(mx + 8, mario.y + 9, 3, 3);
                g2d.fillOval(mx + 18, mario.y + 9, 3, 3);
            }

            g2d.setColor(new Color(100, 60, 30));
            if (model.isViradoDireita()) {
                g2d.drawLine(mx + 10, mario.y + 17, mx + 20, mario.y + 17);
            } else {
                g2d.drawLine(mx + 5, mario.y + 17, mx + 15, mario.y + 17);
            }

            g2d.setColor(Color.BLUE);
            g2d.fillRect(mx + 2, mario.y + mario.height - 8, 10, 8);
            g2d.fillRect(mx + 18, mario.y + mario.height - 8, 10, 8);
            g2d.setColor(new Color(100, 60, 30));
            g2d.fillRect(mx, mario.y + mario.height - 2, 12, 4);
            g2d.fillRect(mx + 18, mario.y + mario.height - 2, 12, 4);
        }
    }

    private void atualizarInfo() {
        infoLabel.setText("Vidas: " + model.getVidas() + "  |  Moedas: " + model.getMoedas());
    }

    private void reiniciarJogo() {
        model = new Mario();
        timer.restart();
        gamePanel.requestFocusInWindow();
        atualizarInfo();
        gamePanel.repaint();
    }
}
