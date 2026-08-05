package math.calculadora.completa.model.calculos;

public class CalculadoraBasica {

    private double  valorAtual = 0;
    private String  operador = "";
    private boolean novoNumero = true;

    public void reset() {
        valorAtual = 0;
        operador = "";
        novoNumero = true;
    }

    public double calcular(double operando, String op) {
        if (op == null || op.isEmpty()) {
            return operando;
        }

        switch (op) {
            case "+":
                valorAtual += operando;
                break;
            case "-":
                valorAtual -= operando;
                break;
            case "*":
                valorAtual *= operando;
                break;
            case "/":
                if (operando == 0) {
                    throw new ArithmeticException("Divisão por zero");
                }
                valorAtual /= operando;
                break;
            default:
                throw new IllegalArgumentException("Operador inválido: " + op);
        }
        return valorAtual;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String op) {
        this.operador = op;
    }

    public double getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(double v) {
        this.valorAtual = v;
    }

    public boolean isNovoNumero() {
        return novoNumero;
    }

    public void setNovoNumero(boolean b) {
        this.novoNumero = b;
    }
}
