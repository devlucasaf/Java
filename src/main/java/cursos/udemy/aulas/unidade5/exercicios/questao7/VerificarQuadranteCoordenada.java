package cursos.udemy.aulas.unidade5.exercicios.questao7;

import java.util.Locale;
import java.util.Scanner;

public class VerificarQuadranteCoordenada {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        double x = scanner.nextDouble();
        double y = scanner.nextDouble();

        if (x == 0.0 && y == 0.0) {
            System.out.println("Origem");
        } else if (x == 0.0) {
            System.out.println("Eixo Y");
        } else if (y == 0.0) {
            System.out.println("Eixo X");
        } else if (x > 0.0 && y > 0.0) {
            System.out.println("Quadrante 1");
        } else if (x < 0.0 && y > 0.0) {
            System.out.println("Quadrante 2");
        } else if (x < 0.0 && y < 0.0) {
            System.out.println("Quadrante 3");
        } else {
            System.out.println("Quadrante 4");
        }

        scanner.close();

    }
}




