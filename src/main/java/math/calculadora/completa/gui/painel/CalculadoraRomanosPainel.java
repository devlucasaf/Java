package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.model.conversoes.ConversorRomanos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraRomanosPainel extends JPanel {
    public CalculadoraRomanosPainel() {
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
                String romano = ConversorRomanos.toRoman(num);
                txtRomano.setText(romano);
                lblResultado.setText("Resultado: " + romano);
            } catch (Exception ex) {
                lblResultado.setText("Resultado: valor inválido!");
            }
        });

        btnParaNumero.addActionListener(e -> {
            try {
                String romano = txtRomano.getText().trim().toUpperCase();
                int num = ConversorRomanos.fromRoman(romano);
                txtNumero.setText(String.valueOf(num));
                lblResultado.setText("Resultado: " + num);
            } catch (Exception ex) {
                lblResultado.setText("Resultado: romano inválido!");
            }
        });
    }
}
