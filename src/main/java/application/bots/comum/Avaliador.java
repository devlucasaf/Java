package application.bots.comum;

public class Avaliador {

    private final String    s;
    private int             pos;

    Avaliador(String s) {
        this.s = s.replace(" ", "").replace(',', '.');
    }

    double avaliar() {
        double v = soma();
        if (pos < s.length()) {
            throw new IllegalArgumentException("expressao invalida");
        }
        return v;
    }

    // --- SOMA E SUBTRAÇÃO ---
    private double soma() {
        double v = produto();
        while (pos < s.length() && (s.charAt(pos) == '+' || s.charAt(pos) == '-')) {
            char op = s.charAt(pos++);
            double d = produto();

            if (op == '+') {
                v = v + d;
            } else {
                v = v - d;
            }
        }
        return v;
    }

    // --- MULTIPLICAÇÃO, DIVISÃO E RESTO ---
    private double produto() {
        double v = potencia();

        while (pos < s.length() && "*/%".indexOf(s.charAt(pos)) >= 0) {
            char op = s.charAt(pos++);
            double d = potencia();

            if (op == '*') {
                v *= d;
            } else if (d == 0) {
                throw new IllegalArgumentException("divisao por zero");
            } else {
                v = op == '/' ? v / d : v % d;
            }
        }
        return v;
    }

    // --- POTÊNCIA ---
    private double potencia() {
        double base = unario();
        if (pos < s.length() && s.charAt(pos) == '^') {
            pos++;
            return Math.pow(base, potencia());
        }
        return base;
    }

    // --- SINAIS, PARÊNTESES E NÚMEROS ---
    private double unario() {
        if (pos < s.length() && s.charAt(pos) == '-') {
            pos++;
            return -unario();
        }

        if (pos < s.length() && s.charAt(pos) == '+') {
            pos++;
            return unario();
        }

        if (pos < s.length() && s.charAt(pos) == '(') {
            pos++;
            double v = soma();
            if (pos >= s.length() || s.charAt(pos++) != ')') {
                throw new IllegalArgumentException("parentese nao fechado");
            }
            return v;
        }
        int ini = pos;
        while (pos < s.length() && (Character.isDigit(s.charAt(pos)) || s.charAt(pos) == '.')) {
            pos++;
        }

        if (ini == pos) {
            throw new IllegalArgumentException("expressao invalida");
        }
        return Double.parseDouble(s.substring(ini, pos));
    }
}