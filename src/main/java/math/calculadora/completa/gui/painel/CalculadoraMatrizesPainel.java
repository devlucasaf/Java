package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.model.calculos.Matrizes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraMatrizesPainel extends JPanel {

    public CalculadoraMatrizesPainel() {
        super(new BorderLayout(10, 10));
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JTabbedPane abas = new JTabbedPane();
        abas.setBackground(TemaEscuro.FUNDO);
        abas.setForeground(TemaEscuro.TEXTO);
        abas.addTab("Soma/Subtração", criarPainelSomaSubtracao());
        abas.addTab("Multiplicação", criarPainelMultiplicacao());
        abas.addTab("Determinante", criarPainelDeterminante());
        add(abas, BorderLayout.CENTER);
    }

    private JPanel criarPainelSomaSubtracao() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(TemaEscuro.FUNDO);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JTextField[][] camposA = new JTextField[2][2];
        JTextField[][] camposB = new JTextField[2][2];
        JLabel lblResultado = new JLabel("Resultado:");

        JLabel lblMatrizA = new JLabel("Matriz A (2x2)");
        lblMatrizA.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0; gridBagConstraints.gridy = 0; gridBagConstraints.gridwidth = 2;
        panel.add(lblMatrizA, gridBagConstraints);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                camposA[i][j] = new JTextField("0", 5);
                camposA[i][j].setBackground(TemaEscuro.CAMPO);
                camposA[i][j].setForeground(TemaEscuro.TEXTO);
                gridBagConstraints.gridx = j + 1; gridBagConstraints.gridy = i + 1; gridBagConstraints.gridwidth = 1;
                panel.add(camposA[i][j], gridBagConstraints);
            }
        }

        JLabel lblMatrizB = new JLabel("Matriz B (2x2)");
        lblMatrizB.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        panel.add(lblMatrizB, gridBagConstraints);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                camposB[i][j] = new JTextField("0", 5);
                camposB[i][j].setBackground(TemaEscuro.CAMPO);
                camposB[i][j].setForeground(TemaEscuro.TEXTO);
                gridBagConstraints.gridx = j + 5;
                gridBagConstraints.gridy = i + 1;
                gridBagConstraints.gridwidth = 1;
                panel.add(camposB[i][j], gridBagConstraints);
            }
        }

        JComboBox<String> cbOperacao = new JComboBox<>(new String[]{"Somar", "Subtrair"});
        cbOperacao.setBackground(TemaEscuro.BOTAO);
        cbOperacao.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 6;
        panel.add(cbOperacao, gridBagConstraints);

        JButton btnCalcular = new JButton("Calcular");
        btnCalcular.setBackground(TemaEscuro.BOTAO);
        btnCalcular.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 6;
        panel.add(btnCalcular, gridBagConstraints);

        lblResultado.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 6;
        panel.add(lblResultado, gridBagConstraints);

        btnCalcular.addActionListener(e -> {
            try {
                double[][] a = lerMatriz(camposA);
                double[][] b = lerMatriz(camposB);
                double[][] r = cbOperacao.getSelectedItem().equals("Somar")
                        ? Matrizes.somar(a, b)
                        : Matrizes.subtrair(a, b);
                lblResultado.setText("Resultado:\n" + matrizToString(r));
            } catch (Exception ex) {
                lblResultado.setText("Erro: " + ex.getMessage());
            }
        });
        return panel;
    }

    private JPanel criarPainelMultiplicacao() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(TemaEscuro.FUNDO);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JTextField[][] camposA = new JTextField[2][2];
        JTextField[][] camposB = new JTextField[2][2];
        JLabel lblResultado = new JLabel("Resultado:");

        JLabel lblMatrizA = new JLabel("Matriz A (2x2)");
        lblMatrizA.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        panel.add(lblMatrizA, gridBagConstraints);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                camposA[i][j] = new JTextField("0", 5);
                camposA[i][j].setBackground(TemaEscuro.CAMPO);
                camposA[i][j].setForeground(TemaEscuro.TEXTO);
                gridBagConstraints.gridx = j + 1;
                gridBagConstraints.gridy = i + 1;
                gridBagConstraints.gridwidth = 1;
                panel.add(camposA[i][j], gridBagConstraints);
            }
        }

        JLabel lblMatrizB = new JLabel("Matriz B (2x2)");
        lblMatrizB.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        panel.add(lblMatrizB, gridBagConstraints);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                camposB[i][j] = new JTextField("0", 5);
                camposB[i][j].setBackground(TemaEscuro.CAMPO);
                camposB[i][j].setForeground(TemaEscuro.TEXTO);
                gridBagConstraints.gridx = j + 5;
                gridBagConstraints.gridy = i + 1;
                gridBagConstraints.gridwidth = 1;
                panel.add(camposB[i][j], gridBagConstraints);
            }
        }

        JButton btnMultiplicar = new JButton("Multiplicar");
        btnMultiplicar.setBackground(TemaEscuro.BOTAO);
        btnMultiplicar.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 6;
        panel.add(btnMultiplicar, gridBagConstraints);

        lblResultado.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 6;
        panel.add(lblResultado, gridBagConstraints);

        btnMultiplicar.addActionListener(e -> {
            try {
                double[][] a = lerMatriz(camposA);
                double[][] b = lerMatriz(camposB);
                double[][] r = Matrizes.multiplicar(a, b);
                lblResultado.setText("Resultado:\n" + matrizToString(r));
            } catch (Exception ex) {
                lblResultado.setText("Erro: " + ex.getMessage());
            }
        });
        return panel;
    }

    private JPanel criarPainelDeterminante() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(TemaEscuro.FUNDO);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTamanhoMatriz = new JLabel("Tamanho da matriz:");
        lblTamanhoMatriz.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        panel.add(lblTamanhoMatriz, gridBagConstraints);

        JComboBox<Integer> cbTamanho = new JComboBox<>(new Integer[]{2, 3});
        cbTamanho.setBackground(TemaEscuro.BOTAO);
        cbTamanho.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        panel.add(cbTamanho, gridBagConstraints);

        JPanel painelCampos = new JPanel(new GridBagLayout());
        painelCampos.setBackground(TemaEscuro.FUNDO);

        JLabel lblResultadoDeterminante = new JLabel("Determinante: ");
        lblResultadoDeterminante.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        panel.add(painelCampos, gridBagConstraints);

        JButton btnCalcular = new JButton("Calcular");
        btnCalcular.setBackground(TemaEscuro.BOTAO);
        btnCalcular.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridy = 2;
        panel.add(btnCalcular, gridBagConstraints);
        gridBagConstraints.gridy = 3;
        panel.add(lblResultadoDeterminante, gridBagConstraints);

        cbTamanho.addActionListener(e -> atualizarCamposDeterminante(painelCampos, (int) cbTamanho.getSelectedItem(), lblResultadoDeterminante));

        btnCalcular.addActionListener(e -> {
            int numeroLinhas = (int) cbTamanho.getSelectedItem();
            double[][] matriz = new double[numeroLinhas][numeroLinhas];
            try {
                Component[] comps = painelCampos.getComponents();
                int idx = 0;
                for (int i = 0; i < numeroLinhas; i++) {
                    for (int j = 0; j < numeroLinhas; j++) {
                        JTextField tf = (JTextField) comps[idx++];
                        matriz[i][j] = Double.parseDouble(tf.getText().trim());
                    }
                }
                double det = Matrizes.determinante(matriz);
                lblResultadoDeterminante.setText("Determinante: " + String.format("%.6f", det));
            } catch (Exception ex) {
                lblResultadoDeterminante.setText("Erro: " + ex.getMessage());
            }
        });

        atualizarCamposDeterminante(painelCampos, 2, lblResultadoDeterminante);
        return panel;
    }

    private void atualizarCamposDeterminante(JPanel painel, int n, JLabel lblResultado) {
        painel.removeAll();
        painel.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                JTextField tf = new JTextField(i == j ? "1" : "0", 5);
                tf.setBackground(TemaEscuro.CAMPO);
                tf.setForeground(TemaEscuro.TEXTO);
                gridBagConstraints.gridx = j; gridBagConstraints.gridy = i;
                painel.add(tf, gridBagConstraints);
            }
        }
        painel.revalidate();
        painel.repaint();
        lblResultado.setText("Determinante: ");
    }

    private double[][] lerMatriz(JTextField[][] campos) {
        double[][] matriz = new double[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                matriz[i][j] = Double.parseDouble(campos[i][j].getText().trim());
            }
        }
        return matriz;
    }

    private String matrizToString(double[][] m) {
        StringBuilder sb = new StringBuilder();
        for (double[] linha : m) {
            for (double v : linha) {
                sb.append(String.format("%8.2f", v));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
