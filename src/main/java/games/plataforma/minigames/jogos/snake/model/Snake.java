package games.plataforma.minigames.jogos.snake.model;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Random;

public class Snake {
    private LinkedList<Point>   corpo;
    private Direcao             direcao;
    private Direcao             proximaDirecao;
    private Point               comida;
    private boolean             gameOver;
    private boolean             venceu;
    private int                 largura;
    private int                 altura;
    private int                 pontuacao;

    public Snake(int largura, int altura) {
        this.largura = largura;
        this.altura = altura;
        iniciar();
    }

    public void iniciar() {
        corpo = new LinkedList<>();
        corpo.add(new Point(largura/2, altura/2));
        direcao = Direcao.DIREITA;
        proximaDirecao = Direcao.DIREITA;
        gameOver = false;
        venceu = false;
        pontuacao = 0;
        gerarComida();
    }

    public void setDirecao(Direcao dir) {
        if ((dir == Direcao.SUBIR && direcao != Direcao.DESCER) ||
                (dir == Direcao.DESCER && direcao != Direcao.SUBIR) ||
                (dir == Direcao.ESQUERDA && direcao != Direcao.DIREITA) ||
                (dir == Direcao.DIREITA && direcao != Direcao.ESQUERDA)) {
            proximaDirecao = dir;
        }
    }

    public void mover() {
        if (gameOver) {
            return;
        }
        direcao = proximaDirecao;

        Point cabeca = corpo.getFirst();
        Point novaCabeca = new Point(cabeca.x, cabeca.y);
        switch (direcao) {
            case SUBIR:
                novaCabeca.y--;
                break;
            case DESCER:
                novaCabeca.y++;
                break;
            case ESQUERDA:
                novaCabeca.x--;
                break;
            case DIREITA:
                novaCabeca.x++;
                break;
        }

        if (novaCabeca.x < 0 || novaCabeca.x >= largura || novaCabeca.y < 0 || novaCabeca.y >= altura) {
            gameOver = true;
            return;
        }

        boolean comeu = novaCabeca.equals(comida);
        if (comeu) {
            pontuacao++;
            corpo.addFirst(novaCabeca);
            gerarComida();
            if (corpo.size() == largura * altura) {
                venceu = true;
                gameOver = true;
            }
        } else {
            corpo.addFirst(novaCabeca);
            corpo.removeLast();
        }

        Point cabecaFinal = corpo.getFirst();
        for (int i = 1; i < corpo.size(); i++) {
            if (corpo.get(i).equals(cabecaFinal)) {
                gameOver = true;
                break;
            }
        }
    }

    private void gerarComida() {
        Random rand = new Random();
        int tentativas = 0;
        do {
            int x = rand.nextInt(largura);
            int y = rand.nextInt(altura);
            comida = new Point(x, y);
            tentativas++;
        } while (corpo.contains(comida) && tentativas < 1000);
    }

    public Point getComida() {
        return comida;
    }

    public LinkedList<Point> getCorpo() {
        return corpo;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isVenceu() {
        return venceu;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }
}
