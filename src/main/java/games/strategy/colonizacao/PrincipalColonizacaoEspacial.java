package games.strategy.colonizacao;

import java.util.Random;
import java.util.Scanner;

public class PrincipalColonizacaoEspacial {

    private static final Scanner    LEITOR = new Scanner(System.in);
    private static final Random     ALEATORIO = new Random();
    private static final int        PLANETAS = 5;
    private static final int        TURNOS = 15;
    private static final int        NINGUEM = 0;
    private static final int        JOGADOR = 1;
    private static final int        IA = 2;

    public static void main(String[] args) {
        int[] dono = new int[PLANETAS];
        int[] bases = new int[PLANETAS];
        dono[0] = JOGADOR;
        bases[0] = 2;
        dono[PLANETAS - 1] = IA;
        bases[PLANETAS - 1] = 2;

        int energiaJogador = 20;
        int minerioJogador = 20;
        int energiaInteligenciaArtificial = 20;
        int minerioInteligenciaArtificial = 20;

        System.out.println("=== COLONIZAÇÃO ESPACIAL ===");

        for (int turno = 1; turno <= TURNOS; turno++) {
            energiaJogador += producao(dono, bases, JOGADOR, 4);
            minerioJogador += producao(dono, bases, JOGADOR, 3);
            energiaInteligenciaArtificial += producao(dono, bases, IA, 4);
            minerioInteligenciaArtificial += producao(dono, bases, IA, 3);

            System.out.println("\nTurno " + turno);
            exibirSistema(dono, bases, energiaJogador, minerioJogador, energiaInteligenciaArtificial, minerioInteligenciaArtificial);

            int acao = lerInteiro("1-Colonizar 2-Expandir base 3-Atacar orbita: ", 1, 3);
            if (acao == 1) {
                int p = lerInteiro("Planeta alvo (1-5): ", 1, PLANETAS) - 1;
                if (dono[p] == NINGUEM && energiaJogador >= 8 && minerioJogador >= 8) {
                    dono[p] = JOGADOR;
                    bases[p] = 1;
                    energiaJogador -= 8;
                    minerioJogador -= 8;
                }
            } else if (acao == 2) {
                int p = lerInteiro("Seu planeta (1-5): ", 1, PLANETAS) - 1;
                if (dono[p] == JOGADOR && energiaJogador >= 6 && minerioJogador >= 10) {
                    bases[p]++;
                    energiaJogador -= 6;
                    minerioJogador -= 10;
                }
            } else {
                int p = lerInteiro("Planeta inimigo (1-5): ", 1, PLANETAS) - 1;
                if (dono[p] == IA && energiaJogador >= 10) {
                    energiaJogador -= 10;
                    int forcaAtaque = 2 + ALEATORIO.nextInt(4);
                    bases[p] -= forcaAtaque;
                    if (bases[p] <= 0) {
                        dono[p] = JOGADOR;
                        bases[p] = 1;
                    }
                }
            }

            int acaoIa = 1 + ALEATORIO.nextInt(3);
            if (acaoIa == 1) {
                int p = ALEATORIO.nextInt(PLANETAS);
                if (dono[p] == NINGUEM && energiaInteligenciaArtificial >= 8 && minerioInteligenciaArtificial >= 8) {
                    dono[p] = IA;
                    bases[p] = 1;
                    energiaInteligenciaArtificial -= 8;
                    minerioInteligenciaArtificial -= 8;
                }
            } else if (acaoIa == 2) {
                int p = ALEATORIO.nextInt(PLANETAS);
                if (dono[p] == IA && energiaInteligenciaArtificial >= 6 && minerioInteligenciaArtificial >= 10) {
                    bases[p]++;
                    energiaInteligenciaArtificial -= 6;
                    minerioInteligenciaArtificial -= 10;
                }
            } else {
                int p = ALEATORIO.nextInt(PLANETAS);
                if (dono[p] == JOGADOR && energiaInteligenciaArtificial >= 10) {
                    energiaInteligenciaArtificial -= 10;
                    bases[p] -= 2 + ALEATORIO.nextInt(4);
                    if (bases[p] <= 0) {
                        dono[p] = IA;
                        bases[p] = 1;
                    }
                }
            }
        }

        int influenciaJ = influencia(dono, bases, JOGADOR);
        int influenciaIa = influencia(dono, bases, IA);
        System.out.println("\nInfluencia final -> Voce: " + influenciaJ + " | IA: " + influenciaIa);
        if (influenciaJ > influenciaIa) {
            System.out.println("Vitoria galactica!");
        } else if (influenciaIa > influenciaJ) {
            System.out.println("Derrota galactica.");
        } else {
            System.out.println("Empate orbital.");
        }
    }

    private static int producao(int[] dono, int[] bases, int faccao, int multiplicador) {
        int total = 0;
        for (int i = 0; i < PLANETAS; i++) {
            if (dono[i] == faccao) {
                total += bases[i] * multiplicador;
            }
        }
        return total;
    }

    private static int influencia(int[] dono, int[] bases, int faccao) {
        int total = 0;
        for (int i = 0; i < PLANETAS; i++) {
            if (dono[i] == faccao) {
                total += 5 + bases[i] * 3;
            }
        }
        return total;
    }

    private static void exibirSistema(int[] dono, int[] bases, int energiaJogador, int minerioJogador,
                                      int energiaInteligenciaArtificial, int minerioInteligenciaArtificial) {
        for (int i = 0; i < PLANETAS; i++) {
            String ocupante = dono[i] == JOGADOR ? "Você" : dono[i] == IA ? "IA" : "Neutro";
            System.out.println("Planeta " + (i + 1) + ":");
            System.out.println("Ocupante: " + ocupante);
            System.out.println("Bases: " + bases[i]);
        }
        System.out.println("Recursos:");
        System.out.println("Você: E" + energiaJogador + " M" + minerioJogador);
        System.out.println("IA: E" + energiaInteligenciaArtificial + " M" + minerioInteligenciaArtificial);
    }

    private static int lerInteiro(String mensagem, int minimo, int maximo) {
        while (true) {
            System.out.print(mensagem);
            try {
                int valor = Integer.parseInt(LEITOR.nextLine().trim());
                if (valor >= minimo && valor <= maximo) {
                    return valor;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Valor invalido.");
        }
    }
}
