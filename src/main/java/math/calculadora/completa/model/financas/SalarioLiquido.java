package math.calculadora.completa.model.financas;

import math.calculadora.completa.model.financas.resultados.ResultadoSalarioLiquido;

public class SalarioLiquido {

    public static ResultadoSalarioLiquido calcular(double salarioBruto) {
        if (salarioBruto <= 0) {
            throw new IllegalArgumentException("Salário bruto deve ser positivo");
        }

        double inss = calcularINSS(salarioBruto);
        double baseIRRF = salarioBruto - inss;
        double irrf = calcularIRRF(baseIRRF);
        double liquido = salarioBruto - inss - irrf;

        return new ResultadoSalarioLiquido(salarioBruto, inss, irrf, liquido);
    }

    private static double calcularINSS(double salario) {
        double teto = 7786.02;
        double valor = Math.min(salario, teto);
        double desconto = 0;

        if (valor <= 1412.00) {
            desconto = valor * 0.075;
        } else if (valor <= 2666.68) {
            desconto = 1412.00 * 0.075 + (valor - 1412.00) * 0.09;
        } else if (valor <= 4000.03) {
            desconto = 1412.00 * 0.075 + (2666.68 - 1412.00) * 0.09 + (valor - 2666.68) * 0.12;
        } else if (valor <= 7786.02) {
            desconto = 1412.00 * 0.075 + (2666.68 - 1412.00) * 0.09 + (4000.03 - 2666.68) * 0.12 + (valor - 4000.03) * 0.14;
        }
        return Math.round(desconto * 100.0) / 100.0;
    }

    private static double calcularIRRF(double base) {
        double deducao = 0;
        double aliquota = 0;
        if (base <= 2259.20) {
            aliquota = 0;
            deducao = 0;
        } else if (base <= 2826.65) {
            aliquota = 0.075;
            deducao = 169.44;
        } else if (base <= 3751.05) {
            aliquota = 0.15;
            deducao = 381.44;
        } else if (base <= 4664.68) {
            aliquota = 0.225;
            deducao = 662.77;
        } else {
            aliquota = 0.275;
            deducao = 896.00;
        }
        double imposto = base * aliquota - deducao;
        return Math.max(0, Math.round(imposto * 100.0) / 100.0);
    }
}
