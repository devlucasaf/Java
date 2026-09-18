package games.strategy.cercoaocastelo;

import java.util.Random;
import java.util.Scanner;

public class PrincipalCercoAoCastelo {

    private static final Scanner LEITOR = new Scanner(System.in);
    private static final Random ALEATORIO = new Random();

    public static void main(String[] args) {
        int muralha = 120;
        int defensores = 80;
        int suprimentosDefesa = 70;

        int tropasAtacante = 110;
        int madeiraCerco = 30;
        int suprimentosAtaque = 70;

        System.out.println("=== CERCO AO CASTELO ===");

        for (int turno = 1; turno <= 15; turno++) {
            System.out.println("\nTurno " + turno);
            exibirEstado(muralha, defensores, suprimentosDefesa, tropasAtacante, madeiraCerco, suprimentosAtaque);

            int acao = lerInteiro("1-Assaltar 2-Construir máquinas 3-Coletar suprimentos: ", 1, 3);
            if (acao == 1) {
                int dano = 10 + madeiraCerco / 6 + ALEATORIO.nextInt(8);
                muralha -= dano;
                tropasAtacante -= 6 + ALEATORIO.nextInt(6);
                System.out.println("Assalto! Dano na muralha: " + dano);
            } else if (acao == 2) {
                madeiraCerco += 12;
                tropasAtacante -= 2;
                System.out.println("Máquinas de cerco reforçadas.");
            } else {
                suprimentosAtaque += 14;
                System.out.println("Suprimentos coletados.");
            }

            suprimentosAtaque -= 8;
            if (suprimentosAtaque < 0) {
                tropasAtacante += suprimentosAtaque;
                suprimentosAtaque = 0;
            }

            int resposta = ALEATORIO.nextInt(3);
            if (resposta == 0) {
                muralha += 8;
                suprimentosDefesa -= 6;
                System.out.println("Defensores reforçaram a muralha.");
            } else if (resposta == 1) {
                int danoSortida = 8 + ALEATORIO.nextInt(8);
                tropasAtacante -= danoSortida;
                defensores -= 4;
                System.out.println("Sortida inimiga causou " + danoSortida + " perdas.");
            } else {
                madeiraCerco = Math.max(0, madeiraCerco - 10);
                System.out.println("Armadilhas queimaram parte das máquinas de cerco.");
            }

            suprimentosDefesa -= 7;
            if (suprimentosDefesa < 0) {
                defensores += suprimentosDefesa;
                suprimentosDefesa = 0;
            }

            muralha = Math.max(0, muralha);
            defensores = Math.max(0, defensores);
            tropasAtacante = Math.max(0, tropasAtacante);

            if (muralha == 0 || defensores == 0) {
                System.out.println("Vitória do atacante! O castelo caiu.");
                return;
            }
            if (tropasAtacante == 0) {
                System.out.println("Derrota do atacante. O cerco fracassou.");
                return;
            }
        }

        System.out.println("Tempo esgotado. A defesa resistiu ao cerco.");
    }

    private static void exibirEstado(int muralha, int defensores, int supDef, int tropas, int madeira, int supAtaque) {
        System.out.println("Castelo:");
        System.out.println("Muralha: " + muralha);
        System.out.println("Defensores: " + defensores);
        System.out.println("Suprimentos: " + supDef);
        System.out.println("Atacante:");
        System.out.println("Tropas: " + tropas);
        System.out.println("Madeira de cerco: " + madeira);
        System.out.println("Suprimentos: " + supAtaque);
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
            System.out.println("Valor inválido.");
        }
    }
}
