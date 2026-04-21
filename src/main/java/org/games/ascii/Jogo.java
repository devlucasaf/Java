package org.games.ascii;

import java.io.IOException;
import java.util.Scanner;

class Jogo {

    private final int LARGURA_TELA = 60;
    private final int ALTURA_TELA = 15;
    private final int FPS = 15;

    private boolean jogando = true;
    private long pontuacao = 0;

    private Jogador jogador;
    private Fase    fase;
    private Scanner scanner;

    public Jogo() {
        jogador = new Jogador(5, ALTURA_TELA - 3);
        fase = new Fase(ALTURA_TELA);
        scanner = new Scanner(System.in);
    }

    public void iniciar() throws Exception {
        mostrarMenu();
        loopDeJogo();
        mostrarGameOver();
    }

    private void mostrarMenu() {
        limparTela();
        System.out.println(">>> JOGO DE PLATAFORMA ASCII <<<");
        System.out.println("Controles:");
        System.out.println("A = esquerda | D = direita");
        System.out.println("W ou ESPAÇO = pular");
        System.out.println("\nPressione ENTER para começar...");
        scanner.nextLine();
    }

    private void loopDeJogo() throws Exception {
        long tempoFrame = 1000 / FPS;

        while (jogando) {
            long inicio = System.currentTimeMillis();

            processarEntrada();
            atualizar();
            renderizar();

            long duracao = System.currentTimeMillis() - inicio;
            Thread.sleep(Math.max(0, tempoFrame - duracao));
        }
    }

    private void processarEntrada() throws IOException {
        if (System.in.available() > 0) {
            char tecla = (char) System.in.read();

            if (tecla == 'a' || tecla == 'A') {
                jogador.moverEsquerda();
            }

            if (tecla == 'd' || tecla == 'D') {
                jogador.moverDireita();
            }

            if (tecla == 'w' || tecla == 'W' || tecla == ' ') {
                jogador.pular();
            }
        }
    }

    private void atualizar() {
        fase.moverCamera();
        jogador.aplicarGravidade();

        int xMundo = jogador.x + fase.cameraX;

        if (fase.ehChao(xMundo, jogador.y + 1)) {
            jogador.aterrissar();
        }

        if (fase.ehBuraco(xMundo, jogador.y + 1)) {
            jogando = false;
        }

        if (fase.ehObstaculo(xMundo, jogador.y)) {
            jogando = false;
        }

        pontuacao++;
    }

    private void renderizar() {
        limparTela();
        char[][] tela = fase.obterMapaVisivel(LARGURA_TELA);

        if (jogador.y >= 0 && jogador.y < ALTURA_TELA &&
                jogador.x >= 0 && jogador.x < LARGURA_TELA) {
            tela[jogador.y][jogador.x] = 'P';
        }

        for (char[] linha : tela) {
            System.out.println(linha);
        }

        System.out.println("\nPontuação: " + pontuacao);
    }

    private void mostrarGameOver() {
        limparTela();
        System.out.println("===== FIM DE JOGO =====");
        System.out.println("Pontuação final: " + pontuacao);
    }

    private void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

