package org.application.bestminds.placar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PageThree {
    public PageThree() {

        JFrame frame = new JFrame("Placar");
        JPanel panel = new JPanel();

        panel.setSize(360, 640);
        panel.setLayout(null);

        frame.setSize(360, 640);
        frame.setVisible(true);
        frame.add(panel);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JLabel label = new JLabel();

        label.setBounds(35, 115, 320, 150);
        panel.add(label);

        if (DataBase.setPointPlayerOne == 3) {
            label.setText("Parabéns! " + DataBase.playerOne + " Você Ganhou!");
            label.setFont(new Font("Arial", Font.BOLD, 16));
            label.setForeground(Color.RED);
        } else {
            label.setText("Parabéns! " + DataBase.playerTwo + " Você Ganhou!");
            label.setFont(new Font("Arial", Font.BOLD, 16));
            label.setForeground(Color.BLUE);
        }

        JLabel labelOneFinish = new JLabel("PLACAR FINAL");

        labelOneFinish.setBounds(105, 250, 150, 50);
        labelOneFinish.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(labelOneFinish);

        JLabel pontosJogador1 = new JLabel(DataBase.setPointPlayerOne + " SETS");

        pontosJogador1.setBounds(35, 300, 100, 50);
        pontosJogador1.setFont(new Font("Arial", Font.BOLD, 20));
        pontosJogador1.setForeground(Color.RED);
        panel.add(pontosJogador1);

        JLabel labelXFinal = new JLabel("X");

        labelXFinal.setBounds(165, 300, 50, 50);
        labelXFinal.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(labelXFinal);

        JLabel pontosJogador2 = new JLabel(DataBase.setPointPlayerTwo + " SETS");

        pontosJogador2.setBounds(225, 300, 100, 50);
        pontosJogador2.setFont(new Font("Arial", Font.BOLD, 20));
        pontosJogador2.setForeground(Color.BLUE);
        panel.add(pontosJogador2);

        JButton fecharBotao = new JButton("FIM");
        fecharBotao.setBounds(120, 470, 100, 50);
        panel.add(fecharBotao);

        fecharBotao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                System.exit(0);
            }
        });
    }
}
