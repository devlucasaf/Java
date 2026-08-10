package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.model.calculos.TabelaVerdade;
import math.calculadora.completa.util.Constantes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraTabelaVerdadePainel extends JPanel {
    public CalculadoraTabelaVerdadePainel() {
        super(new BorderLayout(10, 10));
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(TemaEscuro.FUNDO);
        JLabel lblOperacao = new JLabel("Operação:");
        lblOperacao.setForeground(TemaEscuro.TEXTO);
        topPanel.add(lblOperacao);

        JComboBox<String> cbOperacao = new JComboBox<>(Constantes.OPERACOES_TABELA_VERDADE);
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
            String operacao = (String) cbOperacao.getSelectedItem();
            area.setText(TabelaVerdade.gerarTabela(operacao));
        });

        area.setText(TabelaVerdade.gerarTabela("A AND B"));
    }
}
