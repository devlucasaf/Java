package org.application.faculdade.projetofaculdade;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        JFrame janela = new JFrame("Plataforma de Jogos");
        janela.setSize(1600, 700);
        janela.setLayout(null);
        janela.getContentPane().setBackground(new Color(0x442e73));
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setResizable(false);

        // Cores
        Color roxoClaro = new Color(0x664983);
        Color lilasMedio = new Color(0xa676b0);

        // Frames
        JPanel frameSelect = new JPanel();
        frameSelect.setBounds(250, 15, 1300, 600);
        frameSelect.setVisible(false);

        FrameInsert frameInsert = new FrameInsert();
        frameInsert.setBounds(250, 15, 1300, 600);
        frameInsert.setVisible(false);

        JPanel frameUpdate = new JPanel();
        frameUpdate.setBounds(250, 15, 1300, 600);
        frameUpdate.setVisible(false);

        FrameDelete frameDelete = new FrameDelete();
        frameDelete.setBounds(250, 15, 1300, 600);
        frameDelete.setVisible(false);

        FrameCart frameCart = new FrameCart();
        frameCart.setBounds(250, 15, 470, 600);
        frameCart.setVisible(false);

        janela.add(frameSelect);
        janela.add(frameInsert);
        janela.add(frameUpdate);
        janela.add(frameDelete);
        janela.add(frameCart);

        // CONTROLE DE VISIBILIDADE
        Runnable mostrarSelect = () -> {
            frameSelect.setVisible(true);
            frameInsert.setVisible(false);
            frameUpdate.setVisible(false);
            frameDelete.setVisible(false);
            frameCart.setVisible(false);
        };

        Runnable mostrarInsert = () -> {
            frameSelect.setVisible(false);
            frameInsert.setVisible(true);
            frameUpdate.setVisible(false);
            frameDelete.setVisible(false);
            frameCart.setVisible(false);
        };

        Runnable mostrarUpdate = () -> {
            frameSelect.setVisible(false);
            frameInsert.setVisible(false);
            frameUpdate.setVisible(true);
            frameDelete.setVisible(false);
            frameCart.setVisible(false);
        };

        Runnable mostrarDelete = () -> {
            frameSelect.setVisible(false);
            frameInsert.setVisible(false);
            frameUpdate.setVisible(false);
            frameDelete.setVisible(true);
            frameCart.setVisible(false);
        };

        // BOTÕES
        new AppButton(
                janela,
                roxoClaro,
                lilasMedio,
                frameSelect,
                frameInsert,
                frameCart,
                null, null, null, null,
                mostrarSelect,
                mostrarInsert,
                mostrarUpdate,
                mostrarDelete
        );

        janela.setVisible(true);
    }
}