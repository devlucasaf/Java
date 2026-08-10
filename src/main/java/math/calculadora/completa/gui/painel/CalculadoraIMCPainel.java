package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.model.calculos.IMC;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraIMCPainel extends JPanel {
    public CalculadoraIMCPainel() {
        super(new GridBagLayout());
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblPeso = new JLabel("Peso (kg):");
        lblPeso.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(lblPeso, gridBagConstraints);

        JTextField txtPeso = new JTextField("70", 10);
        txtPeso.setBackground(TemaEscuro.CAMPO);
        txtPeso.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        add(txtPeso, gridBagConstraints);

        JLabel lblAltura = new JLabel("Altura:");
        lblAltura.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        add(lblAltura, gridBagConstraints);

        JTextField txtAltura = new JTextField("1.75", 10);
        txtAltura.setBackground(TemaEscuro.CAMPO);
        txtAltura.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        add(txtAltura, gridBagConstraints);

        JButton btnCalcular = new JButton("Calcular IMC");
        btnCalcular.setBackground(TemaEscuro.BOTAO);
        btnCalcular.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        add(btnCalcular, gridBagConstraints);

        JLabel lblResultado = new JLabel("IMC: ");
        lblResultado.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        add(lblResultado, gridBagConstraints);

        JLabel lblClassificacao = new JLabel("Classificação: ");
        lblClassificacao.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        add(lblClassificacao, gridBagConstraints);

        btnCalcular.addActionListener(e -> {
            try {
                double peso = Double.parseDouble(txtPeso.getText());
                double altura = Double.parseDouble(txtAltura.getText());
                double imc = IMC.calcular(peso, altura);
                lblResultado.setText(String.format("IMC: %.2f", imc));
                lblClassificacao.setText("Classificação: " + IMC.classificar(imc));
            } catch (Exception ex) {
                lblResultado.setText("Erro no valor!");
                lblClassificacao.setText("Classificação: ");
            }
        });
    }
}
