package games.strategy.comandantenaval;

import java.util.Random;
import java.util.Scanner;

public class PrincipalComandanteNaval {

    private static final Scanner    LEITOR = new Scanner(System.in);
    private static final Random     ALEATORIO = new Random();
    private static final int        TAMANHO = 6;
    private static final int        NAVIOS = 6;

    public static void main(String[] args) {
        boolean[][] naviosInimigos = new boolean[TAMANHO][TAMANHO];
        boolean[][] tirosJogador = new boolean[TAMANHO][TAMANHO];
        boolean[][] naviosJogador = new boolean[TAMANHO][TAMANHO];
        boolean[][] tirosIa = new boolean[TAMANHO][TAMANHO];

        posicionarNaviosAleatorios(naviosInimigos);
        posicionarNaviosAleatorios(naviosJogador);

        int destruidosJogador = 0;
        int destruidosIa = 0;

        System.out.println("=== COMANDANTE NAVAL ===");

        while (destruidosJogador < NAVIOS && destruidosIa < NAVIOS) {
            exibirRadar(tirosJogador, naviosInimigos);
            int linha = lerInteiro("Ataque linha (1-6): ", 1, TAMANHO) - 1;
            int coluna = lerInteiro("Ataque coluna (1-6): ", 1, TAMANHO) - 1;
            if (tirosJogador[linha][coluna]) {
                System.out.println("Setor ja atacado.");
            } else {
                tirosJogador[linha][coluna] = true;
                if (naviosInimigos[linha][coluna]) {
                    destruidosJogador++;
                    System.out.println("Acerto!");
                } else {
                    System.out.println("Agua.");
                }
            }

            if (destruidosJogador >= NAVIOS) {
                break;
            }

            int[] alvoIa = escolherAlvoIa(tirosIa);
            tirosIa[alvoIa[0]][alvoIa[1]] = true;
            if (naviosJogador[alvoIa[0]][alvoIa[1]]) {
                destruidosIa++;
                System.out.println("IA acertou o setor (" + (alvoIa[0] + 1) + "," + (alvoIa[1] + 1) + ").");
            } else {
                System.out.println("IA atacou (" + (alvoIa[0] + 1) + "," + (alvoIa[1] + 1) + ") e errou.");
            }
        }

        if (destruidosJogador > destruidosIa) {
            System.out.println("Vitoria naval!");
        } else {
            System.out.println("Derrota naval.");
        }
    }

    private static int[] escolherAlvoIa(boolean[][] tirosIa) {
        while (true) {
            int linha = ALEATORIO.nextInt(TAMANHO);
            int coluna = ALEATORIO.nextInt(TAMANHO);
            if (!tirosIa[linha][coluna]) {
                return new int[]{linha, coluna};
            }
        }
    }

    private static void exibirRadar(boolean[][] tiros, boolean[][] naviosInimigos) {
        System.out.println("\nRadar (fog of war):");
        for (int l = 0; l < TAMANHO; l++) {
            for (int c = 0; c < TAMANHO; c++) {
                char marca = '?';
                if (tiros[l][c]) {
                    marca = naviosInimigos[l][c] ? 'X' : '~';
                }
                System.out.print("[" + marca + "]");
            }
            System.out.println();
        }
    }

    private static void posicionarNaviosAleatorios(boolean[][] tabuleiro) {
        int colocados = 0;
        while (colocados < NAVIOS) {
            int l = ALEATORIO.nextInt(TAMANHO);
            int c = ALEATORIO.nextInt(TAMANHO);
            if (!tabuleiro[l][c]) {
                tabuleiro[l][c] = true;
                colocados++;
            }
        }
    }

    private static int lerInteiro(String mensagem, int minimo, int maximo) {
        while (true) {
            System.out.print(mensagem);
            try {
                int valor = Integer.parseInt(LEITOR.nextLine().trim());
                if (valor >= minimo && valor <= maximo) {
                    return valor;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Valor invalido.");
        }
    }
}
