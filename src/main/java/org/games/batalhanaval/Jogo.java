package org.games.batalhanaval;

import java.util.Scanner;

public class Jogo {
    private final Jogador j1;
    private final Jogador j2;
    private final Scanner leitor;

    public Jogo() {
        leitor = new Scanner(System.in);
        System.out.println("      BATALHA NAVAL EM JAVA      ");
        System.out.print("Digite seu nome, Almirante: ");
        String nome = leitor.nextLine();

        j1 = new JogadorHumano(nome, leitor);
        j2 = new JogadorComputador("Computador (IA)");
    }

    public void iniciar() {
        j2.posicionarNavios();
        j1.posicionarNavios();

        System.out.println("\n--- O JOGO VAI COMEÇAR! ---");

        boolean turnoJ1 = true;

        while (!j1.perdeu() && !j2.perdeu()) {
            Jogador atacante = turnoJ1 ? j1 : j2;
            Jogador defensor = turnoJ1 ? j2 : j1;

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
                }

                else {
                    tiroValido = true;
                    char letraLinha = (char) ('A' + alvo.getLinha());
                    int numColuna = alvo.getColuna() + 1;
                    System.out.println("\n> " + atacante.getNome() + " atirou em " + letraLinha + numColuna);
                    System.out.println("> Resultado: " + resultado);

                    atacante.notificarResultadoAtaque(alvo, resultado);
                }
            }

            turnoJ1 = !turnoJ1; // Alterna o turno

            if (atacante instanceof JogadorComputador) {
                try {
                    Thread.sleep(1500);
                }

                catch (InterruptedException ignorado) {}
            }
        }

        finalizarJogo();
    }

    private void finalizarJogo() {
        System.out.println(" >>>>>>>>>> FIM DE JOGO! <<<<<<<<<< ");
        if (j1.perdeu()) {
            System.out.println("A frota de " + j1.getNome() + " foi aniquilada. A IA venceu!");
        }

        else {
            System.out.println("Parabéns, " + j1.getNome() + "! Você destruiu a frota inimiga!");
        }

        j2.getTabuleiro().imprimirTabuleiro(true, "Frota Inimiga Destruída");
    }
}
