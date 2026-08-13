package games.plataforma.minigames.jogos.corrida.util;

import games.plataforma.minigames.gui.JanelaJogo;
import games.plataforma.minigames.jogos.corrida.model.Corrida;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CorridaUI extends JanelaJogo {
    private Corrida model;
    private JPanel  gamePanel;
    private JLabel  statusLabel;
    private Timer   timer;

    private final int TAMANHO_CELULA = 25;
    private final int LARGURA = 10;
    private final int ALTURA = 20;

    public CorridaUI() {
        super("Corrida de Carros");
        inicializarComponentes();
    }

    @Override
    protected void inicializarComponentes() {
        model = new Corrida(LARGURA, ALTURA);
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
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    model.moverJogador(-1);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    model.moverJogador(1);
                }
            }
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        statusLabel = new JLabel("Pontos: 0 | Velocidade: 1", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setForeground(Color.WHITE);
        topPanel.add(statusLabel, BorderLayout.CENTER);

        JPanel botoesPanel = criarPainelBotoesVoltar();
        JButton btnReiniciar = new JButton("Reiniciar");
        btnReiniciar.setBackground(new Color(64,64,64));
        btnReiniciar.setForeground(Color.WHITE);
        btnReiniciar.setFocusPainted(false);
        btnReiniciar.addActionListener(e -> reiniciar());
        botoesPanel.add(btnReiniciar);
        topPanel.add(botoesPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);

        timer = new Timer(150, (ActionEvent e) -> {
            model.atualizar();
            gamePanel.repaint();
            statusLabel.setText("Pontos: " + model.getPontuacao() + " | Velocidade: " + model.getVelocidade());
            if (model.isGameOver()) {
                timer.stop();
                JOptionPane.showMessageDialog(CorridaUI.this, "Game Over! Pontos: " + model.getPontuacao());
            }
        });
        timer.start();

        pack();
        setLocationRelativeTo(null);
        gamePanel.requestFocusInWindow();
    }

    private void desenhar(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i < ALTURA; i++) {
            if (i % 2 == 0) {
                g.fillRect(LARGURA/2 * TAMANHO_CELULA - 2, i * TAMANHO_CELULA, 4, TAMANHO_CELULA);
            }
        }

        int x = model.getJogadorX() * TAMANHO_CELULA;
        int y = model.getJogadorY() * TAMANHO_CELULA;
        g.setColor(Color.BLUE);
        g.fillRect(x + 2, y + 2, TAMANHO_CELULA - 4, TAMANHO_CELULA - 4);

        x = model.getObstaculoX() * TAMANHO_CELULA;
        y = model.getObstaculoY() * TAMANHO_CELULA;
        g.setColor(Color.RED);
        g.fillRect(x + 2, y + 2, TAMANHO_CELULA - 4, TAMANHO_CELULA - 4);
    }

    private void reiniciar() {
        model.iniciar();
        timer.start();
        statusLabel.setText("Pontos: 0 | Velocidade: 1");
        gamePanel.repaint();
        gamePanel.requestFocusInWindow();
    }
}
