package games.sorteio.blackjack;

import java.util.Scanner;

public class JogoBlackjack {

    private final Scanner entrada = new Scanner(System.in);
    private final Baralho baralho = new Baralho();
    private double saldo;

    public static void main(String[] args) {
        new JogoBlackjack().iniciar();
    }

    public void iniciar() {
        exibirCabecalho();

        System.out.print("Saldo inicial (R$): ");
        saldo = lerDouble();

        while (saldo > 0) {
            System.out.printf("%n  Saldo: R$ %.2f%n", saldo);
            System.out.print("Aposta da rodada (0 para sair): R$ ");
            double aposta = lerDouble();
            if (aposta <= 0) {
                break;
            }

            if (aposta > saldo) {
                System.out.println("⚠ Saldo insuficiente.");
                continue;
            }
            jogarRodada(aposta);
        }

        System.out.printf("%nFim de jogo. Saldo final: R$ %.2f%n", saldo);
        System.out.println("Obrigado por jogar!");
    }

    private void exibirCabecalho() {
        System.out.println();
        System.out.println("==============================================");
        System.out.println("                BLACKJACK 21                  ");
        System.out.println("==============================================");
        System.out.println("  Vença o dealer chegando o mais perto de 21  ");
        System.out.println("  sem ultrapassar. Blackjack paga 3:2.        ");
        System.out.println("==============================================");
    }

    private void jogarRodada(double apostaInicial) {
        Mao jogador = new Mao();
        Mao dealer  = new Mao();

        jogador.receber(baralho.comprar());
        dealer.receber(baralho.comprar());
        jogador.receber(baralho.comprar());
        dealer.receber(baralho.comprar());

        saldo -= apostaInicial;
        double aposta = apostaInicial;

        System.out.println("\n--- Cartas distribuídas ---");
        System.out.println("Dealer:  " + dealer.toStringOcultando());
        System.out.println("Você:    " + jogador);

        if (jogador.isBlackjack()) {
            System.out.println("\n BLACKJACK natural!");
            System.out.println("Dealer:  " + dealer);
            if (dealer.isBlackjack()) {
                System.out.println("Empate (ambos blackjack). Aposta devolvida.");
                saldo += aposta;
            } else {
                double premio = aposta * 2.5;
                System.out.printf("Você ganhou R$ %.2f%n", premio - aposta);
                saldo += premio;
            }
            return;
        }

        boolean pode_dobrar = true;
        while (true) {
            System.out.print("\n[H]it, [S]tand");
            if (pode_dobrar && saldo >= aposta) {
                System.out.print(", [D]ouble");
            }
            System.out.print("? ");
            String comando = entrada.nextLine().trim().toLowerCase();
            pode_dobrar = false;

            if (comando.equals("h")) {
                jogador.receber(baralho.comprar());
                System.out.println("Você:    " + jogador);
                if (jogador.estourou()) {
                    System.out.println("Estourou! Você perdeu R$ " + aposta);
                    return;
                }

                if (jogador.valor() == 21) {
                    break;
                }
            } else if (comando.equals("s")) {
                break;
            } else if (comando.equals("d") && saldo >= aposta) {
                saldo -= aposta;
                aposta *= 2;
                jogador.receber(baralho.comprar());
                System.out.println("Aposta dobrada para R$ " + aposta);
                System.out.println("Você:    " + jogador);
                if (jogador.estourou()) {
                    System.out.println("Estourou! Você perdeu R$ " + aposta);
                    return;
                }
                break;
            } else {
                System.out.println("Comando inválido.");
            }
        }

        System.out.println("\n--- Turno do dealer ---");
        System.out.println("Dealer:  " + dealer);
        while (dealer.valor() < 17) {
            Carta nova = baralho.comprar();
            dealer.receber(nova);
            System.out.println("Dealer compra " + nova + "  → " + dealer);
        }

        int valorJogador = jogador.valor();
        int valorDealer  = dealer.valor();

        System.out.printf("%nVocê: %d  |  Dealer: %d%n", valorJogador, valorDealer);

        if (dealer.estourou() || valorJogador > valorDealer) {
            double premio = aposta * 2;
            System.out.printf("✔ Você venceu! Recebe R$ %.2f%n", premio);
            saldo += premio;
        } else if (valorJogador < valorDealer) {
            System.out.printf("✘ Dealer venceu. Você perdeu R$ %.2f%n", aposta);
        } else {
            System.out.println("= Empate. Aposta devolvida.");
            saldo += aposta;
        }
    }

    private double lerDouble() {
        while (true) {
            String linha = entrada.nextLine().trim().replace(',', '.');
            try {
                return Double.parseDouble(linha);
            } catch (NumberFormatException e) {
                System.out.print("Valor inválido, digite novamente: ");
            }
        }
    }
}

