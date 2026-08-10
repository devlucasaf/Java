package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.model.calculos.NumerosPrimos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraPrimosPainel extends JPanel {
    private JTextField          txtNumero;
    private JTextArea           areaResultado;
    private JComboBox<String>   cbOperacao;

    public CalculadoraPrimosPainel() {
        super(new GridBagLayout());
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblNumero = new JLabel("Número (N):");
        lblNumero.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(lblNumero, gridBagConstraints);

        txtNumero = new JTextField("100", 10);
        txtNumero.setBackground(TemaEscuro.CAMPO);
        txtNumero.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        add(txtNumero, gridBagConstraints);

        JLabel lblOperacao = new JLabel("Operação:");
        lblOperacao.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        add(lblOperacao, gridBagConstraints);

        cbOperacao = new JComboBox<>(new String[]{"Verificar se é primo", "Listar primos até N", "Fatorar N"});
        cbOperacao.setBackground(TemaEscuro.BOTAO);
        cbOperacao.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        add(cbOperacao, gridBagConstraints);

        JButton btnExecutar = new JButton("Executar");
        btnExecutar.setBackground(TemaEscuro.BOTAO);
        btnExecutar.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        add(btnExecutar, gridBagConstraints);

        areaResultado = new JTextArea(10, 30);
        areaResultado.setBackground(TemaEscuro.CAMPO);
        areaResultado.setForeground(TemaEscuro.TEXTO);
        areaResultado.setEditable(false);

        JScrollPane scroll = new JScrollPane(areaResultado);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        add(scroll, gridBagConstraints);

        btnExecutar.addActionListener(e -> executar());
    }

    private void executar() {
        try {
            long n = Long.parseLong(txtNumero.getText().trim());
            if (n < 2) {
                areaResultado.setText("Número deve ser ≥ 2.");
                return;
            }

            int op = cbOperacao.getSelectedIndex();
            switch (op) {
                case 0:
                    areaResultado.setText(NumerosPrimos.isNumeroPrimo(n) ? "É primo." : "Não é primo.");
                    break;
                case 1:
                    areaResultado.setText("Primos até " + n + ":\n" + NumerosPrimos.listarPrimosAte(n).toString());
                    break;
                case 2:
                    areaResultado.setText(NumerosPrimos.fatorar(n));
                    break;
            }
        } catch (NumberFormatException ex) {
            areaResultado.setText("Valor inválido!");
        }
    }
}
