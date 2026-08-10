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
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblAValorDecimal = new JLabel("A (decimal):");
        lblAValorDecimal.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0; gridBagConstraints.gridy = 0;
        add(lblAValorDecimal, gridBagConstraints);
        JTextField txtA = new JTextField("12", 10);
        txtA.setBackground(TemaEscuro.CAMPO);
        txtA.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1; gridBagConstraints.gridy = 0;
        add(txtA, gridBagConstraints);

        JLabel lblBValorDecimal = new JLabel("B (decimal):");
        lblBValorDecimal.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        add(lblBValorDecimal, gridBagConstraints);

        JTextField txtB = new JTextField("10", 10);
        txtB.setBackground(TemaEscuro.CAMPO);
        txtB.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        add(txtB, gridBagConstraints);

        JLabel lblOperacao = new JLabel("Operação:");
        lblOperacao.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        add(lblOperacao, gridBagConstraints);

        JComboBox<String> cbOperacao = new JComboBox<>(Constantes.OPERACOES_BITWISE);
        cbOperacao.setBackground(TemaEscuro.BOTAO);
        cbOperacao.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        add(cbOperacao, gridBagConstraints);

        JButton btnCalcular = new JButton("Calcular");
        btnCalcular.setBackground(TemaEscuro.BOTAO);
        btnCalcular.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        add(btnCalcular, gridBagConstraints);

        JLabel lblDecimal = new JLabel("Decimal: ");
        lblDecimal.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        add(lblDecimal, gridBagConstraints);

        JLabel lblBinario = new JLabel("Binário: ");
        lblBinario.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        add(lblBinario, gridBagConstraints);

        JLabel lblHexadecimal = new JLabel("Hexadecimal: ");
        lblHexadecimal.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        add(lblHexadecimal, gridBagConstraints);

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
