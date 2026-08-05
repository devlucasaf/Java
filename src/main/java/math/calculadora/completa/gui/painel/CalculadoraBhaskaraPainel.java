package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.model.calculos.Bhaskara;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraBhaskaraPainel extends JPanel {
    public CalculadoraBhaskaraPainel() {
        super(new GridBagLayout());
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addLabel("a:", 0, 0, gbc);
        JTextField txtA = addTextField("1", 1, 0, gbc);
        addLabel("b:", 0, 1, gbc);
        JTextField txtB = addTextField("2", 1, 1, gbc);
        addLabel("c:", 0, 2, gbc);
        JTextField txtC = addTextField("1", 1, 2, gbc);

        JButton btnCalcular = new JButton("Calcular");
        btnCalcular.setBackground(TemaEscuro.BOTAO);
        btnCalcular.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        add(btnCalcular, gbc);

        JLabel lblResultado = new JLabel("Resultado: ");
        lblResultado.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        add(lblResultado, gbc);

        btnCalcular.addActionListener(e -> {
            try {
                double a = Double.parseDouble(txtA.getText());
                double b = Double.parseDouble(txtB.getText());
                double c = Double.parseDouble(txtC.getText());
                Bhaskara.Raizes raizes = Bhaskara.calcular(a, b, c);
                if (raizes.complexas) {
                    lblResultado.setText("Resultado: raízes complexas.");
                } else if (raizes.x1 == raizes.x2) {
                    lblResultado.setText(String.format("Resultado: x = %.4f (raiz dupla)", raizes.x1));
                } else {
                    lblResultado.setText(String.format("Resultado: x1 = %.4f, x2 = %.4f", raizes.x1, raizes.x2));
                }
            } catch (Exception ex) {
                lblResultado.setText("Resultado: erro nos valores!");
            }
        });
    }

    private void addLabel(String texto, int x, int y, GridBagConstraints gbc) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = x; gbc.gridy = y; gbc.gridwidth = 1;
        add(lbl, gbc);
    }

    private JTextField addTextField(String valor, int x, int y, GridBagConstraints gbc) {
        JTextField tf = new JTextField(valor, 8);
        tf.setBackground(TemaEscuro.CAMPO);
        tf.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = x; gbc.gridy = y; gbc.gridwidth = 1;
        add(tf, gbc);
        return tf;
    }
}