package math.probabilidade.montecarlo;

public class Main {

    public static void main(String[] args) {
        MonteCarlo monteCarlo = new MonteCarlo(42);

        System.out.println("=== ESTIMATIVA DE PI ===");
        for (int n : new int[]{100, 10_000, 1_000_000, 10_000_000}) {
            double pi = monteCarlo.estimarPi(n);
            System.out.printf("n=%,10d  pi ~ %.6f  (erro %.6f)%n", n, pi, Math.abs(pi - Math.PI));
        }

        System.out.println("\n=== INTEGRAL DE x^2 DE 0 A 1 (analitico = 1/3 = 0.3333) ===");
        double integral = monteCarlo.integrar(x -> x * x, 0, 1, 1_000_000);
        System.out.printf("Monte Carlo: %.6f%n", integral);

        System.out.println("\n=== INTEGRAL DE sin(x) DE 0 A PI (analitico = 2) ===");
        double sin = monteCarlo.integrar(Math::sin, 0, Math.PI, 1_000_000);
        System.out.printf("Monte Carlo: %.6f%n", sin);

        System.out.println("\n=== PROBABILIDADE DE SOMAR 7 COM 2 DADOS (analitico = 6/36 = 16.67%) ===");
        double p = monteCarlo.probabilidadeSomaDados(2, 7, 1_000_000);
        System.out.printf("Monte Carlo: %.2f%%%n", p * 100);

        System.out.println("\n=== PROBABILIDADE DE SOMAR 18 COM 3 DADOS (analitico = 1/216 = 0.46%) ===");
        double p2 = monteCarlo.probabilidadeSomaDados(3, 18, 10_000_000);
        System.out.printf("Monte Carlo: %.2f%%%n", p2 * 100);
    }
}

