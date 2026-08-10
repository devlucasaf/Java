package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.model.utilidades.resultados.ResultadoContagem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static math.calculadora.completa.model.utilidades.ContadorCaracteres.contarCaracteres;

public class CalculadoraContadorPainel extends JPanel {

    public CalculadoraContadorPainel() {
        super(new BorderLayout(10, 10));
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // Área de texto
        JTextArea textArea = new JTextArea(10, 30);
        textArea.setBackground(TemaEscuro.CAMPO);
        textArea.setForeground(TemaEscuro.TEXTO);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(textArea);
        add(scroll, BorderLayout.CENTER);

        // Painel inferior com botão e resultados
        JPanel btnPanel = new JPanel(new GridBagLayout());
        btnPanel.setBackground(TemaEscuro.FUNDO);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        JButton btnContar = new JButton("Contar");
        btnContar.setBackground(TemaEscuro.BOTAO);
        btnContar.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        btnPanel.add(btnContar, gridBagConstraints);

        JLabel lblResultado = new JLabel("Resultado: ");
        lblResultado.setForeground(TemaEscuro.TEXTO);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        btnPanel.add(lblResultado, gridBagConstraints);

        add(btnPanel, BorderLayout.SOUTH);

        btnContar.addActionListener(e -> {
            ResultadoContagem caracteres = contarCaracteres(textArea.getText());
            lblResultado.setText(String.format(
                    "Caracteres: %d | Sem espaços: %d | Palavras: %d | Linhas: %d | Vogais: %d | Consoantes: %d",
                    caracteres.caracteres, caracteres.caracteresSemEspacos, caracteres.palavras, caracteres.linhas,
                    caracteres.vogais, caracteres.consoantes
            ));
        });
    }
}