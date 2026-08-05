package math.calculadora.completa.model.calculos;

public class Bhaskara {

    public static Raizes calcular(double a, double b, double c) {
        if (a == 0) {
            throw new IllegalArgumentException("a não pode ser zero");
        }

        double delta = b * b - 4 * a * c;
        if (delta < 0) {
            return new Raizes(0, 0, true);
        }
        double sqrt = Math.sqrt(delta);
        double x1 = (-b + sqrt) / (2 * a);
        double x2 = (-b - sqrt) / (2 * a);
        return new Raizes(x1, x2, false);
    }
}
