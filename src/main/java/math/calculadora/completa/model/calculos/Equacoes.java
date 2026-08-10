package math.calculadora.completa.model.calculos;

import math.calculadora.completa.model.calculos.resultados.SolucaoSistema;

public class Equacoes {

    public static double resolverPrimeiroGrau(double a, double b) {
        if (a == 0) {
            throw new IllegalArgumentException("a não pode ser zero.");
        }
        return -b / a;
    }

    public static SolucaoSistema resolverSistema2x2(double a1, double b1, double c1,
                                                    double a2, double b2, double c2) {
        double det = a1 * b2 - a2 * b1;
        if (det == 0) {
            throw new ArithmeticException("Sistema impossível ou indeterminado.");
        }

        double x = (c1 * b2 - c2 * b1) / det;
        double y = (a1 * c2 - a2 * c1) / det;

        return new SolucaoSistema(x, y);
    }
}