package math.calculadora.completa.painel;

import math.calculadora.completa.tema.TemaEscuro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraPainelHistorico extends JPanel {

    private final DefaultListModel<String>  modelo = new DefaultListModel<>();
    private final JList<String>             lista = new JList<>(modelo);

    public CalculadoraPainelHistorico() {
        super(new BorderLayout(10, 10));
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(TemaEscuro.FUNDO);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblExpressao = new JLabel("Expressão:");
        lblExpressao.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 0;
        topPanel.add(lblExpressao, gbc);

        JTextField txtExpressao = new JTextField(15);
        txtExpressao.setBackground(TemaEscuro.CAMPO);
        txtExpressao.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 0;
        topPanel.add(txtExpressao, gbc);

        JLabel lblResultado = new JLabel("Resultado:");
        lblResultado.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 1;
        topPanel.add(lblResultado, gbc);

        JTextField txtResultado = new JTextField(15);
        txtResultado.setBackground(TemaEscuro.CAMPO);
        txtResultado.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 1; gbc.gridy = 1;
        topPanel.add(txtResultado, gbc);

        JButton btnAdicionar = new JButton("Adicionar");
        btnAdicionar.setBackground(TemaEscuro.BOTAO);
        btnAdicionar.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        topPanel.add(btnAdicionar, gbc);

        add(topPanel, BorderLayout.NORTH);

        lista.setBackground(TemaEscuro.CAMPO);
        lista.setForeground(TemaEscuro.TEXTO);
        lista.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(lista);
        scroll.getViewport().setBackground(TemaEscuro.CAMPO);
        add(scroll, BorderLayout.CENTER);

        JButton btnLimpar = new JButton("Limpar Histórico");
        btnLimpar.setBackground(TemaEscuro.BOTAO);
        btnLimpar.setForeground(TemaEscuro.TEXTO);
        add(btnLimpar, BorderLayout.SOUTH);

        btnAdicionar.addActionListener(e -> {
            String expr = txtExpressao.getText().trim();
            String res = txtResultado.getText().trim();
            if (!expr.isEmpty() && !res.isEmpty()) {
                modelo.addElement(expr + " = " + res);
                txtExpressao.setText("");
                txtResultado.setText("");
            }
        });

        btnLimpar.addActionListener(e -> modelo.clear());
    }
}
