package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.model.calculos.Estatistica;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CalculadoraEstatisticaPainel extends JPanel {
    public CalculadoraEstatisticaPainel() {
        super(new GridBagLayout());
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblNumeros = new JLabel("Números (separados por vírgula):");
        lblNumeros.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 0;
        add(lblNumeros, gbc);

        JTextField txtNumeros = new JTextField("1,2,3,4,5,6,7,8,9,10", 20);
        txtNumeros.setBackground(TemaEscuro.CAMPO);
        txtNumeros.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 0;
        add(txtNumeros, gbc);

        JButton btnCalcular = new JButton("Calcular");
        btnCalcular.setBackground(TemaEscuro.BOTAO);
        btnCalcular.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(btnCalcular, gbc);

        JTextArea areaResultados = new JTextArea(10, 30);
        areaResultados.setBackground(TemaEscuro.CAMPO);
        areaResultados.setForeground(TemaEscuro.TEXTO);
        areaResultados.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaResultados);
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(scroll, gbc);

        btnCalcular.addActionListener(e -> {
            String[] parts = txtNumeros.getText().split(",");
            List<Double> valores = new ArrayList<>();
            for (String p : parts) {
                try {
                    valores.add(Double.parseDouble(p.trim()));
                } catch (NumberFormatException ex) {
                    areaResultados.setText("Erro: valor inválido!");
                    return;
                }
            }

            try {
                Estatistica.ResultadoEstatistico res = Estatistica.calcular(valores);
                areaResultados.setText(String.format(
                        "Média: %.4f\nMediana: %.4f\nModa: %s\nVariância: %.4f\nDesvio Padrão: %.4f",
                        res.media, res.mediana, res.moda.toString(), res.variancia, res.desvioPadrao));
            } catch (Exception ex) {
                areaResultados.setText("Erro: " + ex.getMessage());
            }
        });
    }
}
