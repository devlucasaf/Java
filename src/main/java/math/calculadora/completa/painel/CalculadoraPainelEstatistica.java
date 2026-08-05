package math.calculadora.completa.painel;

import math.calculadora.completa.tema.TemaEscuro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*;
import java.util.List;

public class CalculadoraPainelEstatistica extends JPanel {

    public CalculadoraPainelEstatistica() {
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

            if (valores.isEmpty()) {
                areaResultados.setText("Nenhum número informado.");
                return;
            }

            Collections.sort(valores);
            int n = valores.size();
            double soma = 0;
            for (double v : valores) {
                soma += v;
            }
            double media = soma / n;

            // Mediana
            double mediana;
            if (n % 2 == 0) {
                mediana = (valores.get(n / 2 - 1) + valores.get(n / 2)) / 2;
            } else {
                mediana = valores.get(n / 2);
            }

            // Moda
            Map<Double, Integer> freq = new HashMap<>();
            for (double v : valores) {
                freq.put(v, freq.getOrDefault(v, 0) + 1);
            }

            int maxFreq = Collections.max(freq.values());
            List<Double> modas = new ArrayList<>();
            for (Map.Entry<Double, Integer> entry : freq.entrySet()) {
                if (entry.getValue() == maxFreq) {
                    modas.add(entry.getKey());
                }
            }
            String modaStr = modas.size() == 1 ? String.valueOf(modas.get(0)) : modas.toString();

            double var = 0;
            for (double v : valores) {
                var += Math.pow(v - media, 2);
            }
            var /= n;
            double dp = Math.sqrt(var);

            areaResultados.setText(String.format(
                    "Média: %.4f\nMediana: %.4f\nModa: %s\nVariância: %.4f\nDesvio Padrão: %.4f",
                    media, mediana, modaStr, var, dp));
        });
    }
}