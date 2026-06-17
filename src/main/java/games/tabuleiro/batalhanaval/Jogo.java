package games.tabuleiro.batalhanaval;

import java.util.Scanner;

public class Jogo {
    private final Jogador jogador1;
    private final Jogador jogador2;
    private final Scanner leitor;

    public Jogo() {
        leitor = new Scanner(System.in);
        System.out.println("      BATALHA NAVAL EM JAVA      ");
        System.out.print("Digite seu nome, Almirante: ");
        String nome = leitor.nextLine();

        jogador1 = new JogadorHumano(nome, leitor);
        jogador2 = new JogadorComputador("Computador (IA)");
    }

    public void iniciar() {
        jogador2.posicionarNavios();
        jogador1.posicionarNavios();

        System.out.println("\n--- O JOGO VAI COMEÇAR! ---");

        boolean turnoJogador1 = true;

        while (!jogador1.perdeu() && !jogador2.perdeu()) {
            Jogador atacante = turnoJogador1 ? jogador1 : jogador2;
            Jogador defensor = turnoJogador1 ? jogador2 : jogador1;

            System.out.println("\n+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=");
            System.out.println("Turno de: " + atacante.getNome());

            if (atacante instanceof JogadorHumano) {
                defensor.getTabuleiro().imprimirTabuleiro(false, "Radar de Ataque (Inimigo)");
                atacante.getTabuleiro().imprimirTabuleiro(true, "Sua Frota");
            }

            boolean tiroValido = false;
            while (!tiroValido) {
                Coordenada alvo = atacante.escolherAtaque();
                String resultado = defensor.getTabuleiro().receberAtaque(alvo);

                if (resultado.equals("JA_ATACADO")) {
                    if (atacante instanceof JogadorHumano) {
                        System.out.println("Você já atirou nessa coordenada! Tente novamente.");
                    }
                } else {
                    tiroValido = true;
                    char letraLinha = (char) ('A' + alvo.getLinha());
                    int numColuna = alvo.getColuna() + 1;
                    System.out.println("\n> " + atacante.getNome() + " atirou em " + letraLinha + numColuna);
                    System.out.println("> Resultado: " + resultado);

                    atacante.notificarResultadoAtaque(alvo, resultado);
                }
            }

            turnoJogador1 = !turnoJogador1; // Alterna o turno

            if (atacante instanceof JogadorComputador) {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException ignorado) {}
            }
        }

        finalizarJogo();
    }

    private void finalizarJogo() {
        System.out.println(" >>>>>>>>>> FIM DE JOGO! <<<<<<<<<< ");
        if (jogador1.perdeu()) {
            System.out.println("A frota de " + jogador1.getNome() + " foi aniquilada. A IA venceu!");
        } else {
            System.out.println("Parabéns, " + jogador1.getNome() + "! Você destruiu a frota inimiga!");
        }

        jogador2.getTabuleiro().imprimirTabuleiro(true, "Frota Inimiga Destruída");
    }
}
