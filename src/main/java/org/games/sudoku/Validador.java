package org.games.sudoku;

public class Validador {

    public static boolean validoLinha(int[][] tabuleiro, int linha, int numero) {
        for (int col = 0; col < 9; col++) {
            if (tabuleiro[linha][col] == numero) {
                return false;
            }
        }
        return true;
    }

    public static boolean validoColuna(int[][] tabuleiro, int coluna, int numero) {
        for (int lin = 0; lin < 9; lin++) {
            if (tabuleiro[lin][coluna] == numero) {
                return false;
            }
        }
        return true;
    }

    public static boolean validoBloco(int[][] tabuleiro, int linha, int coluna, int numero) {
        int inicioLinha = linha - linha % 3;
        int inicioColuna = coluna - coluna % 3;

        for (int i = inicioLinha; i < inicioLinha + 3; i++) {
            for (int j = inicioColuna; j < inicioColuna + 3; j++) {
                if (tabuleiro[i][j] == numero) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean jogadaValida(int[][] tabuleiro, int linha, int coluna, int numero) {
        return validoLinha(tabuleiro, linha, numero)
                && validoColuna(tabuleiro, coluna, numero)
                && validoBloco(tabuleiro, linha, coluna, numero);
    }
}
