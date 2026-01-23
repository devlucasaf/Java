package org.example;

import java.util.Scanner;

public class VerificadorPrimo {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in); // Cria objeto Scanner para ler entrada do usuário

        System.out.print("Digite um número inteiro: ");
        int numero = scanner.nextInt(); // Lê o número digitado

        // Verifica se o número é primo chamando o método ehPrimo()
        if (ehPrimo(numero)) {
            System.out.println(numero + " é um número primo.");
        } else {
            System.out.println(numero + " não é um número primo.");
        }

        scanner.close(); // Fecha o Scanner
    }

    // Método que verifica se um número é primo
    public static boolean ehPrimo(int num) {
        // Números menores ou iguais a 1 não são primos
        if (num <= 1) {
            return false;
        }

        // Loop para verificar divisores até a raiz quadrada do número
        // Isso otimiza a verificação, pois não é necessário testar todos os números até "num"
        for (int i = 2; i <= Math.sqrt(num); i++) {
            // Se o número for divisível por algum valor de i, não é primo
            if (num % i == 0) {
                return false;
            }
        }

        // Se não encontrou nenhum divisor, o número é primo
        return true;
    }
}
