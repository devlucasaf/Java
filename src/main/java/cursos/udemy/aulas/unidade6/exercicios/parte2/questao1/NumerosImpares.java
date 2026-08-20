package cursos.udemy.aulas.unidade6.exercicios.parte2.questao1;

import java.util.Scanner;

public class NumerosImpares {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int x = scanner.nextInt();

        for (int numero = 1; numero <= x; numero += 2) {
            System.out.println(numero);
        }

        scanner.close();
    }
}
