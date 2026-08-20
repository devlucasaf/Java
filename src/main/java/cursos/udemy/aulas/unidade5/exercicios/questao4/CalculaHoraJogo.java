package cursos.udemy.aulas.unidade5.exercicios.questao4;

import java.util.Scanner;

public class CalculaHoraJogo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int inicio = scanner.nextInt();
        int fim = scanner.nextInt();

        int duracao;

        if (inicio < fim) {
            duracao = fim - inicio;
        } else {
            duracao = (24 - inicio) + fim;
        }

        System.out.println("O jogo durou " + duracao + " hora(s)");

        scanner.close();
    }

}
