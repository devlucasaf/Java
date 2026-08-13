package games.plataforma.minigames.jogos.pacman.util;

import games.plataforma.minigames.gui.JanelaJogo;
import games.plataforma.minigames.jogos.pacman.model.Direcao;
import games.plataforma.minigames.jogos.pacman.model.PacMan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PacmanUI extends JanelaJogo {
    private PacMan      model;
    private JPanel      gamePanel;
    private JLabel      statusLabel;
    private Timer       timer;

    private final int TAMANHO_CELULA = 25;
    private final int LARGURA = 15;
    private final int ALTURA = 15;

    public PacmanUI() {
        super("Pacman");
        inicializarComponentes();
    }

    @Override
    protected void inicializarComponentes() {
        model = new PacMan(LARGURA, ALTURA);
        setLayout(new BorderLayout());

        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                desenhar(g);
            }
        };
        gamePanel.setBackground(new Color(45, 45, 45));
        gamePanel.setPreferredSize(new Dimension(LARGURA * TAMANHO_CELULA, ALTURA * TAMANHO_CELULA));
        gamePanel.setFocusable(true);
        gamePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        model.setDirecao(Direcao.SUBIR);
                        break;
                    case KeyEvent.VK_DOWN:
                        model.setDirecao(Direcao.DESCER);
                        break;
                    case KeyEvent.VK_LEFT:
                        model.setDirecao(Direcao.ESQUERDA);
                        break;
                    case KeyEvent.VK_RIGHT:
                        model.setDirecao(Direcao.DIREITA);
                        break;
                }
            }
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        statusLabel = new JLabel("Pontos: 0 | Vidas: 3", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setForeground(Color.WHITE);
        topPanel.add(statusLabel, BorderLayout.CENTER);

        JPanel botoesPanel = criarPainelBotoesVoltar();
        JButton reiniciar = new JButton("Reiniciar");
        reiniciar.setBackground(new Color(64,64,64));
        reiniciar.setForeground(Color.WHITE);
        reiniciar.setFocusPainted(false);
        reiniciar.addActionListener(e -> reiniciar());
        botoesPanel.add(reiniciar);
        topPanel.add(botoesPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);

        timer = new Timer(300, (ActionEvent e) -> {
            model.mover();
            gamePanel.repaint();
            statusLabel.setText("Pontos: " + model.getPontuacao() + " | Vidas: " + model.getVidas());
            if (model.isGameOver()) {
                timer.stop();
                String msg = model.isVenceu() ? "Parabéns! Você venceu!" : "Game Over! Pontos: " + model.getPontuacao();
                JOptionPane.showMessageDialog(PacmanUI.this, msg);
            }
        });
        timer.start();

        pack();
        setLocationRelativeTo(null);
        gamePanel.requestFocusInWindow();
    }

    private void desenhar(Graphics g) {
        g.setColor(new Color(80, 80, 80));
        for (int i = 0; i < LARGURA; i++) {
            for (int j = 0; j < ALTURA; j++) {
                if (model.isParede(i, j)) {
                    g.fillRect(
                            i * TAMANHO_CELULA,
                            j * TAMANHO_CELULA,
                            TAMANHO_CELULA,
                            TAMANHO_CELULA
                    );
                }
            }
        }

        g.setColor(Color.YELLOW);
        for (Point p : model.getPontos()) {
            g.fillOval(
                    p.x * TAMANHO_CELULA + TAMANHO_CELULA/2 - 3,
                    p.y * TAMANHO_CELULA + TAMANHO_CELULA/2 - 3,
                    6,
                    6
            );
        }

        g.setColor(Color.RED);
        for (Point f : model.getFantasmas()) {
            g.fillOval(
                    f.x * TAMANHO_CELULA + 2,
                    f.y * TAMANHO_CELULA + 2,
                    TAMANHO_CELULA - 4,
                    TAMANHO_CELULA - 4
            );
        }

        // Desenha Pacman
        Point p = model.getPacman();
        g.setColor(Color.YELLOW);
        g.fillOval(
                p.x * TAMANHO_CELULA + 2,
                p.y * TAMANHO_CELULA + 2,
                TAMANHO_CELULA - 4,
                TAMANHO_CELULA - 4
        );
    }

    private void reiniciar() {
        model.iniciar();
        timer.start();
        statusLabel.setText("Pontos: 0 | Vidas: 3");
        gamePanel.repaint();
        gamePanel.requestFocusInWindow();
    }
}