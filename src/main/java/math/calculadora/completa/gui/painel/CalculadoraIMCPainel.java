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
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblPeso = new JLabel("Peso (kg):");
        lblPeso.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 0;
        add(lblPeso, gbc);
        JTextField txtPeso = new JTextField("70", 10);
        txtPeso.setBackground(TemaEscuro.CAMPO);
        txtPeso.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 0;
        add(txtPeso, gbc);

        JLabel lblAltura = new JLabel("Altura (m):");
        lblAltura.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 1;
        add(lblAltura, gbc);
        JTextField txtAltura = new JTextField("1.75", 10);
        txtAltura.setBackground(TemaEscuro.CAMPO);
        txtAltura.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 1;
        add(txtAltura, gbc);

        JButton btnCalcular = new JButton("Calcular IMC");
        btnCalcular.setBackground(TemaEscuro.BOTAO);
        btnCalcular.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(btnCalcular, gbc);

        JLabel lblResultado = new JLabel("IMC: ");
        lblResultado.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(lblResultado, gbc);

        JLabel lblClassificacao = new JLabel("Classificação: ");
        lblClassificacao.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(lblClassificacao, gbc);

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
