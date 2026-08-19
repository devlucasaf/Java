package games.plataforma.minigames.jogos.mario.model;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mario {
    public static final int     LARGURA = 800;
    public static final int     ALTURA = 500;
    private static final int    CHAO = ALTURA - 40;
    private static final int    GRAVIDADE = 1;
    private static final int    VELOCIDADE_PULO = -15;
    private static final int    VELOCIDADE_MAX = 8;

    private Rectangle       mario;
    private int             velocidadeX;
    private int             velocidadeY;
    private boolean         noChao;
    private boolean         viradoDireita;
    private int             vidas;
    private int             moedas;
    private boolean         invencivel;
    private int             invencivelContador;
    private List<Rectangle> plataformas;
    private List<Rectangle> moedasList;
    private List<Inimigo>   inimigos;
    private Rectangle       bandeira;
    private boolean         gameOver;
    private boolean         venceu;
    private int             deslocamentoX;
    private Random          random;

    public Mario() {
        random = new Random();
        mario = new Rectangle(80, CHAO - 40, 30, 40);
        velocidadeX = 0;
        velocidadeY = 0;
        noChao = true;
        viradoDireita = true;
        vidas = 3;
        moedas = 0;
        invencivel = false;
        invencivelContador = 0;
        gameOver = false;
        venceu = false;
        deslocamentoX = 0;
        plataformas = new ArrayList<>();
        moedasList = new ArrayList<>();
        inimigos = new ArrayList<>();

        carregarCenario();
    }

    private void carregarCenario() {
        plataformas.add(new Rectangle(0, CHAO, LARGURA + 200, 40));
        plataformas.add(new Rectangle(200, 380, 100, 20));
        plataformas.add(new Rectangle(400, 300, 100, 20));
        plataformas.add(new Rectangle(600, 380, 100, 20));
        plataformas.add(new Rectangle(800, 280, 100, 20));
        plataformas.add(new Rectangle(1000, 380, 100, 20));
        plataformas.add(new Rectangle(1200, 300, 100, 20));
        plataformas.add(new Rectangle(1400, 380, 100, 20));

        int[][] posMoedas = {
                {220, 340}, {240, 340}, {260, 340},
                {420, 260}, {440, 260}, {460, 260},
                {620, 340}, {640, 340},
                {820, 240}, {840, 240}, {860, 240},
                {1020, 340}, {1040, 340},
                {1220, 260}, {1240, 260}, {1260, 260},
                {1420, 340}, {1440, 340}
        };

        for (int[] p : posMoedas) {
            moedasList.add(new Rectangle(p[0], p[1], 20, 20));
        }

        int[][] posIni = {
                {350, CHAO - 30},
                {700, CHAO - 30},
                {1100, CHAO - 30},
                {1300, 300 - 30},
        };

        for (int[] p : posIni) {
            inimigos.add(new Inimigo(p[0], p[1]));
        }

        bandeira = new Rectangle(1550, CHAO - 70, 30, 70);
    }

    public void moverEsquerda() {
        velocidadeX = -5;
        viradoDireita = false;
    }

    public void moverDireita() {
        velocidadeX = 5;
        viradoDireita = true;
    }

    public void pararHorizontal() {
        velocidadeX = 0;
    }

    public void pular() {
        if (noChao) {
            velocidadeY = VELOCIDADE_PULO;
            noChao = false;
        }
    }

    public void update() {
        if (gameOver || venceu) {
            return;
        }

        if (invencivel) {
            invencivelContador++;
            if (invencivelContador > 120) {
                invencivel = false;
                invencivelContador = 0;
            }
        }

        mario.x += velocidadeX;
        if (mario.x + 50 > 600 && velocidadeX > 0) {
            deslocamentoX += velocidadeX;
            mario.x -= velocidadeX;
        }

        if (mario.x < 80 && velocidadeX < 0) {
            deslocamentoX += velocidadeX;
            mario.x -= velocidadeX;
        }
        mario.x = Math.max(0, Math.min(mario.x, 1600 - mario.width));

        velocidadeY += GRAVIDADE;
        if (velocidadeY > VELOCIDADE_MAX) {
            velocidadeY = VELOCIDADE_MAX;
        }
        mario.y += velocidadeY;

        noChao = false;
        for (Rectangle plat : plataformas) {
            if (mario.intersects(plat)) {
                if (velocidadeY > 0 && mario.y + mario.height - velocidadeY <= plat.y + 5) {
                    mario.y = plat.y - mario.height;
                    velocidadeY = 0;
                    noChao = true;
                } else if (velocidadeX > 0 && mario.x + mario.width - velocidadeX <= plat.x + 5) {
                    mario.x = plat.x - mario.width;
                } else if (velocidadeX < 0 && mario.x - velocidadeX >= plat.x + plat.width - 5) {
                    mario.x = plat.x + plat.width;
                } else if (velocidadeY < 0 && mario.y - velocidadeY >= plat.y + plat.height - 5) {
                    mario.y = plat.y + plat.height;
                    velocidadeY = 0;
                }
            }
        }

        if (mario.y > ALTURA + 50) {
            perderVida();
            return;
        }

        List<Rectangle> removerMoedas = new ArrayList<>();
        for (Rectangle moeda : moedasList) {
            if (mario.intersects(moeda)) {
                moedas++;
                removerMoedas.add(moeda);
            }
        }
        moedasList.removeAll(removerMoedas);

        for (Inimigo ini : inimigos) {
            if (!ini.ativo) {
                continue;
            }
            ini.retangulo.x += ini.velocidadeX;
            boolean noChaoIni = false;
            for (Rectangle plat : plataformas) {
                if (ini.retangulo.intersects(plat)) {
                    if (ini.retangulo.y + ini.retangulo.height - 2 >= plat.y && ini.retangulo.y + ini.retangulo.height <= plat.y + 10) {
                        noChaoIni = true;
                        break;
                    }
                }
            }
            if (!noChaoIni) {
                ini.retangulo.y += 4;
            }

            if (ini.retangulo.x < 0 || ini.retangulo.x + ini.retangulo.width > 1600) {
                ini.velocidadeX = -ini.velocidadeX;
            }

            for (Rectangle plat : plataformas) {
                if (ini.retangulo.intersects(plat) && ini.retangulo.y + ini.retangulo.height - 4 > plat.y) {
                    if (ini.velocidadeX > 0 && ini.retangulo.x + ini.retangulo.width - ini.velocidadeX <= plat.x + 5) {
                        ini.velocidadeX = -ini.velocidadeX;
                    } else if (ini.velocidadeX < 0 && ini.retangulo.x - ini.velocidadeX >= plat.x + plat.width - 5) {
                        ini.velocidadeX = -ini.velocidadeX;
                    }
                }
            }
        }

        for (Inimigo ini : inimigos) {
            if (!ini.ativo) {
                continue;
            }

            if (mario.intersects(ini.retangulo)) {
                if (velocidadeY > 0 && mario.y + mario.height - velocidadeY <= ini.retangulo.y + 10) {
                    ini.ativo = false;
                    velocidadeY = VELOCIDADE_PULO / 2;
                } else {
                    if (!invencivel) {
                        perderVida();
                        return;
                    }
                }
            }
        }

        if (mario.intersects(bandeira)) {
            venceu = true;
            gameOver = true;
        }

        if (moedasList.isEmpty() && !venceu) {}
    }

    private void perderVida() {
        vidas--;
        if (vidas <= 0) {
            gameOver = true;
        } else {
            mario.x = 80;
            mario.y = CHAO - 40;
            velocidadeX = 0;
            velocidadeY = 0;
            noChao = true;
            invencivel = true;
            invencivelContador = 0;
        }
    }

    public Rectangle getMario() {
        return mario;
    }

    public int getVelocidadeX() {
        return velocidadeX;
    }

    public int getVelocidadeY() {
        return velocidadeY;
    }

    public boolean isViradoDireita() {
        return viradoDireita;
    }

    public int getVidas() {
        return vidas;
    }

    public int getMoedas() {
        return moedas;
    }

    public boolean isInvencivel() {
        return invencivel;
    }

    public List<Rectangle> getPlataformas() {
        return plataformas;
    }

    public List<Rectangle> getMoedasList() {
        return moedasList;
    }

    public List<Rectangle> getInimigos() {
        List<Rectangle> rects = new ArrayList<>();
        for (Inimigo ini : inimigos) {
            if (ini.ativo) {
                rects.add(ini.retangulo);
            }
        }
        return rects;
    }

    public Rectangle getBandeira() {
        return bandeira;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isVenceu() {
        return venceu;
    }

    public int getDeslocamentoX() {
        return deslocamentoX;
    }

}