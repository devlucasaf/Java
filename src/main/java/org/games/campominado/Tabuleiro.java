package org.games.campominado;

import java.util.Random;

public class Tabuleiro {

    private int         linhas;
    private int         colunas;
    private int         totalMinas;
    private Celula[][]  grid;
    private boolean     minasGeradas = false;

    public Tabuleiro(int linhas, int colunas, int totalMinas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.totalMinas = totalMinas;
        this.grid = new Celula[linhas][colunas];

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                grid[i][j] = new Celula();
            }
        }
    }

    public void gerarMinas(int linhaSegura, int colunaSegura) {
        Random rand = new Random();
        int minasColocadas = 0;

        while (minasColocadas < totalMinas) {
            int l = rand.nextInt(linhas);
            int c = rand.nextInt(colunas);

            if ((l == linhaSegura && c == colunaSegura) || grid[l][c].isMina()) {
                continue;
            }

            grid[l][c].setMina(true);
            minasColocadas++;

            for (int i = l - 1; i <= l + 1; i++) {
                for (int j = c - 1; j <= c + 1; j++) {
                    if (dentroDoTabuleiro(i, j) && !grid[i][j].isMina()) {
                        grid[i][j].incrementarMinasAoRedor();
                    }
                }
            }
        }
        minasGeradas = true;
    }

    public boolean revelar(int linha, int coluna) {
        Celula celula = grid[linha][coluna];

        if (celula.isRevelada() || celula.isBandeira()) {
            return false;
        }

        celula.revelar();

        if (celula.isMina()) {
            return true;
        }

        if (celula.getMinasAoRedor() == 0) {
            abrirArea(linha, coluna);
        }

        return false;
    }

    private void abrirArea(int linha, int coluna) {
        for (int i = linha - 1; i <= linha + 1; i++) {
            for (int j = coluna - 1; j <= coluna + 1; j++) {
                if (dentroDoTabuleiro(i, j)) {
                    Celula c = grid[i][j];
                    if (!c.isRevelada() && !c.isMina()) {
                        c.revelar();
                        if (c.getMinasAoRedor() == 0) {
                            abrirArea(i, j);
                        }
                    }
                }
            }
        }
    }

    public void alternarBandeira(int linha, int coluna) {
        grid[linha][coluna].alternarBandeira();
    }

    public boolean venceu() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                Celula c = grid[i][j];

                if (!c.isMina() && !c.isRevelada()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void mostrar(boolean revelarMinas) {
        System.out.print("   ");
        for (int c = 0; c < colunas; c++) {
            System.out.print(c + " ");
        }
        System.out.println();

        for (int i = 0; i < linhas; i++) {
            System.out.print(i + "  ");
            for (int j = 0; j < colunas; j++) {
                Celula c = grid[i][j];

                if (c.isRevelada()) {
                    if (c.isMina()) {
                        System.out.print("* ");
                    } else if (c.getMinasAoRedor() == 0) {
                        System.out.print("  ");
                    } else {
                        System.out.print(c.getMinasAoRedor() + " ");
                    }
                } else if (c.isBandeira()) {
                    System.out.print("F ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

    private boolean dentroDoTabuleiro(int l, int c) {
        return l >= 0 && l < linhas && c >= 0 && c < colunas;
    }

    public boolean minasGeradas() {
        return minasGeradas;
    }
}
