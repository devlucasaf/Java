package cursos.udemy.aulas.unidade6.exercicios.parte2.questao4;

import java.util.Locale;
import java.util.Scanner;

public class DivisaoDePares {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        int quantidade = scanner.nextInt();

        for (int contador = 0; contador < quantidade; contador++) {
            double numerador = scanner.nextDouble();
            double denominador = scanner.nextDouble();

            if (denominador == 0) {
                System.out.println("divisao impossivel");
            } else {
                double resultado = numerador / denominador;
                System.out.printf("%.1f%n", resultado);
            }
        }

        scanner.close();
    }
}
