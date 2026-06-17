package games.puzzle.cacapalavras;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class CacaPalavras {

    private static final int LINHAS = 12;
    private static final int COLUNAS = 12;

    private final char[][] grade = new char[LINHAS][COLUNAS];
    private final boolean[][] marcado = new boolean[LINHAS][COLUNAS];
    private final List<String> palavras = new ArrayList<>(Arrays.asList(
            "JAVA", "CODIGO", "CLASSE", "OBJETO", "VARIAVEL",
            "METODO", "STRING", "ARRAY", "PUBLICO", "PRIVADO"));
    private final Set<String> encontradas = new HashSet<>();
    private final Random sorteador = new Random();
    private final Scanner entrada = new Scanner(System.in);

    public static void main(String[] args) {
        new CacaPalavras().iniciar();
    }

    public void iniciar() {
        System.out.println("=== CACA-PALAVRAS ===");
        System.out.println("Encontre as palavras escondidas (horizontal, vertical ou diagonal).");
        gerarGrade();
        executar();
    }

    private void executar() {
        while (encontradas.size() < palavras.size()) {
            desenhar();
            System.out.print("Digite uma palavra para marcar (ou 'q' para sair): ");
            String palavra = entrada.nextLine().trim().toUpperCase();
            if (palavra.equals("Q")) {
                return;
            }

            if (!palavras.contains(palavra)) {
                System.out.println("Palavra nao esta na lista.");
                continue;
            }

            if (encontradas.contains(palavra)) {
                System.out.println("Voce ja achou essa palavra.");
                continue;
            }

            if (procurarEMarcar(palavra)) {
                encontradas.add(palavra);
                System.out.println("Encontrou! Faltam " + (palavras.size() - encontradas.size()) + ".");
            } else {
                System.out.println("Nao localizei essa palavra na grade.");
            }
        }
        desenhar();
        System.out.println("PARABENS! Voce encontrou todas as palavras!");
    }

    private void gerarGrade() {
        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                grade[i][j] = ' ';
            }
        }

        for (String p : palavras) {
            colocarPalavra(p);
        }

        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                if (grade[i][j] == ' ') {
                    grade[i][j] = (char) ('A' + sorteador.nextInt(26));
                }
            }
        }
    }

    private void colocarPalavra(String palavra) {
        for (int tentativa = 0; tentativa < 200; tentativa++) {
            int direcao = sorteador.nextInt(8);
            int dx = new int[]{0, 1, -1, 0, 1, -1, 1, -1}[direcao];
            int dy = new int[]{1, 0, 0, -1, 1, -1, -1, 1}[direcao];
            int linha = sorteador.nextInt(LINHAS);
            int coluna = sorteador.nextInt(COLUNAS);

            if (cabe(palavra, linha, coluna, dx, dy)) {
                for (int i = 0; i < palavra.length(); i++) {
                    grade[linha + i * dx][coluna + i * dy] = palavra.charAt(i);
                }
                return;
            }
        }
    }

    private boolean cabe(String palavra, int linha, int coluna, int dx, int dy) {
        int finalL = linha + (palavra.length() - 1) * dx;
        int finalC = coluna + (palavra.length() - 1) * dy;
        if (finalL < 0 || finalL >= LINHAS || finalC < 0 || finalC >= COLUNAS) {
            return false;
        }

        for (int i = 0; i < palavra.length(); i++) {
            int l = linha + i * dx;
            int c = coluna + i * dy;
            if (grade[l][c] != ' ' && grade[l][c] != palavra.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    private boolean procurarEMarcar(String palavra) {
        int[][] direcoes = {{0,1},{0,-1},{1,0},{-1,0},{1,1},{-1,-1},{1,-1},{-1,1}};
        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                for (int[] d : direcoes) {
                    if (combina(palavra, i, j, d[0], d[1])) {
                        for (int k = 0; k < palavra.length(); k++) {
                            marcado[i + k * d[0]][j + k * d[1]] = true;
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean combina(String palavra, int linha, int coluna, int dx, int dy) {
        int finalL = linha + (palavra.length() - 1) * dx;
        int finalC = coluna + (palavra.length() - 1) * dy;
        if (finalL < 0 || finalL >= LINHAS || finalC < 0 || finalC >= COLUNAS) {
            return false;
        }

        for (int i = 0; i < palavra.length(); i++) {
            if (grade[linha + i * dx][coluna + i * dy] != palavra.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    private void desenhar() {
        System.out.println();
        System.out.print("   ");
        for (int j = 0; j < COLUNAS; j++) {
            System.out.printf("%2d ", j);
        }

        System.out.println();
        for (int i = 0; i < LINHAS; i++) {
            System.out.printf("%2d ", i);
            for (int j = 0; j < COLUNAS; j++) {
                char c = grade[i][j];
                if (marcado[i][j]) {
                    System.out.print("[" + c + "]");
                } else {
                    System.out.print(" " + c + " ");
                }
            }
            System.out.println();
        }
        System.out.println("\nPalavras (" + encontradas.size() + "/" + palavras.size() + "):");
        for (String p : palavras) {
            System.out.println("  " + (encontradas.contains(p) ? "[X] " : "[ ] ") + p);
        }
    }
}

