package math.calculadora.completa.model.utilidades;

import math.calculadora.completa.model.utilidades.resultados.ResultadoGorjeta;

public class Gorjeta {
    public static ResultadoGorjeta calcular(double valorConta, double percentualGorjeta, int numeroPessoas) {
        if (valorConta < 0) {
            throw new IllegalArgumentException("Valor da conta não pode ser negativo.");
        }

        if (percentualGorjeta < 0) {
            throw new IllegalArgumentException("Percentual não pode ser negativo.");
        }

        if (numeroPessoas <= 0) {
            throw new IllegalArgumentException("Número de pessoas deve ser positivo.");
        }

        double gorjeta = valorConta * (percentualGorjeta / 100);
        double total = valorConta + gorjeta;
        double porPessoa = total / numeroPessoas;

        return new ResultadoGorjeta(valorConta, gorjeta, total, porPessoa);
    }
}
