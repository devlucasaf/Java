package games.plataforma.minigames.jogos.sonic.model;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sonic {
    public static final int     LARGURA = 800;
    public static final int     ALTURA = 500;
    private static final int    CHAO = ALTURA - 40;
    private static final int    GRAVIDADE = 1;
    private static final int    VELOCIDADE_PULO = -14;
    private static final int    VELOCIDADE_MAX = 10;
    private static final int    ACELERACAO = 1;
    private static final int    ATRITO = 1;

    private Rectangle       sonic;
    private int             velocidadeX;
    private int             velocidadeY;
    private boolean         noChao;
    private boolean         viradoDireita;
    private int             vidas;
    private int             aneis;
    private boolean         invencivel;
    private int             invencivelContador;
    private List<Rectangle> plataformas;
    private List<Rectangle> aneisList;
    private List<Inimigo>   inimigos;
    private Rectangle       bandeira;
    private boolean         gameOver;
    private boolean         venceu;
    private int             deslocamentoX;
    private Random          random;

    public Sonic() {
        random = new Random();
        sonic = new Rectangle(80, CHAO - 40, 30, 40);
        velocidadeX = 0;
        velocidadeY = 0;
        noChao = true;
        viradoDireita = true;
        vidas = 3;
        aneis = 0;
        invencivel = false;
        invencivelContador = 0;
        gameOver = false;
        venceu = false;
        deslocamentoX = 0;

        plataformas = new ArrayList<>();
        aneisList = new ArrayList<>();
        inimigos = new ArrayList<>();

        carregarCenario();
    }

    private void carregarCenario() {
        plataformas.add(new Rectangle(0, CHAO, LARGURA + 300, 40));

        plataformas.add(new Rectangle(150, 380, 120, 20));
        plataformas.add(new Rectangle(400, 320, 120, 20));
        plataformas.add(new Rectangle(650, 380, 120, 20));
        plataformas.add(new Rectangle(900, 280, 120, 20));
        plataformas.add(new Rectangle(1100, 380, 120, 20));
        plataformas.add(new Rectangle(1350, 320, 120, 20));
        plataformas.add(new Rectangle(1600, 380, 120, 20));
        plataformas.add(new Rectangle(1850, 280, 120, 20));

        int[][] posAneis = {
                // Grupo 1
                {170, 340}, {190, 340}, {210, 340}, {230, 340},
                // Grupo 2
                {420, 280}, {440, 280}, {460, 280},
                // Grupo 3
                {670, 340}, {690, 340},
                // Grupo 4
                {920, 240}, {940, 240}, {960, 240}, {980, 240},
                // Grupo 5
                {1120, 340}, {1140, 340},
                // Grupo 6
                {1370, 280}, {1390, 280}, {1410, 280},
                // Grupo 7
                {1620, 340}, {1640, 340}, {1660, 340},
                // Grupo 8
                {1870, 240}, {1890, 240}, {1910, 240}
        };

        for (int[] p : posAneis) {
            aneisList.add(new Rectangle(p[0], p[1], 16, 16));
        }

        int[][] posIni = {
                {350, CHAO - 30},
                {750, CHAO - 30},
                {1200, CHAO - 30},
                {1500, 320 - 30},
                {1800, CHAO - 30}
        };
        for (int[] p : posIni) {
            inimigos.add(new Inimigo(p[0], p[1]));
        }

        bandeira = new Rectangle(1950, CHAO - 70, 30, 70);
    }

    public void moverEsquerda() {
        if (velocidadeX > -VELOCIDADE_MAX) {
            velocidadeX -= ACELERACAO;
        }
        viradoDireita = false;
    }

    public void moverDireita() {
        if (velocidadeX < VELOCIDADE_MAX) {
            velocidadeX += ACELERACAO;
        }
        viradoDireita = true;
    }

    public void pararHorizontal() {
        if (velocidadeX > 0) {
            velocidadeX -= ATRITO;
            if (velocidadeX < 0) {
                velocidadeX = 0;
            }
        } else if (velocidadeX < 0) {
            velocidadeX += ATRITO;
            if (velocidadeX > 0) {
                velocidadeX = 0;
            }
        }
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

        sonic.x += velocidadeX;
        if (sonic.x + 60 > 600 && velocidadeX > 0) {
            deslocamentoX += velocidadeX;
            sonic.x -= velocidadeX;
        }

        if (sonic.x < 80 && velocidadeX < 0) {
            deslocamentoX += velocidadeX;
            sonic.x -= velocidadeX;
        }
        sonic.x = Math.max(0, Math.min(sonic.x, 2000 - sonic.width));

        velocidadeY += GRAVIDADE;
        if (velocidadeY > VELOCIDADE_MAX) {
            velocidadeY = VELOCIDADE_MAX;
        }
        sonic.y += velocidadeY;

        noChao = false;
        for (Rectangle plat : plataformas) {
            if (sonic.intersects(plat)) {
                if (velocidadeY > 0 && sonic.y + sonic.height - velocidadeY <= plat.y + 5) {
                    sonic.y = plat.y - sonic.height;
                    velocidadeY = 0;
                    noChao = true;
                } else if (velocidadeX > 0 && sonic.x + sonic.width - velocidadeX <= plat.x + 5) {
                    sonic.x = plat.x - sonic.width;
                    velocidadeX = 0;
                } else if (velocidadeX < 0 && sonic.x - velocidadeX >= plat.x + plat.width - 5) {
                    sonic.x = plat.x + plat.width;
                    velocidadeX = 0;
                } else if (velocidadeY < 0 && sonic.y - velocidadeY >= plat.y + plat.height - 5) {
                    sonic.y = plat.y + plat.height;
                    velocidadeY = 0;
                }
            }
        }

        if (sonic.y > ALTURA + 50) {
            perderVida();
            return;
        }

        List<Rectangle> removerAneis = new ArrayList<>();
        for (Rectangle anel : aneisList) {
            if (sonic.intersects(anel)) {
                aneis++;
                removerAneis.add(anel);
            }
        }
        aneisList.removeAll(removerAneis);

        for (Inimigo ini : inimigos) {
            if (!ini.ativo) {
                continue;
            }
            ini.rect.x += ini.velocidadeX;

            boolean noChaoIni = false;
            for (Rectangle plat : plataformas) {
                if (ini.rect.intersects(plat) && ini.rect.y + ini.rect.height - 2 >= plat.y && ini.rect.y + ini.rect.height <= plat.y + 10) {
                    noChaoIni = true;
                    break;
                }
            }
            if (!noChaoIni) {
                ini.rect.y += 4;
            }

            if (ini.rect.x < 0 || ini.rect.x + ini.rect.width > 2000) {
                ini.velocidadeX = -ini.velocidadeX;
            }

            for (Rectangle plat : plataformas) {
                if (ini.rect.intersects(plat) && ini.rect.y + ini.rect.height - 4 > plat.y) {
                    if (ini.velocidadeX > 0 && ini.rect.x + ini.rect.width - ini.velocidadeX <= plat.x + 5) {
                        ini.velocidadeX = -ini.velocidadeX;
                    } else if (ini.velocidadeX < 0 && ini.rect.x - ini.velocidadeX >= plat.x + plat.width - 5) {
                        ini.velocidadeX = -ini.velocidadeX;
                    }
                }
            }
        }

        for (Inimigo ini : inimigos) {
            if (!ini.ativo) {
                continue;
            }

            if (sonic.intersects(ini.rect)) {
                if (velocidadeY > 0 && sonic.y + sonic.height - velocidadeY <= ini.rect.y + 10) {
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

        if (sonic.intersects(bandeira)) {
            venceu = true;
            gameOver = true;
        }
    }

    private void perderVida() {
        vidas--;
        if (vidas <= 0) {
            gameOver = true;
        } else {
            sonic.x = 80;
            sonic.y = CHAO - 40;
            velocidadeX = 0;
            velocidadeY = 0;
            noChao = true;
            invencivel = true;
            invencivelContador = 0;
        }
    }

    public Rectangle getSonic() {
        return sonic;
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

    public int getAneis() {
        return aneis;
    }

    public boolean isInvencivel() {
        return invencivel;
    }

    public List<Rectangle> getPlataformas() {
        return plataformas;
    }

    public List<Rectangle> getAneisList() {
        return aneisList;
    }

    public List<Rectangle> getInimigos() {
        List<Rectangle> rects = new ArrayList<>();
        for (Inimigo ini : inimigos) {
            if (ini.ativo) {
                rects.add(ini.rect);
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
