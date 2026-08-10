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
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblData1 = new JLabel("Data 1 (dd/MM/yyyy):");
        lblData1.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(lblData1, gridBagConstraints);

        JTextField txtData1 = new JTextField("01/01/2020", 15);
        txtData1.setBackground(TemaEscuro.CAMPO);
        txtData1.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        add(txtData1, gridBagConstraints);

        JLabel lblData2 = new JLabel("Data 2 (dd/MM/yyyy):");
        lblData2.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        add(lblData2, gridBagConstraints);

        JTextField txtData2 = new JTextField("01/01/2021", 15);
        txtData2.setBackground(TemaEscuro.CAMPO);
        txtData2.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        add(txtData2, gridBagConstraints);

        JButton btnCalcular = new JButton("Calcular Diferença");
        btnCalcular.setBackground(TemaEscuro.BOTAO);
        btnCalcular.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        add(btnCalcular, gridBagConstraints);

        JLabel lblResultado = new JLabel("Resultado: ");
        lblResultado.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        add(lblResultado, gridBagConstraints);

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
