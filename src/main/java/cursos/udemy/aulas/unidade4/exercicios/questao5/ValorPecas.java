package udemy.aulas.unidade4.exercicios.questao5;

import java.util.Locale;
import java.util.Scanner;

public class ValorPecas {
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);

        int codigo1;
        int codigo2;
        int qntd1;
        int qntd2;
        double preco1;
        double preco2;
        double total;

        codigo1 = scanner.nextInt();
        qntd1 = scanner.nextInt();
        preco1 = scanner.nextDouble();

        codigo2 = scanner.nextInt();
        qntd2 = scanner.nextInt();
        preco2 = scanner.nextDouble();

        total = preco1 * qntd1 + preco2 * qntd2;

        System.out.printf("Valor a pagar: R$ %.2f%n", total);

        scanner.close();
    }
}
