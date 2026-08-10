package math.calculadora.completa.gui.action;

import math.calculadora.completa.model.calculos.CalculadoraBasica;
import math.calculadora.completa.util.Formatador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculadoraAction implements ActionListener {
    private JTextField  display;
    private String      comando;
    private CalculadoraBasica calculadora = new CalculadoraBasica();

    public CalculadoraAction(JTextField display, String comando) {
        this.display = display;
        this.comando = comando;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String texto = display.getText();

        if (comando.matches("[0-9.]")) {
            if (calculadora.isNovoNumero()) {
                display.setText(comando);
                calculadora.setNovoNumero(false);
            } else {
                if (comando.equals(".") && texto.contains(".")) {
                    return;
                }
                display.setText(texto + comando);
            }
        } else if (comando.equals("C")) {
            display.setText("0");
            calculadora.reset();
        } else if (comando.equals("⌫")) {
            if (texto.length() > 1) {
                display.setText(texto.substring(0, texto.length() - 1));
            } else {
                display.setText("0");
                calculadora.setNovoNumero(true);
            }
        } else if (comando.equals("=")) {
            if (!calculadora.getOperador().isEmpty()) {
                try {
                    double atual = Double.parseDouble(texto);
                    double resultado = calculadora.calcular(atual, calculadora.getOperador());
                    display.setText(Formatador.formatarNumero(resultado));
                    calculadora.setValorAtual(resultado);
                    calculadora.setOperador("");
                    calculadora.setNovoNumero(true);
                } catch (ArithmeticException ex) {
                    display.setText("Erro");
                }
            }
        } else if (comando.equals("x²")) {
            try {
                double val = Double.parseDouble(texto);
                display.setText(Formatador.formatarNumero(val * val));
                calculadora.setNovoNumero(true);
            } catch (NumberFormatException ex) {
                display.setText("Erro");
            }
        } else if (comando.equals("√")) {
            try {
                double val = Double.parseDouble(texto);
                if (val < 0) {
                    display.setText("Erro");
                    return;
                }
                display.setText(Formatador.formatarNumero(Math.sqrt(val)));
                calculadora.setNovoNumero(true);
            } catch (NumberFormatException ex) {
                display.setText("Erro");
            }
        } else {
            if (!calculadora.getOperador().isEmpty() && !calculadora.isNovoNumero()) {
                try {
                    double atual = Double.parseDouble(texto);
                    double resultado = calculadora.calcular(atual, calculadora.getOperador());
                    display.setText(Formatador.formatarNumero(resultado));
                    calculadora.setValorAtual(resultado);
                } catch (ArithmeticException ex) {
                    display.setText("Erro");
                    return;
                }
            } else {
                try {
                    calculadora.setValorAtual(Double.parseDouble(texto));
                } catch (NumberFormatException ex) {
                    display.setText("Erro");
                    return;
                }
            }
            calculadora.setOperador(comando);
            calculadora.setNovoNumero(true);
        }
    }
}
