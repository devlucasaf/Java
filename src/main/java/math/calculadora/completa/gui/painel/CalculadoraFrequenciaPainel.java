package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.model.conversoes.ConversorFrequencia;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraFrequenciaPainel extends JPanel {

    private static final String[] UNIDADES = {"Hz", "kHz", "MHz", "GHz", "RPM"};

    public CalculadoraFrequenciaPainel() {
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

        JTextField txtValor = new JTextField("1", 10);
        txtValor.setBackground(TemaEscuro.CAMPO);
        txtValor.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        add(txtValor, gridBagConstraints);

        JLabel lblDe = new JLabel("De:");
        lblDe.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        add(lblDe, gridBagConstraints);

        JComboBox<String> cbDe = new JComboBox<>(UNIDADES);
        cbDe.setBackground(TemaEscuro.BOTAO);
        cbDe.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        add(cbDe, gridBagConstraints);

        JLabel lblPara = new JLabel("Para:");
        lblPara.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        add(lblPara, gridBagConstraints);

        JComboBox<String> cbPara = new JComboBox<>(UNIDADES);
        cbPara.setSelectedItem("kHz");
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
                double valor = Double.parseDouble(txtValor.getText().trim());
                String de = (String) cbDe.getSelectedItem();
                String para = (String) cbPara.getSelectedItem();
                double resultado = ConversorFrequencia.converter(valor, de, para);
                lblResultado.setText(String.format("Resultado: %.6f %s", resultado, para));
            } catch (Exception ex) {
                lblResultado.setText("Erro: " + ex.getMessage());
            }
        });
    }
}
