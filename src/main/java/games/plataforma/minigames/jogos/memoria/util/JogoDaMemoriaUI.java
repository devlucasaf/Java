package games.plataforma.minigames.jogos.memoria.util;

import games.plataforma.minigames.gui.JanelaJogo;
import games.plataforma.minigames.jogos.memoria.model.JogoDaMemoria;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class JogoDaMemoriaUI extends JanelaJogo {

    private JogoDaMemoria   model;
    private JButton[]       botoes;
    private JLabel          statusLabel;
    private JLabel          tentativasLabel;
    private Timer           timer;

    public JogoDaMemoriaUI() {
        super("Jogo da Memória");
        inicializarComponentes();
    }

    @Override
    protected void inicializarComponentes() {
        model = new JogoDaMemoria(6);
        setLayout(new BorderLayout(10, 10));

        JPanel painelCartas = new JPanel(new GridLayout(3, 4, 10, 10));
        painelCartas.setBackground(new Color(74, 74, 74));
        painelCartas.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        botoes = new JButton[model.getTotalPares() * 2];
        for (int i = 0; i < botoes.length; i++) {
            JButton btn = new JButton("?");
            btn.setFont(new Font("Arial", Font.BOLD, 24));
            btn.setBackground(new Color(64, 64, 64));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            final int indice = i;
            btn.addActionListener(e -> selecionarCarta(indice));
            botoes[i] = btn;
            painelCartas.add(btn);
        }

        JPanel painelInfo = new JPanel(new BorderLayout());
        painelInfo.setOpaque(false);
        statusLabel = new JLabel("Encontre todos os pares!", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setForeground(Color.WHITE);
        tentativasLabel = new JLabel("Tentativas: 0", SwingConstants.CENTER);
        tentativasLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        tentativasLabel.setForeground(Color.WHITE);

        painelInfo.add(statusLabel, BorderLayout.NORTH);
        painelInfo.add(tentativasLabel, BorderLayout.SOUTH);

        JPanel botoesPanel = criarPainelBotoesVoltar();
        JButton reiniciar = new JButton("Reiniciar");
        reiniciar.setBackground(new Color(64, 64, 64));
        reiniciar.setForeground(Color.WHITE);
        reiniciar.setFocusPainted(false);
        reiniciar.addActionListener(e -> reiniciar());
        botoesPanel.add(reiniciar);

        add(painelCartas, BorderLayout.CENTER);
        add(painelInfo, BorderLayout.NORTH);
        add(botoesPanel, BorderLayout.SOUTH);

        pack();
        setSize(500, 500);
        setLocationRelativeTo(null);

        timer = new Timer(500, (ActionEvent e) -> {
            if (model.isAguardando()) {
                int p = model.getPrimeira();
                int s = model.getSegunda();
                if (p != -1 && s != -1) {
                    model.resetarSelecao();
                    atualizarBotoes();
                }
                timer.stop();
            }
        });
        timer.setRepeats(false);
    }

    private void selecionarCarta(int indice) {
        if (model.isFim()) {
            return;
        }

        if (model.isAguardando()) {
            return;
        }

        boolean ok = model.selecionarCarta(indice);
        if (!ok) {
            return;
        }

        atualizarBotoes();

        if (model.isAguardando()) {
            timer.start();
        }

        tentativasLabel.setText("Tentativas: " + model.getTentativas());

        if (model.isFim()) {
            statusLabel.setText("Parabéns! Você encontrou todos os pares!");
        }
    }

    private void atualizarBotoes() {
        for (int i = 0; i < botoes.length; i++) {
            if (model.isVirada(i)) {
                botoes[i].setText(model.getCarta(i));
                botoes[i].setEnabled(false);
            } else {
                botoes[i].setText("?");
                botoes[i].setEnabled(true);
            }
        }
    }

    private void reiniciar() {
        model.iniciar();
        for (JButton btn : botoes) {
            btn.setText("?");
            btn.setEnabled(true);
            btn.setBackground(new Color(64, 64, 64));
        }
        statusLabel.setText("Encontre todos os pares!");
        tentativasLabel.setText("Tentativas: 0");

        if (timer.isRunning()) {
            timer.stop();
        }
    }
}
