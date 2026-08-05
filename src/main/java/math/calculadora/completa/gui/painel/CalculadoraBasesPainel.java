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
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblValor = new JLabel("Valor:");
        lblValor.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 0;
        add(lblValor, gbc);

        JTextField txtValor = new JTextField("10", 15);
        txtValor.setBackground(TemaEscuro.CAMPO);
        txtValor.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 0;
        add(txtValor, gbc);

        JLabel lblDe = new JLabel("De:");
        lblDe.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 1;
        add(lblDe, gbc);
        JComboBox<String> cbDe = new JComboBox<>(Constantes.BASES_FORMATOS);
        cbDe.setSelectedItem("Decimal");
        cbDe.setBackground(TemaEscuro.BOTAO);
        cbDe.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 1;
        add(cbDe, gbc);

        JLabel lblPara = new JLabel("Para:");
        lblPara.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 2;
        add(lblPara, gbc);
        JComboBox<String> cbPara = new JComboBox<>(Constantes.BASES_FORMATOS);
        cbPara.setSelectedItem("Binário");
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
                String texto = txtValor.getText().trim();
                int baseDe = Constantes.BASES_VALORES[cbDe.getSelectedIndex()];
                int basePara = Constantes.BASES_VALORES[cbPara.getSelectedIndex()];

                if (!Validador.isValidoParaBase(texto, baseDe)) {
                    lblResultado.setText("Erro: valor inválido para " + cbDe.getSelectedItem());
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
