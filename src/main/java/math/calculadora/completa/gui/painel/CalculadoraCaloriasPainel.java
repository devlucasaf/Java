package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.model.saude.CalculadoraTMB;
import math.calculadora.completa.model.saude.resultado.ResultadoTMB;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraCaloriasPainel extends JPanel {

    public CalculadoraCaloriasPainel() {
        super(new GridBagLayout());
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblPeso = new JLabel("Peso (kg):");
        lblPeso.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(lblPeso, gridBagConstraints);

        JTextField txtPeso = new JTextField("70", 8);
        txtPeso.setBackground(TemaEscuro.CAMPO);
        txtPeso.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        add(txtPeso, gridBagConstraints);

        JLabel lblAltura = new JLabel("Altura (cm):");
        lblAltura.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        add(lblAltura, gridBagConstraints);

        JTextField txtAltura = new JTextField("175", 8);
        txtAltura.setBackground(TemaEscuro.CAMPO);
        txtAltura.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        add(txtAltura, gridBagConstraints);

        JLabel lblIdade = new JLabel("Idade:");
        lblIdade.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        add(lblIdade, gridBagConstraints);

        JTextField txtIdade = new JTextField("30", 8);
        txtIdade.setBackground(TemaEscuro.CAMPO);
        txtIdade.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        add(txtIdade, gridBagConstraints);

        JLabel lblSexo = new JLabel("Sexo (M/F):");
        lblSexo.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        add(lblSexo, gridBagConstraints);

        JTextField txtSexo = new JTextField("M", 5);
        txtSexo.setBackground(TemaEscuro.CAMPO);
        txtSexo.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        add(txtSexo, gridBagConstraints);

        JLabel lblAtividade = new JLabel("Fator Atividade:");
        lblAtividade.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        add(lblAtividade, gridBagConstraints);
        String[] fatores = {"1.2 (Sedentário)", "1.375 (Leve)", "1.55 (Moderado)", "1.725 (Ativo)", "1.9 (Muito Ativo)"};

        JComboBox<String> cbAtividade = new JComboBox<>(fatores);
        cbAtividade.setBackground(TemaEscuro.BOTAO);
        cbAtividade.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        add(cbAtividade, gridBagConstraints);

        JButton btnCalcular = new JButton("Calcular TMB");
        btnCalcular.setBackground(TemaEscuro.BOTAO);
        btnCalcular.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        add(btnCalcular, gridBagConstraints);

        JTextArea areaResultado = new JTextArea(4, 20);
        areaResultado.setBackground(TemaEscuro.CAMPO);
        areaResultado.setForeground(TemaEscuro.TEXTO);
        areaResultado.setEditable(false);

        JScrollPane scroll = new JScrollPane(areaResultado);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        add(scroll, gridBagConstraints);

        btnCalcular.addActionListener(e -> {
            try {
                double peso = Double.parseDouble(txtPeso.getText().trim());
                double altura = Double.parseDouble(txtAltura.getText().trim());
                int idade = Integer.parseInt(txtIdade.getText().trim());
                char sexo = txtSexo.getText().trim().charAt(0);
                double fator = 1.2 + (cbAtividade.getSelectedIndex() * 0.175);
                ResultadoTMB calcular = CalculadoraTMB.calcular(peso, altura, idade, sexo, fator);
                areaResultado.setText(String.format(
                        "TMB: %.2f kcal\nGasto Diário Estimado: %.2f kcal",
                        calcular.tmb, calcular.gastoDiario
                ));
            } catch (Exception ex) {
                areaResultado.setText("Erro: " + ex.getMessage());
            }
        });
    }
}
