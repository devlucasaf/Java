package cursos.udemy.aulas.unidade6.exercicios.parte2.quetao3;

import java.util.Locale;
import java.util.Scanner;

public class MediaPonderada {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        int quantidadeCasos = scanner.nextInt();

        for (int contador = 0; contador < quantidadeCasos; contador++) {
            double primeiroValor = scanner.nextDouble();
            double segundoValor = scanner.nextDouble();
            double terceiroValor = scanner.nextDouble();

            double media = (
                    primeiroValor * 2
                            + segundoValor * 3
                            + terceiroValor * 5
            ) / 10.0;

            System.out.printf("%.1f%n", media);
        }

        scanner.close();
    }
}
