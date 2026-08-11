package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.model.datas.CalendarioPerpetuo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraDiaSemanaPainel extends JPanel {

    public CalculadoraDiaSemanaPainel() {
        super(new GridBagLayout());
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblDia = new JLabel("Dia:");
        lblDia.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(lblDia, gridBagConstraints);

        JTextField txtDia = new JTextField("15", 5);
        txtDia.setBackground(TemaEscuro.CAMPO);
        txtDia.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        add(txtDia, gridBagConstraints);

        JLabel lblMes = new JLabel("Mês:");
        lblMes.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        add(lblMes, gridBagConstraints);

        JTextField txtMes = new JTextField("11", 5);
        txtMes.setBackground(TemaEscuro.CAMPO);
        txtMes.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        add(txtMes, gridBagConstraints);

        JLabel lblAno = new JLabel("Ano:");
        lblAno.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        add(lblAno, gridBagConstraints);

        JTextField txtAno = new JTextField("1889", 5);
        txtAno.setBackground(TemaEscuro.CAMPO);
        txtAno.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        add(txtAno, gridBagConstraints);

        JButton btnCalcular = new JButton("Calcular Dia da Semana");
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
                int dia = Integer.parseInt(txtDia.getText().trim());
                int mes = Integer.parseInt(txtMes.getText().trim());
                int ano = Integer.parseInt(txtAno.getText().trim());
                String diaSemana = CalendarioPerpetuo.diaDaSemanaModerno(dia, mes, ano);
                lblResultado.setText("Dia da semana: " + diaSemana);
            } catch (Exception ex) {
                lblResultado.setText("Erro: " + ex.getMessage());
            }
        });
    }
}
