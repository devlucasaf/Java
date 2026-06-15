package org.application.faculdade.projetofaculdade;

import javax.swing.*;
import java.awt.*;

public class FrameCart extends JPanel {

    private JLabel labelCarrinho;

    public FrameCart() {
        setLayout(null);
        setBackground(new Color(0x664983));
        setBounds(0, 0, 470, 600);

        criarComponentes();
    }

    private void criarComponentes() {

        labelCarrinho = new JLabel("Carrinho");
        labelCarrinho.setBounds(200, 20, 200, 30);
        labelCarrinho.setForeground(Color.WHITE);
        labelCarrinho.setFont(new Font("Times New Roman", Font.BOLD, 20));

        add(labelCarrinho);
    }
}