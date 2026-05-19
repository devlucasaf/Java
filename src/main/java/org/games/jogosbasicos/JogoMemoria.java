package org.games.jogosbasicos;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class JogoMemoria {

    private static final char[]         OPCOES = {'A', 'B', 'C', 'D'};
    private static final int            TEMPO_PAUSA_MS = 800;

    private static ArrayList<Character> sequencia = new ArrayList<>();
    private static Random               random = new Random();
    private static Scanner              scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("      JOGO DA MEMÓRIA - GENIUS     ");
        System.out.println("Memorize a sequência de letras!");
        System.out.println("Opções: A, B, C, D");
        System.out.println("Pressione ENTER para começar...");
        scanner.nextLine();

        int rodada = 1;
        boolean jogando = true;

        while (jogando) {
            System.out.println("\n--- RODADA " + rodada + " ---");

            adicionarElemento();
            mostrarSequencia();
            limparTela();

            ArrayList<Character> resposta = lerResposta();

            if (verificarResposta(resposta)) {
                System.out.println("Correto! Próxima rodada...");
                rodada++;
                esperar(1200);
            } else {
                System.out.println("Errado!");
                System.out.println("Fim de jogo!");
                System.out.println("Pontuação final: " + (rodada - 1));
                jogando = false;
            }
        }

        scanner.close();
    }

    // Gera e adiciona um novo elemento à sequência
    private static void adicionarElemento() {
        char novoElemento = gerarElemento();
        sequencia.add(novoElemento);
    }

    // Gera um elemento aleatório (A, B, C ou D)
    private static char gerarElemento() {
        int indice = random.nextInt(OPCOES.length);
        return OPCOES[indice];
    }

    // Mostra a sequência com pausas entre os elementos
    private static void mostrarSequencia() {
        System.out.println("Memorize a sequência:");
        for (char c : sequencia) {
            System.out.println("  " + c);
            esperar(TEMPO_PAUSA_MS);
        }
    }

    // Lê a resposta do jogador
    private static ArrayList<Character> lerResposta() {
        ArrayList<Character> resposta = new ArrayList<>();

        System.out.println("Digite a sequência (uma letra por vez):");

        for (int i = 0; i < sequencia.size(); i++) {
            System.out.print("Elemento " + (i + 1) + ": ");
            String entrada = scanner.nextLine().trim().toUpperCase();

            if (entrada.length() != 1) {
                resposta.add(' '); // força erro
            } else {
                resposta.add(entrada.charAt(0));
            }
        }

        return resposta;
    }

    // Verifica se a resposta está correta
    private static boolean verificarResposta(ArrayList<Character> resposta) {
        for (int i = 0; i < sequencia.size(); i++) {
            if (!sequencia.get(i).equals(resposta.get(i))) {
                return false;
            }
        }
        return true;
    }

    // Simula limpeza de tela
    private static void limparTela() {
        for (int i = 0; i < 40; i++) {
            System.out.println();
        }
    }

    // Pausa o jogo por alguns milissegundos
    private static void esperar(int tempoMs) {
        try {
            Thread.sleep(tempoMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
