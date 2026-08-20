package cursos.udemy.aulas.unidade4.exercicios.questao2;

import java.util.Locale;
import java.util.Scanner;

public class RaioCirculo {
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        double r;
        double a;
        double pi = 3.14159;

        r = scanner.nextDouble();

        a = pi * r * r;

        System.out.printf("A=%.4f%n", a);

        scanner.close();
    }
}
