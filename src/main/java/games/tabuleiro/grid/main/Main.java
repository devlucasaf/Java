package games.tabuleiro.grid.main;

import games.tabuleiro.grid.service.JogoService;

public class Main {

    public static void main(String[] args) {
        JogoService jogo = new JogoService();
        jogo.iniciarJogo();
    }
}
