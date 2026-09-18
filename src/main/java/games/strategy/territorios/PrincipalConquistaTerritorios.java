package games.strategy.territorios;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class PrincipalConquistaTerritorios {

    private static final Scanner    LEITOR = new Scanner(System.in);
    private static final Random     ALEATORIO = new Random();
    private static final int        TAMANHO = 5;
    private static final int        TURNOS_MAXIMOS = 20;
    private static final int        JOGADOR = 1;
    private static final int        IA = 2;

    public static void main(String[] args) {
        int[][] dono = new int[TAMANHO][TAMANHO];
        int[][] tropas = new int[TAMANHO][TAMANHO];

        dono[0][0] = JOGADOR;
        tropas[0][0] = 8;
        dono[TAMANHO - 1][TAMANHO - 1] = IA;
        tropas[TAMANHO - 1][TAMANHO - 1] = 8;

        exibirTitulo();

        for (int turno = 1; turno <= TURNOS_MAXIMOS; turno++) {
            reforcar(dono, tropas, JOGADOR);
            exibirMapa(dono, tropas, turno);
            turnoJogador(dono, tropas);

            if (contarTerritorios(dono, IA) == 0) {
                System.out.println("Vitoria! Voce conquistou todos os territorios inimigos.");
                return;
            }

            reforcar(dono, tropas, IA);
            turnoInteligenciaArtificial(dono, tropas);

            if (contarTerritorios(dono, JOGADOR) == 0) {
                System.out.println("Derrota. O inimigo dominou o mapa.");
                return;
            }
        }

        int territoriosJogador = contarTerritorios(dono, JOGADOR);
        int territoriosInimigo = contarTerritorios(dono, IA);
        if (territoriosJogador > territoriosInimigo) {
            System.out.println("Fim dos turnos. Vitoria por territorios: " + territoriosJogador + " x " + territoriosInimigo + ".");
        } else if (territoriosInimigo > territoriosJogador) {
            System.out.println("Fim dos turnos. Derrota por territorios: " + territoriosJogador + " x " + territoriosInimigo + ".");
        } else {
            System.out.println("Fim dos turnos. Empate em territorios: " + territoriosJogador + " x " + territoriosInimigo + ".");
        }
    }

    private static void turnoJogador(int[][] dono, int[][] tropas) {
        while (true) {
            System.out.println("Digite: linhaOrigem colunaOrigem linhaDestino colunaDestino unidades");
            System.out.print("Ou -1 para passar: ");
            String entrada = LEITOR.nextLine().trim();
            if (entrada.equals("-1")) {
                return;
            }

            String[] partes = entrada.split("\\s+");
            if (partes.length != 5) {
                System.out.println("Entrada inválida.");
                continue;
            }

            try {
                int linhaOrigem = Integer.parseInt(partes[0]) - 1;
                int colunaOrigem = Integer.parseInt(partes[1]) - 1;
                int linhaDestino = Integer.parseInt(partes[2]) - 1;
                int colunaDestino = Integer.parseInt(partes[3]) - 1;
                int unidades = Integer.parseInt(partes[4]);

                if (!dentro(linhaOrigem, colunaOrigem) || !dentro(linhaDestino, colunaDestino)) {
                    throw new IllegalArgumentException("Posição fora do mapa.");
                }

                if (dono[linhaOrigem][colunaOrigem] != JOGADOR) {
                    throw new IllegalArgumentException("A origem precisa ser sua.");
                }

                if (distancia(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino) != 1) {
                    throw new IllegalArgumentException("Mova apenas para casas adjacentes.");
                }

                if (unidades <= 0 || unidades >= tropas[linhaOrigem][colunaOrigem]) {
                    throw new IllegalArgumentException("Quantidade inválida de unidades.");
                }

                atacar(dono, tropas, linhaOrigem, colunaOrigem, linhaDestino, colunaDestino, unidades, JOGADOR);
                return;
            } catch (NumberFormatException excecao) {
                System.out.println("Use apenas números.");
            } catch (IllegalArgumentException excecao) {
                System.out.println(excecao.getMessage());
            }
        }
    }

    private static void turnoInteligenciaArtificial(int[][] dono, int[][] tropas) {
        List<int[]> opcoes = new ArrayList<>();
        for (int l = 0; l < TAMANHO; l++) {
            for (int c = 0; c < TAMANHO; c++) {
                if (dono[l][c] == IA && tropas[l][c] >= 2) {
                    opcoes.add(new int[]{l, c});
                }
            }
        }
        if (opcoes.isEmpty()) {
            return;
        }

        int[] origem = opcoes.get(ALEATORIO.nextInt(opcoes.size()));
        int linhaOrigem = origem[0];
        int colunaOrigem = origem[1];
        int[][] vizinhos = {
                {linhaOrigem + 1, colunaOrigem},
                {linhaOrigem - 1, colunaOrigem},
                {linhaOrigem, colunaOrigem + 1},
                {linhaOrigem, colunaOrigem - 1}
        };
        List<int[]> destinos = new ArrayList<>();
        for (int[] v : vizinhos) {
            if (dentro(v[0], v[1])) {
                destinos.add(v);
            }
        }
        int[] destino = destinos.get(ALEATORIO.nextInt(destinos.size()));
        int unidades = Math.max(1, tropas[linhaOrigem][colunaOrigem] / 2);
        atacar(dono, tropas, linhaOrigem, colunaOrigem, destino[0], destino[1], unidades, IA);
    }

    private static void atacar(int[][] dono, int[][] tropas, int linhaOrigem, int colunaOrigem,
                               int linhaDestino, int colunaDestino, int unidades, int atacante) {
        tropas[linhaOrigem][colunaOrigem] -= unidades;
        if (dono[linhaDestino][colunaDestino] == atacante || dono[linhaDestino][colunaDestino] == 0) {
            if (dono[linhaDestino][colunaDestino] != atacante) {
                dono[linhaDestino][colunaDestino] = atacante;
            }
            tropas[linhaDestino][colunaDestino] += unidades;
            return;
        }

        if (unidades > tropas[linhaDestino][colunaDestino]) {
            int sobra = unidades - tropas[linhaDestino][colunaDestino];
            dono[linhaDestino][colunaDestino] = atacante;
            tropas[linhaDestino][colunaDestino] = sobra;
        } else {
            tropas[linhaDestino][colunaDestino] -= unidades;
            if (tropas[linhaDestino][colunaDestino] == 0) {
                dono[linhaDestino][colunaDestino] = 0;
            }
        }
    }

    private static void reforcar(int[][] dono, int[][] tropas, int faccao) {
        int reforco = 3 + bonusRegiao(dono, faccao);
        List<int[]> celulas = new ArrayList<>();
        for (int l = 0; l < TAMANHO; l++) {
            for (int c = 0; c < TAMANHO; c++) {
                if (dono[l][c] == faccao) {
                    celulas.add(new int[]{l, c});
                }
            }
        }

        for (int i = 0; i < reforco && !celulas.isEmpty(); i++) {
            int[] p = celulas.get(ALEATORIO.nextInt(celulas.size()));
            tropas[p[0]][p[1]]++;
        }
    }

    private static int bonusRegiao(int[][] dono, int faccao) {
        int bonus = 0;
        for (int l = 0; l < TAMANHO; l++) {
            boolean linhaCompleta = true;
            for (int c = 0; c < TAMANHO; c++) {
                if (dono[l][c] != faccao) {
                    linhaCompleta = false;
                    break;
                }
            }

            if (linhaCompleta) {
                bonus += 2;
            }
        }
        return bonus;
    }

    private static void exibirMapa(int[][] dono, int[][] tropas, int turno) {
        System.out.println("\n=== CONQUISTA DE TERRITÓRIOS | TURNO " + turno + " ===");
        for (int l = 0; l < TAMANHO; l++) {
            for (int c = 0; c < TAMANHO; c++) {
                char marca = '.';
                if (dono[l][c] == JOGADOR) {
                    marca = 'J';
                } else if (dono[l][c] == IA) {
                    marca = 'I';
                }
                System.out.print("[" + marca + ":" + tropas[l][c] + "]");
            }
            System.out.println();
        }
        System.out.println("Territórios:");
        System.out.println("Você: " + contarTerritorios(dono, JOGADOR));
        System.out.println("IA: " + contarTerritorios(dono, IA));
    }

    private static int contarTerritorios(int[][] dono, int faccao) {
        int total = 0;
        for (int l = 0; l < TAMANHO; l++) {
            for (int c = 0; c < TAMANHO; c++) {
                if (dono[l][c] == faccao) {
                    total++;
                }
            }
        }
        return total;
    }

    private static int distancia(int l1, int c1, int l2, int c2) {
        return Math.abs(l1 - l2) + Math.abs(c1 - c2);
    }

    private static boolean dentro(int l, int c) {
        return l >= 0 && c >= 0 && l < TAMANHO && c < TAMANHO;
    }

    private static void exibirTitulo() {
        System.out.println("==============================================");
        System.out.println("         CONQUISTA DE TERRITÓRIOS");
        System.out.println("==============================================");
    }
}
