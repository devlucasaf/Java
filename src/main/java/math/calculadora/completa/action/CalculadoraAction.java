package math.calculadora.completa.action;

import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculadoraAction implements ActionListener {
    private JTextField  display;
    private String      comando;
    private double      valorAtual = 0;
    private String      operador = "";
    private boolean     novoNumero = true;

    public CalculadoraAction(JTextField display, String comando) {
        this.display = display;
        this.comando = comando;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String texto = display.getText();

        if (comando.matches("[0-9.]")) {
            if (novoNumero) {
                display.setText(comando);
                novoNumero = false;
            } else {
                if (comando.equals(".") && texto.contains(".")) {
                    return;
                }
                display.setText(texto + comando);
            }
        } else if (comando.equals("C")) {
            display.setText("0");
            valorAtual = 0;
            operador = "";
            novoNumero = true;
        } else if (comando.equals("⌫")) {
            if (texto.length() > 1) {
                display.setText(texto.substring(0, texto.length() - 1));
            } else {
                display.setText("0");
                novoNumero = true;
            }
        } else if (comando.equals("=")) {
            if (!operador.isEmpty()) {
                double atual = Double.parseDouble(texto);
                double resultado = 0;
                switch (operador) {
                    case "+":
                        resultado = valorAtual + atual;
                        break;
                    case "-":
                        resultado = valorAtual - atual;
                        break;
                    case "*":
                        resultado = valorAtual * atual;
                        break;
                    case "/":
                        if (atual == 0) {
                            display.setText("Erro");
                            return;
                        }
                        resultado = valorAtual / atual;
                        break;
                }
                display.setText(formatarResultado(resultado));
                valorAtual = resultado;
                operador = "";
                novoNumero = true;
            }
        } else if (comando.equals("x²")) {
            double val = Double.parseDouble(texto);
            display.setText(formatarResultado(val * val));
            novoNumero = true;
        } else if (comando.equals("√")) {
            double val = Double.parseDouble(texto);
            if (val < 0) {
                display.setText("Erro");
                return;
            }
            display.setText(formatarResultado(Math.sqrt(val)));
            novoNumero = true;
        } else {
            if (!operador.isEmpty() && !novoNumero) {
                double atual = Double.parseDouble(texto);
                double resultado = 0;
                switch (operador) {
                    case "+":
                        resultado = valorAtual + atual;
                        break;
                    case "-":
                        resultado = valorAtual - atual;
                        break;
                    case "*":
                        resultado = valorAtual * atual;
                        break;
                    case "/":
                        if (atual == 0) {
                            display.setText("Erro");
                            return;
                        }
                        resultado = valorAtual / atual;
                        break;
                }
                display.setText(formatarResultado(resultado));
                valorAtual = resultado;
            } else {
                valorAtual = Double.parseDouble(texto);
            }
            operador = comando;
            novoNumero = true;
        }
    }

    private String formatarResultado(double valor) {
        if (valor == (long) valor) {
            return String.valueOf((long) valor);
        } else {
            return String.format("%.10f", valor).replaceAll("0*$", "").replaceAll("\\.$", "");
        }
    }
}