package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.model.financas.Juros;
import math.calculadora.completa.model.financas.resultados.ResultadoJuros;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraJurosPainel extends JPanel {
    public CalculadoraJurosPainel() {
        super(new GridBagLayout());
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblCapital = new JLabel("Capital inicial:");
        lblCapital.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(lblCapital, gridBagConstraints);

        JTextField txtCapital = new JTextField("1000", 10);
        txtCapital.setBackground(TemaEscuro.CAMPO);
        txtCapital.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        add(txtCapital, gridBagConstraints);

        JLabel lblTaxa = new JLabel("Taxa (% por período):");
        lblTaxa.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        add(lblTaxa, gridBagConstraints);

        JTextField txtTaxa = new JTextField("2", 10);
        txtTaxa.setBackground(TemaEscuro.CAMPO);
        txtTaxa.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        add(txtTaxa, gridBagConstraints);

        JLabel lblNumeroPeriodos = new JLabel("Número de períodos:");
        lblNumeroPeriodos.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        add(lblNumeroPeriodos, gridBagConstraints);

        JTextField txtNumeroPeriodos = new JTextField("12", 10);
        txtNumeroPeriodos.setBackground(TemaEscuro.CAMPO);
        txtNumeroPeriodos.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        add(txtNumeroPeriodos, gridBagConstraints);

        JLabel lblTipo = new JLabel("Tipo de juros:");
        lblTipo.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        add(lblTipo, gridBagConstraints);

        JComboBox<String> cbTipo = new JComboBox<>(new String[]{"Simples", "Composto"});
        cbTipo.setSelectedItem("Composto");
        cbTipo.setBackground(TemaEscuro.BOTAO);
        cbTipo.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        add(cbTipo, gridBagConstraints);

        JButton btnCalcular = new JButton("Calcular");
        btnCalcular.setBackground(TemaEscuro.BOTAO);
        btnCalcular.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        add(btnCalcular, gridBagConstraints);

        JLabel lblMontante = new JLabel("Montante: ");
        lblMontante.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        add(lblMontante, gridBagConstraints);

        JLabel lblJuros = new JLabel("Juros: ");
        lblJuros.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        add(lblJuros, gridBagConstraints);

        btnCalcular.addActionListener(e -> {
            try {
                double capital = Double.parseDouble(txtCapital.getText());
                double taxa = Double.parseDouble(txtTaxa.getText());
                double tempo = Double.parseDouble(txtNumeroPeriodos.getText());
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
