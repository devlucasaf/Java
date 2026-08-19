package games.puzzle.kakuro;

import java.util.Scanner;

public class Kakuro {
    private static final int BLOQUEADO = -1;
    private static final int VAZIO = 0;

    private final int[][] tabuleiro = {
            {BLOQUEADO, BLOQUEADO, BLOQUEADO, BLOQUEADO, BLOQUEADO},
            {BLOQUEADO,     VAZIO,     VAZIO, BLOQUEADO, BLOQUEADO},
            {BLOQUEADO,     VAZIO,     VAZIO,     VAZIO, BLOQUEADO},
            {BLOQUEADO, BLOQUEADO,     VAZIO,     VAZIO,     VAZIO},
            {BLOQUEADO, BLOQUEADO, BLOQUEADO,     VAZIO,     VAZIO}
    };

    private final String[][] pistas = {
            {"     ", "  3\\ ", "  4\\ ", "     ", "     "},
            {" \\ 4 ", "     ", "     ", "  3\\ ", "     "},
            {" \\11 ", "     ", "     ", "     ", "  4\\ "},
            {"     ", " \\ 7 ", "     ", "     ", "     "},
            {"     ", "     ", " \\ 3 ", "     ", "     "}
    };

    private final int[][] solucao = {
            {BLOQUEADO, BLOQUEADO, BLOQUEADO, BLOQUEADO, BLOQUEADO},
            {BLOQUEADO,         1,         3, BLOQUEADO, BLOQUEADO},
            {BLOQUEADO,         3,         6,         2, BLOQUEADO},
            {BLOQUEADO, BLOQUEADO,         2,         4,         1},
            {BLOQUEADO, BLOQUEADO, BLOQUEADO,         1,         2}
    };

    private final Scanner entrada = new Scanner(System.in);

    public static void main(String[] args) {
        new Kakuro().iniciar();
    }

    public void iniciar() {
        System.out.println("=== KAKURO ===");
        System.out.println("Preencha as celulas vazias com numeros de 1 a 9.");
        System.out.println("As pistas mostram somas: 'A\\B' significa A para baixo, B para direita.");
        System.out.println("Numeros nao podem repetir dentro do mesmo grupo de soma.");
        System.out.println("Comandos: linha coluna valor   (ex: 1 1 5)   ou 'q' para sair");

        while (true) {
            desenhar();
            if (resolvido()) {
                System.out.println("PARABENS! Voce resolveu o Kakuro!");
                return;
            }

            System.out.print("Jogada: ");
            String linha = entrada.nextLine().trim();
            if (linha.equalsIgnoreCase("q")) {
                return;
            }

            String[] partes = linha.split("\\s+");
            if (partes.length != 3) {
                System.out.println("Formato invalido. Use: linha coluna valor");
                continue;
            }

            try {
                int l = Integer.parseInt(partes[0]);
                int c = Integer.parseInt(partes[1]);
                int v = Integer.parseInt(partes[2]);
                if (l < 1 || l >= tabuleiro.length || c < 1 || c >= tabuleiro[0].length) {
                    System.out.println("Posicao fora do tabuleiro.");
                    continue;
                }

                if (tabuleiro[l][c] == BLOQUEADO) {
                    System.out.println("Esta celula nao pode ser preenchida.");
                    continue;
                }

                if (v < 0 || v > 9) {
                    System.out.println("Valor deve ser entre 0 e 9 (0 limpa).");
                    continue;
                }
                tabuleiro[l][c] = v;
            } catch (NumberFormatException e) {
                System.out.println("Valores devem ser numeros.");
            }
        }
    }

    private boolean resolvido() {
        for (int i = 0; i < tabuleiro.length; i++) {
            for (int j = 0; j < tabuleiro[0].length; j++) {
                if (tabuleiro[i][j] != solucao[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private void desenhar() {
        System.out.println();
        for (int i = 0; i < tabuleiro.length; i++) {
            StringBuilder topo = new StringBuilder();
            StringBuilder meio = new StringBuilder();
            for (int j = 0; j < tabuleiro[0].length; j++) {
                topo.append("+-----");
                if (tabuleiro[i][j] == BLOQUEADO) {
                    meio.append("|").append(pistas[i][j]);
                } else if (tabuleiro[i][j] == VAZIO) {
                    meio.append("|  .  ");
                } else {
                    meio.append(String.format("|  %d  ", tabuleiro[i][j]));
                }
            }
            topo.append("+");
            meio.append("|");
            System.out.println(topo);
            System.out.println(meio);
        }
        StringBuilder fim = new StringBuilder();
        for (int j = 0; j < tabuleiro[0].length; j++) {
            fim.append("+-----");
        }
        fim.append("+");
        System.out.println(fim);
    }
}

