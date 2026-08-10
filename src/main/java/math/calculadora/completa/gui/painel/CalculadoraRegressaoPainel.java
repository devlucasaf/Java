package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.model.calculos.RegressaoLinear;
import math.calculadora.completa.model.calculos.resultados.ResultadoRegressao;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CalculadoraRegressaoPainel extends JPanel {

    private final DefaultTableModel     tableModel;
    private final JTable                tabela;
    private final JTextField            txtPreverX;
    private JTextArea                   areaResultado;

    public CalculadoraRegressaoPainel() {
        super(new BorderLayout(10, 10));
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Tabela de dados
        tableModel = new DefaultTableModel(new String[]{"X", "Y"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };

        tabela = new JTable(tableModel);
        tabela.setBackground(TemaEscuro.CAMPO);
        tabela.setForeground(TemaEscuro.TEXTO);
        tabela.setGridColor(Color.GRAY);
        tabela.setRowHeight(25);

        JScrollPane scrollTabela = new JScrollPane(tabela);
        scrollTabela.getViewport().setBackground(TemaEscuro.CAMPO);

        // Painel de botões da tabela
        JPanel panelBotoesTabela = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBotoesTabela.setBackground(TemaEscuro.FUNDO);

        JButton btnAdicionar = new JButton("Adicionar Linha");
        btnAdicionar.setBackground(TemaEscuro.BOTAO);
        btnAdicionar.setForeground(TemaEscuro.TEXTO);
        btnAdicionar.addActionListener(e -> tableModel.addRow(new Object[]{"", ""}));

        JButton btnRemover = new JButton("Remover Última");
        btnRemover.setBackground(TemaEscuro.BOTAO);
        btnRemover.setForeground(TemaEscuro.TEXTO);
        btnRemover.addActionListener(e -> {
            if (tableModel.getRowCount() > 0) {
                tableModel.removeRow(tableModel.getRowCount() - 1);
            }
        });

        JButton btnLimpar = new JButton("Limpar Tudo");
        btnLimpar.setBackground(TemaEscuro.BOTAO);
        btnLimpar.setForeground(TemaEscuro.TEXTO);
        btnLimpar.addActionListener(e -> {
            tableModel.setRowCount(0);
            areaResultado.setText("");
        });

        panelBotoesTabela.add(btnAdicionar);
        panelBotoesTabela.add(btnRemover);
        panelBotoesTabela.add(btnLimpar);

        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.setBackground(TemaEscuro.FUNDO);
        topPanel.add(scrollTabela, BorderLayout.CENTER);
        topPanel.add(panelBotoesTabela, BorderLayout.SOUTH);

        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBackground(TemaEscuro.FUNDO);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JButton btnCalcular = new JButton("Calcular Regressão");
        btnCalcular.setBackground(TemaEscuro.BOTAO);
        btnCalcular.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0; gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        bottomPanel.add(btnCalcular, gridBagConstraints);

        areaResultado = new JTextArea(6, 30);
        areaResultado.setBackground(TemaEscuro.CAMPO);
        areaResultado.setForeground(TemaEscuro.TEXTO);
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollResultado = new JScrollPane(areaResultado);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        bottomPanel.add(scrollResultado, gridBagConstraints);

        // Previsão
        JLabel lblPrever = new JLabel("Prever para x = ");
        lblPrever.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 0;
        bottomPanel.add(lblPrever, gridBagConstraints);

        txtPreverX = new JTextField("0", 8);
        txtPreverX.setBackground(TemaEscuro.CAMPO);
        txtPreverX.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        bottomPanel.add(txtPreverX, gridBagConstraints);

        JButton btnPrever = new JButton("Prever");
        btnPrever.setBackground(TemaEscuro.BOTAO);
        btnPrever.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        bottomPanel.add(btnPrever, gridBagConstraints);

        JLabel lblPrevisao = new JLabel("ŷ = ");
        lblPrevisao.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        bottomPanel.add(lblPrevisao, gridBagConstraints);

        // Ações
        btnCalcular.addActionListener(e -> calcularRegressao());
        btnPrever.addActionListener(e -> prever(lblPrevisao));

        // Adiciona painéis ao principal
        add(topPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Dados iniciais de exemplo
        tableModel.addRow(new Object[]{"1", "2"});
        tableModel.addRow(new Object[]{"2", "4"});
        tableModel.addRow(new Object[]{"3", "6"});
    }

    private void calcularRegressao() {
        try {
            List<Double> x = new ArrayList<>();
            List<Double> y = new ArrayList<>();
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String sx = tableModel.getValueAt(i, 0).toString().trim();
                String sy = tableModel.getValueAt(i, 1).toString().trim();
                if (sx.isEmpty() || sy.isEmpty()) {
                    continue;
                }
                x.add(Double.parseDouble(sx));
                y.add(Double.parseDouble(sy));
            }

            if (x.size() < 2) {
                areaResultado.setText("Adicione pelo menos 2 pares (x,y) válidos.");
                return;
            }
            ResultadoRegressao regressao = RegressaoLinear.calcular(x, y);
            areaResultado.setText(String.format(
                    "Equação: y = %.6f + %.6f x\n" +
                            "a (intercepto) = %.6f\n" +
                            "b (inclinação) = %.6f\n" +
                            "R² = %.6f\n" +
                            "r (correlação) = %.6f",
                    regressao.a, regressao.b, regressao.a, regressao.b, regressao.r2, regressao.correlacao
            ));
        } catch (Exception ex) {
            areaResultado.setText("Erro: " + ex.getMessage());
        }
    }

    private void prever(JLabel lblPrevisao) {
        try {
            double x = Double.parseDouble(txtPreverX.getText().trim());
            List<Double> xLista = new ArrayList<>();
            List<Double> yLista = new ArrayList<>();
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String sx = tableModel.getValueAt(i, 0).toString().trim();
                String sy = tableModel.getValueAt(i, 1).toString().trim();
                if (sx.isEmpty() || sy.isEmpty()) {
                    continue;
                }

                xLista.add(Double.parseDouble(sx));
                yLista.add(Double.parseDouble(sy));
            }
            if (xLista.size() < 2) {
                lblPrevisao.setText("ŷ = (dados insuficientes)");
                return;
            }
            ResultadoRegressao regressao = RegressaoLinear.calcular(xLista, yLista);
            double yPred = RegressaoLinear.prever(x, regressao);
            lblPrevisao.setText(String.format("ŷ = %.6f", yPred));
        } catch (Exception ex) {
            lblPrevisao.setText("ŷ = (erro)");
        }
    }
}
