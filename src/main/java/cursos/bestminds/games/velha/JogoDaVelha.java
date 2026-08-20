package games.bestminds.velha;

import java.util.Random;
import java.util.Scanner;

public class JogoDaVelha {

    private static char[][] tabuleiro = new char[3][3];
    private static Scanner  scanner = new Scanner(System.in);
    private static Random   random = new Random();

    public static void main(String[] args) {
        boolean jogarNovamente;

        do {
            iniciarTabuleiro();
            System.out.println("    JOGO DA VELHA    ");
            System.out.println("1 - Jogador vs Jogador");
            System.out.println("2 - Jogador vs Máquina");
            System.out.print("Escolha o modo de jogo: ");
            int modo = scanner.nextInt();

            jogar(modo);

            System.out.print("\nDeseja jogar novamente? (s/n): ");
            jogarNovamente = scanner.next().equalsIgnoreCase("s");

        }
        while (jogarNovamente);

        System.out.println("Obrigado por jogar!");
    }

    private static void jogar(int modo) {
        char jogadorAtual = 'X';
        boolean jogoAtivo = true;

        while (jogoAtivo) {
            exibirTabuleiro();

            if (modo == 2 && jogadorAtual == 'O') {
                jogadaMaquina();
                System.out.println("Máquina jogou.");
            } else {
                jogadaJogador(jogadorAtual);
            }

            if (verificarVitoria(jogadorAtual)) {
                exibirTabuleiro();
                System.out.println("Jogador " + jogadorAtual + " venceu!");
                jogoAtivo = false;
            } else if (verificarEmpate()) {
                exibirTabuleiro();
                System.out.println("Empate!");
                jogoAtivo = false;
            } else {
                jogadorAtual = (jogadorAtual == 'X') ? 'O' : 'X';
            }
        }
    }

    private static void iniciarTabuleiro() {
        char posicao = '1';
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tabuleiro[i][j] = posicao++;
            }
        }
    }

    private static void exibirTabuleiro() {
        System.out.println();
        for (int i = 0; i < 3; i++) {
            System.out.print(" ");
            for (int j = 0; j < 3; j++) {
                System.out.print(tabuleiro[i][j]);
                if (j < 2) {
                    System.out.print(" | ");
                }
            }
            System.out.println();
            if (i < 2) {
                System.out.println("---+---+---");
            }
        }
        System.out.println();
    }

    private static void jogadaJogador(char jogador) {
        int posicao;
        boolean valido = false;

        do {
            System.out.print("Jogador " + jogador + ", escolha uma posição (1-9): ");
            posicao = scanner.nextInt();

            valido = validarJogada(posicao);
            if (!valido) {
                System.out.println("Jogada inválida. Tente novamente.");
            }
        }
        while (!valido);

        marcarPosicao(posicao, jogador);
    }

    private static void jogadaMaquina() {
        int posicao;
        do {
            posicao = random.nextInt(9) + 1;
        }
        while (!validarJogada(posicao));

        marcarPosicao(posicao, 'O');
    }

    private static boolean validarJogada(int posicao) {
        if (posicao < 1 || posicao > 9) {
            return false;
        }

        int linha = (posicao - 1) / 3;
        int coluna = (posicao - 1) % 3;

        return tabuleiro[linha][coluna] != 'X' && tabuleiro[linha][coluna] != 'O';
    }

    private static void marcarPosicao(int posicao, char jogador) {
        int linha = (posicao - 1) / 3;
        int coluna = (posicao - 1) % 3;
        tabuleiro[linha][coluna] = jogador;
    }

    private static boolean verificarVitoria(char jogador) {
        for (int i = 0; i < 3; i++) {
            if ((tabuleiro[i][0] == jogador &&
                    tabuleiro[i][1] == jogador &&
                    tabuleiro[i][2] == jogador) ||

                    (tabuleiro[0][i] == jogador &&
                            tabuleiro[1][i] == jogador &&
                            tabuleiro[2][i] == jogador)) {
                return true;
            }
        }

        return (tabuleiro[0][0] == jogador &&
                tabuleiro[1][1] == jogador &&
                tabuleiro[2][2] == jogador) ||

                (tabuleiro[0][2] == jogador &&
                        tabuleiro[1][1] == jogador &&
                        tabuleiro[2][0] == jogador);
    }

    private static boolean verificarEmpate() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiro[i][j] != 'X' && tabuleiro[i][j] != 'O') {
                    return false;
                }
            }
        }
        return true;
    }
}