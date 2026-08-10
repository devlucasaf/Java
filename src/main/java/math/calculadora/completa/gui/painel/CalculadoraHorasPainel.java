package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.util.Formatador;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraHorasPainel extends JPanel {
    public CalculadoraHorasPainel() {
        super(new GridBagLayout());
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblHora1 = new JLabel("Hora 1 (HH:MM):");
        lblHora1.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(lblHora1, gridBagConstraints);

        JTextField txtHora1 = new JTextField("10:30", 10);
        txtHora1.setBackground(TemaEscuro.CAMPO);
        txtHora1.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        add(txtHora1, gridBagConstraints);

        JLabel lblHora2 = new JLabel("Hora 2 (HH:MM):");
        lblHora2.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        add(lblHora2, gridBagConstraints);

        JTextField txtHora2 = new JTextField("02:45", 10);
        txtHora2.setBackground(TemaEscuro.CAMPO);
        txtHora2.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        add(txtHora2, gridBagConstraints);

        JComboBox<String> cbOperacao = new JComboBox<>(new String[]{"Somar", "Subtrair"});
        cbOperacao.setBackground(TemaEscuro.BOTAO);
        cbOperacao.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        add(cbOperacao, gridBagConstraints);

        JButton btnCalcular = new JButton("Calcular");
        btnCalcular.setBackground(TemaEscuro.BOTAO);
        btnCalcular.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        add(btnCalcular, gridBagConstraints);

        JLabel lblResultado = new JLabel("Resultado: ");
        lblResultado.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        add(lblResultado, gridBagConstraints);

        btnCalcular.addActionListener(e -> {
            try {
                String[] p1 = txtHora1.getText().split(":");
                String[] p2 = txtHora2.getText().split(":");
                int hora1 = Integer.parseInt(p1[0]);
                int minuto1 = Integer.parseInt(p1[1]);
                int hora2 = Integer.parseInt(p2[0]);
                int minuto2 = Integer.parseInt(p2[1]);
                int totalMinuto1 = hora1 * 60 + minuto1;
                int totalMinuto2 = hora2 * 60 + minuto2;
                int resultadoMin;
                if (cbOperacao.getSelectedItem().equals("Somar")) {
                    resultadoMin = totalMinuto1 + totalMinuto2;
                } else {
                    resultadoMin = Math.abs(totalMinuto1 - totalMinuto2);
                }
                lblResultado.setText("Resultado: " + Formatador.formatarMinutosParaHora(resultadoMin));
            } catch (Exception ex) {
                lblResultado.setText("Formato inválido! Use HH:MM");
            }
        });
    }
}
