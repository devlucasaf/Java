package math.calculadora.completa.painel;

import math.calculadora.completa.tema.TemaEscuro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// --- ABA HORAS ---
public class CalculadoraPainelHoras extends JPanel {

    public CalculadoraPainelHoras() {
        super(new GridBagLayout());
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblHora1 = new JLabel("Hora 1 (HH:MM):");
        lblHora1.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0; gridBagConstraints.gridy = 0;
        add(lblHora1, gridBagConstraints);

        JTextField txtHora1 = new JTextField("10:30", 10);
        txtHora1.setBackground(TemaEscuro.CAMPO);
        txtHora1.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1; gridBagConstraints.gridy = 0;
        add(txtHora1, gridBagConstraints);

        JLabel lblHora2 = new JLabel("Hora 2 (HH:MM):");
        lblHora2.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0; gridBagConstraints.gridy = 1;
        add(lblHora2, gridBagConstraints);

        JTextField txtHora2 = new JTextField("02:45", 10);
        txtHora2.setBackground(TemaEscuro.CAMPO);
        txtHora2.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1; gridBagConstraints.gridy = 1;
        add(txtHora2, gridBagConstraints);

        JComboBox<String> cbOperacao = new JComboBox<>(new String[]{"Somar", "Subtrair"});
        cbOperacao.setBackground(TemaEscuro.BOTAO);
        cbOperacao.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0; gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        add(cbOperacao, gridBagConstraints);

        JButton btnCalcularHora = new JButton("Calcular");
        btnCalcularHora.setBackground(TemaEscuro.BOTAO);
        btnCalcularHora.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0; gridBagConstraints.gridy = 3;
        add(btnCalcularHora, gridBagConstraints);

        JLabel lblResultadoHora = new JLabel("Resultado: ");
        lblResultadoHora.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0; gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        add(lblResultadoHora, gridBagConstraints);

        btnCalcularHora.addActionListener(e -> {
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
                int horas = resultadoMin / 60;
                int minutos = resultadoMin % 60;
                lblResultadoHora.setText(String.format("Resultado: %02d:%02d", horas, minutos));
            } catch (Exception ex) {
                lblResultadoHora.setText("Formato inválido! Use HH:MM");
            }
        });
    }
}