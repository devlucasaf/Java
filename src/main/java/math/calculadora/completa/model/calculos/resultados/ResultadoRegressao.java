package math.calculadora.completa.model.calculos.resultados;

import java.util.List;

public class ResultadoRegressao {
    public double       a;
    public double       b;
    public double       r2;
    public double       correlacao;
    public List<Double> x;
    public List<Double> y;

    public ResultadoRegressao(double a, double b, double r2, double correlacao, List<Double> x, List<Double> y) {
        this.a = a;
        this.b = b;
        this.r2 = r2;
        this.correlacao = correlacao;
        this.x = x;
        this.y = y;
    }
}