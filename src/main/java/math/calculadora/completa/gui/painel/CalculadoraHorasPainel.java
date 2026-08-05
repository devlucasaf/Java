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
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblHora1 = new JLabel("Hora 1 (HH:MM):");
        lblHora1.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 0;
        add(lblHora1, gbc);

        JTextField txtHora1 = new JTextField("10:30", 10);
        txtHora1.setBackground(TemaEscuro.CAMPO);
        txtHora1.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 0;
        add(txtHora1, gbc);

        JLabel lblHora2 = new JLabel("Hora 2 (HH:MM):");
        lblHora2.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 1;
        add(lblHora2, gbc);

        JTextField txtHora2 = new JTextField("02:45", 10);
        txtHora2.setBackground(TemaEscuro.CAMPO);
        txtHora2.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 1;
        add(txtHora2, gbc);

        JComboBox<String> cbOperacao = new JComboBox<>(new String[]{"Somar", "Subtrair"});
        cbOperacao.setBackground(TemaEscuro.BOTAO);
        cbOperacao.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(cbOperacao, gbc);

        JButton btnCalcular = new JButton("Calcular");
        btnCalcular.setBackground(TemaEscuro.BOTAO);
        btnCalcular.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(btnCalcular, gbc);

        JLabel lblResultado = new JLabel("Resultado: ");
        lblResultado.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(lblResultado, gbc);

        btnCalcular.addActionListener(e -> {
            try {
                String[] p1 = txtHora1.getText().split(":");
                String[] p2 = txtHora2.getText().split(":");
                int h1 = Integer.parseInt(p1[0]);
                int m1 = Integer.parseInt(p1[1]);
                int h2 = Integer.parseInt(p2[0]);
                int m2 = Integer.parseInt(p2[1]);
                int totalMin1 = h1 * 60 + m1;
                int totalMin2 = h2 * 60 + m2;
                int resultadoMin;
                if (cbOperacao.getSelectedItem().equals("Somar")) {
                    resultadoMin = totalMin1 + totalMin2;
                } else {
                    resultadoMin = Math.abs(totalMin1 - totalMin2);
                }
                lblResultado.setText("Resultado: " + Formatador.formatarMinutosParaHora(resultadoMin));
            } catch (Exception ex) {
                lblResultado.setText("Formato inválido! Use HH:MM");
            }
        });
    }
}
