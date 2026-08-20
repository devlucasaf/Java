package cursos.udemy.aulas.unidade6.exercicios.parte2.questao6;

import java.util.Scanner;

public class CalculadoraDeDivisores {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();

        for (int numero = 1; numero <= n; numero++) {
            if (n % numero == 0) {
                System.out.println(numero);
            }
        }

        scanner.close();
    }
}
