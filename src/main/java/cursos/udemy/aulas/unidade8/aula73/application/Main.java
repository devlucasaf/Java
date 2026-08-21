package cursos.udemy.aulas.unidade8.aula73.application;

import cursos.udemy.aulas.unidade8.aula73.entities.Triangulo;

import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        Triangulo x = new Triangulo();
        Triangulo y = new Triangulo();

        System.out.println("Digite as medidas do triângulo X:");
        x.a = scanner.nextDouble();
        x.b = scanner.nextDouble();
        x.c = scanner.nextDouble();

        System.out.println("Digite as medidas do triângulo Y:");
        y.a = scanner.nextDouble();
        y.b = scanner.nextDouble();
        y.c = scanner.nextDouble();

        double areaX = x.area();
        double areaY = y.area();

        System.out.printf("Área do triângulo X: %.4f%n", areaX);
        System.out.printf("Área do triângulo Y: %.4f%n", areaY);

        if (areaX > areaY) {
            System.out.println("Maior área: X");
        } else if (areaY > areaX) {
            System.out.println("Maior área: Y");
        } else {
            System.out.println("Os triângulos possuem áreas iguais.");
        }

        scanner.close();
    }

}

