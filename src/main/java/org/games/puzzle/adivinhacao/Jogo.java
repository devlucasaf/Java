package org.games.puzzle.adivinhacao;

import java.util.Random;
import java.util.Scanner;

import java.util.*;

public class Jogo {

    private Scanner scanner = new Scanner(System.in);
    private Random  random = new Random();

    public void iniciar() {
        int opcao;

        do {
            mostrarMenuTerminal();
            opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> modoJogadorAdivinha();
                case 2 -> modoIAAdivinha();
                case 0 -> System.out.println("Obrigado por jogar!");
                default -> System.out.println("Opção inválida.");
            }
        }

        while (opcao != 0);
    }

    private void mostrarMenuTerminal() {
        System.out.println("\n>>>>> JOGO DE ADIVINHAÇÃO INTELIGENTE <<<<<");
        System.out.println("1 - Jogador adivinha");
        System.out.println("2 - IA adivinha");
        System.out.println("0 - Sair");
    }

    // --- MODO JOGADOR ADIVINHA ---
    private void modoJogadorAdivinha() {
        int limite = escolherDificuldade();
        int numeroSecreto = random.nextInt(limite) + 1;
        int tentativas = 0;
        int maxTentativas = 10;

        List<Integer> historico = new ArrayList<>();

        System.out.println("\n Adivinhe o número entre 1 e " + limite);
        System.out.println("Você tem no máximo " + maxTentativas + " tentativas.");

        while (tentativas < maxTentativas) {
            int palpite = lerInteiro("Seu palpite: ");
            historico.add(palpite);
            tentativas++;

            if (palpite == numeroSecreto) {
                System.out.println("Parabéns! Você acertou!");
                System.out.println("Tentativas: " + tentativas);
                System.out.println("Histórico: " + historico);
                return;
            } else if (palpite < numeroSecreto) {
                System.out.println("O número é MAIOR.");
            } else {
                System.out.println("O número é MENOR.");
            }
        }

        System.out.println("Você perdeu! O número era: " + numeroSecreto);
        System.out.println("Histórico: " + historico);
    }

    private void modoIAAdivinha() {
        int limite = escolherDificuldade();
        IA ia = new IA(1, limite);
        int tentativas = 0;

        System.out.println("\nPense em um número entre 1 e " + limite);
        System.out.println("Responda com:");
        System.out.println("(M) - Meu número é MENOR");
        System.out.println("(m) - Meu número é MAIOR");
        System.out.println("(C) - Correto");

        while (true) {
            int palpite = ia.gerarPalpite();
            tentativas++;

            System.out.print("A IA chuta: " + palpite + " → Resposta: ");
            String resposta = scanner.nextLine().trim().toUpperCase();

            if (resposta.equals("C")) {
                System.out.println("A IA acertou em " + tentativas + " tentativas!");
                break;
            } else if (resposta.equals("M")) {
                ia.diminuirLimite(palpite);
            } else if (resposta.equals("m")) {
                ia.aumentarLimite(palpite);
            } else {
                System.out.println("Entrada inválida. Use M, m ou C.");
                tentativas--;
            }
        }
    }

    private int escolherDificuldade() {
        System.out.println("\nEscolha a dificuldade:");
        System.out.println("1 - Fácil (1 a 50)");
        System.out.println("2 - Médio (1 a 100)");
        System.out.println("3 - Difícil (1 a 1000)");

        int opcao = lerInteiro("Opção: ");

        return switch (opcao) {
            case 1 -> 50;
            case 3 -> 1000;
            default -> 100;
        };
    }

    private int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números.");
            }
        }
    }
}
