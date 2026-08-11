package math.calculadora.completa.model.saude;

import math.calculadora.completa.model.saude.resultado.ResultadoTMB;

public class CalculadoraTMB {

    public static ResultadoTMB calcular(double peso, double altura, int idade, char sexo, double fatorAtividade) {
        if (peso <= 0 || altura <= 0 || idade <= 0) {
            throw new IllegalArgumentException("Peso, altura e idade devem ser positivos.");
        }

        double tmb;
        if (sexo == 'M' || sexo == 'm') {
            tmb = 66 + (13.7 * peso) + (5 * altura * 100) - (6.8 * idade);
        } else if (sexo == 'F' || sexo == 'f') {
            tmb = 655 + (9.6 * peso) + (1.8 * altura * 100) - (4.7 * idade);
        } else {
            throw new IllegalArgumentException("Sexo deve ser 'M' ou 'F'");
        }

        double gastoDiario = tmb * fatorAtividade;
        return new ResultadoTMB(tmb, gastoDiario);
    }
}
