package cursos.udemy.aulas.unidade6.aula57;

import java.util.Scanner;

public class EstruturaRepeticaoFor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int quantidade = scanner.nextInt();
        int soma = 0;

        for (int contador = 0; contador < quantidade; contador++) {
            int numero = scanner.nextInt();
            soma += numero;
        }

        System.out.println(soma);

        scanner.close();
    }
}
