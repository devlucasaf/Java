package math.calculadora.completa.model.financas;

import math.calculadora.completa.model.financas.resultados.ResultadoMargem;

public class MargemLucro {

    public static ResultadoMargem calcular(double receita, double custoTotal, double despesasOperacionais) {
        if (receita <= 0) {
            throw new IllegalArgumentException("Receita deve ser maior que zero.");
        }

        double lucroBruto = receita - custoTotal;
        double lucroLiquido = lucroBruto - despesasOperacionais;
        double margemBruta = (lucroBruto / receita) * 100;
        double margemLiquida = (lucroLiquido / receita) * 100;
        double markup = (receita / custoTotal) - 1;

        return new ResultadoMargem(margemBruta, margemLiquida, markup);
    }
}