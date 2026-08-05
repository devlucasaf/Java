package math.calculadora.completa.painel;

import math.calculadora.completa.tema.TemaEscuro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraPainelBases extends JPanel {

    private static final String[] FORMATOS = {"Hexadecimal", "Decimal", "Octal", "Binário"};
    private static final int[] BASES = {16, 10, 8, 2};

    public CalculadoraPainelBases() {
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

        JTextField txtValor = new JTextField("10", 15);
        txtValor.setBackground(TemaEscuro.CAMPO);
        txtValor.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1; gridBagConstraints.gridy = 0;
        add(txtValor, gridBagConstraints);

        JLabel lblDe = new JLabel("De:");
        lblDe.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0; gridBagConstraints.gridy = 1;
        add(lblDe, gridBagConstraints);

        JComboBox<String> cbDe = new JComboBox<>(FORMATOS);
        cbDe.setSelectedItem("Decimal");
        cbDe.setBackground(TemaEscuro.BOTAO);
        cbDe.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1; gridBagConstraints.gridy = 1;
        add(cbDe, gridBagConstraints);

        JLabel lblPara = new JLabel("Para:");
        lblPara.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0; gridBagConstraints.gridy = 2;
        add(lblPara, gridBagConstraints);

        JComboBox<String> cbPara = new JComboBox<>(FORMATOS);
        cbPara.setSelectedItem("Binário");
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
                String texto = txtValor.getText().trim();
                int baseDe = BASES[cbDe.getSelectedIndex()];
                int basePara = BASES[cbPara.getSelectedIndex()];

                if (!validoParaBase(texto, baseDe)) {
                    lblResultado.setText("Erro: valor inválido para " + cbDe.getSelectedItem());
                    return;
                }

                long decimal = Long.parseLong(texto, baseDe);
                if (decimal < 0) {
                    lblResultado.setText("Erro: use apenas inteiros positivos");
                    return;
                }

                String resultado = Long.toString(decimal, basePara).toUpperCase();
                lblResultado.setText("Resultado: " + resultado);
            } catch (NumberFormatException ex) {
                lblResultado.setText("Erro no valor!");
            }
        });
    }

    private boolean validoParaBase(String texto, int base) {
        if (texto.isEmpty()) {
            return false;
        }

        for (char c : texto.toUpperCase().toCharArray()) {
            if (Character.digit(c, base) < 0) {
                return false;
            }
        }
        return true;
    }
}
