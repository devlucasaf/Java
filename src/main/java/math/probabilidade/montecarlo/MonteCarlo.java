package math.probabilidade.montecarlo;

import java.util.Random;
import java.util.function.DoubleUnaryOperator;

public class MonteCarlo {

    private final Random random;

    public MonteCarlo() {
        this.random = new Random();
    }

    public MonteCarlo(long seed) {
        this.random = new Random(seed);
    }

    public double estimarPi(int amostras) {
        int dentro = 0;
        for (int i = 0; i < amostras; i++) {
            double x = random.nextDouble();
            double y = random.nextDouble();
            if (x * x + y * y <= 1.0) {
                dentro++;
            }
        }
        return 4.0 * dentro / amostras;
    }

    public double integrar(DoubleUnaryOperator f, double a, double b, int amostras) {
        double soma = 0;
        for (int i = 0; i < amostras; i++) {
            double x = a + random.nextDouble() * (b - a);
            soma += f.applyAsDouble(x);
        }
        return (b - a) * soma / amostras;
    }

    public double probabilidadeSomaDados(int nDados, int somaAlvo, int amostras) {
        int sucessos = 0;
        for (int i = 0; i < amostras; i++) {
            int total = 0;
            for (int d = 0; d < nDados; d++) {
                total += random.nextInt(6) + 1;
            }

            if (total == somaAlvo) {
                sucessos++;
            }
        }
        return (double) sucessos / amostras;
    }
}

