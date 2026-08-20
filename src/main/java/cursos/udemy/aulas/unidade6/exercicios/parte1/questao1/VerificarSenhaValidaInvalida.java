package cursos.udemy.aulas.unidade6.exercicios.parte1.questao1;

import java.util.Scanner;

public class VerificarSenhaValidaInvalida {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int senha = scanner.nextInt();

        while (senha != 2002) {
            System.out.println("Senha Invalida");
            senha = scanner.nextInt();
        }

        System.out.println("Acesso Permitido");

        scanner.close();
    }
}
