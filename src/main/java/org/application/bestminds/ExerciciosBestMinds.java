package org.application.bestminds;

import javax.swing.*;

public class ExerciciosBestMinds {
    public static void main(String[] args) {

        JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        frame.add(panel);
        panel.setBounds(45, 23, 100, 100);
        frame.setBounds(45, 23, 500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Calculadora");
        frame.setResizable(false);
        frame.setVisible(true);

        for (int i = 0; i < 3 ; i++){
            addButton(panel, "Botão " + (i + 1),50 + i * 150,50,100,100);
        }

        //addButton(panel, "Marcelo", 45, 23, 100, 100);
    }

    // Método para adicionar um botão ao painel
    public static void addButton(JPanel panel, String nome, int x, int y, int w, int h) {
        JButton button = new JButton(nome);

        button.setBounds(x, y, w, h);
        panel.add(button);


    }
}
