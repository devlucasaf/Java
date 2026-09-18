package games.strategy.suprimento;

import java.util.Random;
import java.util.Scanner;

public class PrincipalLinhaSuprimento {

    private static final Scanner LEITOR = new Scanner(System.in);
    private static final Random ALEATORIO = new Random();
    private static final int ROTAS = 3;
    private static final int TURNOS = 12;

    public static void main(String[] args) {
        int[] integridadeJogador = {80, 80, 80};
        int[] integridadeIa = {80, 80, 80};
        int pontosJogador = 0;
        int pontosInteligenciaArtificial = 0;

        System.out.println("=== LINHA DE SUPRIMENTO ===");

        for (int turno = 1; turno <= TURNOS; turno++) {
            System.out.println("\nTurno " + turno);
            exibirEstado(integridadeJogador, integridadeIa, pontosJogador, pontosInteligenciaArtificial);
            turnoJogador(integridadeJogador, integridadeIa);
            turnoInteligenciaArtificial(integridadeJogador, integridadeIa);

            pontosJogador += contarAtivas(integridadeJogador);
            pontosInteligenciaArtificial += contarAtivas(integridadeIa);
        }

        System.out.println("\nPontuação final:");
        System.out.println("Você: " + pontosJogador);
        System.out.println("IA: " + pontosInteligenciaArtificial);
        if (pontosJogador > pontosInteligenciaArtificial) {
            System.out.println("Vitória! Sua logística superou o inimigo.");
        } else if (pontosInteligenciaArtificial > pontosJogador) {
            System.out.println("Derrota. O inimigo manteve as melhores rotas.");
        } else {
            System.out.println("Empate logístico.");
        }
    }

    private static void turnoJogador(int[] rotasJogador, int[] rotasInimigas) {
        System.out.println("1. Reparar rota (+20)");
        System.out.println("2. Sabotar rota inimiga (-20)");
        int acao = lerInteiro("Escolha: ", 1, 2);
        int rota = lerInteiro("Rota alvo (1-3): ", 1, ROTAS) - 1;

        if (acao == 1) {
            rotasJogador[rota] = limitar(rotasJogador[rota] + 20);
        } else {
            rotasInimigas[rota] = limitar(rotasInimigas[rota] - 20);
        }
    }

    private static void turnoInteligenciaArtificial(int[] rotasJogador, int[] rotasInimigas) {
        int acao = ALEATORIO.nextBoolean() ? 1 : 2;
        int rota = ALEATORIO.nextInt(ROTAS);
        if (acao == 1) {
            rotasInimigas[rota] = limitar(rotasInimigas[rota] + 18);
        } else {
            rotasJogador[rota] = limitar(rotasJogador[rota] - 18);
        }
    }

    private static int contarAtivas(int[] rotas) {
        int total = 0;
        for (int rota : rotas) {
            if (rota >= 60) {
                total++;
            }
        }
        return total;
    }

    private static int limitar(int valor) {
        if (valor < 0) {
            return 0;
        }
        return Math.min(valor, 100);
    }

    private static void exibirEstado(int[] rotasJogador, int[] rotasInimigas, int pontosJogador, int pontosInteligenciaArtificial) {
        for (int i = 0; i < ROTAS; i++) {
            System.out.println("Rota " + (i + 1) + " -> Sua: " + rotasJogador[i] + "%");
            System.out.println("Rota " + (i + 1) + " -> Inimiga: " + rotasInimigas[i] + "%");
            System.out.println("Status da rota " + (i + 1) + ": " + (rotasJogador[i] >= 60 ? "ATIVA" : "CRÍTICA"));
        }
        System.out.println("Rotas ativas (>=60%):");
        System.out.println("Você: " + contarAtivas(rotasJogador));
        System.out.println("IA: " + contarAtivas(rotasInimigas));
        System.out.println("Pontos acumulados:");
        System.out.println("Você: " + pontosJogador);
        System.out.println("IA: " + pontosInteligenciaArtificial);
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
            System.out.println("Valor inválido. Faixa permitida: " + minimo + " a " + maximo + ".");
        }
    }
}
