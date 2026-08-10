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
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblBase = new JLabel("Base (a):");
        lblBase.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(lblBase, gridBagConstraints);

        JTextField txtBase = new JTextField("2", 8);
        txtBase.setBackground(TemaEscuro.CAMPO);
        txtBase.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        add(txtBase, gridBagConstraints);

        JLabel lblValor = new JLabel("Valor (b):");
        lblValor.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        add(lblValor, gridBagConstraints);

        JTextField txtValor = new JTextField("8", 8);
        txtValor.setBackground(TemaEscuro.CAMPO);
        txtValor.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        add(txtValor, gridBagConstraints);

        JButton btnCalcular = new JButton("Calcular logₐ(b)");
        btnCalcular.setBackground(TemaEscuro.BOTAO);
        btnCalcular.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        add(btnCalcular, gridBagConstraints);

        JLabel lblResultado = new JLabel("Resultado: ");
        lblResultado.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        add(lblResultado, gridBagConstraints);

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
