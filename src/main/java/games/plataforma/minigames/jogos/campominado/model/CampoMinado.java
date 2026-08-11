package games.plataforma.minigames.jogos.campominado.model;

import games.plataforma.minigames.util.GeradorAleatorio;

public class CampoMinado {
    private int         linhas = 9;
    private int         colunas = 9;
    private int         minas = 10;
    private int[][]     campo;
    private boolean[][] revelado;
    private boolean[][] bandeira;
    private boolean     gameOver;
    private boolean     venceu;
    private int         celulasReveladas;
    private int         totalCelulas;

    public CampoMinado() {
        this(9, 9, 10);
    }

    public CampoMinado(int linhas, int colunas, int minas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.minas = minas;
        totalCelulas = linhas * colunas - minas;
        iniciar();
    }

    public void iniciar() {
        campo = new int[linhas][colunas];
        revelado = new boolean[linhas][colunas];
        bandeira = new boolean[linhas][colunas];
        gameOver = false;
        venceu = false;
        celulasReveladas = 0;

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                campo[i][j] = 0;
                revelado[i][j] = false;
                bandeira[i][j] = false;
            }
        }

        int colocadas = 0;
        while (colocadas < minas) {
            int l = GeradorAleatorio.nextInt(linhas);
            int c = GeradorAleatorio.nextInt(colunas);
            if (campo[l][c] != -1) {
                campo[l][c] = -1;
                colocadas++;
            }
        }

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                if (campo[i][j] == -1) {

                    continue;
                }
                int count = 0;
                for (int di = -1; di <= 1; di++) {
                    for (int dj = -1; dj <= 1; dj++) {
                        if (di == 0 && dj == 0) {
                            continue;
                        }
                        int ni = i + di, nj = j + dj;
                        if (ni >= 0 && ni < linhas && nj >= 0 && nj < colunas && campo[ni][nj] == -1) {
                            count++;
                        }
                    }
                }
                campo[i][j] = count;
            }
        }
    }

    public boolean revelar(int linha, int coluna) {
        if (gameOver || linha < 0 || linha >= linhas || coluna < 0 || coluna >= colunas) {
            return false;
        }

        if (revelado[linha][coluna] || bandeira[linha][coluna]) {
            return false;
        }

        if (campo[linha][coluna] == -1) {
            gameOver = true;
            return true;
        }

        revelarRecursivo(linha, coluna);
        verificarVitoria();
        return false;
    }

    private void revelarRecursivo(int linha, int coluna) {
        if (linha < 0 || linha >= linhas || coluna < 0 || coluna >= colunas) {
            return;
        }

        if (revelado[linha][coluna] || bandeira[linha][coluna]) {
            return;
        }

        if (campo[linha][coluna] == -1) {
            return;
        }

        revelado[linha][coluna] = true;
        celulasReveladas++;

        if (campo[linha][coluna] == 0) {
            for (int di = -1; di <= 1; di++) {
                for (int dj = -1; dj <= 1; dj++) {
                    if (di == 0 && dj == 0) {
                        continue;
                    }
                    revelarRecursivo(linha + di, coluna + dj);
                }
            }
        }
    }

    public void alternarBandeira(int linha, int coluna) {
        if (gameOver || linha < 0 || linha >= linhas || coluna < 0 || coluna >= colunas) {
            return;
        }

        if (revelado[linha][coluna]) {
            return;
        }
        bandeira[linha][coluna] = !bandeira[linha][coluna];
    }

    private void verificarVitoria() {
        if (celulasReveladas == totalCelulas) {
            venceu = true;
            gameOver = true;
        }
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    public int getValor(int linha, int coluna) {
        return campo[linha][coluna];
    }

    public boolean isRevelado(int linha, int coluna) {
        return revelado[linha][coluna];
    }

    public boolean isBandeira(int linha, int coluna) {
        return bandeira[linha][coluna];
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isVenceu() {
        return venceu;
    }

    public int getMinasRestantes() {
        int count = 0;
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                if (bandeira[i][j]) {
                    count++;
                }
            }
        }
        return minas - count;
    }
}
