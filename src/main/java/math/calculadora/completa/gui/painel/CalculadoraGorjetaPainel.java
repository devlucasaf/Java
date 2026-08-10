package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.model.utilidades.Gorjeta;
import math.calculadora.completa.model.utilidades.resultados.ResultadoGorjeta;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraGorjetaPainel extends JPanel {
    public CalculadoraGorjetaPainel() {
        super(new GridBagLayout());
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10,10,10,10));
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5,5,5,5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblConta = new JLabel("Valor da conta:");
        lblConta.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=0; gridBagConstraints.gridy=0;
        add(lblConta, gridBagConstraints);

        JTextField txtConta = new JTextField("100", 10);
        txtConta.setBackground(TemaEscuro.CAMPO);
        txtConta.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=1;
        gridBagConstraints.gridy=0;
        add(txtConta, gridBagConstraints);

        JLabel lblPercentual = new JLabel("Percentual de gorjeta (%):");
        lblPercentual.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=1;
        add(lblPercentual, gridBagConstraints);

        JTextField txtPercentual = new JTextField("10", 10);
        txtPercentual.setBackground(TemaEscuro.CAMPO);
        txtPercentual.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=1;
        gridBagConstraints.gridy=1;
        add(txtPercentual, gridBagConstraints);

        JLabel lblPessoas = new JLabel("Número de pessoas:");
        lblPessoas.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=2;
        add(lblPessoas, gridBagConstraints);

        JTextField txtPessoas = new JTextField("2", 10);
        txtPessoas.setBackground(TemaEscuro.CAMPO);
        txtPessoas.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=1;
        gridBagConstraints.gridy=2;
        add(txtPessoas, gridBagConstraints);

        JButton btnCalcular = new JButton("Calcular");
        btnCalcular.setBackground(TemaEscuro.BOTAO);
        btnCalcular.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=3;
        gridBagConstraints.gridwidth=2;
        add(btnCalcular, gridBagConstraints);

        JTextArea areaResultado = new JTextArea(5,20);
        areaResultado.setBackground(TemaEscuro.CAMPO);
        areaResultado.setForeground(TemaEscuro.TEXTO);
        areaResultado.setEditable(false);

        JScrollPane scroll = new JScrollPane(areaResultado);
        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=4;
        gridBagConstraints.gridwidth=2;
        add(scroll, gridBagConstraints);

        btnCalcular.addActionListener(e -> {
            try {
                double conta = Double.parseDouble(txtConta.getText().trim());
                double perc = Double.parseDouble(txtPercentual.getText().trim());
                int pessoas = Integer.parseInt(txtPessoas.getText().trim());
                ResultadoGorjeta resultadoGorjeta = Gorjeta.calcular(conta, perc, pessoas);
                areaResultado.setText(String.format(
                        "Valor da conta: R$ %.2f\nGorjeta: R$ %.2f\nTotal a pagar: R$ %.2f\nPor pessoa: R$ %.2f",
                        resultadoGorjeta.totalConta, resultadoGorjeta.valorGorjeta, resultadoGorjeta.totalPagar, resultadoGorjeta.porPessoa
                ));
            } catch (Exception ex) {
                areaResultado.setText("Erro: " + ex.getMessage());
            }
        });
    }
}
