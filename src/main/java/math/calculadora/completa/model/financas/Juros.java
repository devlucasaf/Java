package math.calculadora.completa.model.financas;

import math.calculadora.completa.model.financas.resultados.ResultadoJuros;

public class Juros {

    public static ResultadoJuros calcular(double capital, double taxaPercentual, double periodos, String tipo) {
        if (capital < 0) {
            throw new IllegalArgumentException("Capital não pode ser negativo");
        }

        if (periodos < 0) {
            throw new IllegalArgumentException("Períodos não pode ser negativo");
        }

        double taxa = taxaPercentual / 100.0;
        double montante;

        if ("Simples".equalsIgnoreCase(tipo)) {
            montante = capital * (1 + taxa * periodos);
        } else {
            montante = capital * Math.pow(1 + taxa, periodos);
        }

        double juros = montante - capital;
        return new ResultadoJuros(montante, juros);
    }
}
