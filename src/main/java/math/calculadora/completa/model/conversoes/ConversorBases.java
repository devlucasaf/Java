package math.calculadora.completa.model.conversoes;

import math.calculadora.completa.util.Validador;

public class ConversorBases {

    public static String converter(String valor, int baseDe, int basePara) {
        if (!Validador.isValidoParaBase(valor, baseDe)) {
            throw new IllegalArgumentException("Valor inválido para a base " + baseDe);
        }
        long decimal = Long.parseLong(valor, baseDe);
        return Long.toString(decimal, basePara).toUpperCase();
    }
}
