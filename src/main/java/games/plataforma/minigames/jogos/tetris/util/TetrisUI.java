package games.plataforma.minigames.jogos.tetris.util;

import games.plataforma.minigames.gui.JanelaJogo;
import games.plataforma.minigames.jogos.tetris.model.Tetris;

import javax.swing.*;
import java.awt.*;

public class TetrisUI extends JanelaJogo {
    private Tetris      model;
    private JPanel      gamePanel;
    private JLabel      infoLabel;
    private Timer       timer;

    public TetrisUI() {
        super("Tetris");
        inicializarComponentes();
    }

    @Override
    protected void inicializarComponentes() {
        model = new Tetris();
        setLayout(new BorderLayout());

        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                desenharJogo(g);
            }
        };
        gamePanel.setPreferredSize(new Dimension(300, 600));
        gamePanel.setBackground(new Color(45, 45, 45));
        gamePanel.setFocusable(true);

        infoLabel = new JLabel("Pontos: 0  Nível: 1");
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setOpaque(false);
        topPanel.add(infoLabel);

        JPanel botoesPanel = criarPainelBotoesVoltar();
        JButton reiniciar = new JButton("Reiniciar");
        reiniciar.setBackground(new Color(64,64,64));
        reiniciar.setForeground(Color.WHITE);
        reiniciar.setFocusPainted(false);
        reiniciar.addActionListener(e -> reiniciar());
        botoesPanel.add(reiniciar);

        add(topPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);
        add(botoesPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);

        timer = new Timer(model.getVelocidade(), e -> {
            model.moverBaixo();
            atualizarInfo();
            gamePanel.repaint();
            if (model.isGameOver()) {
                timer.stop();
                JOptionPane.showMessageDialog(this, "Game Over! Pontuação: " + model.getPontuacao());
            }
        });
        timer.start();

        gamePanel.addKeyListener(new TecladoAdapter(model, timer, gamePanel, this, this::atualizarInfo));
    }

    private void desenharJogo(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int tamanhoCelula = 25;
        int offsetX = 20;
        int offsetY = 20;

        // Tabuleiro
        int[][] tab = model.getTabuleiro();
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                if (tab[i][j] != 0) {
                    g2d.setColor(Color.CYAN);
                    g2d.fillRect(offsetX + j * tamanhoCelula, offsetY + i * tamanhoCelula, tamanhoCelula-1, tamanhoCelula-1);
                } else {
                    g2d.setColor(Color.DARK_GRAY);
                    g2d.drawRect(offsetX + j * tamanhoCelula, offsetY + i * tamanhoCelula, tamanhoCelula, tamanhoCelula);
                }
            }
        }

        // Peça atual
        int[][] peca = model.getPecaAtual();
        if (peca != null) {
            int posX = model.getPosicaoX();
            int posY = model.getPosicaoY();
            g2d.setColor(Color.GREEN);
            for (int i = 0; i < peca.length; i++) {
                for (int j = 0; j < peca[i].length; j++) {
                    if (peca[i][j] != 0) {
                        int px = offsetX + (posX + j) * tamanhoCelula;
                        int py = offsetY + (posY + i) * tamanhoCelula;
                        g2d.fillRect(px, py, tamanhoCelula-1, tamanhoCelula-1);
                    }
                }
            }
        }
    }

    private void atualizarInfo() {
        infoLabel.setText("Pontos: " + model.getPontuacao() + "  Nível: " + model.getNivel());
        timer.setDelay(model.getVelocidade());
    }

    private void reiniciar() {
        model.iniciar();
        timer.setDelay(model.getVelocidade());
        timer.restart();
        atualizarInfo();
        gamePanel.repaint();
        gamePanel.requestFocusInWindow();
    }
}
