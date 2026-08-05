package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.model.conversoes.ConversorUnidades;
import math.calculadora.completa.util.Constantes;
import math.calculadora.completa.util.Formatador;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraDadosPainel extends JPanel {
    public CalculadoraDadosPainel() {
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

        JTextField txtValor = new JTextField("1", 10);
        txtValor.setBackground(TemaEscuro.CAMPO);
        txtValor.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 0;
        add(txtValor, gbc);

        JLabel lblDe = new JLabel("De:");
        lblDe.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 1;
        add(lblDe, gbc);
        JComboBox<String> cbDe = new JComboBox<>(Constantes.UNIDADES_DADOS);
        cbDe.setSelectedItem("GB");
        cbDe.setBackground(TemaEscuro.BOTAO);
        cbDe.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 1;
        add(cbDe, gbc);

        JLabel lblPara = new JLabel("Para:");
        lblPara.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 2;
        add(lblPara, gbc);
        JComboBox<String> cbPara = new JComboBox<>(Constantes.UNIDADES_DADOS);
        cbPara.setSelectedItem("MB");
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
                double valor = Double.parseDouble(txtValor.getText());
                int idxDe = cbDe.getSelectedIndex();
                int idxPara = cbPara.getSelectedIndex();
                double resultado = ConversorUnidades.converter(valor, Constantes.FATORES_DADOS[idxDe], Constantes.FATORES_DADOS[idxPara]);
                lblResultado.setText("Resultado: " + Formatador.formatarNumero(resultado));
            } catch (Exception ex) {
                lblResultado.setText("Erro no valor!");
            }
        });
    }
}
