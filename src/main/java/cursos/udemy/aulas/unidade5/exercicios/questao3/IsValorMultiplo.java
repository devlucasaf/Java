package cursos.udemy.aulas.unidade5.exercicios.questao3;

import java.util.Scanner;

public class IsValorMultiplo {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int a = scanner.nextInt();
        int b = scanner.nextInt();

        if (a % b == 0 || b % a == 0) {
            System.out.println("São múltiplos");
        }
        else {
            System.out.println("Não são múltiplos");
        }

        scanner.close();
    }
}
