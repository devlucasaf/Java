package org.application.faculdade.projetofaculdade;

import javax.swing.*;
import java.awt.*;

public class FrameUpdate extends JPanel {

    public FrameUpdate() {

        setLayout(null);
        setBackground(new Color(0x664983));
        setBounds(0, 0, 1300, 600);

        criarComponentes();
    }

    private void criarComponentes() {

        JLabel titulo = new JLabel("Tela de Atualização");
        titulo.setBounds(500, 50, 300, 30);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));

        add(titulo);
    }
}