package application.exercicios.faculdade.projeto.view;

import application.exercicios.faculdade.projeto.util.Cores;

import javax.swing.*;
import java.util.HashMap;

public class FrameInsert extends JPanel {

    private HashMap<String, JComponent> widgets;

    public FrameInsert() {
        setLayout(null);
        setBackground(Cores.ROXO_CLARO);
        setBounds(0, 0, 1300, 600);

        widgets = new HashMap<>();

        criarCampos();
    }

    private void criarCampos() {

        JLabel labelNome = new JLabel("Nome");
        labelNome.setBounds(150, 100, 200, 30);
        labelNome.setForeground(Cores.TEXTO);
        add(labelNome);

        JTextField campoNome = new JTextField();
        campoNome.setBounds(150, 140, 200, 30);
        add(campoNome);

        widgets.put("nome", campoNome);
    }

    public void mostrarCamposJogo() {
        System.out.println("Campos de jogo ativados");
    }

    public void mostrarCamposJogador() {
        System.out.println("Campos de jogador ativados");
    }
}

