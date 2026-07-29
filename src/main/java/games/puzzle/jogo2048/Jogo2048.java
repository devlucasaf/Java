package games.puzzle.jogo2048;

import java.util.Random;
import java.util.Scanner;

public class Jogo2048 {

    private static final int TAMANHO = 4;

    private final int[][]   tabuleiro = new int[TAMANHO][TAMANHO];
    private final Random    sorteador = new Random();
    private final Scanner   entrada = new Scanner(System.in);

    private int pontuacao;
    private int recorde;

    public static void main(String[] args) {
        new Jogo2048().iniciar();
    }

    public void iniciar() {
        System.out.println("=== 2048 ===");
        System.out.println("Comandos: w (cima) | a (esquerda) | s (baixo) | d (direita) | q (sair)");
        boolean novaPartida = true;
        while (novaPartida) {
            reiniciar();
            adicionarNumero();
            adicionarNumero();
            executarPartida();
            System.out.print("\nJogar novamente? (s/n): ");
            novaPartida = entrada.nextLine().trim().equalsIgnoreCase("s");
        }
    }

    private void reiniciar() {
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                tabuleiro[i][j] = 0;
            }
        }
        pontuacao = 0;
    }

    private void executarPartida() {
        while (true) {
            desenhar();
            if (chegouAo2048()) {
                System.out.println("VOCE GANHOU! Chegou a 2048!");
                return;
            }

            if (!temMovimento()) {
                System.out.println("GAME OVER! Sem movimentos possiveis.");
                return;
            }

            System.out.print("Direcao: ");
            String comando = entrada.nextLine().trim().toLowerCase();
            if (comando.equals("q")) {
                return;
            }

            boolean mudou = switch (comando) {
                case "w" -> moverCima();
                case "a" -> moverEsquerda();
                case "s" -> moverBaixo();
                case "d" -> moverDireita();
                default -> false;
            };

            if (mudou) {
                adicionarNumero();
                if (pontuacao > recorde) {
                    recorde = pontuacao;
                }
            }
        }
    }

    private void adicionarNumero() {
        int vazias = 0;
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                if (tabuleiro[i][j] == 0) {
                    vazias++;
                }
            }
        }

        if (vazias == 0) {
            return;
        }

        int alvo = sorteador.nextInt(vazias);
        int contador = 0;
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                if (tabuleiro[i][j] == 0) {
                    if (contador == alvo) {
                        tabuleiro[i][j] = sorteador.nextInt(10) == 0 ? 4 : 2;
                        return;
                    }
                    contador++;
                }
            }
        }
    }

    private boolean moverEsquerda() {
        boolean mudou = false;
        for (int i = 0; i < TAMANHO; i++) {
            int[] nova = comprimirLinha(tabuleiro[i]);
            if (!iguais(tabuleiro[i], nova)) {
                tabuleiro[i] = nova;
                mudou = true;
            }
        }
        return mudou;
    }

    private boolean moverDireita() {
        boolean mudou = false;
        for (int i = 0; i < TAMANHO; i++) {
            int[] invertida = inverter(tabuleiro[i]);
            int[] nova = inverter(comprimirLinha(invertida));
            if (!iguais(tabuleiro[i], nova)) {
                tabuleiro[i] = nova;
                mudou = true;
            }
        }
        return mudou;
    }

    private boolean moverCima() {
        boolean mudou = false;
        for (int j = 0; j < TAMANHO; j++) {
            int[] coluna = new int[TAMANHO];
            for (int i = 0; i < TAMANHO; i++) {
                coluna[i] = tabuleiro[i][j];
            }

            int[] nova = comprimirLinha(coluna);
            if (!iguais(coluna, nova)) {
                for (int i = 0; i < TAMANHO; i++) {
                    tabuleiro[i][j] = nova[i];
                }
                mudou = true;
            }
        }
        return mudou;
    }

    private boolean moverBaixo() {
        boolean mudou = false;
        for (int j = 0; j < TAMANHO; j++) {
            int[] coluna = new int[TAMANHO];
            for (int i = 0; i < TAMANHO; i++) {
                coluna[i] = tabuleiro[i][j];
            }

            int[] invertida = inverter(coluna);
            int[] nova = inverter(comprimirLinha(invertida));
            if (!iguais(coluna, nova)) {
                for (int i = 0; i < TAMANHO; i++) {
                    tabuleiro[i][j] = nova[i];
                }
                mudou = true;
            }
        }
        return mudou;
    }

    private int[] comprimirLinha(int[] linha) {
        int[] semZeros = new int[TAMANHO];
        int idx = 0;
        for (int v : linha) {
            if (v != 0) {
                semZeros[idx++] = v;
            }
        }

        for (int i = 0; i < idx - 1; i++) {
            if (semZeros[i] == semZeros[i + 1]) {
                semZeros[i] *= 2;
                pontuacao += semZeros[i];
                semZeros[i + 1] = 0;
            }
        }

        int[] resultado = new int[TAMANHO];
        int k = 0;
        for (int v : semZeros) {
            if (v != 0) {
                resultado[k++] = v;
            }
        }
        return resultado;
    }

    private int[] inverter(int[] arr) {
        int[] r = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            r[i] = arr[arr.length - 1 - i];
        }
        return r;
    }

    private boolean iguais(int[] a, int[] b) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }

    private boolean chegouAo2048() {
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                if (tabuleiro[i][j] >= 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean temMovimento() {
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                if (tabuleiro[i][j] == 0) {
                    return true;
                }
            }
        }

        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO - 1; j++) {
                if (tabuleiro[i][j] == tabuleiro[i][j + 1]) {
                    return true;
                }
            }
        }

        for (int j = 0; j < TAMANHO; j++) {
            for (int i = 0; i < TAMANHO - 1; i++) {
                if (tabuleiro[i][j] == tabuleiro[i + 1][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    private void desenhar() {
        System.out.println();
        System.out.println("Pontuacao: " + pontuacao + "   Recorde: " + recorde);
        String linha = "+------+------+------+------+";
        System.out.println(linha);
        for (int i = 0; i < TAMANHO; i++) {
            StringBuilder sb = new StringBuilder("|");
            for (int j = 0; j < TAMANHO; j++) {
                if (tabuleiro[i][j] == 0) {
                    sb.append("      |");
                } else {
                    sb.append(String.format(" %4d |", tabuleiro[i][j]));
                }
            }
            System.out.println(sb);
            System.out.println(linha);
        }
    }
}
