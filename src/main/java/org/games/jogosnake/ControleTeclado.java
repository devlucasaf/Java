package org.games.jogosnake;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ControleTeclado extends KeyAdapter {

    private JogoSnake jogo;

    public ControleTeclado(JogoSnake jogo) {
        this.jogo = jogo;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int tecla = e.getKeyCode();

        if (tecla == KeyEvent.VK_UP && jogo.getVelocidadeY() == 0) {
            jogo.setVelocidadeX(0);
            jogo.setVelocidadeY(-jogo.getTamanhoQuadrado());
        } else if (tecla == KeyEvent.VK_DOWN && jogo.getVelocidadeY() == 0) {
            jogo.setVelocidadeX(0);
            jogo.setVelocidadeY(jogo.getTamanhoQuadrado());
        } else if (tecla == KeyEvent.VK_LEFT && jogo.getVelocidadeX() == 0) {
            jogo.setVelocidadeX(-jogo.getTamanhoQuadrado());
            jogo.setVelocidadeY(0);
        } else if (tecla == KeyEvent.VK_RIGHT && jogo.getVelocidadeX() == 0) {
            jogo.setVelocidadeX(jogo.getTamanhoQuadrado());
            jogo.setVelocidadeY(0);
        }
    }
}