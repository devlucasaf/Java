package games.plataforma.minigames.jogos.bingo.util;

import games.plataforma.minigames.gui.JanelaJogo;
import games.plataforma.minigames.jogos.bingo.model.Bingo;

import javax.swing.*;
import java.awt.*;

public class BingoUI extends JanelaJogo {

    private Bingo model;
    private JButton[][] botoes;
    private JLabel      lblUltimoNum;
    private JLabel      lblSorteados;
    private JButton     btnSortear;
    private JButton     btnNovaCartela;

    public BingoUI() {
        super("Bingo");
        inicializarComponentes();
    }

    @Override
    protected void inicializarComponentes() {
        model = new Bingo();
        setLayout(new BorderLayout(10, 10));

        JPanel painelCartela = new JPanel(new GridLayout(5, 5, 5, 5));
        painelCartela.setBackground(new Color(74, 74, 74));
        painelCartela.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        botoes = new JButton[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                JButton btn = new JButton(String.valueOf(model.getCartela()[i][j]));
                btn.setFont(new Font("Arial", Font.BOLD, 16));
                btn.setBackground(new Color(64, 64, 64));
                btn.setForeground(Color.WHITE);
                btn.setEnabled(false);
                btn.setFocusPainted(false);

                if (model.getMarcado()[i][j]) {
                    btn.setBackground(new Color(0, 200, 0));
                }
                botoes[i][j] = btn;
                painelCartela.add(btn);
            }
        }

        JPanel painelInfo = new JPanel(new GridLayout(2, 1));
        painelInfo.setOpaque(false);
        lblUltimoNum = new JLabel("Último número: -", SwingConstants.CENTER);
        lblUltimoNum.setFont(new Font("Arial", Font.BOLD, 16));
        lblUltimoNum.setForeground(Color.WHITE);
        lblSorteados = new JLabel("Sorteados: 0", SwingConstants.CENTER);
        lblSorteados.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSorteados.setForeground(Color.WHITE);
        painelInfo.add(lblUltimoNum);
        painelInfo.add(lblSorteados);

        JPanel painelBotoes = new JPanel(new FlowLayout());
        painelBotoes.setOpaque(false);
        btnSortear = new JButton("Sortear Número");
        btnSortear.setBackground(new Color(64, 64, 64));
        btnSortear.setForeground(Color.WHITE);
        btnSortear.setFocusPainted(false);
        btnSortear.addActionListener(e -> sortear());

        btnNovaCartela = new JButton("Nova Cartela");
        btnNovaCartela.setBackground(new Color(64, 64, 64));
        btnNovaCartela.setForeground(Color.WHITE);
        btnNovaCartela.setFocusPainted(false);
        btnNovaCartela.addActionListener(e -> novaCartela());
        painelBotoes.add(btnSortear);
        painelBotoes.add(btnNovaCartela);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(painelBotoes, BorderLayout.CENTER);
        JPanel voltarPanel = criarPainelBotoesVoltar();
        bottomPanel.add(voltarPanel, BorderLayout.SOUTH);

        add(painelInfo, BorderLayout.NORTH);
        add(painelCartela, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        pack();
        setSize(500, 600);
        setLocationRelativeTo(null);
    }

    private void sortear() {
        if (model.isFinalizado()) {
            JOptionPane.showMessageDialog(this, "Bingo! Você já venceu!");
            return;
        }

        boolean ok = model.sortearNumero();
        if (!ok) {
            JOptionPane.showMessageDialog(this, "Não há mais números para sortear.");
            return;
        }
        lblUltimoNum.setText("Último número: " + model.getUltimoSorteado());
        lblSorteados.setText("Sorteados: " + model.getNumerosSorteados().size());

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (model.getMarcado()[i][j]) {
                    botoes[i][j].setBackground(new Color(0, 200, 0));
                }
            }
        }

        if (model.isFinalizado()) {
            JOptionPane.showMessageDialog(this, "BINGO! Você completou uma linha/coluna/diagonal!");
        }
    }

    private void novaCartela() {
        model.gerarNovaCartela();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                botoes[i][j].setText(String.valueOf(model.getCartela()[i][j]));
                if (model.getMarcado()[i][j]) {
                    botoes[i][j].setBackground(new Color(0, 200, 0));
                } else {
                    botoes[i][j].setBackground(new Color(64, 64, 64));
                }
            }
        }
        lblUltimoNum.setText("Último número: -");
        lblSorteados.setText("Sorteados: 0");
    }
}
