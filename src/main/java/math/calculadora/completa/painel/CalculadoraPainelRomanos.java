package math.calculadora.completa.painel;

import math.calculadora.completa.tema.TemaEscuro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraPainelRomanos extends JPanel {

    public CalculadoraPainelRomanos() {
        super(new GridBagLayout());
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblNumero = new JLabel("Número (1-3999):");
        lblNumero.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 0;
        add(lblNumero, gbc);

        JTextField txtNumero = new JTextField("2024", 10);
        txtNumero.setBackground(TemaEscuro.CAMPO);
        txtNumero.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 0;
        add(txtNumero, gbc);

        JLabel lblRomano = new JLabel("Romano:");
        lblRomano.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 1;
        add(lblRomano, gbc);

        JTextField txtRomano = new JTextField("MMXXIV", 10);
        txtRomano.setBackground(TemaEscuro.CAMPO);
        txtRomano.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 1;
        add(txtRomano, gbc);

        JButton btnParaRomano = new JButton("→ Romano");
        btnParaRomano.setBackground(TemaEscuro.BOTAO);
        btnParaRomano.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(btnParaRomano, gbc);

        JButton btnParaNumero = new JButton("→ Número");
        btnParaNumero.setBackground(TemaEscuro.BOTAO);
        btnParaNumero.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 2;
        add(btnParaNumero, gbc);

        JLabel lblResultado = new JLabel("Resultado: ");
        lblResultado.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(lblResultado, gbc);

        btnParaRomano.addActionListener(e -> {
            try {
                int num = Integer.parseInt(txtNumero.getText().trim());
                if (num < 1 || num > 3999) {
                    lblResultado.setText("Resultado: número fora do intervalo (1-3999)");
                    return;
                }
                String romano = toRoman(num);
                txtRomano.setText(romano);
                lblResultado.setText("Resultado: " + romano);
            } catch (NumberFormatException ex) {
                lblResultado.setText("Resultado: valor inválido!");
            }
        });

        btnParaNumero.addActionListener(e -> {
            String romano = txtRomano.getText().trim().toUpperCase();
            try {
                int num = fromRoman(romano);
                if (num < 1 || num > 3999) {
                    lblResultado.setText("Resultado: romano inválido!");
                    return;
                }
                txtNumero.setText(String.valueOf(num));
                lblResultado.setText("Resultado: " + num);
            } catch (IllegalArgumentException ex) {
                lblResultado.setText("Resultado: romano inválido!");
            }
        });
    }

    private String toRoman(int n) {
        String[] mil = {"", "M", "MM", "MMM"};
        String[] cen = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] dez = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] uni = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        return mil[n / 1000] + cen[(n % 1000) / 100] + dez[(n % 100) / 10] + uni[n % 10];
    }

    private int fromRoman(String s) {
        int result = 0;
        int prev = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            int val = romanValue(s.charAt(i));
            if (val < prev) {
                result -= val;
            } else {
                result += val;
            }
            prev = val;
        }
        return result;
    }

    private int romanValue(char c) {
        switch (c) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                throw new IllegalArgumentException();
        }
    }
}