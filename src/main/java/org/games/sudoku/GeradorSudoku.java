package org.games.sudoku;

import java.util.Random;

public class GeradorSudoku {

    private static Random random = new Random();

    public static boolean gerarCompleto(int[][] tabuleiro) {
        for (int linha = 0; linha < 9; linha++) {
            for (int coluna = 0; coluna < 9; coluna++) {
                if (tabuleiro[linha][coluna] == 0) {
                    for (int numero = 1; numero <= 9; numero++) {
                        if (Validador.jogadaValida(tabuleiro, linha, coluna, numero)) {
                            tabuleiro[linha][coluna] = numero;
                            if (gerarCompleto(tabuleiro)) {
                                return true;
                            }
                            tabuleiro[linha][coluna] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public static void removerNumeros(int[][] tabuleiro, int dificuldade) {
        int removidos;

        switch (dificuldade) {
            case 1: {
                removidos = 30;
            }
            break;

            case 2: {
                removidos = 40;
            }
            break;

            default: {
                removidos = 50;
            }
        }

        while (removidos > 0) {
            int linha = random.nextInt(9);
            int coluna = random.nextInt(9);

            if (tabuleiro[linha][coluna] != 0) {
                tabuleiro[linha][coluna] = 0;
                removidos--;
            }
        }
    }
}
