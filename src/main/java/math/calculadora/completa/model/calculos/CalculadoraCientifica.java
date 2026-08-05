package math.calculadora.completa.model.calculos;

public class CalculadoraCientifica extends CalculadoraBasica {

    public double seno(double anguloGraus) {
        return Math.sin(Math.toRadians(anguloGraus));
    }

    public double cosseno(double anguloGraus) {
        return Math.cos(Math.toRadians(anguloGraus));
    }

    public double tangente(double anguloGraus) {
        if (anguloGraus % 90 == 0 && anguloGraus % 180 != 0) {
            throw new ArithmeticException("Tangente indefinida para este ângulo");
        }
        return Math.tan(Math.toRadians(anguloGraus));
    }

    public double log10(double val) {
        if (val <= 0) {
            throw new IllegalArgumentException("Logaritmo de número não positivo");
        }
        return Math.log10(val);
    }

    public double ln(double val) {
        if (val <= 0) {
            throw new IllegalArgumentException("Logaritmo natural de número não positivo");
        }
        return Math.log(val);
    }

    public double exp(double val) {
        return Math.exp(val);
    }

    public double raiz(double val) {
        if (val < 0) {
            throw new IllegalArgumentException("Raiz de número negativo");
        }
        return Math.sqrt(val);
    }

    public double potencia(double base, double expoente) {
        return Math.pow(base, expoente);
    }
}
