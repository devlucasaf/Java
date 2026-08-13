package games.plataforma.minigames.jogos.batalhanaval.model;

import java.util.Random;

public class BatalhaNaval {
    public static final int TAMANHO = 10;
    private int[][]         tabuleiro;
    private int             naviosRestantes;
    private int             tiros;
    private boolean         gameOver;

    public BatalhaNaval() {
        iniciar();
    }

    public void iniciar() {
        tabuleiro = new int[TAMANHO][TAMANHO];
        naviosRestantes = 0;
        tiros = 0;
        gameOver = false;
        posicionarNavios();
    }

    private void posicionarNavios() {
        int[] tamanhos = {5, 4, 3, 3, 2};
        Random rand = new Random();
        for (int tamanho : tamanhos) {
            boolean posicionado = false;
            int tentativas = 0;
            while (!posicionado && tentativas < 1000) {
                tentativas++;
                boolean horizontal = rand.nextBoolean();
                int linha = rand.nextInt(TAMANHO);
                int coluna = rand.nextInt(TAMANHO);
                if (horizontal && coluna + tamanho <= TAMANHO) {
                    boolean ok = true;
                    for (int c = coluna; c < coluna + tamanho; c++) {
                        if (tabuleiro[linha][c] != 0) {
                            ok = false;
                            break;
                        }
                    }

                    if (ok) {
                        for (int c = coluna; c < coluna + tamanho; c++) {
                            tabuleiro[linha][c] = 1;
                        }
                        posicionado = true;
                    }
                } else if (!horizontal && linha + tamanho <= TAMANHO) {
                    boolean ok = true;
                    for (int l = linha; l < linha + tamanho; l++) {
                        if (tabuleiro[l][coluna] != 0) {
                            ok = false;
                            break;
                        }
                    }

                    if (ok) {
                        for (int l = linha; l < linha + tamanho; l++) {
                            tabuleiro[l][coluna] = 1;
                        }
                        posicionado = true;
                    }
                }
            }
        }

        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                if (tabuleiro[i][j] == 1) {
                    naviosRestantes++;
                }
            }
        }
    }

    public boolean atirar(int linha, int coluna) {
        if (gameOver) {
            return false;
        }

        if (linha < 0 || linha >= TAMANHO || coluna < 0 || coluna >= TAMANHO) {
            return false;
        }

        if (tabuleiro[linha][coluna] == 2 || tabuleiro[linha][coluna] == 3) {
            return false;
        }

        tiros++;
        if (tabuleiro[linha][coluna] == 1) {
            tabuleiro[linha][coluna] = 2;
            naviosRestantes--;
            if (naviosRestantes == 0) {
                gameOver = true;
            }
            return true;
        } else {
            tabuleiro[linha][coluna] = 3;
            return false;
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getNaviosRestantes() {
        return naviosRestantes;
    }

    public int getTiros() {
        return tiros;
    }

    public int getCelula(int linha, int coluna) {
        return tabuleiro[linha][coluna];
    }
}
