package math.calculadora.completa.painel;

import math.calculadora.completa.tema.TemaEscuro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraPainelRegraTres extends JPanel {

    public CalculadoraPainelRegraTres() {
        super(new GridBagLayout());
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblInfo = new JLabel("A está para B, assim como C está para X");
        lblInfo.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblInfo, gbc);
        gbc.gridwidth = 1;

        JLabel lblA = new JLabel("A:");
        lblA.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 1;
        add(lblA, gbc);

        JTextField txtA = new JTextField("5", 10);
        txtA.setBackground(TemaEscuro.CAMPO);
        txtA.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 1;
        add(txtA, gbc);

        JLabel lblB = new JLabel("B:");
        lblB.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 2;
        add(lblB, gbc);

        JTextField txtB = new JTextField("10", 10);
        txtB.setBackground(TemaEscuro.CAMPO);
        txtB.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 2;
        add(txtB, gbc);

        JLabel lblC = new JLabel("C:");
        lblC.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 3;
        add(lblC, gbc);

        JTextField txtC = new JTextField("8", 10);
        txtC.setBackground(TemaEscuro.CAMPO);
        txtC.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 3;
        add(txtC, gbc);

        JLabel lblTipo = new JLabel("Proporção:");
        lblTipo.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 4;
        add(lblTipo, gbc);

        JComboBox<String> cbTipo = new JComboBox<>(new String[]{"Direta", "Inversa"});
        cbTipo.setBackground(TemaEscuro.BOTAO);
        cbTipo.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 4;
        add(cbTipo, gbc);

        JButton btnCalcular = new JButton("Calcular X");
        btnCalcular.setBackground(TemaEscuro.BOTAO);
        btnCalcular.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(btnCalcular, gbc);

        JLabel lblResultado = new JLabel("X: ");
        lblResultado.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        add(lblResultado, gbc);

        btnCalcular.addActionListener(e -> {
            try {
                double a = Double.parseDouble(txtA.getText());
                double b = Double.parseDouble(txtB.getText());
                double c = Double.parseDouble(txtC.getText());
                String tipo = (String) cbTipo.getSelectedItem();

                if (a == 0) {
                    lblResultado.setText("Erro: A não pode ser zero");
                    return;
                }

                double x;
                if (tipo.equals("Direta")) {
                    x = (b * c) / a;
                } else {
                    x = (a * b) / c;
                }
                lblResultado.setText(String.format("X: %.4f", x));
            } catch (Exception ex) {
                lblResultado.setText("Erro no valor!");
            }
        });
    }
}