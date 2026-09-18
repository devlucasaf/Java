package games.strategy.reidacolina;

import java.util.Random;
import java.util.Scanner;

public class PrincipalReiDaColina {

    private static final Scanner    LEITOR          = new Scanner(System.in);
    private static final Random     ALEATORIO       = new Random();
    private static final int        PONTOS_VITORIA  = 15;

    public static void main(String[] args) {
        int[] reservas = {12, 12};
        int[][] colinas = new int[3][2];
        int pontuacaoJogador = 0;
        int pontuacaoIa = 0;
        int turno = 1;

        System.out.println("=== REI DA COLINA ===");

        while (pontuacaoJogador < PONTOS_VITORIA && pontuacaoIa < PONTOS_VITORIA) {
            System.out.println("\nTurno " + turno);
            exibirEstado(colinas, reservas, pontuacaoJogador, pontuacaoIa);
            turnoJogador(colinas, reservas);
            turnoInteligenciaArtificial(colinas, reservas);

            for (int i = 0; i < 3; i++) {
                if (colinas[i][0] > colinas[i][1]) {
                    pontuacaoJogador++;
                } else if (colinas[i][1] > colinas[i][0]) {
                    pontuacaoIa++;
                }
            }

            reservas[0] += 2;
            reservas[1] += 2;
            turno++;
        }

        if (pontuacaoJogador > pontuacaoIa) {
            System.out.println("Vitoria! Voce dominou as colinas.");
        } else {
            System.out.println("Derrota. A IA manteve o controle do terreno.");
        }
    }

    private static void turnoJogador(int[][] colinas, int[] reservas) {
        System.out.println("Reserva atual: " + reservas[0]);
        int alvo = lerInteiro("Enviar reforcos para qual colina? (1-3): ", 1, 3) - 1;
        int reforcos = lerInteiro("Quantidade de reforcos: ", 0, reservas[0]);
        colinas[alvo][0] += reforcos;
        reservas[0] -= reforcos;

        int origem = lerInteiro("Mover tropas de qual colina? (1-3): ", 1, 3) - 1;
        int destino = lerInteiro("Mover para qual colina? (1-3): ", 1, 3) - 1;
        if (origem == destino) {
            return;
        }
        int mover = lerInteiro("Quantidade para mover: ", 0, colinas[origem][0]);
        colinas[origem][0] -= mover;
        colinas[destino][0] += mover;
    }

    private static void turnoInteligenciaArtificial(int[][] colinas, int[] reservas) {
        int alvo = ALEATORIO.nextInt(3);
        int reforcos = Math.min(reservas[1], 1 + ALEATORIO.nextInt(3));
        colinas[alvo][1] += reforcos;
        reservas[1] -= reforcos;

        int origem = ALEATORIO.nextInt(3);
        int destino = ALEATORIO.nextInt(3);
        while (destino == origem) {
            destino = ALEATORIO.nextInt(3);
        }
        int mover = colinas[origem][1] / 2;
        colinas[origem][1] -= mover;
        colinas[destino][1] += mover;
    }

    private static void exibirEstado(int[][] colinas, int[] reservas, int pj, int pi) {
        for (int i = 0; i < 3; i++) {
            System.out.println("Colina " + (i + 1) + ":");
            System.out.println("Você: " + colinas[i][0]);
            System.out.println("IA: " + colinas[i][1]);
        }
        System.out.println("Reservas:");
        System.out.println("Você: " + reservas[0]);
        System.out.println("IA: " + reservas[1]);
        System.out.println("Pontos:");
        System.out.println("Você: " + pj);
        System.out.println("IA: " + pi);
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
