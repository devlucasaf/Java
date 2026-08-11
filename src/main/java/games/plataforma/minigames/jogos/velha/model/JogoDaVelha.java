package games.plataforma.minigames.jogos.velha.model;

public class JogoDaVelha {
    private char[][]    tabuleiro;
    private char        jogadorAtual;
    private boolean     fimDeJogo;
    private int         jogadas;

    public JogoDaVelha() {
        reiniciar();
    }

    public void reiniciar() {
        tabuleiro = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tabuleiro[i][j] = ' ';
            }
        }
        jogadorAtual = 'X';
        fimDeJogo = false;
        jogadas = 0;
    }

    public boolean fazerJogada(int linha, int coluna) {
        if (fimDeJogo || linha < 0 || linha > 2 || coluna < 0 || coluna > 2 || tabuleiro[linha][coluna] != ' ') {
            return false;
        }

        tabuleiro[linha][coluna] = jogadorAtual;
        jogadas++;
        if (verificarVitoria()) {
            fimDeJogo = true;
            return true;
        }

        if (jogadas == 9) {
            fimDeJogo = true;
            return true;
        }
        alternarJogador();
        return true;
    }

    private void alternarJogador() {
        jogadorAtual = (jogadorAtual == 'X') ? 'O' : 'X';
    }

    public boolean verificarVitoria() {
        for (int i = 0; i < 3; i++) {
            if (tabuleiro[i][0] != ' ' && tabuleiro[i][0] == tabuleiro[i][1] && tabuleiro[i][1] == tabuleiro[i][2]) {
                return true;
            }
        }

        for (int j = 0; j < 3; j++) {
            if (tabuleiro[0][j] != ' ' && tabuleiro[0][j] == tabuleiro[1][j] && tabuleiro[1][j] == tabuleiro[2][j]) {
                return true;
            }
        }

        if (tabuleiro[0][0] != ' ' && tabuleiro[0][0] == tabuleiro[1][1] && tabuleiro[1][1] == tabuleiro[2][2]) {
            return true;
        }

        if (tabuleiro[0][2] != ' ' && tabuleiro[0][2] == tabuleiro[1][1] && tabuleiro[1][1] == tabuleiro[2][0]) {
            return true;
        }
        return false;
    }

    public boolean isFimDeJogo() {
        return fimDeJogo;
    }

    public char getJogadorAtual() {
        return jogadorAtual;
    }

    public char getCelula(int linha, int coluna) {
        return tabuleiro[linha][coluna];
    }

    public int getJogadas() {
        return jogadas;
    }
}
