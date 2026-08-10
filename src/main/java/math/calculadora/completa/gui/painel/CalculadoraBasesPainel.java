package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.model.conversoes.ConversorBases;
import math.calculadora.completa.util.Validador;
import math.calculadora.completa.util.Constantes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraBasesPainel extends JPanel {
    public CalculadoraBasesPainel() {
        super(new GridBagLayout());
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblValor = new JLabel("Valor:");
        lblValor.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(lblValor, gridBagConstraints);

        JTextField txtValor = new JTextField("10", 15);
        txtValor.setBackground(TemaEscuro.CAMPO);
        txtValor.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        add(txtValor, gridBagConstraints);

        JLabel lblDecimal = new JLabel("De:");
        lblDecimal.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        add(lblDecimal, gridBagConstraints);

        JComboBox<String> cbDecimal = new JComboBox<>(Constantes.BASES_FORMATOS);
        cbDecimal.setSelectedItem("Decimal");
        cbDecimal.setBackground(TemaEscuro.BOTAO);
        cbDecimal.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        add(cbDecimal, gridBagConstraints);

        JLabel lblPara = new JLabel("Para:");
        lblPara.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        add(lblPara, gridBagConstraints);

        JComboBox<String> cbPara = new JComboBox<>(Constantes.BASES_FORMATOS);
        cbPara.setSelectedItem("Binário");
        cbPara.setBackground(TemaEscuro.BOTAO);
        cbPara.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        add(cbPara, gridBagConstraints);

        JButton btnConverter = new JButton("Converter");
        btnConverter.setBackground(TemaEscuro.BOTAO);
        btnConverter.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        add(btnConverter, gridBagConstraints);

        JLabel lblResultado = new JLabel("Resultado: ");
        lblResultado.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        add(lblResultado, gridBagConstraints);

        btnConverter.addActionListener(e -> {
            try {
                String texto = txtValor.getText().trim();
                int baseDe = Constantes.BASES_VALORES[cbDecimal.getSelectedIndex()];
                int basePara = Constantes.BASES_VALORES[cbPara.getSelectedIndex()];

                if (!Validador.isValidoParaBase(texto, baseDe)) {
                    lblResultado.setText("Erro: valor inválido para " + cbDecimal.getSelectedItem());
                    return;
                }
                String resultado = ConversorBases.converter(texto, baseDe, basePara);
                lblResultado.setText("Resultado: " + resultado);
            } catch (Exception ex) {
                lblResultado.setText("Erro no valor!");
            }
        });
    }
}
