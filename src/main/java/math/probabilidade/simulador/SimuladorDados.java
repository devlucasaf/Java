package math.probabilidade;

import java.util.Random;
import java.util.Scanner;

public class SimuladorDados {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        Random gerador = new Random();

        System.out.print("Digite a soma que deseja observar (entre 2 e 12): ");
        int somaAlvo = entrada.nextInt();

        if (somaAlvo < 2 || somaAlvo > 12) {
            System.out.println("Soma inválida! A soma deve estar entre 2 e 12.");
            entrada.close();
            return;
        }

        System.out.print("Digite o número de simulações (ex.: 1000000): ");
        long numeroSimulacoes = entrada.nextLong();

        if (numeroSimulacoes <= 0) {
            System.out.println("Número de simulações deve ser positivo.");
            entrada.close();
            return;
        }

        long contagemSucessos = 0;

        for (long i = 0; i < numeroSimulacoes; i++) {
            int dado1 = gerador.nextInt(6) + 1; // faces de 1 a 6
            int dado2 = gerador.nextInt(6) + 1;
            int soma = dado1 + dado2;

            if (soma == somaAlvo) {
                contagemSucessos++;
            }
        }

        double probabilidadeEmpirica = (double) contagemSucessos / numeroSimulacoes;

        int combinacoesFavoraveis = calcularCombinacoesFavoraveis(somaAlvo);
        double probabilidadeTeorica = (double) combinacoesFavoraveis / 36.0;

        System.out.println("\n--- RESULTADOS DA SIMULAÇÃO ---");
        System.out.printf("Soma alvo: %d%n", somaAlvo);
        System.out.printf("Número de simulações: %d%n", numeroSimulacoes);
        System.out.printf("Sucessos observados: %d%n", contagemSucessos);
        System.out.printf("Probabilidade empírica: %.6f (%.2f%%)%n",
                probabilidadeEmpirica, probabilidadeEmpirica * 100);
        System.out.printf("Probabilidade teórica: %.6f (%.2f%%)%n",
                probabilidadeTeorica, probabilidadeTeorica * 100);
        System.out.printf("Diferença: %.6f%n", Math.abs(probabilidadeEmpirica - probabilidadeTeorica));

        entrada.close();
    }

    private static int calcularCombinacoesFavoraveis(int soma) {
        int contagem = 0;
        for (int d1 = 1; d1 <= 6; d1++) {
            for (int d2 = 1; d2 <= 6; d2++) {
                if (d1 + d2 == soma) {
                    contagem++;
                }
            }
        }
        return contagem;
    }
}