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

    public double calcular(double operando, String operador) {
        if (operador == null || operador.isEmpty()) {
            return operando;
        }

        switch (operador) {
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
                throw new IllegalArgumentException("Operador inválido: " + operador);
        }
        return valorAtual;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public double getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(double valorAtual) {
        this.valorAtual = valorAtual;
    }

    public boolean isNovoNumero() {
        return novoNumero;
    }

    public void setNovoNumero(boolean novoNumero) {
        this.novoNumero = novoNumero;
    }
}
