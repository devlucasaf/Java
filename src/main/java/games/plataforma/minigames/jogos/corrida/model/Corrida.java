package games.plataforma.minigames.jogos.corrida.model;

import java.util.Random;

public class Corrida {
    private int     jogadorX;
    private int     jogadorY;
    private int     obstaculoX;
    private int     obstaculoY;
    private int     pontuacao;
    private boolean gameOver;
    private int     largura;
    private int     altura;
    private int     velocidade;
    private Random  rand;

    public Corrida(int largura, int altura) {
        this.largura = largura;
        this.altura = altura;
        rand = new Random();
        iniciar();
    }

    public void iniciar() {
        jogadorX = largura / 2;
        jogadorY = altura - 2;
        pontuacao = 0;
        gameOver = false;
        velocidade = 1;
        gerarObstaculo();
    }

    public void moverJogador(int dx) {
        if (gameOver) {
            return;
        }

        jogadorX += dx;
        if (jogadorX < 0) {
            jogadorX = 0;
        }

        if (jogadorX >= largura) {
            jogadorX = largura - 1;
        }
    }

    public void atualizar() {
        if (gameOver) {
            return;
        }

        obstaculoY += velocidade;
        if (obstaculoY >= altura) {
            pontuacao++;
            gerarObstaculo();
            if (pontuacao % 5 == 0) {
                velocidade++;
            }
        }

        if (obstaculoY == jogadorY && obstaculoX == jogadorX) {
            gameOver = true;
        }
    }

    private void gerarObstaculo() {
        obstaculoX = rand.nextInt(largura);
        obstaculoY = 0;
    }

    public int getJogadorX() {
        return jogadorX;
    }

    public int getJogadorY() {
        return jogadorY;
    }

    public int getObstaculoX() {
        return obstaculoX;
    }

    public int getObstaculoY() {
        return obstaculoY;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }

    public int getVelocidade() {
        return velocidade;
    }
}