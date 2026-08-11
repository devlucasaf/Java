package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.model.saude.PesoIdeal;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraPesoIdealPainel extends JPanel {

    public CalculadoraPesoIdealPainel() {
        super(new GridBagLayout());
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblAltura = new JLabel("Altura (cm):");
        lblAltura.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(lblAltura, gridBagConstraints);

        JTextField txtAltura = new JTextField("175", 8);
        txtAltura.setBackground(TemaEscuro.CAMPO);
        txtAltura.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        add(txtAltura, gridBagConstraints);

        JLabel lblSexo = new JLabel("Sexo (M/F):");
        lblSexo.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        add(lblSexo, gridBagConstraints);

        JTextField txtSexo = new JTextField("M", 5);
        txtSexo.setBackground(TemaEscuro.CAMPO);
        txtSexo.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        add(txtSexo, gridBagConstraints);

        JButton btnCalcular = new JButton("Calcular Peso Ideal");
        btnCalcular.setBackground(TemaEscuro.BOTAO);
        btnCalcular.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        add(btnCalcular, gridBagConstraints);

        JLabel lblResultado = new JLabel("Peso Ideal: ");
        lblResultado.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        add(lblResultado, gridBagConstraints);

        btnCalcular.addActionListener(e -> {
            try {
                double altura = Double.parseDouble(txtAltura.getText().trim());
                char sexo = txtSexo.getText().trim().charAt(0);
                double peso = PesoIdeal.calcularPesoIdeal(altura, sexo);
                lblResultado.setText(String.format("Peso Ideal: %.2f kg", peso));
            } catch (Exception ex) {
                lblResultado.setText("Erro: " + ex.getMessage());
            }
        });
    }
}
