package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.model.calculos.Bhaskara;
import math.calculadora.completa.model.calculos.Raizes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraBhaskaraPainel extends JPanel {
    public CalculadoraBhaskaraPainel() {
        super(new GridBagLayout());
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        addLabel("a:", 0, 0, gridBagConstraints);
        JTextField txtA = addTextField("1", 1, 0, gridBagConstraints);
        addLabel("b:", 0, 1, gridBagConstraints);
        JTextField txtB = addTextField("2", 1, 1, gridBagConstraints);
        addLabel("c:", 0, 2, gridBagConstraints);
        JTextField txtC = addTextField("1", 1, 2, gridBagConstraints);

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
                double a = Double.parseDouble(txtA.getText());
                double b = Double.parseDouble(txtB.getText());
                double c = Double.parseDouble(txtC.getText());
                Raizes raizes = Bhaskara.calcular(a, b, c);
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

    private void addLabel(String texto, int x, int y, GridBagConstraints gridBagConstraints) {
        JLabel label = new JLabel(texto);
        label.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = x; gridBagConstraints.gridy = y; gridBagConstraints.gridwidth = 1;
        add(label, gridBagConstraints);
    }

    private JTextField addTextField(String valor, int x, int y, GridBagConstraints gridBagConstraints) {
        JTextField tf = new JTextField(valor, 8);
        tf.setBackground(TemaEscuro.CAMPO);
        tf.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = x; gridBagConstraints.gridy = y; gridBagConstraints.gridwidth = 1;
        add(tf, gridBagConstraints);
        return tf;
    }
}