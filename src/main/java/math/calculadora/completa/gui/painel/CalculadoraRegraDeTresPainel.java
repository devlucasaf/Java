package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.model.financas.RegraDeTres;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraRegraDeTresPainel extends JPanel {
    public CalculadoraRegraDeTresPainel() {
        super(new GridBagLayout());
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblInformacao = new JLabel("A está para B, assim como C está para X");
        lblInformacao.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        add(lblInformacao, gridBagConstraints);
        gridBagConstraints.gridwidth = 1;

        JLabel lblA = new JLabel("A:");
        lblA.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        add(lblA, gridBagConstraints);

        JTextField txtA = new JTextField("5", 10);
        txtA.setBackground(TemaEscuro.CAMPO);
        txtA.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        add(txtA, gridBagConstraints);

        JLabel lblB = new JLabel("B:");
        lblB.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        add(lblB, gridBagConstraints);

        JTextField txtB = new JTextField("10", 10);
        txtB.setBackground(TemaEscuro.CAMPO);
        txtB.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        add(txtB, gridBagConstraints);

        JLabel lblC = new JLabel("C:");
        lblC.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        add(lblC, gridBagConstraints);

        JTextField txtC = new JTextField("8", 10);
        txtC.setBackground(TemaEscuro.CAMPO);
        txtC.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        add(txtC, gridBagConstraints);

        JLabel lblTipoProporcao = new JLabel("Proporção:");
        lblTipoProporcao.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        add(lblTipoProporcao, gridBagConstraints);

        JComboBox<String> cbTipo = new JComboBox<>(new String[]{"Direta", "Inversa"});
        cbTipo.setBackground(TemaEscuro.BOTAO);
        cbTipo.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        add(cbTipo, gridBagConstraints);

        JButton btnCalcular = new JButton("Calcular X");
        btnCalcular.setBackground(TemaEscuro.BOTAO);
        btnCalcular.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        add(btnCalcular, gridBagConstraints);

        JLabel lblResultado = new JLabel("X: ");
        lblResultado.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        add(lblResultado, gridBagConstraints);

        btnCalcular.addActionListener(e -> {
            try {
                double a = Double.parseDouble(txtA.getText());
                double b = Double.parseDouble(txtB.getText());
                double c = Double.parseDouble(txtC.getText());
                boolean direta = cbTipo.getSelectedItem().equals("Direta");
                double x = RegraDeTres.calcular(a, b, c, direta);
                lblResultado.setText(String.format("X: %.4f", x));
            } catch (Exception ex) {
                lblResultado.setText("Erro no valor!");
            }
        });
    }
}