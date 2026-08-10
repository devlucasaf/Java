package math.calculadora.completa.model.calculos;

import math.calculadora.completa.model.calculos.resultados.ResultadoRegressao;

import java.util.ArrayList;
import java.util.List;

public class RegressaoLinear {

    public static ResultadoRegressao calcular(List<Double> x, List<Double> y) {
        if (x == null || y == null || x.size() != y.size() || x.size() < 2) {
            throw new IllegalArgumentException("São necessários pelo menos 2 pares (x,y) e tamanhos iguais.");
        }
        int n = x.size();
        double somaX = 0;
        double somaY = 0;
        double somaXY = 0;
        double somaX2 = 0;
        double somaY2 = 0;

        for (int i = 0; i < n; i++) {
            double xi = x.get(i);
            double yi = y.get(i);
            somaX += xi;
            somaY += yi;
            somaXY += xi * yi;
            somaX2 += xi * xi;
            somaY2 += yi * yi;
        }

        double denominador = n * somaX2 - somaX * somaX;
        if (denominador == 0) {
            throw new ArithmeticException("Denominador zero (x constante?)");
        }
        double b = (n * somaXY - somaX * somaY) / denominador;
        double a = (somaY * somaX2 - somaX * somaXY) / denominador;
        double mediaY = somaY / n;
        double somaTotalQuadrados = 0;
        double somaResidualQuadrados = 0;

        for (int i = 0; i < n; i++) {
            double yi = y.get(i);
            double yHat = a + b * x.get(i);
            somaTotalQuadrados += Math.pow(yi - mediaY, 2);
            somaResidualQuadrados += Math.pow(yi - yHat, 2);
        }

        double r2 = somaTotalQuadrados == 0 ? 1 : 1 - (somaResidualQuadrados / somaTotalQuadrados);
        double correlacao = Math.sqrt(r2) * (b >= 0 ? 1 : -1);
        return new ResultadoRegressao(a, b, r2, correlacao, new ArrayList<>(x), new ArrayList<>(y));
    }

    public static double prever(double x, ResultadoRegressao resultado) {
        return resultado.a + resultado.b * x;
    }
}