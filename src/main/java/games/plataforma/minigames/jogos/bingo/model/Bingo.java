package games.plataforma.minigames.jogos.bingo.model;

import games.plataforma.minigames.util.GeradorAleatorio;

import java.util.ArrayList;
import java.util.List;

public class Bingo {
    private int[][]         cartela;
    private boolean[][]     marcado;
    private List<Integer>   numerosSorteados;
    private int             ultimoSorteado;
    private boolean         finalizado;

    public Bingo() {
        gerarNovaCartela();
    }

    public void gerarNovaCartela() {
        cartela = new int[5][5];
        marcado = new boolean[5][5];
        numerosSorteados = new ArrayList<>();
        finalizado = false;

        int[] min = {1, 16, 31, 46, 61};
        int[] max = {15, 30, 45, 60, 75};

        for (int col = 0; col < 5; col++) {
            List<Integer> numeros = new ArrayList<>();
            for (int n = min[col]; n <= max[col]; n++) {
                numeros.add(n);
            }

            for (int lin = 0; lin < 5; lin++) {
                int idx = GeradorAleatorio.nextInt(numeros.size());
                cartela[lin][col] = numeros.remove(idx);
            }
        }
        marcado[2][2] = true;
    }

    public boolean sortearNumero() {
        if (finalizado) {
            return false;
        }

        if (numerosSorteados.size() >= 75) {
            return false;
        }

        List<Integer> disponiveis = new ArrayList<>();
        for (int i = 1; i <= 75; i++) {
            if (!numerosSorteados.contains(i)) {
                disponiveis.add(i);
            }
        }

        if (disponiveis.isEmpty()) {
            return false;
        }
        int idx = GeradorAleatorio.nextInt(disponiveis.size());
        ultimoSorteado = disponiveis.get(idx);
        numerosSorteados.add(ultimoSorteado);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (cartela[i][j] == ultimoSorteado) {
                    marcado[i][j] = true;
                }
            }
        }

        if (verificarVitoria()) {
            finalizado = true;
        }
        return true;
    }

    private boolean verificarVitoria() {
        for (int i = 0; i < 5; i++) {
            boolean linha = true;
            for (int j = 0; j < 5; j++) {
                if (!marcado[i][j]) {
                    linha = false;
                    break;
                }
            }

            if (linha) {
                return true;
            }
        }

        for (int j = 0; j < 5; j++) {
            boolean coluna = true;
            for (int i = 0; i < 5; i++) {
                if (!marcado[i][j]) {
                    coluna = false;
                    break;
                }
            }

            if (coluna) {
                return true;
            }
        }

        boolean diag1 = true;
        for (int i = 0; i < 5; i++) {
            if (!marcado[i][i]) {
                diag1 = false;
                break;
            }
        }

        if (diag1) {
            return true;
        }

        boolean diag2 = true;
        for (int i = 0; i < 5; i++) {
            if (!marcado[i][4-i]) {
                diag2 = false;
                break;
            }
        }
        return diag2;
    }

    public int[][] getCartela() {
        return cartela;
    }

    public boolean[][] getMarcado() {
        return marcado;
    }

    public List<Integer> getNumerosSorteados() {
        return numerosSorteados;
    }

    public int getUltimoSorteado() {
        return ultimoSorteado;
    }

    public boolean isFinalizado() {
        return finalizado;
    }
}
