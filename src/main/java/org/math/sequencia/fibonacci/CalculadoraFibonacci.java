package org.math.sequencia.fibonacci;

import java.util.Scanner;

public class CalculadoraFibonacci {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        System.out.print("Digite a quantidade de termos da sequência de Fibonacci (N): ");
        int numeroTermos = entrada.nextInt();

        if (numeroTermos <= 0) {
            System.out.println("Por favor, digite um número inteiro positivo.");
        } else {
            System.out.println("\n--- Sequência de Fibonacci (Abordagem Iterativa) ---");
            exibirFibonacciIterativo(numeroTermos);

            System.out.println("\n--- Sequência de Fibonacci (Abordagem Recursiva) ---");
            exibirFibonacciRecursivo(numeroTermos);
        }

        entrada.close();
    }

    public static void exibirFibonacciIterativo(int quantidade) {
        long termoAtual = 0;
        long proximoTermo = 1;

        for (int contador = 1; contador <= quantidade; contador++) {
            System.out.print(termoAtual + " ");
            long soma = termoAtual + proximoTermo;
            termoAtual = proximoTermo;
            proximoTermo = soma;
        }
        System.out.println();
    }

    public static void exibirFibonacciRecursivo(int quantidade) {
        for (int posicao = 1; posicao <= quantidade; posicao++) {
            long valor = calcularFibonacciRecursivo(posicao - 1);
            System.out.print(valor + " ");
        }
        System.out.println(); // Quebra de linha ao final
    }

    public static long calcularFibonacciRecursivo(int indice) {
        if (indice == 0) {
            return 0;
        }

        if (indice == 1) {
            return 1;
        }
        return calcularFibonacciRecursivo(indice - 1) + calcularFibonacciRecursivo(indice - 2);
    }
}