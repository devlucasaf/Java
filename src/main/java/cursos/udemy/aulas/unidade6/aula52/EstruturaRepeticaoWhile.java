package udemy.aulas.unidade6.aula52;

import java.util.Scanner;

public class EstruturaRepeticaoWhile {
    private void exemplo1While() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite um número (0 para sair): ");
        int numero = scanner.nextInt();

        while (numero != 0) {
            System.out.println("Você digitou: " + numero);
            System.out.println("Digite outro número (0 para sair): ");
            numero = scanner.nextInt();
        }
        scanner.close();
    }

    private void exemplo2While() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite um número (0 para sair): ");
        int numero = scanner.nextInt();

        int soma = 0;

        while (numero != 0) {
            soma += numero;
            System.out.println("Você digitou: " + numero);
            System.out.println("Digite outro número (0 para sair): ");
            numero = scanner.nextInt();
        }
        System.out.println("A soma dos números digitados é: " + soma);
        scanner.close();
    }

    public static void main(String[] args) {
        EstruturaRepeticaoWhile repeticaoWhile = new EstruturaRepeticaoWhile();
        repeticaoWhile.exemplo1While();
        repeticaoWhile.exemplo2While();
    }
}
