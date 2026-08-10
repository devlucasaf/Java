package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.model.financas.SalarioLiquido;
import math.calculadora.completa.model.financas.resultados.ResultadoSalarioLiquido;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraSalarioPainel extends JPanel {

    public CalculadoraSalarioPainel() {
        super(new GridBagLayout());
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblSalarioBruto = new JLabel("Salário Bruto (R$):");
        lblSalarioBruto.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(lblSalarioBruto, gridBagConstraints);

        JTextField txtSalarioBruto = new JTextField("3000", 10);
        txtSalarioBruto.setBackground(TemaEscuro.CAMPO);
        txtSalarioBruto.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        add(txtSalarioBruto, gridBagConstraints);

        JButton btnCalcularSalarioLiquido = new JButton("Calcular Líquido");
        btnCalcularSalarioLiquido.setBackground(TemaEscuro.BOTAO);
        btnCalcularSalarioLiquido.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        add(btnCalcularSalarioLiquido, gridBagConstraints);

        JTextArea areaResultado = new JTextArea(6, 20);
        areaResultado.setBackground(TemaEscuro.CAMPO);
        areaResultado.setForeground(TemaEscuro.TEXTO);
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scroll = new JScrollPane(areaResultado);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        add(scroll, gridBagConstraints);

        btnCalcularSalarioLiquido.addActionListener(e -> {
            try {
                double bruto = Double.parseDouble(txtSalarioBruto.getText().trim());
                ResultadoSalarioLiquido salario = SalarioLiquido.calcular(bruto);
                areaResultado.setText(String.format(
                        "Salário Bruto: R$ %.2f\n" +
                                "Desconto INSS: R$ %.2f\n" +
                                "Desconto IRRF: R$ %.2f\n" +
                                "Salário Líquido: R$ %.2f",
                        salario.salarioBruto, salario.descontoINSS, salario.descontoIRRF, salario.salarioLiquido
                ));
            } catch (Exception ex) {
                areaResultado.setText("Erro: " + ex.getMessage());
            }
        });
    }
}
