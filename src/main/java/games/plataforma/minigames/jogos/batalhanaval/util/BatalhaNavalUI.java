package games.plataforma.minigames.jogos.batalhanaval.util;

import games.plataforma.minigames.gui.JanelaJogo;
import games.plataforma.minigames.jogos.batalhanaval.model.BatalhaNaval;

import javax.swing.*;
import java.awt.*;

public class BatalhaNavalUI extends JanelaJogo {
    private BatalhaNaval    model;
    private JButton[][]     botoes;
    private JLabel          statusLabel;
    private JLabel          tirosLabel;
    private final int TAMANHO = 10;

    public BatalhaNavalUI() {
        super("Batalha Naval");
        inicializarComponentes();
    }

    @Override
    protected void inicializarComponentes() {
        model = new BatalhaNaval();
        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(TAMANHO, TAMANHO, 2, 2));
        gridPanel.setBackground(new Color(74,74,74));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        botoes = new JButton[TAMANHO][TAMANHO];

        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                JButton btn = new JButton(" ");
                btn.setBackground(new Color(64,64,64));
                btn.setForeground(Color.WHITE);
                btn.setFocusPainted(false);
                final int linha = i, coluna = j;
                btn.addActionListener(e -> atirar(linha, coluna));
                botoes[i][j] = btn;
                gridPanel.add(btn);
            }
        }

        JPanel topPanel = new JPanel(new GridLayout(2,1));
        topPanel.setOpaque(false);
        statusLabel = new JLabel("Navios restantes: " + model.getNaviosRestantes(), SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setForeground(Color.WHITE);
        tirosLabel = new JLabel("Tiros: 0", SwingConstants.CENTER);
        tirosLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        tirosLabel.setForeground(Color.WHITE);
        topPanel.add(statusLabel);
        topPanel.add(tirosLabel);

        JPanel botoesPanel = criarPainelBotoesVoltar();
        JButton btnReiniciar = new JButton("Reiniciar");
        btnReiniciar.setBackground(new Color(64,64,64));
        btnReiniciar.setForeground(Color.WHITE);
        btnReiniciar.setFocusPainted(false);
        btnReiniciar.addActionListener(e -> reiniciar());
        botoesPanel.add(btnReiniciar);

        add(topPanel, BorderLayout.NORTH);
        add(gridPanel, BorderLayout.CENTER);
        add(botoesPanel, BorderLayout.SOUTH);

        pack();
        setSize(500, 550);
        setLocationRelativeTo(null);
    }

    private void atirar(int linha, int coluna) {
        if (model.isGameOver()) {
            return;
        }
        boolean acertou = model.atirar(linha, coluna);
        atualizarBotoes();
        statusLabel.setText("Navios restantes: " + model.getNaviosRestantes());
        tirosLabel.setText("Tiros: " + model.getTiros());
        if (model.isGameOver()) {
            JOptionPane.showMessageDialog(this, "Parabéns! Você afundou todos os navios em " +
                    model.getTiros() + " tiros!");
        }
    }

    private void atualizarBotoes() {
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                int val = model.getCelula(i, j);
                JButton btn = botoes[i][j];
                if (val == 2) {
                    btn.setText("X");
                    btn.setBackground(Color.RED);
                    btn.setEnabled(false);
                } else if (val == 3) {
                    btn.setText("~");
                    btn.setBackground(new Color(100,100,100));
                    btn.setEnabled(false);
                }
            }
        }
    }

    private void reiniciar() {
        model.iniciar();
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                botoes[i][j].setText(" ");
                botoes[i][j].setBackground(new Color(64,64,64));
                botoes[i][j].setEnabled(true);
            }
        }
        statusLabel.setText("Navios restantes: " + model.getNaviosRestantes());
        tirosLabel.setText("Tiros: 0");
    }
}