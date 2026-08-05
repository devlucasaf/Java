package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.model.financas.Juros;
import math.calculadora.completa.model.financas.ResultadoJuros;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraJurosPainel extends JPanel {
    public CalculadoraJurosPainel() {
        super(new GridBagLayout());
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblCapital = new JLabel("Capital inicial:");
        lblCapital.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 0;
        add(lblCapital, gbc);

        JTextField txtCapital = new JTextField("1000", 10);
        txtCapital.setBackground(TemaEscuro.CAMPO);
        txtCapital.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 0;
        add(txtCapital, gbc);

        JLabel lblTaxa = new JLabel("Taxa (% por período):");
        lblTaxa.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 1;
        add(lblTaxa, gbc);

        JTextField txtTaxa = new JTextField("2", 10);
        txtTaxa.setBackground(TemaEscuro.CAMPO);
        txtTaxa.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 1;
        add(txtTaxa, gbc);

        JLabel lblTempo = new JLabel("Número de períodos:");
        lblTempo.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 2;
        add(lblTempo, gbc);

        JTextField txtTempo = new JTextField("12", 10);
        txtTempo.setBackground(TemaEscuro.CAMPO);
        txtTempo.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 2;
        add(txtTempo, gbc);

        JLabel lblTipo = new JLabel("Tipo de juros:");
        lblTipo.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 3;
        add(lblTipo, gbc);

        JComboBox<String> cbTipo = new JComboBox<>(new String[]{"Simples", "Composto"});
        cbTipo.setSelectedItem("Composto");
        cbTipo.setBackground(TemaEscuro.BOTAO);
        cbTipo.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 3;
        add(cbTipo, gbc);

        JButton btnCalcular = new JButton("Calcular");
        btnCalcular.setBackground(TemaEscuro.BOTAO);
        btnCalcular.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(btnCalcular, gbc);

        JLabel lblMontante = new JLabel("Montante: ");
        lblMontante.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(lblMontante, gbc);

        JLabel lblJuros = new JLabel("Juros: ");
        lblJuros.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        add(lblJuros, gbc);

        btnCalcular.addActionListener(e -> {
            try {
                double capital = Double.parseDouble(txtCapital.getText());
                double taxa = Double.parseDouble(txtTaxa.getText());
                double tempo = Double.parseDouble(txtTempo.getText());
                String tipo = (String) cbTipo.getSelectedItem();
                ResultadoJuros resultado = Juros.calcular(capital, taxa, tempo, tipo);
                lblMontante.setText(String.format("Montante: %.2f", resultado.montante));
                lblJuros.setText(String.format("Juros: %.2f", resultado.juros));
            } catch (Exception ex) {
                lblMontante.setText("Erro no valor!");
                lblJuros.setText("Juros: ");
            }
        });
    }
}