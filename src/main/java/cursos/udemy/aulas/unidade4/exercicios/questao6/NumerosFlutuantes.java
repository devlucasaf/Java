package udemy.aulas.unidade4.exercicios.questao6;

import java.util.Locale;
import java.util.Scanner;

public class NumerosFlutuantes {
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        double a;
        double b;
        double c;
        double triangulo;
        double circulo;
        double trapezio;
        double quadrado;
        double retangulo;

        a = scanner.nextDouble();
        b = scanner.nextDouble();
        c = scanner.nextDouble();

        triangulo = a * c / 2.0;
        circulo = 3.14159 * c * c;
        trapezio = (a + b) / 2.0 * c;
        quadrado = b * b;
        retangulo = a * b;

        System.out.printf("Triângulo: %.3f%n", triangulo);
        System.out.printf("Círculo: %.3f%n", circulo);
        System.out.printf("Trapézio: %.3f%n", trapezio);
        System.out.printf("Quadrado: %.3f%n", quadrado);
        System.out.printf("Retângulo: %.3f%n", retangulo);

        scanner.close();
    }
}
