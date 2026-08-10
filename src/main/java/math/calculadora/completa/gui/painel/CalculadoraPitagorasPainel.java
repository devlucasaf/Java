package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.model.calculos.Pitagoras;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraPitagorasPainel extends JPanel {

    public CalculadoraPitagorasPainel() {
        super(new GridBagLayout());
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblCalcularOpcao = new JLabel("Calcular:");
        lblCalcularOpcao.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0; gridBagConstraints.gridy = 0;
        add(lblCalcularOpcao, gridBagConstraints);

        String[] opcoes = {"Hipotenusa (a²+b²=c²)", "Cateto (c²-a²=b²)"};
        JComboBox<String> cbOpcao = new JComboBox<>(opcoes);
        cbOpcao.setBackground(TemaEscuro.BOTAO);
        cbOpcao.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1; gridBagConstraints.gridy = 0;
        add(cbOpcao, gridBagConstraints);

        // Campo 1
        JLabel lblValor1 = new JLabel("Valor 1:");
        lblValor1.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0; gridBagConstraints.gridy = 1;
        add(lblValor1, gridBagConstraints);

        JTextField txtValor1 = new JTextField("3", 10);
        txtValor1.setBackground(TemaEscuro.CAMPO);
        txtValor1.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1; gridBagConstraints.gridy = 1;
        add(txtValor1, gridBagConstraints);

        // Campo 2
        JLabel lblValor2 = new JLabel("Valor 2:");
        lblValor2.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0; gridBagConstraints.gridy = 2;
        add(lblValor2, gridBagConstraints);

        JTextField txtValor2 = new JTextField("4", 10);
        txtValor2.setBackground(TemaEscuro.CAMPO);
        txtValor2.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1; gridBagConstraints.gridy = 2;
        add(txtValor2, gridBagConstraints);

        // Botão
        JButton btnCalcular = new JButton("Calcular");
        btnCalcular.setBackground(TemaEscuro.BOTAO);
        btnCalcular.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0; gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        add(btnCalcular, gridBagConstraints);

        // Resultado
        JLabel lblResultado = new JLabel("Resultado: ");
        lblResultado.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0; gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        add(lblResultado, gridBagConstraints);

        // Ação
        btnCalcular.addActionListener(e -> {
            try {
                double valor1 = Double.parseDouble(txtValor1.getText().trim());
                double valor2 = Double.parseDouble(txtValor2.getText().trim());
                boolean calcularHipotenusa = cbOpcao.getSelectedIndex() == 0;
                double resultado;
                if (calcularHipotenusa) {
                    resultado = Pitagoras.calcularHipotenusa(valor1, valor2);
                    lblResultado.setText(String.format("Hipotenusa = %.4f", resultado));
                } else {
                    resultado = Pitagoras.calcularCateto(valor1, valor2);
                    lblResultado.setText(String.format("Cateto = %.4f", resultado));
                }
            } catch (Exception ex) {
                lblResultado.setText("Erro: " + ex.getMessage());
            }
        });
    }
}
