package math.calculadora.completa.painel;

import math.calculadora.completa.tema.TemaEscuro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraPainelJuros extends JPanel {

    public CalculadoraPainelJuros() {
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

        JLabel lblResultado = new JLabel("Montante: ");
        lblResultado.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(lblResultado, gbc);

        JLabel lblJuros = new JLabel("Juros: ");
        lblJuros.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        add(lblJuros, gbc);

        btnCalcular.addActionListener(e -> {
            try {
                double capital = Double.parseDouble(txtCapital.getText());
                double taxa = Double.parseDouble(txtTaxa.getText()) / 100.0;
                double tempo = Double.parseDouble(txtTempo.getText());
                String tipo = (String) cbTipo.getSelectedItem();

                double montante;
                if (tipo.equals("Simples")) {
                    montante = capital * (1 + taxa * tempo);
                } else {
                    montante = capital * Math.pow(1 + taxa, tempo);
                }
                double juros = montante - capital;

                lblResultado.setText(String.format("Montante: %.2f", montante));
                lblJuros.setText(String.format("Juros: %.2f", juros));
            } catch (Exception ex) {
                lblResultado.setText("Erro no valor!");
                lblJuros.setText("Juros: ");
            }
        });
    }
}