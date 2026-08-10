package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.model.conversoes.ConversorMoeda;
import math.calculadora.completa.util.Constantes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraMoedaPainel extends JPanel {
    private ConversorMoeda conversor = new ConversorMoeda();

    public CalculadoraMoedaPainel() {
        super(new GridBagLayout());
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblValor = new JLabel("Valor:");
        lblValor.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(lblValor, gridBagConstraints);

        JTextField txtValor = new JTextField("1", 10);
        txtValor.setBackground(TemaEscuro.CAMPO);
        txtValor.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        add(txtValor, gridBagConstraints);

        JLabel lblDe = new JLabel("De:");
        lblDe.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        add(lblDe, gridBagConstraints);

        JComboBox<String> cbDe = new JComboBox<>(Constantes.MOEDAS);
        cbDe.setSelectedItem("USD");
        cbDe.setBackground(TemaEscuro.BOTAO);
        cbDe.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        add(cbDe, gridBagConstraints);

        JLabel lblPara = new JLabel("Para:");
        lblPara.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        add(lblPara, gridBagConstraints);

        JComboBox<String> cbPara = new JComboBox<>(Constantes.MOEDAS);
        cbPara.setSelectedItem("BRL");
        cbPara.setBackground(TemaEscuro.BOTAO);
        cbPara.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        add(cbPara, gridBagConstraints);

        JButton btnConverter = new JButton("Converter");
        btnConverter.setBackground(TemaEscuro.BOTAO);
        btnConverter.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        add(btnConverter, gridBagConstraints);

        JLabel lblResultado = new JLabel("Resultado: ");
        lblResultado.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        add(lblResultado, gridBagConstraints);

        JLabel lblAviso = new JLabel("Taxas fornecidas pela API Frankfurter");
        lblAviso.setForeground(Color.LIGHT_GRAY);
        lblAviso.setFont(lblAviso.getFont().deriveFont(11f));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        add(lblAviso, gridBagConstraints);

        btnConverter.addActionListener(e -> {
            try {
                double valor = Double.parseDouble(txtValor.getText());
                String de = (String) cbDe.getSelectedItem();
                String para = (String) cbPara.getSelectedItem();
                btnConverter.setEnabled(false);
                lblResultado.setText("Buscando...");
                SwingWorker<Double, Void> worker = new SwingWorker<>() {
                    @Override protected Double doInBackground() throws Exception {
                        return conversor.converter(valor, de, para);
                    }

                    @Override protected void done() {
                        btnConverter.setEnabled(true);
                        try {
                            double resultado = get();
                            lblResultado.setText(String.format("Resultado: %.4f %s", resultado, para));
                        } catch (Exception ex) {
                            lblResultado.setText("Erro ao buscar taxa!");
                        }
                    }
                };
                worker.execute();
            } catch (NumberFormatException ex) {
                lblResultado.setText("Erro no valor!");
            }
        });
    }
}
