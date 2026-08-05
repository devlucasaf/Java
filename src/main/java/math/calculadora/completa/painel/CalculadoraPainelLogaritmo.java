package math.calculadora.completa.painel;

import math.calculadora.completa.tema.TemaEscuro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraPainelLogaritmo extends JPanel {

    public CalculadoraPainelLogaritmo() {
        super(new GridBagLayout());
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblBase = new JLabel("Base (a):");
        lblBase.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 0;
        add(lblBase, gbc);

        JTextField txtBase = new JTextField("2", 8);
        txtBase.setBackground(TemaEscuro.CAMPO);
        txtBase.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 0;
        add(txtBase, gbc);

        JLabel lblValor = new JLabel("Valor (b):");
        lblValor.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 1;
        add(lblValor, gbc);

        JTextField txtValor = new JTextField("8", 8);
        txtValor.setBackground(TemaEscuro.CAMPO);
        txtValor.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 1;
        add(txtValor, gbc);

        JButton btnCalcular = new JButton("Calcular logₐ(b)");
        btnCalcular.setBackground(TemaEscuro.BOTAO);
        btnCalcular.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(btnCalcular, gbc);

        JLabel lblResultado = new JLabel("Resultado: ");
        lblResultado.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(lblResultado, gbc);

        btnCalcular.addActionListener(e -> {
            try {
                double base = Double.parseDouble(txtBase.getText());
                double valor = Double.parseDouble(txtValor.getText());
                if (base <= 0 || base == 1 || valor <= 0) {
                    lblResultado.setText("Resultado: base e valor devem ser > 0, base ≠ 1");
                    return;
                }
                double resultado = Math.log(valor) / Math.log(base);
                lblResultado.setText(String.format("Resultado: %.10f", resultado));
            } catch (NumberFormatException ex) {
                lblResultado.setText("Resultado: valor inválido!");
            }
        });
    }
}