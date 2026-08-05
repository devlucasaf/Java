package math.calculadora.completa.gui.action;

import math.calculadora.completa.model.calculos.CalculadoraCientifica;
import math.calculadora.completa.util.Formatador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CientificaAction implements ActionListener {
    private JTextField  display;
    private String      comando;
    private CalculadoraCientifica calc = new CalculadoraCientifica();

    public CientificaAction(JTextField display, String comando) {
        this.display = display;
        this.comando = comando;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String texto = display.getText();

        if (comando.matches("[0-9.]")) {
            if (calc.isNovoNumero()) {
                display.setText(comando);
                calc.setNovoNumero(false);
            } else {
                if (comando.equals(".") && texto.contains(".")) {
                    return;
                }
                display.setText(texto + comando);
            }
        } else if (comando.equals("C")) {
            display.setText("0");
            calc.reset();
        } else if (comando.equals("⌫")) {
            if (texto.length() > 1) {
                display.setText(texto.substring(0, texto.length() - 1));
            } else {
                display.setText("0");
                calc.setNovoNumero(true);
            }
        } else if (comando.equals("=")) {
            if (!calc.getOperador().isEmpty()) {
                try {
                    double atual = Double.parseDouble(texto);
                    double resultado = calc.calcular(atual, calc.getOperador());
                    display.setText(Formatador.formatarNumero(resultado));
                    calc.setValorAtual(resultado);
                    calc.setOperador("");
                    calc.setNovoNumero(true);
                } catch (ArithmeticException ex) {
                    display.setText("Erro");
                }
            }
        } else if (comando.equals("sin") || comando.equals("cos") || comando.equals("tan") ||
                comando.equals("log") || comando.equals("ln") || comando.equals("exp") ||
                comando.equals("√")) {
            try {
                double val = Double.parseDouble(texto);
                double resultado = 0;
                switch (comando) {
                    case "sin":
                        resultado = calc.seno(val);
                        break;
                    case "cos":
                        resultado = calc.cosseno(val);
                        break;
                    case "tan":
                        resultado = calc.tangente(val);
                        break;
                    case "log":
                        resultado = calc.log10(val);
                        break;
                    case "ln":
                        resultado = calc.ln(val);
                        break;
                    case "exp":
                        resultado = calc.exp(val);
                        break;
                    case "√":
                        resultado = calc.raiz(val);
                        break;
                }
                display.setText(Formatador.formatarNumero(resultado));
                calc.setNovoNumero(true);
            } catch (IllegalArgumentException | ArithmeticException ex) {
                display.setText("Erro");
            }
        } else if (comando.equals("x^y")) {
            if (!calc.getOperador().isEmpty() && !calc.isNovoNumero()) {
                try {
                    double atual = Double.parseDouble(texto);
                    double resultado = calc.potencia(calc.getValorAtual(), atual);
                    display.setText(Formatador.formatarNumero(resultado));
                    calc.setValorAtual(resultado);
                } catch (ArithmeticException ex) {
                    display.setText("Erro");
                    return;
                }
            } else {
                try {
                    calc.setValorAtual(Double.parseDouble(texto));
                } catch (NumberFormatException ex) {
                    display.setText("Erro");
                    return;
                }
            }
            calc.setOperador("x^y");
            calc.setNovoNumero(true);
        } else {
            if (!calc.getOperador().isEmpty() && !calc.isNovoNumero()) {
                try {
                    double atual = Double.parseDouble(texto);
                    double resultado = calc.calcular(atual, calc.getOperador());
                    display.setText(Formatador.formatarNumero(resultado));
                    calc.setValorAtual(resultado);
                } catch (ArithmeticException ex) {
                    display.setText("Erro");
                    return;
                }
            } else {
                try {
                    calc.setValorAtual(Double.parseDouble(texto));
                } catch (NumberFormatException ex) {
                    display.setText("Erro");
                    return;
                }
            }
            calc.setOperador(comando);
            calc.setNovoNumero(true);
        }
    }
}
