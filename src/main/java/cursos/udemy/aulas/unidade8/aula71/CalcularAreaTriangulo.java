package cursos.udemy.aulas.unidade8.aula71;

import java.util.Locale;
import java.util.Scanner;

public class CalcularAreaTriangulo {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        double xA;
        double xB;
        double xC;
        double yA;
        double yB;
        double yC;

        System.out.print("Digite o primeiro valor: ");

        xA = scanner.nextDouble();
        xB = scanner.nextDouble();
        xC = scanner.nextDouble();

        System.out.print("Digite o segundo valor: ");

        yA = scanner.nextDouble();
        yB = scanner.nextDouble();
        yC = scanner.nextDouble();

        double p = (xA + xB + xC) / 2.0;
        double areaX = Math.sqrt(p * (p - xA) * (p - xB) * (p * xC));

        p = (yA + yB + yC) / 2.0;
        double areaY = Math.sqrt(p * (p - yA) * (p - yB) * (p - yC));

        System.out.printf("Area do triangulo X: %.4f%n", areaX);
        System.out.printf("Area do triangulo Y: %.4f%n", areaY);

        if (areaX > areaY) {
            System.out.println("Maior área: X");
        } else {
            System.out.println("Maior área: Y");
        }
    }
}

