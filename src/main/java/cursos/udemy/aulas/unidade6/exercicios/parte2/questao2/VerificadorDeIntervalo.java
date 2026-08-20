package cursos.udemy.aulas.unidade6.exercicios.parte2.questao2;

import java.util.Scanner;

public class VerificadorDeIntervalo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int quantidade = scanner.nextInt();
        int dentro = 0;
        int fora = 0;

        for (int contador = 0; contador < quantidade; contador++) {
            int valor = scanner.nextInt();

            if (valor >= 10 && valor <= 20) {
                dentro++;
            } else {
                fora++;
            }
        }

        System.out.println(dentro + " in");
        System.out.println(fora + " out");

        scanner.close();
    }
}
