package org.math.calculadora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculadora extends JFrame implements ActionListener {

    // Componentes da interface
    private JTextField  display;
    private double      numero1;
    private double      numero2;
    private double      resultado;
    private char        operator;

    public Calculadora() {
        setTitle("Calculadora");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        display = new JTextField();

        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        add(display, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4));

        String[] button = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+"
        };

        for (String text : button) {
            JButton buttons = new JButton(text);

            buttons.setFont(new Font("Arial", Font.BOLD, 20));
            buttons.addActionListener(this);
            panel.add(buttons);
        }

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ((command.charAt(0) >= '0' && command.charAt(0) <= '9') || command.equals(".")) {
            display.setText(display.getText() + command);
        } else if (command.charAt(0) == 'C' || command.charAt(0) == '-' || command.charAt(0) == '*' || command.charAt(0) == '/') {
            display.setText("");
        } else if (command.equals("=")) {
            numero2 = Double.parseDouble(display.getText());

            switch (operator) {
                case '+':
                    resultado = numero1 + numero2;
                    break;
                case '-':
                    resultado = numero1 - numero2;
                    break;
                case '*':
                    resultado = numero1 * numero2;
                    break;
                case '/':
                    if (numero2 != 0) {
                        resultado = numero1 / numero2;
                    } else {
                        display.setText("Erro: Divisão por zero");
                        return;
                    }
                    break;
            }

            display.setText(String.valueOf(resultado));
        } else {
            numero1 = Double.parseDouble(display.getText());
            operator = command.charAt(0);
            display.setText("");
        }
    }

    public static void main(String[] args) {
        new Calculadora();
    }
}
