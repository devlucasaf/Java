package math.calculadora.emprestimo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.Locale;

public class CalculadoraEmprestimo extends JFrame {
    private final Color COR_FUNDO = new Color(43, 45, 48);
    private final Color COR_PAINEL = new Color(60, 63, 65);
    private final Color COR_TEXTO = new Color(200, 200, 200);
    private final Color COR_BOTAO = new Color(75, 110, 175);
    private final Color COR_TEXTO_BOTAO = Color.WHITE;

    private JTextField          campoValor;
    private JComboBox<Integer>  comboParcelas;
    private JLabel              labelResultadoParcela;
    private JLabel              labelResultadoTotal;

    private final double TAXA_JUROS_MENSAL = 0.085; // 8,5% ao mês
    private final Locale LOCAL_BR = new Locale("pt", "BR");

    public CalculadoraEmprestimo() {
        setTitle("Calculadora de Empréstimo");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Painel Principal
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBackground(COR_FUNDO);
        painelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Título
        JLabel labelTitulo = new JLabel("Simulador de Empréstimo");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        labelTitulo.setForeground(Color.WHITE);
        labelTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(labelTitulo);
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));

        // Painel do Formulário
        JPanel painelForm = new JPanel(new GridLayout(2, 2, 10, 15));
        painelForm.setBackground(COR_FUNDO);

        // Campo de Valor
        JLabel labelValor = criarLabel("Valor do Empréstimo (R$):");
        campoValor = new JTextField();
        estilizarCampo(campoValor);
        aplicarMascaraDinheiro(campoValor);

        // Campo de Parcelas
        JLabel labelParcelas = criarLabel("Quantidade de Parcelas:");
        Integer[] opcoesParcelas = {1, 3, 6, 12, 24, 36};
        comboParcelas = new JComboBox<>(opcoesParcelas);
        comboParcelas.setBackground(COR_PAINEL);
        comboParcelas.setForeground(Color.WHITE);

        painelForm.add(labelValor);
        painelForm.add(campoValor);
        painelForm.add(labelParcelas);
        painelForm.add(comboParcelas);

        painelPrincipal.add(painelForm);
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));

        // Botão Calcular
        JButton btnCalcular = new JButton("Calcular");
        btnCalcular.setFont(new Font("Arial", Font.BOLD, 14));
        btnCalcular.setBackground(COR_BOTAO);
        btnCalcular.setForeground(COR_TEXTO_BOTAO);
        btnCalcular.setFocusPainted(false);
        btnCalcular.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCalcular.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCalcular.addActionListener(e -> calcularEmprestimo());

        painelPrincipal.add(btnCalcular);
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 20)));

        labelResultadoParcela = new JLabel("Valor da Parcela: R$ 0,00");
        labelResultadoParcela.setFont(new Font("Arial", Font.BOLD, 16));
        labelResultadoParcela.setForeground(new Color(137, 207, 113)); // Verde claro
        labelResultadoParcela.setAlignmentX(Component.CENTER_ALIGNMENT);

        labelResultadoTotal = new JLabel("Valor Total Pago: R$ 0,00");
        labelResultadoTotal.setFont(new Font("Arial", Font.PLAIN, 14));
        labelResultadoTotal.setForeground(COR_TEXTO);
        labelResultadoTotal.setAlignmentX(Component.CENTER_ALIGNMENT);

        painelPrincipal.add(labelResultadoParcela);
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
        painelPrincipal.add(labelResultadoTotal);

        add(painelPrincipal);
    }

    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(COR_TEXTO);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        return label;
    }

    private void estilizarCampo(JTextField campo) {
        campo.setBackground(COR_PAINEL);
        campo.setForeground(Color.WHITE);
        campo.setCaretColor(Color.WHITE);
        campo.setFont(new Font("Arial", Font.PLAIN, 14));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 80)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
    }

    private void aplicarMascaraDinheiro(JTextField campo) {
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String textoLimpo = campo.getText().replaceAll("[^0-9]", "");
                if (!textoLimpo.isEmpty()) {
                    try {
                        long valorLong = Long.parseLong(textoLimpo);
                        NumberFormat formato = NumberFormat.getNumberInstance(LOCAL_BR);
                        campo.setText(formato.format(valorLong));
                    } catch (NumberFormatException ex) {
                        // Ignora caso o número exceda o limite
                    }
                }
            }
        });
    }

    private void calcularEmprestimo() {
        String textoValor = campoValor.getText().replaceAll("[^0-9]", "");

        if (textoValor.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, insira o valor do empréstimo.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double p = Double.parseDouble(textoValor);
            int n = (Integer) comboParcelas.getSelectedItem();
            double i = TAXA_JUROS_MENSAL;

            double pmt;

            if (n == 1) {
                pmt = p * (1 + i);
            } else {
                double fator = Math.pow(1 + i, n);
                pmt = p * (i * fator) / (fator - 1);
            }

            double valorTotal = pmt * n;

            NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(LOCAL_BR);
            labelResultadoParcela.setText("Valor da Parcela: " + formatoMoeda.format(pmt));
            labelResultadoTotal.setText("Valor Total Pago: " + formatoMoeda.format(valorTotal));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocorreu um erro no cálculo. Verifique os valores inseridos.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new CalculadoraEmprestimo().setVisible(true);
        });
    }
}