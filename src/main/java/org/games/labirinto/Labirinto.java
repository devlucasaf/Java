package org.games.labirinto;

import java.util.Random;

/**
 * 🏰 Labirinto com Backtracking
 *
 * Gera um labirinto aleatório e resolve usando recursão com backtracking.
 * Também permite que o jogador tente resolver manualmente.
 *
 * Conceitos praticados:
 * - Recursão / Backtracking
 * - Matrizes bidimensionais
 * - Algoritmo DFS (Depth-First Search)
 * - Geração procedural de labirinto
 */
public class Labirinto {
    private final int           linhas;
    private final int           colunas;
    private final char[][]      mapa;
    private final boolean[][]   visitado;
    private int                 inicioLinha;
    private int                 inicioColuna;
    private int                 fimLinha;
    private int                 fimColuna;

    public static final char PAREDE  = '█';
    public static final char CAMINHO = ' ';
    public static final char INICIO  = 'S';
    public static final char FIM     = 'E';
    public static final char SOLUCAO = '·';
    public static final char JOGADOR = '@';

    public Labirinto(int linhas, int colunas) {
        this.linhas = linhas % 2 == 0 ? linhas + 1 : linhas;
        this.colunas = colunas % 2 == 0 ? colunas + 1 : colunas;
        this.mapa = new char[this.linhas][this.colunas];
        this.visitado = new boolean[this.linhas][this.colunas];

        gerarLabirinto();
    }

    private void gerarLabirinto() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                mapa[i][j] = PAREDE;
            }
        }

        Random random = new Random();
        gerarCaminho(1, 1, random);

        inicioLinha = 1;
        inicioColuna = 0;
        mapa[inicioLinha][inicioColuna] = INICIO;

        fimLinha = linhas - 2;
        fimColuna = colunas - 1;
        mapa[fimLinha][fimColuna] = FIM;
    }

    private void gerarCaminho(int linha, int coluna, Random random) {
        mapa[linha][coluna] = CAMINHO;

        int[][] direcoes = {{-2, 0}, {2, 0}, {0, -2}, {0, 2}};
        embaralhar(direcoes, random);

        for (int[] dir : direcoes) {
            int novaLinha = linha + dir[0];
            int novaColuna = coluna + dir[1];

            if (novaLinha > 0 && novaLinha < linhas - 1 &&
                novaColuna > 0 && novaColuna < colunas - 1 &&
                mapa[novaLinha][novaColuna] == PAREDE) {

                mapa[linha + dir[0] / 2][coluna + dir[1] / 2] = CAMINHO;
                gerarCaminho(novaLinha, novaColuna, random);
            }
        }
    }

    private void embaralhar(int[][] array, Random random) {
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int[] temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    public boolean resolver() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                visitado[i][j] = false;
                if (mapa[i][j] == SOLUCAO) mapa[i][j] = CAMINHO;
            }
        }
        return resolverRecursivo(inicioLinha, inicioColuna);
    }

    private boolean resolverRecursivo(int linha, int coluna) {
        if (linha == fimLinha && coluna == fimColuna) {
            return true;
        }

        if (linha < 0 || linha >= linhas || coluna < 0 || coluna >= colunas) {
            return false;
        }

        if (mapa[linha][coluna] == PAREDE) {
            return false;
        }

        if (visitado[linha][coluna]) {
            return false;
        }

        visitado[linha][coluna] = true;

        // Tenta todas as direções
        int[][] direcoes = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        for (int[] dir : direcoes) {
            if (resolverRecursivo(linha + dir[0], coluna + dir[1])) {
                if (mapa[linha][coluna] != INICIO) {
                    mapa[linha][coluna] = SOLUCAO;
                }
                return true;
            }
        }

        return false;
    }

    public void imprimir() {
        System.out.println();
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                System.out.print(mapa[i][j]);
            }
            System.out.println();
        }
    }

    public void imprimirComJogador(int jogadorLinha, int jogadorColuna) {
        System.out.println();
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                if (i == jogadorLinha && j == jogadorColuna) {
                    System.out.print(JOGADOR);
                } else {
                    System.out.print(mapa[i][j]);
                }
            }
            System.out.println();
        }
    }

    public boolean podeMover(int linha, int coluna) {
        if (linha < 0 || linha >= linhas || coluna < 0 || coluna >= colunas) {
            return false;
        }
        return mapa[linha][coluna] != PAREDE;
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    public int getInicioLinha() {
        return inicioLinha;
    }

    public int getInicioColuna() {
        return inicioColuna;
    }

    public int getFimLinha() {
        return fimLinha;
    }

    public int getFimColuna() {
        return fimColuna;
    }

    public char[][] getMapa() {
        return mapa;
    }

}

