package org.games.tabuleiro.campominado;

import java.util.Scanner;

public class Jogo {

    private Tabuleiro   tabuleiro;
    private Scanner     scanner;
    private int         jogadas = 0;
    private long        inicio;

    public Jogo(int linhas, int colunas, int minas) {
        this.tabuleiro = new Tabuleiro(linhas, colunas, minas);
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        boolean fim = false;
        inicio = System.currentTimeMillis();

        while (!fim) {
            tabuleiro.mostrar(false);
            System.out.print("\nComando (R linha col | F linha col): ");

            String acao = scanner.next().toUpperCase();
            int l = scanner.nextInt();
            int c = scanner.nextInt();

            if (l < 0 || c < 0) {
                continue;
            }

            if (!tabuleiro.minasGeradas()) {
                tabuleiro.gerarMinas(l, c);
            }

            jogadas++;

            if (acao.equals("R")) {
                boolean explodiu = tabuleiro.revelar(l, c);
                if (explodiu) {
                    tabuleiro.mostrar(true);
                    System.out.println("\nVocê perdeu!");
                    fim = true;
                }
            } else if (acao.equals("F")) {
                tabuleiro.alternarBandeira(l, c);
            }

            if (tabuleiro.venceu()) {
                tabuleiro.mostrar(true);
                System.out.println("\nVocê venceu!");
                fim = true;
            }
        }

        long tempo = (System.currentTimeMillis() - inicio) / 1000;
        System.out.println("Tempo: " + tempo + "s | Jogadas: " + jogadas);
    }
}
