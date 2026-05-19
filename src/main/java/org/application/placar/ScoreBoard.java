package org.application.placar;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScoreBoard {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Placar");

        JPanel panel = new JPanel();

        frame.setSize(360,640);
        frame.setVisible(true);
        frame.add(panel);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        panel.setSize(360,640);

        labelChato(panel, "Insira os dados abaixo", 10);
        label(panel,"Jogador 1",56);
        label(panel,"Jogador 2",156);
        label(panel,"Set por partidas",256);
        label(panel,"Ponto por set",356);

        JTextField textFieldOne = new JTextField("Jogador 1");
        textFieldOne.setBounds(10, 95, 320, 50);
        panel.add(textFieldOne);

        JTextField textFieldTwo = new JTextField("Jogador 2");
        textFieldTwo.setBounds(10, 195, 320, 50);
        panel.add(textFieldTwo);

        JTextField textFieldThree = new JTextField("3");
        textFieldThree.setBounds(10, 295, 320, 50);
        panel.add(textFieldThree);

        JTextField textFieldFour = new JTextField("5");
        textFieldFour.setBounds(10, 395, 320, 50);
        panel.add(textFieldFour);

        JButton button = new JButton("Começar");
        button.setBounds(120, 470,100,50);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataBase.playerOne  = textFieldOne.getText().toUpperCase();
                DataBase.playerTwo  = textFieldTwo.getText().toUpperCase();
                DataBase.set        = Integer.parseInt(textFieldThree.getText());
                DataBase.point      = Integer.parseInt(textFieldFour.getText());

                new PageTwo();

                frame.dispose();
            }
        });

        panel.add(button);
    }

    public static void labelChato(JPanel panel, String o, int y) {
        JLabel label = new JLabel(o);
        label.setBounds(10,y,150,50);
        panel.add(label);
    }

    public static void label(JPanel panel, String o, int y) {
        JLabel label = new JLabel(o);
        label.setBounds(10,y,100,50);
        panel.add(label);
    }
}
