package math.calculadora.completa.model.calculos;

import math.calculadora.completa.model.calculos.resultados.ResultadoSolucao;

public class SistemaLinear3x3 {

    public static ResultadoSolucao resolver(double[][] coeficientes, double[] constantes) {
        double a1 = coeficientes[0][0], b1 = coeficientes[0][1], c1 = coeficientes[0][2];
        double a2 = coeficientes[1][0], b2 = coeficientes[1][1], c2 = coeficientes[1][2];
        double a3 = coeficientes[2][0], b3 = coeficientes[2][1], c3 = coeficientes[2][2];
        double d1 = constantes[0], d2 = constantes[1], d3 = constantes[2];

        double det = a1 * (b2 * c3 - c2 * b3) - b1 * (a2 * c3 - c2 * a3) + c1 * (a2 * b3 - b2 * a3);
        if (Math.abs(det) < 1e-10) {
            return new ResultadoSolucao(0, 0, 0, false);
        }

        double detX = d1 * (b2 * c3 - c2 * b3) - b1 * (d2 * c3 - c2 * d3) + c1 * (d2 * b3 - b2 * d3);
        double detY = a1 * (d2 * c3 - c2 * d3) - d1 * (a2 * c3 - c2 * a3) + c1 * (a2 * d3 - d2 * a3);
        double detZ = a1 * (b2 * d3 - d2 * b3) - b1 * (a2 * d3 - d2 * a3) + d1 * (a2 * b3 - b2 * a3);

        double x = detX / det;
        double y = detY / det;
        double z = detZ / det;
        return new ResultadoSolucao(x, y, z, true);
    }
}
