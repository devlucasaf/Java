package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.model.calculos.Fibonacci;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class CalculadoraFibonacciPainel extends JPanel {

    public CalculadoraFibonacciPainel() {
        super(new GridBagLayout());
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Escolha da operação
        JLabel lblOperacao = new JLabel("Operação:");
        lblOperacao.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 0;
        add(lblOperacao, gbc);

        JComboBox<String> cbOperacao = new JComboBox<>(new String[]{"Gerar sequência até N", "Verificar se é Fibonacci"});
        cbOperacao.setBackground(TemaEscuro.BOTAO);
        cbOperacao.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 0;
        add(cbOperacao, gbc);

        // Campo para entrada
        JLabel lblEntrada = new JLabel("Valor (N ou número):");
        lblEntrada.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 1;
        add(lblEntrada, gbc);

        JTextField txtEntrada = new JTextField("10", 10);
        txtEntrada.setBackground(TemaEscuro.CAMPO);
        txtEntrada.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 1;
        add(txtEntrada, gbc);

        // Botão executar
        JButton btnExecutar = new JButton("Executar");
        btnExecutar.setBackground(TemaEscuro.BOTAO);
        btnExecutar.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(btnExecutar, gbc);

        // Área de resultados (texto)
        JTextArea areaResultado = new JTextArea(10, 30);
        areaResultado.setBackground(TemaEscuro.CAMPO);
        areaResultado.setForeground(TemaEscuro.TEXTO);
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(areaResultado);
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(scroll, gbc);

        // Ação do botão
        btnExecutar.addActionListener(e -> {
            int op = cbOperacao.getSelectedIndex();
            String entrada = txtEntrada.getText().trim();
            if (entrada.isEmpty()) {
                areaResultado.setText("Por favor, insira um valor.");
                return;
            }
            try {
                if (op == 0) {
                    int n = Integer.parseInt(entrada);
                    if (n < 0) {
                        areaResultado.setText("N deve ser >= 0.");
                        return;
                    }
                    List<Long> seq = Fibonacci.gerarSequencia(n);
                    StringBuilder sb = new StringBuilder("Sequência de Fibonacci (F0 a F" + n + "):\n");
                    for (int i = 0; i < seq.size(); i++) {
                        sb.append("F").append(i).append(" = ").append(seq.get(i)).append("\n");
                    }
                    areaResultado.setText(sb.toString());
                } else {
                    long num = Long.parseLong(entrada);
                    if (num < 0) {
                        areaResultado.setText("Número deve ser >= 0.");
                        return;
                    }
                    boolean isFib = Fibonacci.isFibonacci(num);
                    areaResultado.setText(num + (isFib ? " é " : " NÃO é ") + "um número de Fibonacci.");
                }
            } catch (NumberFormatException ex) {
                areaResultado.setText("Valor inválido. Digite um número inteiro.");
            } catch (IllegalArgumentException ex) {
                areaResultado.setText("Erro: " + ex.getMessage());
            }
        });
    }
}
