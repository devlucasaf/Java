package org.games.futbol11.main;

import org.games.futbol11.service.JogoService;

public class Main {

    public static void main(String[] args) {
        JogoService jogo = new JogoService();
        jogo.iniciarJogo();
    }
}
