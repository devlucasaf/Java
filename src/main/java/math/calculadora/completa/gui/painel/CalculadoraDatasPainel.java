package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.model.datas.CalculadoraDatas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraDatasPainel extends JPanel {
    public CalculadoraDatasPainel() {
        super(new GridBagLayout());
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblData1 = new JLabel("Data 1 (dd/MM/yyyy):");
        lblData1.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 0;
        add(lblData1, gbc);

        JTextField txtData1 = new JTextField("01/01/2020", 15);
        txtData1.setBackground(TemaEscuro.CAMPO);
        txtData1.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 0;
        add(txtData1, gbc);

        JLabel lblData2 = new JLabel("Data 2 (dd/MM/yyyy):");
        lblData2.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 1;
        add(lblData2, gbc);

        JTextField txtData2 = new JTextField("01/01/2021", 15);
        txtData2.setBackground(TemaEscuro.CAMPO);
        txtData2.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 1;
        add(txtData2, gbc);

        JButton btnCalcular = new JButton("Calcular Diferença");
        btnCalcular.setBackground(TemaEscuro.BOTAO);
        btnCalcular.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(btnCalcular, gbc);

        JLabel lblResultado = new JLabel("Resultado: ");
        lblResultado.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(lblResultado, gbc);

        btnCalcular.addActionListener(e -> {
            try {
                long dias = CalculadoraDatas.diferencaDias(txtData1.getText(), txtData2.getText());
                long meses = dias / 30;
                long anos = dias / 365;
                lblResultado.setText("Resultado: " + dias + " dias, ~" + meses + " meses, ~" + anos + " anos");
            } catch (Exception ex) {
                lblResultado.setText("Formato inválido! Use dd/MM/yyyy");
            }
        });
    }
}
