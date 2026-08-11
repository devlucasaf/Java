package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.model.datas.ContagemRegressiva;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraContagemRegressivaPainel extends JPanel {

    public CalculadoraContagemRegressivaPainel() {
        super(new GridBagLayout());
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblData = new JLabel("Data (dd/MM/yyyy HH:mm):");
        lblData.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(lblData, gridBagConstraints);

        JTextField txtData = new JTextField("31/12/2026 23:59", 15);
        txtData.setBackground(TemaEscuro.CAMPO);
        txtData.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        add(txtData, gridBagConstraints);

        JButton btnCalcular = new JButton("Calcular Contagem");
        btnCalcular.setBackground(TemaEscuro.BOTAO);
        btnCalcular.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        add(btnCalcular, gridBagConstraints);

        JLabel lblResultado = new JLabel("Resultado: ");
        lblResultado.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        add(lblResultado, gridBagConstraints);

        btnCalcular.addActionListener(e -> {
            try {
                String contagem = ContagemRegressiva.calcularContagem(txtData.getText().trim());
                lblResultado.setText("Contagem: " + contagem);
            } catch (Exception ex) {
                lblResultado.setText("Erro: " + ex.getMessage());
            }
        });
    }
}
