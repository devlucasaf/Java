package games.strategy.infiltracao;

import java.util.Scanner;

public class PrincipalTaticaInfiltracao {

    private static final Scanner LEITOR = new Scanner(System.in);
    private static final int TAMANHO = 8;

    public static void main(String[] args) {
        int agenteL = 7;
        int agenteC = 0;
        int extraL = 0;
        int extraC = 7;
        int intelL = 3;
        int intelC = 4;
        int guarda1L = 2;
        int guarda1C = 2;
        int guarda2L = 5;
        int guarda2C = 5;
        boolean intelColetada = false;

        System.out.println("=== TÁTICA DE INFILTRAÇÃO ===");
        System.out.println("Objetivo: pegar a inteligência (I) e extrair em (E).");

        for (int turno = 1; turno <= 30; turno++) {
            exibirMapa(agenteL, agenteC, extraL, extraC, intelL, intelC, guarda1L, guarda1C, guarda2L, guarda2C, intelColetada);
            System.out.print("Mover (W/A/S/D): ");
            String comando = LEITOR.nextLine().trim().toUpperCase();
            if (comando.equals("W")) {
                agenteL--;
            } else if (comando.equals("S")) {
                agenteL++;
            } else if (comando.equals("A")) {
                agenteC--;
            } else if (comando.equals("D")) {
                agenteC++;
            }
            agenteL = limitar(agenteL);
            agenteC = limitar(agenteC);

            guarda1C += guarda1L % 2 == 0 ? 1 : -1;
            if (guarda1C < 1) {
                guarda1C = 1;
                guarda1L++;
            } else if (guarda1C > 6) {
                guarda1C = 6;
                guarda1L++;
            }
            guarda1L = limitar(guarda1L);

            guarda2L += guarda2C % 2 == 0 ? 1 : -1;
            if (guarda2L < 1) {
                guarda2L = 1;
                guarda2C--;
            } else if (guarda2L > 6) {
                guarda2L = 6;
                guarda2C--;
            }
            guarda2C = limitar(guarda2C);

            if (agenteL == intelL && agenteC == intelC) {
                intelColetada = true;
                System.out.println("Inteligência coletada.");
            }

            if (detectado(agenteL, agenteC, guarda1L, guarda1C) || detectado(agenteL, agenteC, guarda2L, guarda2C)) {
                System.out.println("Você foi detectado. Missão falhou.");
                return;
            }

            if (intelColetada && agenteL == extraL && agenteC == extraC) {
                System.out.println("Extração concluída. Missão completa!");
                return;
            }
        }

        System.out.println("Tempo esgotado. A janela de extração foi perdida.");
    }

    private static boolean detectado(int al, int ac, int gl, int gc) {
        return Math.abs(al - gl) + Math.abs(ac - gc) <= 1 || (al == gl && Math.abs(ac - gc) <= 2) || (ac == gc && Math.abs(al - gl) <= 2);
    }

    private static int limitar(int v) {
        if (v < 0) {
            return 0;
        }
        return Math.min(v, TAMANHO - 1);
    }

    private static void exibirMapa(int al, int ac, int el, int ec, int il, int ic, int g1l, int g1c, int g2l, int g2c, boolean intelColetada) {
        System.out.println();
        for (int l = 0; l < TAMANHO; l++) {
            for (int c = 0; c < TAMANHO; c++) {
                char marca = '.';
                if (l == al && c == ac) {
                    marca = 'A';
                } else if (!intelColetada && l == il && c == ic) {
                    marca = 'I';
                } else if (l == el && c == ec) {
                    marca = 'E';
                } else if ((l == g1l && c == g1c) || (l == g2l && c == g2c)) {
                    marca = 'G';
                }
                System.out.print("[" + marca + "]");
            }
            System.out.println();
        }
    }
}
