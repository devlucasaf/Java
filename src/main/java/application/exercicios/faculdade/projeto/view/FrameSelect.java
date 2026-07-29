package application.exercicios.faculdade.projeto.view;

import application.exercicios.faculdade.projeto.util.Cores;

import javax.swing.*;
import java.awt.*;

public class FrameSelect extends JPanel {

    private JLabel labelInfoJogo;
    private JLabel labelInfoUsuario;

    public FrameSelect() {

        setLayout(null);
        setBackground(Cores.ROXO_CLARO);
        setBounds(0, 0, 800, 600);

        criarComponentes();
    }

    private void criarComponentes() {

        labelInfoJogo = new JLabel("Informação do Jogo");
        labelInfoJogo.setBounds(20, 20, 300, 30);
        labelInfoJogo.setForeground(Cores.TEXTO);
        labelInfoJogo.setFont(new Font("Times New Roman", Font.BOLD, 20));
        add(labelInfoJogo);

        labelInfoUsuario = new JLabel("Informação do Usuário");
        labelInfoUsuario.setBounds(20, 360, 300, 30);
        labelInfoUsuario.setForeground(Cores.TEXTO);
        labelInfoUsuario.setFont(new Font("Times New Roman", Font.BOLD, 20));
        add(labelInfoUsuario);
    }
}

