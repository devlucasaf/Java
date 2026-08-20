package cursos.udemy.aulas.unidade5.exercicios.questao5;

import java.util.Scanner;

public class LerItemQuantidadeItem {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int codigo = scanner.nextInt();
        int quantidade = scanner.nextInt();

        double total;
        if (codigo == 1) {
            total = quantidade * 4.0;
        } else if (codigo == 2) {
            total = quantidade * 4.5;
        } else if (codigo == 3) {
            total = quantidade * 5.0;
        } else if (codigo == 4) {
            total = quantidade * 2.0;
        } else {
            total = quantidade * 1.5;
        }

        System.out.printf("Total: R$ %.2f%n", total);

        scanner.close();
    }
}
