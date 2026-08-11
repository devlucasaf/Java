package games.plataforma.minigames.jogos.pedrapapeltesoura.util;

import games.plataforma.minigames.gui.JanelaJogo;
import games.plataforma.minigames.jogos.pedrapapeltesoura.model.Jogada;
import games.plataforma.minigames.jogos.pedrapapeltesoura.model.PedraPapelTesoura;

import javax.swing.*;
import java.awt.*;

public class PedraPapelTesouraUI extends JanelaJogo {

    private PedraPapelTesoura   model;
    private JLabel              placarLabel;
    private JLabel              resultadoLabel;
    private JLabel              jogadorLabel;
    private JLabel              computadorLabel;

    public PedraPapelTesouraUI() {
        super("Pedra, Papel e Tesoura");
        inicializarComponentes();
    }

    @Override
    protected void inicializarComponentes() {
        model = new PedraPapelTesoura();
        setLayout(new BorderLayout(10, 10));

        JPanel painelCentral = new JPanel(new GridLayout(4, 1, 10, 10));
        painelCentral.setOpaque(false);
        painelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        placarLabel = new JLabel("Placar: Vitórias: 0 | Derrotas: 0 | Empates: 0", SwingConstants.CENTER);
        placarLabel.setFont(new Font("Arial", Font.BOLD, 16));
        placarLabel.setForeground(Color.WHITE);

        resultadoLabel = new JLabel("Escolha sua jogada!", SwingConstants.CENTER);
        resultadoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        resultadoLabel.setForeground(Color.WHITE);

        JPanel jogadasPanel = new JPanel(new FlowLayout());
        jogadasPanel.setOpaque(false);
        String[] opcoes = {"Pedra", "Papel", "Tesoura"};

        for (String op : opcoes) {
            JButton btn = new JButton(op);
            btn.setBackground(new Color(64, 64, 64));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.addActionListener(e -> {
                Jogada jogada = Jogada.valueOf(op.toUpperCase());
                model.jogar(jogada);
                atualizarTela();
            });
            jogadasPanel.add(btn);
        }

        jogadorLabel = new JLabel("Você: -", SwingConstants.CENTER);
        jogadorLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        jogadorLabel.setForeground(Color.WHITE);

        computadorLabel = new JLabel("Computador: -", SwingConstants.CENTER);
        computadorLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        computadorLabel.setForeground(Color.WHITE);

        painelCentral.add(placarLabel);
        painelCentral.add(resultadoLabel);
        painelCentral.add(jogadorLabel);
        painelCentral.add(computadorLabel);

        JPanel botoesPanel = criarPainelBotoesVoltar();
        JButton btnReiniciar = new JButton("Reiniciar Placar");
        btnReiniciar.setBackground(new Color(64, 64, 64));
        btnReiniciar.setForeground(Color.WHITE);
        btnReiniciar.setFocusPainted(false);
        btnReiniciar.addActionListener(e -> {
            model.reiniciarPlacar();
            atualizarTela();
        });
        botoesPanel.add(btnReiniciar);
        add(painelCentral, BorderLayout.CENTER);
        add(botoesPanel, BorderLayout.SOUTH);

        add(jogadasPanel, BorderLayout.NORTH);

        pack();
        setSize(500, 350);
        setLocationRelativeTo(null);
    }

    private void atualizarTela() {
        placarLabel.setText("Placar: Vitórias: " + model.getVitorias() + " | Derrotas: " + model.getDerrotas() + " | Empates: " + model.getEmpates());
        if (model.getResultado() != null) {
            if (model.getResultado().equals("Jogador")) {
                resultadoLabel.setText("Você venceu!");
            } else if (model.getResultado().equals("Computador")) {
                resultadoLabel.setText("Computador venceu!");
            } else {
                resultadoLabel.setText("Empate!");
            }
            jogadorLabel.setText("Você: " + model.getJogadaJogador());
            computadorLabel.setText("Computador: " + model.getJogadaComputador());
        }
    }
}
