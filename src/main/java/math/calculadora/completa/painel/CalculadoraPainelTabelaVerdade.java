package math.calculadora.completa.painel;

import math.calculadora.completa.tema.TemaEscuro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraPainelTabelaVerdade extends JPanel {

    private static final String[] OPERACOES = {
            "A AND B", "A OR B", "A XOR B", "NOT A",
            "A NAND B", "A NOR B", "A XNOR B",
            "(A AND B) OR C", "(A OR B) AND C", "A AND B AND C", "A OR B OR C"
    };

    public CalculadoraPainelTabelaVerdade() {
        super(new BorderLayout(10, 10));
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(TemaEscuro.FUNDO);
        JLabel lblOp = new JLabel("Operação:");
        lblOp.setForeground(TemaEscuro.TEXTO);
        topPanel.add(lblOp);

        JComboBox<String> cbOperacao = new JComboBox<>(OPERACOES);
        cbOperacao.setBackground(TemaEscuro.BOTAO);
        cbOperacao.setForeground(TemaEscuro.TEXTO);
        topPanel.add(cbOperacao);

        JButton btnGerar = new JButton("Gerar Tabela");
        btnGerar.setBackground(TemaEscuro.BOTAO);
        btnGerar.setForeground(TemaEscuro.TEXTO);
        topPanel.add(btnGerar);

        add(topPanel, BorderLayout.NORTH);

        JTextArea area = new JTextArea(20, 40);
        area.setBackground(TemaEscuro.CAMPO);
        area.setForeground(TemaEscuro.TEXTO);
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));
        area.setEditable(false);
        JScrollPane scroll = new JScrollPane(area);
        scroll.getViewport().setBackground(TemaEscuro.CAMPO);
        add(scroll, BorderLayout.CENTER);

        btnGerar.addActionListener(e -> {
            String op = (String) cbOperacao.getSelectedItem();
            area.setText(gerarTabela(op));
        });

        area.setText(gerarTabela("A AND B"));
    }

    private String gerarTabela(String operacao) {
        StringBuilder sb = new StringBuilder();
        boolean usaC = operacao.contains("C");
        boolean usaB = operacao.contains("B") || usaC;
        boolean usaA = true;

        sb.append("A\tB");
        if (usaC) sb.append("\tC");
        sb.append("\t| Resultado\n");
        sb.append("----------------------------\n");

        int max = usaC ? 8 : 4;
        for (int i = 0; i < max; i++) {
            boolean a = (i & 4) != 0;
            boolean b = (i & 2) != 0;
            boolean c = (i & 1) != 0;
            if (!usaC && (i & 1) != 0) {
                continue;
            }
            boolean resultado = avaliar(operacao, a, b, c);
            sb.append((a ? 1 : 0)).append("\t").append((b ? 1 : 0));
            if (usaC) {
                sb.append("\t").append((c ? 1 : 0));
            }
            sb.append("\t| ").append(resultado ? 1 : 0).append("\n");
        }
        return sb.toString();
    }

    private boolean avaliar(String op, boolean a, boolean b, boolean c) {
        switch (op) {
            case "A AND B":
                return a && b;
            case "A OR B":
                return a || b;
            case "A XOR B":
                return a ^ b;
            case "NOT A":
                return !a;
            case "A NAND B":
                return !(a && b);
            case "A NOR B":
                return !(a || b);
            case "A XNOR B":
                return !(a ^ b);
            case "(A AND B) OR C":
                return (a && b) || c;
            case "(A OR B) AND C":
                return (a || b) && c;
            case "A AND B AND C":
                return a && b && c;
            case "A OR B OR C":
                return a || b || c;
            default:
                return false;
        }
    }
}