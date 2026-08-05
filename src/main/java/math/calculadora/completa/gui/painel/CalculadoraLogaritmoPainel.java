package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.model.calculos.Logaritmo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraLogaritmoPainel extends JPanel {
    public CalculadoraLogaritmoPainel() {
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
                double resultado = Logaritmo.logBase(base, valor);
                lblResultado.setText(String.format("Resultado: %.10f", resultado));
            } catch (Exception ex) {
                lblResultado.setText("Resultado: " + ex.getMessage());
            }
        });
    }
}
