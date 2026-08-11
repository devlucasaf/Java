package games.plataforma.minigames.jogos.velha.util;

import games.plataforma.minigames.gui.JanelaJogo;
import games.plataforma.minigames.jogos.velha.model.JogoDaVelha;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JogoDaVelhaUI extends JanelaJogo {

    private JogoDaVelha         model;
    private JButton[][]         botoes;
    private JLabel              statusLabel;

    public JogoDaVelhaUI() {
        super("Jogo da Velha");
        inicializarComponentes();
    }

    @Override
    protected void inicializarComponentes() {
        model = new JogoDaVelha();
        setLayout(new BorderLayout(10, 10));

        JPanel painelTabuleiro = new JPanel(new GridLayout(3, 3, 5, 5));
        painelTabuleiro.setBackground(new Color(74, 74, 74));
        painelTabuleiro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        botoes = new JButton[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton btn = new JButton(" ");
                btn.setFont(new Font("Arial", Font.BOLD, 40));
                btn.setBackground(new Color(64, 64, 64));
                btn.setForeground(Color.WHITE);
                btn.setFocusPainted(false);
                final int linha = i;
                final int coluna = j;
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        processarJogada(linha, coluna);
                    }
                });
                botoes[i][j] = btn;
                painelTabuleiro.add(btn);
            }
        }

        statusLabel = new JLabel("Vez do jogador X", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        statusLabel.setForeground(Color.WHITE);

        JPanel painelInferior = new JPanel(new BorderLayout());
        painelInferior.setOpaque(false);
        painelInferior.add(statusLabel, BorderLayout.CENTER);

        JPanel painelBotoes = criarPainelBotoesVoltar();
        JButton reiniciar = new JButton("Reiniciar");
        reiniciar.setBackground(new Color(64, 64, 64));
        reiniciar.setForeground(Color.WHITE);
        reiniciar.setFocusPainted(false);
        reiniciar.addActionListener(e -> reiniciarJogo());
        painelBotoes.add(reiniciar);

        painelInferior.add(painelBotoes, BorderLayout.SOUTH);

        add(painelTabuleiro, BorderLayout.CENTER);
        add(painelInferior, BorderLayout.SOUTH);

        pack();
        setSize(500, 500);
        setLocationRelativeTo(null);
    }

    private void processarJogada(int linha, int coluna) {
        if (model.isFimDeJogo()) {
            return;
        }

        boolean sucesso = model.fazerJogada(linha, coluna);
        if (!sucesso) {
            return;
        }

        atualizarTabuleiro();

        if (model.isFimDeJogo()) {
            if (model.verificarVitoria()) {
                statusLabel.setText("Jogador " + model.getJogadorAtual() + " venceu!");
            } else {
                statusLabel.setText("Empate!");
            }
        } else {
            statusLabel.setText("Vez do jogador " + model.getJogadorAtual());
        }
    }

    private void atualizarTabuleiro() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                char c = model.getCelula(i, j);
                botoes[i][j].setText(c == ' ' ? " " : String.valueOf(c));
            }
        }
    }

    private void reiniciarJogo() {
        model.reiniciar();
        atualizarTabuleiro();
        statusLabel.setText("Vez do jogador X");
    }
}
