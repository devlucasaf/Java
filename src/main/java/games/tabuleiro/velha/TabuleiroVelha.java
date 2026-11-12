package games.tabuleiro.velha;

public class TabuleiroVelha {

    public static final char VAZIO = ' ';
    public static final char X = 'X';
    public static final char O = 'O';

    private static final int[][] LINHAS = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };

    private final char[] casas = new char[9];

    public TabuleiroVelha() {
        java.util.Arrays.fill(casas, VAZIO);
    }

    public boolean jogar(int posicao, char jogador) {
        if (posicao < 0 || posicao > 8 || casas[posicao] != VAZIO) {
            return false;
        }
        casas[posicao] = jogador;
        return true;
    }

    public char vencedor() {
        for (int[] l : LINHAS) {
            if (casas[l[0]] != VAZIO && casas[l[0]] == casas[l[1]] && casas[l[1]] == casas[l[2]]) {
                return casas[l[0]];
            }
        }
        return VAZIO;
    }

    public boolean cheio() {
        for (char c : casas) {
            if (c == VAZIO) {
                return false;
            }
        }
        return true;
    }

    public boolean terminou() {
        return vencedor() != VAZIO || cheio();
    }

    public int melhorJogada(char ia) {
        char humano = ia == X ? O : X;
        int melhor = -1;
        int melhorValor = Integer.MIN_VALUE;

        for (int i = 0; i < 9; i++) {
            if (casas[i] != VAZIO) {
                continue;
            }

            casas[i] = ia;
            int valor = minimax(false, ia, humano, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
            casas[i] = VAZIO;
            if (valor > melhorValor) {
                melhorValor = valor;
                melhor = i;
            }
        }
        return melhor;
    }

    private int minimax(boolean maximizando, char ia, char humano, int alfa, int beta, int profundidade) {
        char v = vencedor();
        if (v == ia) {
            return 10 - profundidade;
        }

        if (v == humano) {
            return profundidade - 10;
        }

        if (cheio()) {
            return 0;
        }

        if (maximizando) {
            int melhor = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                if (casas[i] != VAZIO) {
                    continue;
                }

                casas[i] = ia;
                melhor = Math.max(melhor, minimax(false, ia, humano, alfa, beta, profundidade + 1));
                casas[i] = VAZIO;
                alfa = Math.max(alfa, melhor);
                if (beta <= alfa) {
                    break;
                }
            }
            return melhor;
        }

        int pior = Integer.MAX_VALUE;
        for (int i = 0; i < 9; i++) {
            if (casas[i] != VAZIO) {
                continue;
            }

            casas[i] = humano;
            pior = Math.min(pior, minimax(true, ia, humano, alfa, beta, profundidade + 1));
            casas[i] = VAZIO;
            beta = Math.min(beta, pior);
            if (beta <= alfa) {
                break;
            }
        }
        return pior;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                int i = r * 3 + c;
                sb.append(' ').append(casas[i] == VAZIO ? (char) ('1' + i) : casas[i]).append(' ');
                if (c < 2) {
                    sb.append('|');
                }
            }

            sb.append('\n');
            if (r < 2) {
                sb.append("---+---+---\n");
            }
        }
        return sb.toString();
    }
}
