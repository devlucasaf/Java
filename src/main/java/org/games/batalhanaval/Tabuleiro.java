package org.games.batalhanaval;

public class Tabuleiro {
    public static final int     TAMANHO = 10;
    private final char[][]      grade;
    private final Navio[][]     posicoesNavios;

    // Cores ANSI para o terminal
    private static final String RESETAR = "\u001B[0m";
    private static final String AZUL = "\u001B[34m";
    private static final String VERDE = "\u001B[32m";
    private static final String VERMELHO = "\u001B[31m";
    private static final String AMARELO = "\u001B[33m";

    public Tabuleiro() {
        grade = new char[TAMANHO][TAMANHO];
        posicoesNavios = new Navio[TAMANHO][TAMANHO];
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                grade[i][j] = '~'; // Preenche com água
            }
        }
    }

    public boolean posicionarNavio(Navio navio, Coordenada inicio, boolean horizontal) {
        int l = inicio.getLinha();
        int c = inicio.getColuna();

        // Valida limites do tabuleiro
        if (horizontal && c + navio.getTamanho() > TAMANHO) {
            return false;
        }

        if (!horizontal && l + navio.getTamanho() > TAMANHO) {
            return false;
        }

        // Valida sobreposição
        for (int i = 0; i < navio.getTamanho(); i++) {
            if (horizontal) {
                if (posicoesNavios[l][c + i] != null) {
                    return false;
                }
            } else {
                if (posicoesNavios[l + i][c] != null) {
                    return false;
                }
            }
        }

        // Posiciona o navio
        for (int i = 0; i < navio.getTamanho(); i++) {
            if (horizontal) {
                posicoesNavios[l][c + i] = navio;
            } else {
                posicoesNavios[l + i][c] = navio;
            }
        }
        return true;
    }

    public String receberAtaque(Coordenada coord) {
        int l = coord.getLinha();
        int c = coord.getColuna();

        if (grade[l][c] == 'X' || grade[l][c] == 'O') {
            return "JA_ATACADO";
        }

        if (posicoesNavios[l][c] != null) {
            grade[l][c] = 'X';
            Navio navioAtingido = posicoesNavios[l][c];
            navioAtingido.receberDano();

            if (navioAtingido.estaDestruido()) {
                return "DESTRUÍDO: " + navioAtingido.getNome() + "!";
            }
            return "ACERTO!";
        } else {
            grade[l][c] = 'O';
            return "ÁGUA!";
        }
    }

    public void imprimirTabuleiro(boolean mostrarNavios, String titulo) {
        System.out.println("\n--- " + titulo + " ---");
        System.out.println("   1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < TAMANHO; i++) {
            char letraLinha = (char) ('A' + i);
            System.out.print(letraLinha + "  ");
            for (int j = 0; j < TAMANHO; j++) {
                char simbolo = grade[i][j];

                if (simbolo == '~' && mostrarNavios && posicoesNavios[i][j] != null) {
                    System.out.print(VERDE + "N " + RESETAR);
                } else if (simbolo == '~') {
                    System.out.print(AZUL + "~ " + RESETAR);
                } else if (simbolo == 'X') {
                    System.out.print(VERMELHO + "X " + RESETAR);
                } else if (simbolo == 'O') {
                    System.out.print(AMARELO + "O " + RESETAR);
                }
            }
            System.out.println();
        }
    }
}
