package org.games.bingo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bingo {

    private static final int        TAM = 5;

    private static int[][]          cartela = new int[TAM][TAM];
    private static boolean[][]      marcacoes = new boolean[TAM][TAM];

    private static List<Integer>    numerosSorteados = new ArrayList<>();
    private static Random           random = new Random();

    public static void main(String[] args) {

        gerarCartela();

        System.out.println("BINGO");
        imprimirCartela();

        while (true) {
            int numero = sortearNumero();
            System.out.println("\nNúmero sorteado: " + numero);

            marcarNumero(numero);
            imprimirCartela();

            if (verificarVitoria()) {
                System.out.println("\nBINGO! VOCÊ VENCEU!");
                break;
            }

            try {
                Thread.sleep(1000); // pequena pausa entre sorteios
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Gera cartela 5x5 com números únicos
    private static void gerarCartela() {
        List<Integer> numerosUsados = new ArrayList<>();

        for (int i = 0; i < TAM; i++) {
            for (int j = 0; j < TAM; j++) {

                // Centro FREE
                if (i == 2 && j == 2) {
                    cartela[i][j] = 0;
                    marcacoes[i][j] = true;
                    continue;
                }

                int numero;
                do {
                    numero = random.nextInt(75) + 1;
                } while (numerosUsados.contains(numero));

                numerosUsados.add(numero);
                cartela[i][j] = numero;
            }
        }
    }

    // Sorteia número de 1 a 75 sem repetição
    private static int sortearNumero() {
        int numero;
        do {
            numero = random.nextInt(75) + 1;
        } while (numerosSorteados.contains(numero));

        numerosSorteados.add(numero);
        return numero;
    }

    // Marca número sorteado na cartela
    private static void marcarNumero(int numero) {
        for (int i = 0; i < TAM; i++) {
            for (int j = 0; j < TAM; j++) {
                if (cartela[i][j] == numero) {
                    marcacoes[i][j] = true;
                }
            }
        }
    }

    // Imprime cartela no terminal
    private static void imprimirCartela() {
        System.out.println("-----------------------------");
        for (int i = 0; i < TAM; i++) {
            for (int j = 0; j < TAM; j++) {
                if (i == 2 && j == 2) {
                    System.out.print(" FREE ");
                } else if (marcacoes[i][j]) {
                    System.out.printf("[%2d] ", cartela[i][j]);
                } else {
                    System.out.printf(" %2d  ", cartela[i][j]);
                }
            }
            System.out.println();
        }
        System.out.println("-----------------------------");
    }

    // Verifica vitória (linhas, colunas e diagonais)
    private static boolean verificarVitoria() {

        // Linhas
        for (int i = 0; i < TAM; i++) {
            boolean linhaCompleta = true;
            for (int j = 0; j < TAM; j++) {
                if (!marcacoes[i][j]) {
                    linhaCompleta = false;
                    break;
                }
            }
            if (linhaCompleta) {
                return true;
            }
        }

        // Colunas
        for (int j = 0; j < TAM; j++) {
            boolean colunaCompleta = true;
            for (int i = 0; i < TAM; i++) {
                if (!marcacoes[i][j]) {
                    colunaCompleta = false;
                    break;
                }
            }
            if (colunaCompleta) {
                return true;
            }
        }

        // Diagonal principal
        boolean diagonal1 = true;
        for (int i = 0; i < TAM; i++) {
            if (!marcacoes[i][i]) {
                diagonal1 = false;
                break;
            }
        }
        if (diagonal1) {
            return true;
        }

        // Diagonal secundária
        boolean diagonal2 = true;
        for (int i = 0; i < TAM; i++) {
            if (!marcacoes[i][TAM - 1 - i]) {
                diagonal2 = false;
                break;
            }
        }
        return diagonal2;
    }
}