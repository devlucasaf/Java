package games.plataforma.minigames.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public abstract class JanelaJogo extends JFrame {

    public JanelaJogo(String titulo) {
        TemaEscuro.aplicar();
        setTitle(titulo);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(45, 45, 45));
    }

    protected JPanel criarPainelBotoesVoltar() {
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.setOpaque(false);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setBackground(new Color(64, 64, 64));
        btnVoltar.setForeground(Color.WHITE);
        btnVoltar.setFocusPainted(false);
        btnVoltar.addActionListener((ActionEvent e) -> {
            dispose();
        });
        painelBotoes.add(btnVoltar);
        return painelBotoes;
    }

    protected abstract void inicializarComponentes();
}