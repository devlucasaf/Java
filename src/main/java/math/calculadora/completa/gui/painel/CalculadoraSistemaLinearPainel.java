package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.model.calculos.SistemaLinear3x3;
import math.calculadora.completa.model.calculos.resultados.ResultadoSolucao;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraSistemaLinearPainel extends JPanel {

    public CalculadoraSistemaLinearPainel() {
        super(new GridBagLayout());
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("Sistema 3x3: a1x + b1y + c1z = d1");
        lblTitulo.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        add(lblTitulo, gridBagConstraints);

        // Linhas de entrada
        JTextField[][] campos = new JTextField[3][4];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                campos[i][j] = new JTextField((i == j) ? "1" : "0", 5);
                campos[i][j].setBackground(TemaEscuro.CAMPO);
                campos[i][j].setForeground(TemaEscuro.TEXTO);
                gridBagConstraints.gridx = j;
                gridBagConstraints.gridy = i + 1;
                gridBagConstraints.gridwidth = 1;
                add(campos[i][j], gridBagConstraints);
            }
        }

        // Labels das variáveis
        JLabel lblVariaveis = new JLabel("a  b  c  d");
        lblVariaveis.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        add(lblVariaveis, gridBagConstraints);

        JButton btnResolver = new JButton("Resolver");
        btnResolver.setBackground(TemaEscuro.BOTAO);
        btnResolver.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        add(btnResolver, gridBagConstraints);

        JLabel lblResultado = new JLabel("Resultado: ");
        lblResultado.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 4;
        add(lblResultado, gridBagConstraints);

        btnResolver.addActionListener(e -> {
            try {
                double[][] coef = new double[3][3];
                double[] consts = new double[3];
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        coef[i][j] = Double.parseDouble(campos[i][j].getText().trim());
                    }
                    consts[i] = Double.parseDouble(campos[i][3].getText().trim());
                }
                ResultadoSolucao resultadoSolucao = SistemaLinear3x3.resolver(coef, consts);
                if (resultadoSolucao.unica) {
                    lblResultado.setText(String.format("x = %.4f, y = %.4f, z = %.4f", resultadoSolucao.x, resultadoSolucao.y, resultadoSolucao.z));
                } else {
                    lblResultado.setText("Sistema sem solução única.");
                }
            } catch (Exception ex) {
                lblResultado.setText("Erro: " + ex.getMessage());
            }
        });
    }
}
