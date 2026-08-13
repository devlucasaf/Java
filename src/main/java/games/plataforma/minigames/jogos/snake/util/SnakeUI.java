package games.plataforma.minigames.jogos.snake.util;

import games.plataforma.minigames.gui.JanelaJogo;
import games.plataforma.minigames.jogos.snake.model.Snake;
import games.plataforma.minigames.jogos.snake.model.Direcao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SnakeUI extends JanelaJogo {
    private Snake   model;
    private JPanel  gamePanel;
    private JLabel  scoreLabel;
    private Timer   timer;

    private final int TAMANHO_CELULA = 20;
    private final int LARGURA = 20;
    private final int ALTURA = 20;

    public SnakeUI() {
        super("Jogo da Cobrinha");
        inicializarComponentes();
    }

    @Override
    protected void inicializarComponentes() {
        model = new Snake(LARGURA, ALTURA);
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
        scoreLabel = new JLabel("Pontuação: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        scoreLabel.setForeground(Color.WHITE);
        topPanel.add(scoreLabel, BorderLayout.CENTER);

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

        timer = new Timer(200, (ActionEvent e) -> {
            model.mover();
            gamePanel.repaint();
            scoreLabel.setText("Pontuação: " + model.getPontuacao());
            if (model.isGameOver()) {
                timer.stop();
                String msg = model.isVenceu() ? "Parabéns! Você venceu!" : "Game Over! Pontuação: " + model.getPontuacao();
                JOptionPane.showMessageDialog(SnakeUI.this, msg);
            }
        });
        timer.start();

        pack();
        setLocationRelativeTo(null);
        gamePanel.requestFocusInWindow();
    }

    private void desenhar(Graphics g) {
        Point comida = model.getComida();
        g.setColor(Color.RED);
        g.fillOval(
                comida.x * TAMANHO_CELULA + 2,
                comida.y * TAMANHO_CELULA + 2,
                TAMANHO_CELULA - 4,
                TAMANHO_CELULA - 4
        );

        for (Point p : model.getCorpo()) {
            g.setColor(Color.GREEN);
            g.fillRect(
                    p.x * TAMANHO_CELULA + 1,
                    p.y * TAMANHO_CELULA + 1,
                    TAMANHO_CELULA - 2,
                    TAMANHO_CELULA - 2
            );
        }

        if (!model.getCorpo().isEmpty()) {
            Point cabeca = model.getCorpo().getFirst();
            g.setColor(new Color(0, 200, 0));
            g.fillRect(
                    cabeca.x * TAMANHO_CELULA + 1,
                    cabeca.y * TAMANHO_CELULA + 1,
                    TAMANHO_CELULA - 2,
                    TAMANHO_CELULA - 2
            );
        }
    }

    private void reiniciar() {
        model.iniciar();
        timer.start();
        scoreLabel.setText("Pontuação: 0");
        gamePanel.repaint();
        gamePanel.requestFocusInWindow();
    }
}
