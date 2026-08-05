package math.calculadora.completa.painel;

import math.calculadora.completa.tema.TemaEscuro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraPainelIMC extends JPanel {

    public CalculadoraPainelIMC() {
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

                if (peso <= 0 || altura <= 0) {
                    lblResultado.setText("Erro: valores devem ser positivos");
                    lblClassificacao.setText("Classificação: ");
                    return;
                }

                double imc = peso / (altura * altura);
                lblResultado.setText(String.format("IMC: %.2f", imc));
                lblClassificacao.setText("Classificação: " + classificar(imc));
            } catch (Exception ex) {
                lblResultado.setText("Erro no valor!");
                lblClassificacao.setText("Classificação: ");
            }
        });
    }

    private String classificar(double imc) {
        if (imc < 18.5) {
            return "Abaixo do peso";
        } else if (imc < 25) {
            return "Peso normal";
        } else if (imc < 30) {
            return "Sobrepeso";
        } else if (imc < 35) {
            return "Obesidade grau I";
        } else if (imc < 40) {
            return "Obesidade grau II";
        } else {
            return "Obesidade grau III";
        }
    }
}