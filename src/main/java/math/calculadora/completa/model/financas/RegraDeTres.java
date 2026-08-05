package math.calculadora.completa.model.financas;

public class RegraDeTres {

    public static double calcular(double a, double b, double c, boolean direta) {
        if (a == 0) {
            throw new IllegalArgumentException("A não pode ser zero");
        }

        if (!direta && c == 0) {
            throw new IllegalArgumentException("C não pode ser zero na regra de três inversa");
        }

        if (direta) {
            return (b * c) / a;
        } else {
            return (a * b) / c;
        }
    }
}
