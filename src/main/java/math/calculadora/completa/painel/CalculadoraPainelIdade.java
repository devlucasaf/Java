package math.calculadora.completa.painel;

import math.calculadora.completa.tema.TemaEscuro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CalculadoraPainelIdade extends JPanel {

    public CalculadoraPainelIdade() {
        super(new GridBagLayout());
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblData = new JLabel("Data de Nascimento (dd/MM/yyyy):");
        lblData.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 0;
        add(lblData, gbc);

        JTextField txtData = new JTextField("01/01/2000", 10);
        txtData.setBackground(TemaEscuro.CAMPO);
        txtData.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 0;
        add(txtData, gbc);

        JButton btnCalcular = new JButton("Calcular Idade");
        btnCalcular.setBackground(TemaEscuro.BOTAO);
        btnCalcular.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(btnCalcular, gbc);

        JLabel lblResultado = new JLabel("Idade: ");
        lblResultado.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(lblResultado, gbc);

        btnCalcular.addActionListener(e -> {
            try {
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate nascimento = LocalDate.parse(txtData.getText(), fmt);
                LocalDate hoje = LocalDate.now();
                Period periodo = Period.between(nascimento, hoje);
                lblResultado.setText(String.format("Idade: %d anos, %d meses, %d dias",
                        periodo.getYears(), periodo.getMonths(), periodo.getDays()));
            } catch (DateTimeParseException ex) {
                lblResultado.setText("Formato inválido! Use dd/MM/yyyy");
            }
        });
    }
}
