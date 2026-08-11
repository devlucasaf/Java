package games.plataforma.minigames.jogos.campominado.util;

import games.plataforma.minigames.gui.JanelaJogo;
import games.plataforma.minigames.jogos.campominado.model.CampoMinado;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CampoMinadoUI extends JanelaJogo {

    private CampoMinado         model;
    private JButton[][]         botoes;
    private JLabel              lblStatus;
    private JLabel              lblMinas;

    public CampoMinadoUI() {
        super("Campo Minado");
        inicializarComponentes();
    }

    @Override
    protected void inicializarComponentes() {
        model = new CampoMinado();
        setLayout(new BorderLayout());

        JPanel painelTabuleiro = new JPanel(new GridLayout(model.getLinhas(), model.getColunas(), 2, 2));
        painelTabuleiro.setBackground(new Color(74, 74, 74));
        painelTabuleiro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        botoes = new JButton[model.getLinhas()][model.getColunas()];
        for (int i = 0; i < model.getLinhas(); i++) {
            for (int j = 0; j < model.getColunas(); j++) {
                JButton btn = new JButton(" ");
                btn.setFont(new Font("Arial", Font.BOLD, 14));
                btn.setBackground(new Color(64, 64, 64));
                btn.setForeground(Color.WHITE);
                btn.setFocusPainted(false);
                final int linha = i, coluna = j;

                btn.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            processarClique(linha, coluna);
                        } else if (SwingUtilities.isRightMouseButton(e)) {
                            processarBandeira(linha, coluna);
                        }
                    }
                });
                botoes[i][j] = btn;
                painelTabuleiro.add(btn);
            }
        }

        JPanel painelInfo = new JPanel(new BorderLayout());
        painelInfo.setOpaque(false);
        lblStatus = new JLabel("Clique em uma célula para começar", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Arial", Font.BOLD, 16));
        lblStatus.setForeground(Color.WHITE);
        lblMinas = new JLabel("Minas restantes: " + model.getMinasRestantes(), SwingConstants.CENTER);
        lblMinas.setFont(new Font("Arial", Font.PLAIN, 14));
        lblMinas.setForeground(Color.WHITE);

        JPanel top = new JPanel(new GridLayout(2, 1));
        top.setOpaque(false);
        top.add(lblStatus);
        top.add(lblMinas);

        JPanel botoesPanel = criarPainelBotoesVoltar();
        JButton btnReiniciar = new JButton("Reiniciar");
        btnReiniciar.setBackground(new Color(64, 64, 64));
        btnReiniciar.setForeground(Color.WHITE);
        btnReiniciar.setFocusPainted(false);
        btnReiniciar.addActionListener(e -> reiniciar());
        botoesPanel.add(btnReiniciar);

        add(top, BorderLayout.NORTH);
        add(painelTabuleiro, BorderLayout.CENTER);
        add(botoesPanel, BorderLayout.SOUTH);

        pack();
        setSize(500, 500);
        setLocationRelativeTo(null);
    }

    private void processarClique(int linha, int coluna) {
        if (model.isGameOver()) {
            return;
        }

        boolean explodiu = model.revelar(linha, coluna);
        atualizarTabuleiro();
        if (explodiu) {
            lblStatus.setText("Você perdeu! Clicou em uma mina.");
            revelarMinas();
        } else if (model.isVenceu()) {
            lblStatus.setText("Parabéns! Você venceu!");
        } else {
            lblStatus.setText("Continue...");
        }
        lblMinas.setText("Minas restantes: " + model.getMinasRestantes());
    }

    private void processarBandeira(int linha, int coluna) {
        if (model.isGameOver()) {
            return;
        }
        model.alternarBandeira(linha, coluna);
        atualizarTabuleiro();
        lblMinas.setText("Minas restantes: " + model.getMinasRestantes());
    }

    private void atualizarTabuleiro() {
        for (int i = 0; i < model.getLinhas(); i++) {
            for (int j = 0; j < model.getColunas(); j++) {
                JButton btn = botoes[i][j];
                if (model.isRevelado(i, j)) {
                    int valor = model.getValor(i, j);
                    btn.setBackground(new Color(100, 100, 100));
                    btn.setEnabled(false);
                    if (valor == 0) {
                        btn.setText(" ");
                    } else if (valor == -1) {
                        btn.setText("💣");
                        btn.setBackground(Color.RED);
                    } else {
                        btn.setText(String.valueOf(valor));
                        switch (valor) {
                            case 1:
                                btn.setForeground(Color.BLUE);
                                break;
                            case 2:
                                btn.setForeground(Color.GREEN);
                                break;
                            case 3:
                                btn.setForeground(Color.RED);
                                break;
                            case 4:
                                btn.setForeground(new Color(0, 0, 128));
                                break;
                            case 5:
                                btn.setForeground(new Color(128, 0, 0));
                                break;
                            case 6:
                                btn.setForeground(Color.CYAN);
                                break;
                            case 7:
                                btn.setForeground(Color.BLACK);
                                break;
                            case 8:
                                btn.setForeground(Color.GRAY);
                                break;
                        }
                    }
                } else if (model.isBandeira(i, j)) {
                    btn.setText("🚩");
                    btn.setBackground(new Color(64, 64, 64));
                    btn.setEnabled(true);
                } else {
                    btn.setText(" ");
                    btn.setBackground(new Color(64, 64, 64));
                    btn.setEnabled(true);
                }
            }
        }
    }

    private void revelarMinas() {
        for (int i = 0; i < model.getLinhas(); i++) {
            for (int j = 0; j < model.getColunas(); j++) {
                if (model.getValor(i, j) == -1) {
                    botoes[i][j].setText("💣");
                    botoes[i][j].setBackground(Color.RED);
                }
            }
        }
    }

    private void reiniciar() {
        model.iniciar();
        for (int i = 0; i < model.getLinhas(); i++) {
            for (int j = 0; j < model.getColunas(); j++) {
                botoes[i][j].setText(" ");
                botoes[i][j].setBackground(new Color(64, 64, 64));
                botoes[i][j].setEnabled(true);
                botoes[i][j].setForeground(Color.WHITE);
            }
        }
        lblStatus.setText("Clique em uma célula para começar");
        lblMinas.setText("Minas restantes: " + model.getMinasRestantes());
    }
}
