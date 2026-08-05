package math.calculadora.completa.painel;

import math.calculadora.completa.tema.TemaEscuro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraPainelCombustivel extends JPanel {

    private static final String[]   UNIDADES = {"L/100km", "km/L", "MPG (EUA)"};
    private static final double     KM_POR_MILHA = 1.60934;
    private static final double     LITROS_POR_GALAO = 3.78541;

    public CalculadoraPainelCombustivel() {
        super(new GridBagLayout());
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblValor = new JLabel("Valor:");
        lblValor.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 0;
        add(lblValor, gbc);

        JTextField txtValor = new JTextField("10", 10);
        txtValor.setBackground(TemaEscuro.CAMPO);
        txtValor.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 0;
        add(txtValor, gbc);

        JLabel lblDe = new JLabel("De:");
        lblDe.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 1;
        add(lblDe, gbc);

        JComboBox<String> cbDe = new JComboBox<>(UNIDADES);
        cbDe.setBackground(TemaEscuro.BOTAO);
        cbDe.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 1;
        add(cbDe, gbc);

        JLabel lblPara = new JLabel("Para:");
        lblPara.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 2;
        add(lblPara, gbc);

        JComboBox<String> cbPara = new JComboBox<>(UNIDADES);
        cbPara.setSelectedItem("km/L");
        cbPara.setBackground(TemaEscuro.BOTAO);
        cbPara.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 2;
        add(cbPara, gbc);

        JButton btnConverter = new JButton("Converter");
        btnConverter.setBackground(TemaEscuro.BOTAO);
        btnConverter.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(btnConverter, gbc);

        JLabel lblResultado = new JLabel("Resultado: ");
        lblResultado.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(lblResultado, gbc);

        btnConverter.addActionListener(e -> {
            try {
                double valor = Double.parseDouble(txtValor.getText());
                String de = (String) cbDe.getSelectedItem();
                String para = (String) cbPara.getSelectedItem();

                double kmPorLitro = paraKmPorLitro(valor, de);
                if (Double.isNaN(kmPorLitro) || Double.isInfinite(kmPorLitro)) {
                    lblResultado.setText("Erro no valor!");
                    return;
                }

                double resultado = deKmPorLitro(kmPorLitro, para);
                lblResultado.setText(String.format("Resultado: %.4f", resultado));
            } catch (Exception ex) {
                lblResultado.setText("Erro no valor!");
            }
        });
    }

    // Converte qualquer unidade de origem para km/L (unidade intermediária comum)
    private double paraKmPorLitro(double valor, String unidade) {
        switch (unidade) {
            case "km/L":
                return valor;
            case "L/100km":
                if (valor == 0) {
                    return Double.POSITIVE_INFINITY;
                }
                return 100.0 / valor;
            case "MPG (EUA)":
                return (valor * KM_POR_MILHA) / LITROS_POR_GALAO;
            default:
                return Double.NaN;
        }
    }

    // Converte de km/L para a unidade de destino desejada
    private double deKmPorLitro(double kmPorLitro, String unidade) {
        switch (unidade) {
            case "km/L":
                return kmPorLitro;
            case "L/100km":
                if (kmPorLitro == 0) {
                    return Double.POSITIVE_INFINITY;
                }
                return 100.0 / kmPorLitro;
            case "MPG (EUA)":
                return (kmPorLitro * LITROS_POR_GALAO) / KM_POR_MILHA;
            default:
                return Double.NaN;
        }
    }
}