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
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblNumero = new JLabel("Número (1-3999):");
        lblNumero.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(lblNumero, gridBagConstraints);

        JTextField txtNumero = new JTextField("2024", 10);
        txtNumero.setBackground(TemaEscuro.CAMPO);
        txtNumero.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        add(txtNumero, gridBagConstraints);

        JLabel lblRomano = new JLabel("Romano:");
        lblRomano.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        add(lblRomano, gridBagConstraints);

        JTextField txtRomano = new JTextField("MMXXIV", 10);
        txtRomano.setBackground(TemaEscuro.CAMPO);
        txtRomano.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        add(txtRomano, gridBagConstraints);

        JButton btnParaRomano = new JButton("→ Romano");
        btnParaRomano.setBackground(TemaEscuro.BOTAO);
        btnParaRomano.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        add(btnParaRomano, gridBagConstraints);

        JButton btnParaNumero = new JButton("→ Número");
        btnParaNumero.setBackground(TemaEscuro.BOTAO);
        btnParaNumero.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        add(btnParaNumero, gridBagConstraints);

        JLabel lblResultado = new JLabel("Resultado: ");
        lblResultado.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        add(lblResultado, gridBagConstraints);

        btnParaRomano.addActionListener(e -> {
            try {
                int num = Integer.parseInt(txtNumero.getText().trim());
                String romano = ConversorRomanos.paraRomano(num);
                txtRomano.setText(romano);
                lblResultado.setText("Resultado: " + romano);
            } catch (Exception ex) {
                lblResultado.setText("Resultado: valor inválido!");
            }
        });

        btnParaNumero.addActionListener(e -> {
            try {
                String romano = txtRomano.getText().trim().toUpperCase();
                int num = ConversorRomanos.deRomano(romano);
                txtNumero.setText(String.valueOf(num));
                lblResultado.setText("Resultado: " + num);
            } catch (Exception ex) {
                lblResultado.setText("Resultado: romano inválido!");
            }
        });
    }
}
