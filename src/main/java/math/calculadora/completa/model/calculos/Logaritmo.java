package math.calculadora.completa.model.calculos;

public class Logaritmo {

    public static double logBase(double base, double valor) {
        if (base <= 0 || base == 1) {
            throw new IllegalArgumentException("Base deve ser > 0 e diferente de 1");
        }

        if (valor <= 0) {
            throw new IllegalArgumentException("Valor deve ser > 0");
        }
        return Math.log(valor) / Math.log(base);
    }
}