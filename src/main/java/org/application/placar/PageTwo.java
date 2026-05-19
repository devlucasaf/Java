package org.application.placar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PageTwo {

    public PageTwo() {
        JFrame frame = new JFrame("Placar");
        JPanel panel = new JPanel();
        frame.setSize(360, 640);
        frame.setVisible(true);
        frame.add(panel);
        panel.setSize(360, 640);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        panel.setLayout(null);

        JLabel label = new JLabel("Quem fez o ponto?");
        label.setBounds(10, 16, 150, 50);
        panel.add(label);

        JTextField textField = new JTextField("Quem fez o ponto?");
        textField.setBounds(10, 65, 320, 50);
        panel.add(textField);

        JLabel labelPlayerOne = new JLabel("Pontos " + DataBase.playerOne);
        labelPlayerOne.setBounds(15, 115, 150, 50);
        panel.add(labelPlayerOne);

        JLabel labelTwo = new JLabel(String.valueOf(DataBase.setPointPlayerOne));
        labelTwo.setBounds(15, 140, 150, 50);
        labelTwo.setFont(new Font("Arial", Font.BOLD, 35));
        labelTwo.setForeground(Color.RED);
        panel.add(labelTwo);

        JLabel labelThree = new JLabel(String.valueOf(DataBase.pointPlayerOne));
        labelThree.setBounds(140, 175, 200, 200);
        labelThree.setFont(new Font("Arial", Font.BOLD, 150));
        labelThree.setForeground(Color.RED);
        panel.add(labelThree);

        JLabel labelPlayerTwo = new JLabel("Pontos " + DataBase.playerTwo);
        labelPlayerTwo.setBounds(15, 525, 150, 50);
        panel.add(labelPlayerTwo);

        JLabel labelFour = new JLabel(String.valueOf(DataBase.setPointPlayerTwo));
        labelFour.setBounds(15, 490, 150, 50);
        labelFour.setFont(new Font("Arial", Font.BOLD, 35));
        labelFour.setForeground(Color.BLUE);
        panel.add(labelFour);

        JLabel labelFive = new JLabel(String.valueOf(DataBase.pointPlayerTwo));
        labelFive.setBounds(140, 340, 200, 200);
        labelFive.setFont(new Font("Arial", Font.BOLD, 150));
        labelFive.setForeground(Color.BLUE);
        panel.add(labelFive);

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Point(
                            frame,
                            textField,
                            DataBase.playerOne,
                            labelThree,
                            labelFive,
                            labelTwo,
                            labelFour
                    );
                }
            }
        });
    }

    static void Point(JFrame frame, JTextField camp, String playerOne,
                      JLabel pointPlayerOne, JLabel pointPlayerTwo,
                      JLabel labelTwo, JLabel labelFour) {

        if (camp.getText().equalsIgnoreCase(playerOne)) {
            DataBase.pointPlayerOne++;
            pointPlayerOne.setText(String.valueOf(DataBase.pointPlayerOne));
        } else {
            DataBase.pointPlayerTwo++;
            pointPlayerTwo.setText(String.valueOf(DataBase.pointPlayerTwo));
        }

        if (DataBase.pointPlayerOne == DataBase.point) {
            DataBase.setPointPlayerOne++;
            labelTwo.setText(String.valueOf(DataBase.setPointPlayerOne));
            DataBase.pointPlayerOne = 0;
            DataBase.pointPlayerTwo = 0;
            pointPlayerOne.setText("0");
            pointPlayerTwo.setText("0");
        }

        if (DataBase.pointPlayerTwo == DataBase.point) {
            DataBase.setPointPlayerTwo++;
            labelFour.setText(String.valueOf(DataBase.setPointPlayerTwo));
            DataBase.pointPlayerTwo = 0;
            DataBase.pointPlayerOne = 0;
            pointPlayerOne.setText("0");
            pointPlayerTwo.setText("0");
        }

        if (DataBase.setPointPlayerOne == DataBase.set || DataBase.setPointPlayerTwo == DataBase.set) {
            frame.dispose();
            new PageThree();
        }
    }
}
