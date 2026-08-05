package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.model.calculos.Bitwise;
import math.calculadora.completa.util.Constantes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraBitwisePainel extends JPanel {
    public CalculadoraBitwisePainel() {
        super(new GridBagLayout());
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblA = new JLabel("A (decimal):");
        lblA.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 0;
        add(lblA, gbc);
        JTextField txtA = new JTextField("12", 10);
        txtA.setBackground(TemaEscuro.CAMPO);
        txtA.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 0;
        add(txtA, gbc);

        JLabel lblB = new JLabel("B (decimal):");
        lblB.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 1;
        add(lblB, gbc);
        JTextField txtB = new JTextField("10", 10);
        txtB.setBackground(TemaEscuro.CAMPO);
        txtB.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 1;
        add(txtB, gbc);

        JLabel lblOperacao = new JLabel("Operação:");
        lblOperacao.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 2;
        add(lblOperacao, gbc);
        JComboBox<String> cbOperacao = new JComboBox<>(Constantes.OPERACOES_BITWISE);
        cbOperacao.setBackground(TemaEscuro.BOTAO);
        cbOperacao.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 2;
        add(cbOperacao, gbc);

        JButton btnCalcular = new JButton("Calcular");
        btnCalcular.setBackground(TemaEscuro.BOTAO);
        btnCalcular.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(btnCalcular, gbc);

        JLabel lblDecimal = new JLabel("Decimal: ");
        lblDecimal.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(lblDecimal, gbc);

        JLabel lblBinario = new JLabel("Binário: ");
        lblBinario.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(lblBinario, gbc);

        JLabel lblHexadecimal = new JLabel("Hexadecimal: ");
        lblHexadecimal.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        add(lblHexadecimal, gbc);

        btnCalcular.addActionListener(e -> {
            try {
                long a = Long.parseLong(txtA.getText().trim());
                long b = Long.parseLong(txtB.getText().trim());
                String operacao = (String) cbOperacao.getSelectedItem();
                long resultado = Bitwise.executar(operacao, a, b);
                lblDecimal.setText("Decimal: " + resultado);
                lblBinario.setText("Binário: " + Long.toBinaryString(resultado));
                lblHexadecimal.setText("Hexadecimal: " + Long.toHexString(resultado).toUpperCase());
            } catch (Exception ex) {
                lblDecimal.setText("Erro no valor!");
                lblBinario.setText("Binário: ");
                lblHexadecimal.setText("Hexadecimal: ");
            }
        });
    }
}
