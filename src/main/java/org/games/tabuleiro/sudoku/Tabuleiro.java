package org.games.tabuleiro.sudoku;

public class Tabuleiro {

    private int[][] tabuleiro;

    public Tabuleiro(int dificuldade) {
        tabuleiro = new int[9][9];
        GeradorSudoku.gerarCompleto(tabuleiro);
        GeradorSudoku.removerNumeros(tabuleiro, dificuldade);
    }

    public void exibir() {
        System.out.println("\n   1 2 3   4 5 6   7 8 9");
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0) {
                System.out.println("  +-------+-------+-------+");
            }
            System.out.print((i + 1) + " | ");
            for (int j = 0; j < 9; j++) {
                System.out.print(tabuleiro[i][j] == 0 ? ". " : tabuleiro[i][j] + " ");
                if ((j + 1) % 3 == 0) {
                    System.out.print("| ");
                }
            }
            System.out.println();
        }
        System.out.println("  +-------+-------+-------+");
    }

    public boolean inserir(int linha, int coluna, int numero) {
        if (tabuleiro[linha][coluna] != 0) {
            return false;
        }

        if (Validador.jogadaValida(tabuleiro, linha, coluna, numero)) {
            tabuleiro[linha][coluna] = numero;
            return true;
        }
        return false;
    }

    public boolean completo() {
        for (int[] linha : tabuleiro) {
            for (int valor : linha) {
                if (valor == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
