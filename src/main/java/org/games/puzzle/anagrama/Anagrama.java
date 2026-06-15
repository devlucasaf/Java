package org.games.puzzle.anagrama;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Anagrama {

    private final List<String> banco = new ArrayList<>(Arrays.asList(
            "JAVA", "CODIGO", "COMPUTADOR", "TECLADO", "MONITOR",
            "PROGRAMACAO", "ALGORITMO", "VARIAVEL", "INTELIGENCIA",
            "DESENVOLVIMENTO", "BIBLIOTECA", "FUNCAO", "OBJETO", "CLASSE"));

    private final Random sorteador = new Random();
    private final Scanner entrada = new Scanner(System.in);
    private int pontos;
    private int rodadas;

    public static void main(String[] args) {
        new Anagrama().iniciar();
    }

    public void iniciar() {
        System.out.println("=== ANAGRAMA ===");
        System.out.println("Reorganize as letras embaralhadas e digite a palavra correta.");
        System.out.println("Digite 'pular' para passar ou 'sair' para encerrar.");

        Collections.shuffle(banco);
        for (String palavra : banco) {
            rodadas++;
            String embaralhada = embaralhar(palavra);
            System.out.println("\nRodada " + rodadas + " (" + palavra.length() + " letras)");
            System.out.println("Letras: " + embaralhada);
            int tentativas = 3;
            boolean acertou = false;
            while (tentativas > 0) {
                System.out.print("Sua resposta (" + tentativas + " tentativas): ");
                String resposta = entrada.nextLine().trim().toUpperCase();
                if (resposta.equals("SAIR")) {
                    encerrar();
                    return;
                }

                if (resposta.equals("PULAR")) {
                    break;
                }

                if (resposta.equals(palavra)) {
                    int ganho = palavra.length() * tentativas;
                    pontos += ganho;
                    System.out.println("Correto! +" + ganho + " pontos.");
                    acertou = true;
                    break;
                }
                tentativas--;
                if (tentativas > 0) {
                    System.out.println("Errado. Tente novamente.");
                }
            }

            if (!acertou) {
                System.out.println("A palavra era: " + palavra);
            }
        }
        encerrar();
    }

    private void encerrar() {
        System.out.println("\nFim do jogo!");
        System.out.println("Rodadas: " + rodadas);
        System.out.println("Pontuacao final: " + pontos);
    }

    private String embaralhar(String palavra) {
        char[] letras = palavra.toCharArray();
        for (int i = letras.length - 1; i > 0; i--) {
            int j = sorteador.nextInt(i + 1);
            char temp = letras[i];
            letras[i] = letras[j];
            letras[j] = temp;
        }
        String resultado = new String(letras);
        if (resultado.equals(palavra)) {
            return embaralhar(palavra);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < resultado.length(); i++) {
            sb.append(resultado.charAt(i));
            if (i < resultado.length() - 1) {
                sb.append(' ');
            }
        }
        return sb.toString();
    }
}

