package org.games.tabuleiro.grid.main;

import org.games.tabuleiro.grid.service.JogoService;

public class Main {

    public static void main(String[] args) {
        JogoService jogo = new JogoService();
        jogo.iniciarJogo();
    }
}
