package math.calculadora.completa.model.calculos;

import java.math.BigInteger;

public class Fatorial {

    public static BigInteger calcular(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n deve ser ≥ 0");
        }
        BigInteger fat = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            fat = fat.multiply(BigInteger.valueOf(i));
        }
        return fat;
    }
}