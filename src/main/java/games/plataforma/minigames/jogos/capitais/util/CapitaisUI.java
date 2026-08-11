package games.plataforma.minigames.jogos.capitais.util;

import games.plataforma.minigames.gui.JanelaJogo;
import games.plataforma.minigames.jogos.capitais.model.Capitais;

import javax.swing.*;
import java.awt.*;

public class CapitaisUI extends JanelaJogo {

    private Capitais    model;
    private JLabel      lblPais;
    private JLabel      lblPlacar;
    private JLabel      lblFeedback;
    private JPanel      panelOpcoes;
    private JButton     btnProximo;

    public CapitaisUI() {
        super("Capitais");
        inicializarComponentes();
    }

    @Override
    protected void inicializarComponentes() {
        model = new Capitais();
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        lblPais = new JLabel("País: " + model.getPaisAtual(), SwingConstants.CENTER);
        lblPais.setFont(new Font("Arial", Font.BOLD, 20));
        lblPais.setForeground(Color.WHITE);

        lblPlacar = new JLabel("Acertos: 0 | Erros: 0", SwingConstants.CENTER);
        lblPlacar.setFont(new Font("Arial", Font.PLAIN, 14));
        lblPlacar.setForeground(Color.WHITE);

        lblFeedback = new JLabel(" ", SwingConstants.CENTER);
        lblFeedback.setFont(new Font("Arial", Font.BOLD, 14));
        lblFeedback.setForeground(Color.WHITE);

        topPanel.add(lblPais);
        topPanel.add(lblPlacar);
        topPanel.add(lblFeedback);

        panelOpcoes = new JPanel(new GridLayout(2, 2, 10, 10));
        panelOpcoes.setOpaque(false);
        panelOpcoes.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        carregarOpcoes();

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.setOpaque(false);

        btnProximo = new JButton("Próximo País");
        btnProximo.setBackground(new Color(64, 64, 64));
        btnProximo.setForeground(Color.WHITE);
        btnProximo.setFocusPainted(false);
        btnProximo.setEnabled(false);
        btnProximo.addActionListener(e -> proximo());
        JPanel voltarPanel = criarPainelBotoesVoltar();
        btnPanel.add(btnProximo);
        btnPanel.add(voltarPanel.getComponent(0));

        add(topPanel, BorderLayout.NORTH);
        add(panelOpcoes, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        pack();
        setSize(500, 400);
        setLocationRelativeTo(null);
    }

    private void carregarOpcoes() {
        panelOpcoes.removeAll();
        model.novaPergunta();
        for (String opcao : model.getOpcoes()) {
            JButton btn = new JButton(opcao);
            btn.setBackground(new Color(64, 64, 64));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.addActionListener(e -> responder(opcao));
            panelOpcoes.add(btn);
        }
        panelOpcoes.revalidate();
        panelOpcoes.repaint();
        lblPais.setText("País: " + model.getPaisAtual());
        lblFeedback.setText(" ");
        btnProximo.setEnabled(false);
    }

    private void responder(String resposta) {
        boolean certo = model.responder(resposta);
        if (certo) {
            lblFeedback.setText("Correto! A capital de " + model.getPaisAtual() + " é " + model.getCapitalCorreta());
            lblFeedback.setForeground(Color.GREEN);
        } else {
            lblFeedback.setText("Errado! A capital de " + model.getPaisAtual() + " é " + model.getCapitalCorreta());
            lblFeedback.setForeground(Color.RED);
        }
        lblPlacar.setText("Acertos: " + model.getAcertos() + " | Erros: " + model.getErros());

        for (Component comp : panelOpcoes.getComponents()) {
            comp.setEnabled(false);
        }
        btnProximo.setEnabled(true);
    }

    private void proximo() {
        for (Component comp : panelOpcoes.getComponents()) {
            comp.setEnabled(true);
        }
        carregarOpcoes();
    }
}
