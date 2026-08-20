package cursos.udemy.aulas.unidade6.exercicios.parte2.questao7;

import java.util.Scanner;

public class QuadradoECubo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();

        for (int numero = 1; numero <= n; numero++) {
            int quadrado = numero * numero;
            int cubo = numero * numero * numero;

            System.out.println(numero + " " + quadrado + " " + cubo);
        }

        scanner.close();
    }
}
