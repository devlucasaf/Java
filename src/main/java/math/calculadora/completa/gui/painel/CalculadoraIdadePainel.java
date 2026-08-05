package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.model.datas.CalculadoraIdade;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.Period;

public class CalculadoraIdadePainel extends JPanel {
    public CalculadoraIdadePainel() {
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
                Period p = CalculadoraIdade.calcularIdade(txtData.getText());
                lblResultado.setText(String.format("Idade: %d anos, %d meses, %d dias",
                        p.getYears(), p.getMonths(), p.getDays()));
            } catch (Exception ex) {
                lblResultado.setText("Formato inválido! Use dd/MM/yyyy");
            }
        });
    }
}
