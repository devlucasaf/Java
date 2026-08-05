package math.calculadora.completa.model.conversoes;

import math.calculadora.completa.util.Constantes;

public class ConversorCombustivel {

    public static double converter(double valor, String de, String para) {
        double kmPorLitro = paraKmPorLitro(valor, de);
        if (Double.isNaN(kmPorLitro) || Double.isInfinite(kmPorLitro)) {
            return kmPorLitro;
        }
        return deKmPorLitro(kmPorLitro, para);
    }

    private static double paraKmPorLitro(double valor, String unidade) {
        switch (unidade) {
            case "km/L":
                return valor;
            case "L/100km":
                if (valor == 0) {
                    return Double.POSITIVE_INFINITY;
                }
                return 100.0 / valor;
            case "MPG (EUA)":
                return (valor * Constantes.KM_POR_MILHA) / Constantes.LITROS_POR_GALAO;
            default:
                return Double.NaN;
        }
    }

    private static double deKmPorLitro(double kmPorLitro, String unidade) {
        switch (unidade) {
            case "km/L":
                return kmPorLitro;
            case "L/100km":
                if (kmPorLitro == 0) {
                    return Double.POSITIVE_INFINITY;
                }
                return 100.0 / kmPorLitro;
            case "MPG (EUA)":
                return (kmPorLitro * Constantes.LITROS_POR_GALAO) / Constantes.KM_POR_MILHA;
            default:
                return Double.NaN;
        }
    }
}
