package games.plataforma.minigames.jogos.tetris.model;

import java.util.Random;

public class Tetris {
    public static final int LARGURA = 10;
    public static final int ALTURA = 20;

    private int[][] tabuleiro;
    private int[][] pecaAtual;
    private int     posicaoX;
    private int     posicaoY;
    private int     proximaPeca;
    private int     pontuacao;
    private int     nivel;
    private int     linhasRemovidas;
    private boolean gameOver;
    private Random  random;

    private static final int[][][] PECAS = {
            // I
            {{1,1,1,1}},
            // O
            {{1,1},{1,1}},
            // T
            {{0,1,0},{1,1,1}},
            // S
            {{0,1,1},{1,1,0}},
            // Z
            {{1,1,0},{0,1,1}},
            // L
            {{1,0,0},{1,1,1}},
            // J
            {{0,0,1},{1,1,1}}
    };

    public Tetris() {
        random = new Random();
        tabuleiro = new int[ALTURA][LARGURA];
        iniciar();
    }

    public void iniciar() {
        for (int i = 0; i < ALTURA; i++) {
            for (int j = 0; j < LARGURA; j++) {
                tabuleiro[i][j] = 0;
            }
        }
        pontuacao = 0;
        nivel = 1;
        linhasRemovidas = 0;
        gameOver = false;
        proximaPeca = random.nextInt(PECAS.length);
        spawnPeca();
    }

    private void spawnPeca() {
        int tipo = proximaPeca;
        proximaPeca = random.nextInt(PECAS.length);
        pecaAtual = copiarPeca(PECAS[tipo]);
        posicaoX = LARGURA / 2 - pecaAtual[0].length / 2;
        posicaoY = 0;

        if (colisao(pecaAtual, posicaoX, posicaoY)) {
            gameOver = true;
        }
    }

    private int[][] copiarPeca(int[][] p) {
        int[][] nova = new int[p.length][p[0].length];
        for (int i = 0; i < p.length; i++) {
            System.arraycopy(p[i], 0, nova[i], 0, p[i].length);
        }
        return nova;
    }

    private boolean colisao(int[][] peca, int x, int y) {
        for (int i = 0; i < peca.length; i++) {
            for (int j = 0; j < peca[i].length; j++) {
                if (peca[i][j] != 0) {
                    int px = x + j;
                    int py = y + i;
                    if (px < 0 || px >= LARGURA || py >= ALTURA || py < 0) {
                        return true;
                    }

                    if (py >= 0 && tabuleiro[py][px] != 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void moverEsquerda() {
        if (!gameOver && !colisao(pecaAtual, posicaoX - 1, posicaoY)) {
            posicaoX--;
        }
    }

    public void moverDireita() {
        if (!gameOver && !colisao(pecaAtual, posicaoX + 1, posicaoY)) {
            posicaoX++;
        }
    }

    public void moverBaixo() {
        if (!gameOver) {
            if (!colisao(pecaAtual, posicaoX, posicaoY + 1)) {
                posicaoY++;
            } else {
                fixarPeca();
            }
        }
    }

    public void rotacionar() {
        if (gameOver) {
            return;
        }

        int[][] rot = new int[pecaAtual[0].length][pecaAtual.length];
        for (int i = 0; i < pecaAtual.length; i++) {
            for (int j = 0; j < pecaAtual[i].length; j++) {
                rot[j][pecaAtual.length - 1 - i] = pecaAtual[i][j];
            }
        }

        if (!colisao(rot, posicaoX, posicaoY)) {
            pecaAtual = rot;
        } else if (!colisao(rot, posicaoX - 1, posicaoY)) {
            pecaAtual = rot;
            posicaoX--;
        } else if (!colisao(rot, posicaoX + 1, posicaoY)) {
            pecaAtual = rot;
            posicaoX++;
        }
    }

    public void drop() {
        while (!colisao(pecaAtual, posicaoX, posicaoY + 1)) {
            posicaoY++;
        }
        fixarPeca();
    }

    private void fixarPeca() {
        for (int i = 0; i < pecaAtual.length; i++) {
            for (int j = 0; j < pecaAtual[i].length; j++) {
                if (pecaAtual[i][j] != 0) {
                    int py = posicaoY + i;
                    int px = posicaoX + j;
                    if (py < 0) {
                        gameOver = true;
                        return;
                    }
                    tabuleiro[py][px] = 1;
                }
            }
        }
        removerLinhas();
        spawnPeca();
    }

    private void removerLinhas() {
        int removidas = 0;
        for (int i = ALTURA - 1; i >= 0; i--) {
            boolean completa = true;
            for (int j = 0; j < LARGURA; j++) {
                if (tabuleiro[i][j] == 0) {
                    completa = false;
                    break;
                }
            }

            if (completa) {
                removidas++;
                for (int k = i; k > 0; k--) {
                    System.arraycopy(tabuleiro[k-1], 0, tabuleiro[k], 0, LARGURA);
                }
                tabuleiro[0] = new int[LARGURA];
                i++;
            }
        }

        if (removidas > 0) {
            linhasRemovidas += removidas;
            int[] pontos = {0, 100, 300, 500, 800};
            pontuacao += pontos[Math.min(removidas, 4)];
            nivel = 1 + linhasRemovidas / 10;
        }
    }

    public int getVelocidade() {
        return Math.max(100, 500 - nivel * 20);
    }

    public int[][] getTabuleiro() {
        return tabuleiro;
    }

    public int[][] getPecaAtual() {
        return pecaAtual;
    }

    public int getPosicaoX() {
        return posicaoX;
    }

    public int getPosicaoY() {
        return posicaoY;
    }

    public int getProximaPeca() {
        return proximaPeca;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public int getNivel() {
        return nivel;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
