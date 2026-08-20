package udemy.aulas.unidade4.exercicios.questao3;

import java.util.Scanner;

public class DiferencaProdutos {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int a;
        int b;
        int c;
        int d;
        int diferenca;

        a = scanner.nextInt();
        b = scanner.nextInt();
        c = scanner.nextInt();
        d = scanner.nextInt();

        diferenca = a * b - c * d;

        System.out.println("Diferença = " + diferenca);

        scanner.close();
    }
}
