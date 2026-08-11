package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.model.calculos.Equacoes;
import math.calculadora.completa.model.calculos.Bhaskara;
import math.calculadora.completa.model.calculos.Raizes;
import math.calculadora.completa.model.calculos.resultados.ResultadoSolucaoSistema;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraEquacoesPainel extends JPanel {
    public CalculadoraEquacoesPainel() {
        super(new BorderLayout(10,10));
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10,10,10,10));

        JTabbedPane abas = new JTabbedPane();
        abas.setBackground(TemaEscuro.FUNDO);
        abas.setForeground(TemaEscuro.TEXTO);

        abas.addTab("1º Grau", criarPainelPrimeiroGrau());
        abas.addTab("2º Grau", criarPainelSegundoGrau());
        abas.addTab("Sistema 2x2", criarPainelSistema());

        add(abas, BorderLayout.CENTER);
    }

    private JPanel criarPainelPrimeiroGrau() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(TemaEscuro.FUNDO);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5,5,5,5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblA = new JLabel("a:");
        lblA.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=0;
        add(lblA, gridBagConstraints);

        JTextField txtA = new JTextField("2", 8);
        txtA.setBackground(TemaEscuro.CAMPO);
        txtA.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=1;
        gridBagConstraints.gridy=0;
        add(txtA, gridBagConstraints);

        JLabel lblB = new JLabel("b:");
        lblB.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=1;
        add(lblB, gridBagConstraints);

        JTextField txtB = new JTextField("3", 8);
        txtB.setBackground(TemaEscuro.CAMPO);
        txtB.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=1;
        gridBagConstraints.gridy=1;
        add(txtB, gridBagConstraints);

        JButton btnCalcularX = new JButton("Calcular x");
        btnCalcularX.setBackground(TemaEscuro.BOTAO);
        btnCalcularX.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=2;
        gridBagConstraints.gridwidth=2;
        panel.add(btnCalcularX, gridBagConstraints);

        JLabel lblResultado = new JLabel("x = ");
        lblResultado.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=3;
        gridBagConstraints.gridwidth=2;
        panel.add(lblResultado, gridBagConstraints);

        btnCalcularX.addActionListener(e -> {
            try {
                double a = Double.parseDouble(txtA.getText().trim());
                double b = Double.parseDouble(txtB.getText().trim());
                double x = Equacoes.resolverPrimeiroGrau(a, b);
                lblResultado.setText("x = " + String.format("%.4f", x));
            } catch (Exception ex) {
                lblResultado.setText("Erro: " + ex.getMessage());
            }
        });
        return panel;
    }

    private JPanel criarPainelSegundoGrau() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(TemaEscuro.FUNDO);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5,5,5,5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblA = new JLabel("a:");
        lblA.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=0;
        panel.add(lblA, gridBagConstraints);

        JTextField txtA = new JTextField("1", 8);
        txtA.setBackground(TemaEscuro.CAMPO);
        txtA.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=1;
        gridBagConstraints.gridy=0;
        panel.add(txtA, gridBagConstraints);

        JLabel lblB = new JLabel("b:");
        lblB.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=1;
        panel.add(lblB, gridBagConstraints);

        JTextField txtB = new JTextField("-3", 8);
        txtB.setBackground(TemaEscuro.CAMPO);
        txtB.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=1;
        gridBagConstraints.gridy=1;
        panel.add(txtB, gridBagConstraints);

        JLabel lblC = new JLabel("c:");
        lblC.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=2;
        panel.add(lblC, gridBagConstraints);

        JTextField txtC = new JTextField("2", 8);
        txtC.setBackground(TemaEscuro.CAMPO);
        txtC.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=1;
        gridBagConstraints.gridy=2;
        panel.add(txtC, gridBagConstraints);

        JButton btnCalcular = new JButton("Calcular");
        btnCalcular.setBackground(TemaEscuro.BOTAO);
        btnCalcular.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=3;
        gridBagConstraints.gridwidth=2;
        panel.add(btnCalcular, gridBagConstraints);

        JLabel lblResultado = new JLabel("Resultado: ");
        lblResultado.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=0; gridBagConstraints.gridy=4; gridBagConstraints.gridwidth=2;
        panel.add(lblResultado, gridBagConstraints);

        btnCalcular.addActionListener(e -> {
            try {
                double a = Double.parseDouble(txtA.getText().trim());
                double b = Double.parseDouble(txtB.getText().trim());
                double c = Double.parseDouble(txtC.getText().trim());
                Raizes r = Bhaskara.calcular(a, b, c);
                if (r.complexas) {
                    lblResultado.setText("Raízes complexas.");
                } else if (r.x1 == r.x2) {
                    lblResultado.setText(String.format("x = %.4f (dupla)", r.x1));
                } else {
                    lblResultado.setText(String.format("x1 = %.4f, x2 = %.4f", r.x1, r.x2));
                }
            } catch (Exception ex) {
                lblResultado.setText("Erro: " + ex.getMessage());
            }
        });
        return panel;
    }

    private JPanel criarPainelSistema() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(TemaEscuro.FUNDO);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5,5,5,5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblA1 = new JLabel("a1:");
        lblA1.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=0;
        panel.add(lblA1, gridBagConstraints);

        JTextField txtA1 = new JTextField("2", 5);
        txtA1.setBackground(TemaEscuro.CAMPO);
        txtA1.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=1;
        gridBagConstraints.gridy=0;
        panel.add(txtA1, gridBagConstraints);

        JLabel lblB1 = new JLabel("b1:");
        lblB1.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=2;
        gridBagConstraints.gridy=0;
        panel.add(lblB1, gridBagConstraints);

        JTextField txtB1 = new JTextField("1", 5);
        txtB1.setBackground(TemaEscuro.CAMPO);
        txtB1.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=3;
        gridBagConstraints.gridy=0;
        panel.add(txtB1, gridBagConstraints);

        JLabel lblC1 = new JLabel("c1:");
        lblC1.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=4;
        gridBagConstraints.gridy=0;
        panel.add(lblC1, gridBagConstraints);

        JTextField txtC1 = new JTextField("5", 5);
        txtC1.setBackground(TemaEscuro.CAMPO);
        txtC1.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=5;
        gridBagConstraints.gridy=0;
        panel.add(txtC1, gridBagConstraints);

        JLabel lblA2 = new JLabel("a2:");
        lblA2.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=1;
        panel.add(lblA2, gridBagConstraints);

        JTextField txtA2 = new JTextField("1", 5);
        txtA2.setBackground(TemaEscuro.CAMPO);
        txtA2.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=1;
        gridBagConstraints.gridy=1;
        panel.add(txtA2, gridBagConstraints);

        JLabel lblB2 = new JLabel("b2:");
        lblB2.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=2;
        gridBagConstraints.gridy=1;
        panel.add(lblB2, gridBagConstraints);

        JTextField txtB2 = new JTextField("3", 5);
        txtB2.setBackground(TemaEscuro.CAMPO);
        txtB2.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=3;
        gridBagConstraints.gridy=1;
        panel.add(txtB2, gridBagConstraints);

        JLabel lblC2 = new JLabel("c2:");
        lblC2.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=4;
        gridBagConstraints.gridy=1;
        panel.add(lblC2, gridBagConstraints);

        JTextField txtC2 = new JTextField("6", 5);
        txtC2.setBackground(TemaEscuro.CAMPO);
        txtC2.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=5;
        gridBagConstraints.gridy=1;
        panel.add(txtC2, gridBagConstraints);

        JButton btn = new JButton("Resolver");
        btn.setBackground(TemaEscuro.BOTAO);
        btn.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=2;
        gridBagConstraints.gridwidth=6;
        panel.add(btn, gridBagConstraints);

        JLabel lblRes = new JLabel("Solução: ");
        lblRes.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=3;
        gridBagConstraints.gridwidth=6;
        panel.add(lblRes, gridBagConstraints);

        btn.addActionListener(e -> {
            try {
                double a1 = Double.parseDouble(txtA1.getText().trim());
                double b1 = Double.parseDouble(txtB1.getText().trim());
                double c1 = Double.parseDouble(txtC1.getText().trim());
                double a2 = Double.parseDouble(txtA2.getText().trim());
                double b2 = Double.parseDouble(txtB2.getText().trim());
                double c2 = Double.parseDouble(txtC2.getText().trim());

                ResultadoSolucaoSistema solucaoSistema = Equacoes.resolverSistema2x2(a1,b1,c1,a2,b2,c2);
                lblRes.setText(String.format("x = %.4f, y = %.4f", solucaoSistema.x, solucaoSistema.y));
            } catch (Exception ex) {
                lblRes.setText("Erro: " + ex.getMessage());
            }
        });
        return panel;
    }
}
