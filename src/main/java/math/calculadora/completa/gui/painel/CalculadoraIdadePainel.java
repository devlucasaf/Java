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
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblData = new JLabel("Data de Nascimento (dd/MM/yyyy):");
        lblData.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(lblData, gridBagConstraints);

        JTextField txtData = new JTextField("01/01/2000", 10);
        txtData.setBackground(TemaEscuro.CAMPO);
        txtData.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        add(txtData, gridBagConstraints);

        JButton btnCalcular = new JButton("Calcular Idade");
        btnCalcular.setBackground(TemaEscuro.BOTAO);
        btnCalcular.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        add(btnCalcular, gridBagConstraints);

        JLabel lblResultado = new JLabel("Idade: ");
        lblResultado.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        add(lblResultado, gridBagConstraints);

        btnCalcular.addActionListener(e -> {
            try {
                Period periodo = CalculadoraIdade.calcularIdade(txtData.getText());
                lblResultado.setText(String.format("Idade: %d anos, %d meses, %d dias",
                        periodo.getYears(), periodo.getMonths(), periodo.getDays()));
            } catch (Exception ex) {
                lblResultado.setText("Formato inválido! Use dd/MM/yyyy");
            }
        });
    }
}
