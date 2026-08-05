package math.calculadora.completa.painel;

import math.calculadora.completa.tema.TemaEscuro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraPainelTemperatura extends JPanel {

    public CalculadoraPainelTemperatura() {
        super(new GridBagLayout());
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblValor = new JLabel("Valor:");
        lblValor.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0; gridBagConstraints.gridy = 0;
        add(lblValor, gridBagConstraints);

        JTextField txtValor = new JTextField("0", 10);
        txtValor.setBackground(TemaEscuro.CAMPO);
        txtValor.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1; gridBagConstraints.gridy = 0;
        add(txtValor, gridBagConstraints);

        JLabel lblDe = new JLabel("De:");
        lblDe.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0; gridBagConstraints.gridy = 1;
        add(lblDe, gridBagConstraints);

        String[] escalas = {"Celsius", "Fahrenheit", "Kelvin"};
        JComboBox<String> cbDe = new JComboBox<>(escalas);
        cbDe.setBackground(TemaEscuro.BOTAO);
        cbDe.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1; gridBagConstraints.gridy = 1;
        add(cbDe, gridBagConstraints);

        JLabel lblPara = new JLabel("Para:");
        lblPara.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0; gridBagConstraints.gridy = 2;
        add(lblPara, gridBagConstraints);

        JComboBox<String> cbPara = new JComboBox<>(escalas);
        cbPara.setBackground(TemaEscuro.BOTAO);
        cbPara.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1; gridBagConstraints.gridy = 2;
        add(cbPara, gridBagConstraints);

        JButton btnConverter = new JButton("Converter");
        btnConverter.setBackground(TemaEscuro.BOTAO);
        btnConverter.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0; gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        add(btnConverter, gridBagConstraints);

        JLabel lblResultado = new JLabel("Resultado: ");
        lblResultado.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0; gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        add(lblResultado, gridBagConstraints);

        btnConverter.addActionListener(e -> {
            try {
                double valor = Double.parseDouble(txtValor.getText());
                String de = (String) cbDe.getSelectedItem();
                String para = (String) cbPara.getSelectedItem();
                double celsius;

                switch (de) {
                    case "Celsius":
                        celsius = valor;
                        break;
                    case "Fahrenheit":
                        celsius = (valor - 32) * 5.0 / 9.0;
                        break;
                    case "Kelvin":
                        celsius = valor - 273.15;
                        break;
                    default: celsius = 0;
                }

                double resultado;
                switch (para) {
                    case "Celsius":
                        resultado = celsius;
                        break;
                    case "Fahrenheit":
                        resultado = celsius * 9.0 / 5.0 + 32;
                        break;
                    case "Kelvin":
                        resultado = celsius + 273.15;
                        break;
                    default:
                        resultado = 0;
                }
                lblResultado.setText(String.format("Resultado: %.2f", resultado));
            } catch (Exception ex) {
                lblResultado.setText("Erro no valor!");
            }
        });
    }
}
