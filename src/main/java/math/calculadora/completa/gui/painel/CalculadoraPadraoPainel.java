package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.gui.action.CalculadoraAction;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraPadraoPainel extends JPanel {
    public CalculadoraPadraoPainel() {
        super(new BorderLayout(10, 10));
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JTextField display = new JTextField("0");
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setEditable(false);
        display.setBackground(TemaEscuro.CAMPO);
        display.setForeground(TemaEscuro.TEXTO);
        add(display, BorderLayout.NORTH);

        JPanel botaoPanel = new JPanel(new GridLayout(5, 4, 5, 5));
        botaoPanel.setBackground(TemaEscuro.FUNDO);
        String[] buttons = {
                "7","8","9","/",
                "4","5","6","*",
                "1","2","3","-",
                "0",".","=","+",
                "x²","√","C","⌫"
        };
        for (String text : buttons) {
            JButton botao = new JButton(text);
            botao.setFont(new Font("Arial", Font.PLAIN, 18));
            botao.setBackground(TemaEscuro.BOTAO);
            botao.setForeground(TemaEscuro.TEXTO);
            botao.addActionListener(new CalculadoraAction(display, text));
            botaoPanel.add(botao);
        }
        add(botaoPanel, BorderLayout.CENTER);
    }
}