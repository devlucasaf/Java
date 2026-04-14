package org.games.megasena;

import java.util.List;
import java.util.Scanner;

// Classe principal que executa o programa
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Scanner para entrada de dados
        MegaSena ms = new MegaSena(); // Instância da classe MegaSena

        // Pergunta quantos jogos o usuário deseja fazer
        System.out.print("Digite quantos jogos de Mega Sena quer fazer: ");
        int numJogos = Integer.parseInt(scanner.nextLine());

        // Loop para gerar a quantidade de jogos solicitada
        for (int i = 0; i < numJogos; i++) {
            System.out.println("\nNovo jogo!");
            System.out.print("Digite a quantidade de dezenas (6-20): ");
            int qtd = Integer.parseInt(scanner.nextLine());

            try {
                // Gera o jogo e mostra os números sorteados
                List<Integer> jogo = ms.gerarJogo(qtd);
                System.out.printf("Seu jogo está pronto! Números: %s\n", jogo);
                System.out.printf("Custo da aposta: R$%.2f\n", ms.custoJogoMegaSena(qtd));

                // Grava o jogo no arquivo
                ms.gravarJogos();
            }

            catch (IllegalArgumentException e) {
                // Caso o usuário informe quantidade inválida de dezenas
                System.out.println("Erro: " + e.getMessage());
            }
        }
        scanner.close(); // Fecha o scanner
    }
}

