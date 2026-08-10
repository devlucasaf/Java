package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.model.financas.MargemLucro;
import math.calculadora.completa.model.financas.resultados.ResultadoMargem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraMargemLucroPainel extends JPanel {
    public CalculadoraMargemLucroPainel() {
        super(new GridBagLayout());
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10,10,10,10));
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5,5,5,5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblReceita = new JLabel("Receita total:");
        lblReceita.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=0;
        add(lblReceita, gridBagConstraints);

        JTextField txtReceita = new JTextField("1000", 10);
        txtReceita.setBackground(TemaEscuro.CAMPO);
        txtReceita.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=1;
        gridBagConstraints.gridy=0;
        add(txtReceita, gridBagConstraints);

        JLabel lblCusto = new JLabel("Custo total:");
        lblCusto.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=1;
        add(lblCusto, gridBagConstraints);

        JTextField txtCusto = new JTextField("600", 10);
        txtCusto.setBackground(TemaEscuro.CAMPO);
        txtCusto.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=1;
        gridBagConstraints.gridy=1;
        add(txtCusto, gridBagConstraints);

        JLabel lblDespesas = new JLabel("Despesas operacionais:");
        lblDespesas.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=2;
        add(lblDespesas, gridBagConstraints);

        JTextField txtDespesas = new JTextField("100", 10);
        txtDespesas.setBackground(TemaEscuro.CAMPO);
        txtDespesas.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=1;
        gridBagConstraints.gridy=2;
        add(txtDespesas, gridBagConstraints);

        JButton btnCalcular = new JButton("Calcular Margem");
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
                double receita = Double.parseDouble(txtReceita.getText().trim());
                double custo = Double.parseDouble(txtCusto.getText().trim());
                double despesas = Double.parseDouble(txtDespesas.getText().trim());
                ResultadoMargem resultadoMargem = MargemLucro.calcular(receita, custo, despesas);
                areaResultado.setText(String.format(
                        "Margem Bruta: %.2f%%\nMargem Líquida: %.2f%%\nMarkup: %.2f%%",
                        resultadoMargem.margemBruta, resultadoMargem.margemLiquida, resultadoMargem.markup * 100
                ));
            } catch (Exception ex) {
                areaResultado.setText("Erro: " + ex.getMessage());
            }
        });
    }
}
