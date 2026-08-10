package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.service.HistoricoService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

public class CalculadoraHistoricoPainel extends JPanel {
    private final DefaultListModel<String>  modelo = new DefaultListModel<>();
    private final JList<String>             lista = new JList<>(modelo);
    private final HistoricoService          service = new HistoricoService();

    public CalculadoraHistoricoPainel() {
        super(new BorderLayout(10, 10));
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(TemaEscuro.FUNDO);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblExpressao = new JLabel("Expressão:");
        lblExpressao.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        topPanel.add(lblExpressao, gridBagConstraints);

        JTextField txtExpressao = new JTextField(15);
        txtExpressao.setBackground(TemaEscuro.CAMPO);
        txtExpressao.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        topPanel.add(txtExpressao, gridBagConstraints);

        JLabel lblResultado = new JLabel("Resultado:");
        lblResultado.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        topPanel.add(lblResultado, gridBagConstraints);

        JTextField txtResultado = new JTextField(15);
        txtResultado.setBackground(TemaEscuro.CAMPO);
        txtResultado.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        topPanel.add(txtResultado, gridBagConstraints);

        JButton btnAdicionar = new JButton("Adicionar");
        btnAdicionar.setBackground(TemaEscuro.BOTAO);
        btnAdicionar.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        topPanel.add(btnAdicionar, gridBagConstraints);
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

        carregarHistorico();

        btnAdicionar.addActionListener(e -> {
            String expressao = txtExpressao.getText().trim();
            String resultado = txtResultado.getText().trim();
            if (!expressao.isEmpty() && !resultado.isEmpty()) {
                String entrada = expressao + " = " + resultado;
                modelo.addElement(entrada);
                try {
                    service.adicionarEntrada(entrada);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar histórico.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
                txtExpressao.setText("");
                txtResultado.setText("");
            }
        });

        btnLimpar.addActionListener(e -> {
            modelo.clear();
            try {
                service.limpar();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao limpar histórico.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void carregarHistorico() {
        try {
            for (String entrada : service.carregar()) {
                modelo.addElement(entrada);
            }
        } catch (IOException e) {
            // Ignora se não conseguir carregar
        }
    }
}
