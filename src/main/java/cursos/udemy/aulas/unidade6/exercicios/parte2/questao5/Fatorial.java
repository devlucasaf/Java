package cursos.udemy.aulas.unidade6.exercicios.parte2.questao5;

import java.util.Scanner;

public class Fatorial {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        long fatorial = 1;

        for (int numero = 1; numero <= n; numero++) {
            fatorial *= numero;
        }

        System.out.println(fatorial);

        scanner.close();
    }
}
