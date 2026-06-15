package org.games.puzzle.boggle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Boggle {

    private static final int TAMANHO = 4;
    private static final long TEMPO_LIMITE_MS = 180_000;

    private final char[][] grade = new char[TAMANHO][TAMANHO];
    private final Random sorteador = new Random();
    private final Scanner entrada = new Scanner(System.in);

    private final Set<String> dicionario = new HashSet<>(Arrays.asList(
            "JAVA", "RIO", "MAR", "SOL", "PAR", "LUA", "CASA", "BOLA", "VIDA",
            "AMOR", "PORTO", "CARRO", "PONTE", "FOLHA", "FOGO", "AGUA", "TERRA",
            "GATO", "PATO", "RATO", "COR", "ROSA", "AZUL", "VERDE", "PRETO",
            "BOM", "MAU", "SIM", "NAO", "LAR", "AR", "EU", "TU"));

    private final Set<String> encontradas = new HashSet<>();

    private static final char[] DADOS = {
            'A','B','C','D','E','F','G','H','I','J','L','M','N',
            'O','P','Q','R','S','T','U','V','X','Z','A','E','I','O','U','R','S','T','L'
    };

    public static void main(String[] args) {
        new Boggle().iniciar();
    }

    public void iniciar() {
        System.out.println("=== BOGGLE ===");
        System.out.println("Encontre palavras de 3+ letras conectando celulas adjacentes (incluindo diagonais).");
        System.out.println("Voce tem " + (TEMPO_LIMITE_MS / 1000) + " segundos.");
        System.out.print("Pressione ENTER para comecar...");
        entrada.nextLine();

        gerarGrade();
        long inicio = System.currentTimeMillis();
        long fim = inicio + TEMPO_LIMITE_MS;

        while (System.currentTimeMillis() < fim) {
            desenhar();
            long restante = (fim - System.currentTimeMillis()) / 1000;
            System.out.println("Tempo restante: " + restante + "s   Palavras: " + encontradas.size());
            System.out.print("Digite uma palavra (ou 'sair'): ");
            String palavra = entrada.nextLine().trim().toUpperCase();
            if (palavra.equals("SAIR")) {
                break;
            }

            if (palavra.length() < 3) {
                System.out.println("Palavra muito curta.");
                continue;
            }

            if (encontradas.contains(palavra)) {
                System.out.println("Voce ja achou essa.");
                continue;
            }

            if (!dicionario.contains(palavra)) {
                System.out.println("Palavra nao esta no dicionario do jogo.");
                continue;
            }

            if (!existeNaGrade(palavra)) {
                System.out.println("Nao e possivel formar essa palavra na grade.");
                continue;
            }
            encontradas.add(palavra);
            System.out.println("Aceita! +" + pontos(palavra));
        }

        encerrar();
    }

    private void gerarGrade() {
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                grade[i][j] = DADOS[sorteador.nextInt(DADOS.length)];
            }
        }
    }

    private boolean existeNaGrade(String palavra) {
        boolean[][] visitado = new boolean[TAMANHO][TAMANHO];
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                if (grade[i][j] == palavra.charAt(0)) {
                    if (buscar(palavra, 0, i, j, visitado)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean buscar(String palavra, int pos, int linha, int coluna, boolean[][] visitado) {
        if (linha < 0 || linha >= TAMANHO || coluna < 0 || coluna >= TAMANHO) {
            return false;
        }

        if (visitado[linha][coluna]) {
            return false;
        }

        if (grade[linha][coluna] != palavra.charAt(pos)) {
            return false;
        }

        if (pos == palavra.length() - 1) {
            return true;
        }

        visitado[linha][coluna] = true;
        for (int dl = -1; dl <= 1; dl++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dl == 0 && dc == 0) {
                    continue;
                }

                if (buscar(palavra, pos + 1, linha + dl, coluna + dc, visitado)) {
                    visitado[linha][coluna] = false;
                    return true;
                }
            }
        }
        visitado[linha][coluna] = false;
        return false;
    }

    private int pontos(String palavra) {
        int n = palavra.length();
        if (n <= 4) {
            return 1;
        }

        if (n == 5) {
            return 2;
        }

        if (n == 6) {
            return 3;
        }

        if (n == 7) {
            return 5;
        }
        return 11;
    }

    private void desenhar() {
        System.out.println();
        String divisor = "+---+---+---+---+";
        System.out.println(divisor);
        for (int i = 0; i < TAMANHO; i++) {
            StringBuilder sb = new StringBuilder("|");
            for (int j = 0; j < TAMANHO; j++) {
                sb.append(" ").append(grade[i][j]).append(" |");
            }
            System.out.println(sb);
            System.out.println(divisor);
        }
    }

    private void encerrar() {
        int total = 0;
        for (String p : encontradas) {
            total += pontos(p);
        }
        System.out.println("\n=== FIM ===");
        System.out.println("Palavras encontradas: " + encontradas);
        System.out.println("Pontuacao total: " + total);
    }
}


