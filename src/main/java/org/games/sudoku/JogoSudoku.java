package org.games.sudoku;

import java.util.Scanner;

public class JogoSudoku {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n+=+=+= SUDOKU +=+=+=");
            System.out.println("Escolha a dificuldade:");
            System.out.println("1 - Fácil");
            System.out.println("2 - Médio");
            System.out.println("3 - Difícil");
            System.out.println("0 - Sair");

            int escolha = sc.nextInt();
            if (escolha == 0) {
                break;
            }

            Tabuleiro tabuleiro = new Tabuleiro(escolha);

            while (true) {
                tabuleiro.exibir();

                if (tabuleiro.completo()) {
                    System.out.println("🎉 Parabéns! Você venceu!");
                    break;
                }

                System.out.println("Digite linha coluna número (0 para sair):");
                int linha = sc.nextInt();
                if (linha == 0) {
                    break;
                }

                int coluna = sc.nextInt();
                int numero = sc.nextInt();

                if (linha < 1 || linha > 9 || coluna < 1 || coluna > 9 || numero < 1 || numero > 9) {
                    System.out.println("Entrada inválida.");
                    continue;
                }

                if (!tabuleiro.inserir(linha - 1, coluna - 1, numero)) {
                    System.out.println("Jogada inválida!");
                }
            }
        }

        System.out.println("Obrigado por jogar!");
        sc.close();
    }
}
