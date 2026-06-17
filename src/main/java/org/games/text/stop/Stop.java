package org.games.text.stop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Stop {

    private static final List<String> CATEGORIAS = Arrays.asList(
            "Nome", "Cidade", "Pais", "Animal", "Comida",
            "Cor", "Profissao", "Objeto", "Marca", "Filme"
    );

    private static final int TEMPO_LIMITE_SEGUNDOS = 60;
    private static final String LETRAS_VALIDAS = "ABCDEFGHIJLMNOPRSTUV";

    private final Scanner entrada = new Scanner(System.in);
    private final Random aleatorio = new Random();

    public static void main(String[] args) {
        new Stop().iniciar();
    }

    public void iniciar() {
        System.out.println("=== STOP / ADEDONHA ===");
        System.out.print("Quantos jogadores? ");
        int numJogadores = lerInteiroPositivo();

        List<String> nomes = new ArrayList<>();
        for (int i = 1; i <= numJogadores; i++) {
            System.out.printf("Nome do jogador %d: ", i);
            nomes.add(entrada.nextLine().trim());
        }

        int[] pontuacao = new int[numJogadores];
        System.out.print("Quantas rodadas? ");
        int rodadas = lerInteiroPositivo();

        for (int r = 1; r <= rodadas; r++) {
            char letra = LETRAS_VALIDAS.charAt(aleatorio.nextInt(LETRAS_VALIDAS.length()));
            System.out.printf("%n----- RODADA %d -----%n", r);
            System.out.printf("LETRA SORTEADA: %c%n", letra);
            System.out.printf("Voces tem %d segundos. Pressione ENTER quando comecar.%n", TEMPO_LIMITE_SEGUNDOS);
            entrada.nextLine();

            long inicio = System.currentTimeMillis();
            List<String[]> respostas = new ArrayList<>();

            for (int j = 0; j < numJogadores; j++) {
                System.out.printf("%n[%s] Sua vez. Responda as categorias:%n", nomes.get(j));
                String[] resp = new String[CATEGORIAS.size()];
                for (int c = 0; c < CATEGORIAS.size(); c++) {
                    System.out.printf("  %s com '%c': ", CATEGORIAS.get(c), letra);
                    resp[c] = entrada.nextLine().trim();
                }
                respostas.add(resp);
            }

            long fim = System.currentTimeMillis();
            long duracao = (fim - inicio) / 1000;
            System.out.printf("%nTempo total: %d segundos%n", duracao);
            if (duracao > TEMPO_LIMITE_SEGUNDOS) {
                System.out.println("Tempo excedido! Pontuacao desta rodada vale metade.");
            }

            int[] pontosRodada = calcularPontos(respostas, letra, numJogadores);
            if (duracao > TEMPO_LIMITE_SEGUNDOS) {
                for (int i = 0; i < pontosRodada.length; i++) {
                    pontosRodada[i] /= 2;
                }
            }

            System.out.println("\nPontuacao da rodada:");
            for (int j = 0; j < numJogadores; j++) {
                System.out.printf("  %s: %d pontos%n", nomes.get(j), pontosRodada[j]);
                pontuacao[j] += pontosRodada[j];
            }
        }

        System.out.println("\n===== RESULTADO FINAL =====");
        int maior = -1;
        String campeao = "";
        for (int j = 0; j < numJogadores; j++) {
            System.out.printf("%s: %d pontos%n", nomes.get(j), pontuacao[j]);
            if (pontuacao[j] > maior) {
                maior = pontuacao[j];
                campeao = nomes.get(j);
            }
        }
        System.out.printf("%nVENCEDOR: %s com %d pontos!%n", campeao, maior);
    }

    private int[] calcularPontos(List<String[]> respostas, char letra, int numJogadores) {
        int[] pontos = new int[numJogadores];
        for (int c = 0; c < CATEGORIAS.size(); c++) {
            List<String> normalizadas = new ArrayList<>();
            for (String[] r : respostas) {
                String n = r[c].trim().toUpperCase();
                if (!n.isEmpty() && n.charAt(0) == Character.toUpperCase(letra)) {
                    normalizadas.add(n);
                } else {
                    normalizadas.add("");
                }
            }
            for (int j = 0; j < numJogadores; j++) {
                String resp = normalizadas.get(j);
                if (resp.isEmpty()) {
                    continue;
                }
                int contagem = 0;
                for (String outra : normalizadas) {
                    if (outra.equals(resp)) {
                        contagem++;
                    }
                }
                pontos[j] += (contagem == 1) ? 10 : 5;
            }
        }
        return pontos;
    }

    private int lerInteiroPositivo() {
        while (true) {
            try {
                int n = Integer.parseInt(entrada.nextLine().trim());
                if (n > 0) {
                    return n;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.print("Digite um numero positivo valido: ");
        }
    }
}

