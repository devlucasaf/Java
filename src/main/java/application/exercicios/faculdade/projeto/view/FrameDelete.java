package application.exercicios.faculdade.projetofaculdade.view;

import application.exercicios.faculdade.projetofaculdade.util.Cores;

import javax.swing.*;
import java.awt.*;

public class FrameDelete extends JPanel {

    public FrameDelete() {
        setLayout(null);
        setBackground(Cores.ROXO_CLARO);
        setBounds(0, 0, 1300, 600);

        criarComponentes();
    }

    private void criarComponentes() {

        JLabel titulo = new JLabel("Tela de Exclusão");
        titulo.setBounds(500, 50, 300, 30);
        titulo.setForeground(Cores.TEXTO);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));

        add(titulo);
    }
}

