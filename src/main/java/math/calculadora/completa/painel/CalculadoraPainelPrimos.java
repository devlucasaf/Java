package math.calculadora.completa.painel;

import math.calculadora.completa.tema.TemaEscuro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CalculadoraPainelPrimos extends JPanel {

    private JTextField txtNumero;
    private JTextArea areaResultado;
    private JComboBox<String> cbOperacao;

    public CalculadoraPainelPrimos() {
        super(new GridBagLayout());
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblNumero = new JLabel("Número (N):");
        lblNumero.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 0;
        add(lblNumero, gbc);

        txtNumero = new JTextField("100", 10);
        txtNumero.setBackground(TemaEscuro.CAMPO);
        txtNumero.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 0;
        add(txtNumero, gbc);

        JLabel lblOp = new JLabel("Operação:");
        lblOp.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 1;
        add(lblOp, gbc);

        cbOperacao = new JComboBox<>(new String[]{"Verificar se é primo", "Listar primos até N", "Fatorar N"});
        cbOperacao.setBackground(TemaEscuro.BOTAO);
        cbOperacao.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 1;
        add(cbOperacao, gbc);

        JButton btnExecutar = new JButton("Executar");
        btnExecutar.setBackground(TemaEscuro.BOTAO);
        btnExecutar.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(btnExecutar, gbc);

        areaResultado = new JTextArea(10, 30);
        areaResultado.setBackground(TemaEscuro.CAMPO);
        areaResultado.setForeground(TemaEscuro.TEXTO);
        areaResultado.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaResultado);
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(scroll, gbc);

        btnExecutar.addActionListener(e -> executar());
    }

    private void executar() {
        try {
            long n = Long.parseLong(txtNumero.getText().trim());
            if (n < 2) {
                areaResultado.setText("Número deve ser ≥ 2.");
                return;
            }
            int op = cbOperacao.getSelectedIndex();
            switch (op) {
                case 0:
                    areaResultado.setText(isPrime(n) ? "É primo." : "Não é primo.");
                    break;
                case 1:
                    List<Long> primos = listarPrimosAte(n);
                    areaResultado.setText("Primos até " + n + ":\n" + primos.toString());
                    break;
                case 2:
                    areaResultado.setText(fatorar(n));
                    break;
            }
        } catch (NumberFormatException ex) {
            areaResultado.setText("Valor inválido!");
        }
    }

    private boolean isPrime(long n) {
        if (n < 2) {
            return false;
        }

        if (n % 2 == 0) {
            return n == 2;
        }

        for (long i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    private List<Long> listarPrimosAte(long n) {
        List<Long> lista = new ArrayList<>();
        for (long i = 2; i <= n; i++) {
            if (isPrime(i)) {
                lista.add(i);
            }
        }
        return lista;
    }

    private String fatorar(long n) {
        StringBuilder sb = new StringBuilder("Fatoração de " + n + ":\n");
        long original = n;
        for (long i = 2; i * i <= n; i++) {
            int count = 0;
            while (n % i == 0) {
                n /= i;
                count++;
            }

            if (count > 0) {
                sb.append(i).append("^").append(count).append("  ");
            }
        }

        if (n > 1) {
            sb.append(n).append("^1");
        }

        if (sb.toString().endsWith("  ")) {
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }
}