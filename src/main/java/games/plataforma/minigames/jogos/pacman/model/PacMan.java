package games.plataforma.minigames.jogos.pacman.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PacMan {
    private int         largura;
    private int         altura;
    private Point       pacman;
    private Direcao     direcao;
    private Direcao     proximaDirecao;
    private List<Point> fantasmas;
    private List<Point> pontos;
    private int         pontuacao;
    private int         vidas;
    private boolean     gameOver;
    private boolean     venceu;
    private boolean[][] paredes;

    public PacMan(int largura, int altura) {
        this.largura = largura;
        this.altura = altura;
        iniciar();
    }

    public void iniciar() {
        paredes = new boolean[largura][altura];
        for (int i = 0; i < largura; i++) {
            for (int j = 0; j < altura; j++) {
                if (i == 0 || i == largura-1 || j == 0 || j == altura-1) {
                    paredes[i][j] = true;
                } else {
                    paredes[i][j] = false;
                }
            }
        }

        for (int i = 3; i < 7; i++) {
            paredes[i][3] = true;
            paredes[i][altura-4] = true;
        }

        for (int j = 3; j < 7; j++) {
            paredes[3][j] = true;
            paredes[largura-4][j] = true;
        }

        for (int i = 4; i <= 6; i++) {
            for (int j = 4; j <= 6; j++) {
                paredes[i][j] = true;
            }
        }

        pacman = new Point(largura/2, altura/2);
        direcao = Direcao.DIREITA;
        proximaDirecao = Direcao.DIREITA;
        vidas = 3;
        pontuacao = 0;
        gameOver = false;
        venceu = false;

        fantasmas = new ArrayList<>();
        fantasmas.add(new Point(1, 1));
        fantasmas.add(new Point(1, altura-2));
        fantasmas.add(new Point(largura-2, 1));

        pontos = new ArrayList<>();
        for (int i = 0; i < largura; i++) {
            for (int j = 0; j < altura; j++) {
                if (!paredes[i][j] && !(i == pacman.x && j == pacman.y)) {
                    boolean isFantasma = false;
                    for (Point f : fantasmas) {
                        if (f.x == i && f.y == j) {
                            isFantasma = true;
                        }
                    }
                    if (!isFantasma) {
                        pontos.add(new Point(i, j));
                    }
                }
            }
        }
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

        Point novo = new Point(pacman.x, pacman.y);
        switch (direcao) {
            case SUBIR:
                novo.y--;
                break;
            case DESCER:
                novo.y++;
                break;
            case ESQUERDA:
                novo.x--;
                break;
            case DIREITA:
                novo.x++;
                break;
        }

        if (!paredes[novo.x][novo.y]) {
            pacman = novo;
        }

        for (int i = 0; i < pontos.size(); i++) {
            Point p = pontos.get(i);
            if (p.x == pacman.x && p.y == pacman.y) {
                pontos.remove(i);
                pontuacao++;
                break;
            }
        }

        Random rand = new Random();
        for (Point f : fantasmas) {
            Direcao d = Direcao.values()[rand.nextInt(4)];
            Point novoF = new Point(f.x, f.y);
            switch (d) {
                case SUBIR:
                    novoF.y--;
                    break;
                case DESCER:
                    novoF.y++;
                    break;
                case ESQUERDA:
                    novoF.x--;
                    break;
                case DIREITA:
                    novoF.x++;
                    break;
            }
            if (!paredes[novoF.x][novoF.y]) {
                f.setLocation(novoF);
            }
        }

        for (Point f : fantasmas) {
            if (f.x == pacman.x && f.y == pacman.y) {
                vidas--;
                if (vidas <= 0) {
                    gameOver = true;
                } else {
                    pacman.setLocation(largura/2, altura/2);
                }
                break;
            }
        }

        if (pontos.isEmpty()) {
            venceu = true;
            gameOver = true;
        }
    }

    public Point getPacman() {
        return pacman;
    }

    public List<Point> getFantasmas() {
        return fantasmas;
    }

    public List<Point> getPontos() {
        return pontos;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public int getVidas() {
        return vidas;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isVenceu() {
        return venceu;
    }

    public boolean isParede(int x, int y) {
        return paredes[x][y];
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }
}
