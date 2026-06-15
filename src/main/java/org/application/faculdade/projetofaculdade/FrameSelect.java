package org.application.faculdade.projetofaculdade;

import javax.swing.*;
import java.awt.*;

public class FrameSelect extends JPanel {

    private JLabel labelInfoJogo;
    private JLabel labelInfoUsuario;

    public FrameSelect() {

        setLayout(null);
        setBackground(new Color(0x664983));
        setBounds(0, 0, 800, 600);

        criarComponentes();
    }

    private void criarComponentes() {

        labelInfoJogo = new JLabel("Informação do Jogo");
        labelInfoJogo.setBounds(20, 20, 300, 30);
        labelInfoJogo.setForeground(Color.WHITE);
        labelInfoJogo.setFont(new Font("Times New Roman", Font.BOLD, 20));
        add(labelInfoJogo);

        labelInfoUsuario = new JLabel("Informação do Usuário");
        labelInfoUsuario.setBounds(20, 360, 300, 30);
        labelInfoUsuario.setForeground(Color.WHITE);
        labelInfoUsuario.setFont(new Font("Times New Roman", Font.BOLD, 20));
        add(labelInfoUsuario);
    }
}