package cursos.udemy.aulas.unidade5.aula42;

import java.util.Scanner;

public class CondicoesIfElse {
    private void exemplo1IfElse() {
        int x = 5;
        System.out.println("Bom dia");

        if (x > 5) {
            System.out.println("Boa tarde");
        }

        System.out.println("Boa noite");
    }

    private void exemplo2IfElse() {
        int x = 5;
        System.out.println("Bom dia");

        if (x < 5) {
            System.out.println("Boa tarde");
        }

        System.out.println("Boa noite");
    }

    private void exemplo3IfElse() {
        Scanner scanner = new Scanner(System.in);
        int hora;

        System.out.print("Quantas horas? ");
        hora = scanner.nextInt();

        if (hora < 12) {
            System.out.println("Bom dia");
        }  else {
            System.out.println("Boa tarde");
        }
    }

    private void exemplo4IfElse() {
        Scanner scanner = new Scanner(System.in);
        int hora;

        System.out.print("Quantas horas? ");
        hora = scanner.nextInt();

        if (hora < 12) {
            System.out.println("Bom dia");
        } else if (hora < 18) {
            System.out.println("Boa tarde");
        } else {
            System.out.println("Boa noite");
        }
    }

    public static void main(String[] args) {
        CondicoesIfElse condicoesIfElse = new CondicoesIfElse();
        System.out.println("Exemplo 1:");
        condicoesIfElse.exemplo1IfElse();
        System.out.println("Exemplo 2:");
        condicoesIfElse.exemplo2IfElse();
        System.out.println("Exemplo 3:");
        condicoesIfElse.exemplo3IfElse();
    }
}
