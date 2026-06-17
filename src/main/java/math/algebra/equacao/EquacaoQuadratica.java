package math.algebra.equacao;

import java.util.Scanner;

public class EquacaoQuadratica {

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        System.out.print("Digite o valor de a: ");
        double a = entrada.nextDouble();
        while (a == 0) {
            System.out.print("Coeficiente 'a' inválido para equação quadrática. Digite novamente: ");
            a = entrada.nextDouble();
        }

        System.out.print("Digite o valor de b: ");
        double b = entrada.nextDouble();

        System.out.print("Digite o valor de c: ");
        double c = entrada.nextDouble();

        double delta = calcularDelta(a, b, c);
        System.out.println("Discriminante (Δ) = " + delta);

        double[] raizes = calcularRaizes(a, b, c, delta);

        exibirResultado(delta, raizes);

        entrada.close();
    }

    public static double calcularDelta(double a, double b, double c) {
        return b * b - 4 * a * c;
    }

    public static double[] calcularRaizes(double a, double b, double c, double delta) {
        double[] raizes = new double[4];

        if (delta >= 0) {
            double raiz1 = (-b + Math.sqrt(delta)) / (2 * a);
            double raiz2 = (-b - Math.sqrt(delta)) / (2 * a);
            raizes[0] = raiz1; raizes[1] = 0.0;
            raizes[2] = raiz2; raizes[3] = 0.0;
        } else {
            double parteReal = -b / (2 * a);
            double parteImaginaria = Math.sqrt(-delta) / (2 * a);
            raizes[0] = parteReal; raizes[1] = parteImaginaria;
            raizes[2] = parteReal; raizes[3] = -parteImaginaria;
        }

        return raizes;
    }

    public static void exibirResultado(double delta, double[] raizes) {
        if (delta > 0) {
            System.out.println("A equação possui duas raízes reais e distintas:");
            System.out.println("x₁ = " + raizes[0]);
            System.out.println("x₂ = " + raizes[2]);
        } else if (delta == 0) {
            System.out.println("A equação possui uma raiz real (dupla):");
            System.out.println("x = " + raizes[0]);
        } else {
            System.out.println("A equação possui duas raízes complexas conjugadas:");
            System.out.printf("x₁ = %.2f + %.2fi%n", raizes[0], raizes[1]);
            System.out.printf("x₂ = %.2f - %.2fi%n", raizes[2], -raizes[3]);
        }
    }
}