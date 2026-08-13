package games.plataforma.minigames.jogos.donkeykong.model;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DonkeyKong {

    // Dimensões da tela
    public static final int     LARGURA = 600;
    public static final int     ALTURA = 500;

    // Plataformas
    private Rectangle           chao;
    private Rectangle           plataforma1;
    private Rectangle           plataforma2;
    private Rectangle           plataforma3;
    private Rectangle           topo;
    private List<Rectangle>     plataformas;
    private List<Rectangle>     escadas;

    // Entidades
    private Rectangle           mario;
    private int                 marioVelocidadeX;
    private int                 marioVelocidadeY;
    private boolean             marioNoChao;
    private boolean             subindoEscada;
    private int                 direcao;
    private Rectangle           donkeyKong;
    private Rectangle           pauline;
    private List<Barril>        barris;

    // Estado do jogo
    private int                 vidas;
    private int                 score;
    private boolean             gameOver;
    private boolean             venceu;
    private int                 contadorBarris;
    private static final int    INTERVALO_BARRI = 80;

    private Random              random;

    public DonkeyKong() {
        random = new Random();
        inicializarPlataformas();
        inicializarEntidades();
        reiniciar();
    }

    private void inicializarPlataformas() {
        chao = new Rectangle(0, ALTURA - 40, LARGURA, 40);
        plataforma1 = new Rectangle(60, 350, 160, 20);
        plataforma2 = new Rectangle(260, 250, 160, 20);
        plataforma3 = new Rectangle(460, 150, 160, 20);
        topo = new Rectangle(0, 30, LARGURA, 30);

        plataformas = new ArrayList<>();
        plataformas.add(chao);
        plataformas.add(plataforma1);
        plataformas.add(plataforma2);
        plataformas.add(plataforma3);

        escadas = new ArrayList<>();
        escadas.add(new Rectangle(100, 350, 20, 80));
        escadas.add(new Rectangle(300, 250, 20, 80));
        escadas.add(new Rectangle(500, 150, 20, 80));
        escadas.add(new Rectangle(200, 30, 20, 100));
    }

    private void inicializarEntidades() {
        mario = new Rectangle(60, ALTURA - 60, 25, 30);
        marioVelocidadeX = 0;
        marioVelocidadeY = 0;
        marioNoChao = true;
        subindoEscada = false;
        direcao = 1;

        donkeyKong = new Rectangle(LARGURA/2 - 30, 10, 60, 60);
        pauline = new Rectangle(LARGURA/2 + 40, 15, 25, 40);

        barris = new ArrayList<>();
    }

    public void reiniciar() {
        vidas = 3;
        score = 0;
        gameOver = false;
        venceu = false;
        contadorBarris = 0;
        mario.x = 60;
        mario.y = ALTURA - 60;
        marioVelocidadeX = 0;
        marioVelocidadeY = 0;
        marioNoChao = true;
        subindoEscada = false;
        barris.clear();
    }

    public void reiniciarAposMorte() {
        mario.x = 60;
        mario.y = ALTURA - 60;
        marioVelocidadeX = 0;
        marioVelocidadeY = 0;
        marioNoChao = true;
        subindoEscada = false;
        barris.clear();
        contadorBarris = 0;
    }

    public void moverEsquerda() {
        if (gameOver || venceu) {
            return;
        }
        marioVelocidadeX = -3;
        direcao = -1;
    }

    public void moverDireita() {
        if (gameOver || venceu) {
            return;
        }
        marioVelocidadeX = 3;
        direcao = 1;
    }

    public void pararHorizontal() {
        marioVelocidadeX = 0;
    }

    public void subirEscada() {
        if (gameOver || venceu) {
            return;
        }

        for (Rectangle escada : escadas) {
            if (mario.intersects(escada) ||
                    (mario.x + mario.width > escada.x - 5 && mario.x < escada.x + escada.width + 5 &&
                            mario.y + mario.height > escada.y - 5 && mario.y < escada.y + escada.height + 5)) {
                subindoEscada = true;
                marioVelocidadeX = 0;
                marioVelocidadeY = -2;
                marioNoChao = false;
                if (mario.x < escada.x) {
                    mario.x = escada.x - 2;
                }

                if (mario.x + mario.width > escada.x + escada.width) {
                    mario.x = escada.x + escada.width - mario.width + 2;
                }
                break;
            }
        }
    }

    public void pararSubir() {
        subindoEscada = false;
        marioVelocidadeY = 0;
    }

    public void pular() {
        if (gameOver || venceu) {
            return;
        }

        if (marioNoChao && !subindoEscada) {
            marioVelocidadeY = -8;
            marioNoChao = false;
        }
    }

    public void update() {
        if (gameOver || venceu) {
            return;
        }

        if (!subindoEscada) {
            mario.x += marioVelocidadeX;
            marioVelocidadeY += 0.5;
            if (marioVelocidadeY > 10) {
                marioVelocidadeY = 10;
            }
            mario.y += marioVelocidadeY;

            marioNoChao = false;
            for (Rectangle plat : plataformas) {
                if (mario.intersects(plat)) {
                    if (marioVelocidadeY > 0 && mario.y + mario.height - marioVelocidadeY <= plat.y + 5) {
                        mario.y = plat.y - mario.height;
                        marioVelocidadeY = 0;
                        marioNoChao = true;
                    }
                }
            }

            if (mario.x < 0) {
                mario.x = 0;
            }

            if (mario.x + mario.width > LARGURA) {
                mario.x = LARGURA - mario.width;
            }

            if (mario.y + mario.height > ALTURA) {
                mario.y = ALTURA - mario.height;
                marioVelocidadeY = 0;
                marioNoChao = true;
            }
        } else {
            mario.y += marioVelocidadeY;
            boolean aindaNaEscada = false;
            for (Rectangle escada : escadas) {
                if (mario.intersects(escada) ||
                        (mario.x + mario.width > escada.x - 5 && mario.x < escada.x + escada.width + 5 &&
                                mario.y + mario.height > escada.y - 5 && mario.y < escada.y + escada.height + 5)) {
                    aindaNaEscada = true;
                    break;
                }
            }
            if (!aindaNaEscada) {
                subindoEscada = false;
                marioVelocidadeY = 0;
                for (Rectangle plat : plataformas) {
                    if (mario.x + mario.width > plat.x && mario.x < plat.x + plat.width &&
                            mario.y + mario.height >= plat.y && mario.y + mario.height <= plat.y + 20) {
                        mario.y = plat.y - mario.height;
                        marioNoChao = true;
                        break;
                    }
                }
            }
        }

        if (mario.intersects(pauline)) {
            venceu = true;
            score += 100;
            return;
        }

        contadorBarris++;
        if (contadorBarris >= INTERVALO_BARRI) {
            contadorBarris = 0;
            if (barris.size() < 5) {
                int x = donkeyKong.x + donkeyKong.width/2 - 10;
                int y = donkeyKong.y + donkeyKong.height;
                Barril barril = new Barril(x, y);
                barril.velocidadeX = random.nextBoolean() ? 2 : -2;
                barril.velocidadeY = 2;
                barris.add(barril);
            }
        }

        List<Barril> remover = new ArrayList<>();
        for (Barril b : barris) {
            b.retangulo.x += b.velocidadeX;
            b.retangulo.y += b.velocidadeY;

            // Gravidade
            b.velocidadeY += 0.3;
            if (b.velocidadeY > 8) {
                b.velocidadeY = 8;
            }

            boolean colidiu = false;
            for (int i = 0; i < plataformas.size(); i++) {
                Rectangle plat = plataformas.get(i);
                if (b.retangulo.intersects(plat)) {
                    if (b.velocidadeY > 0 && b.retangulo.y + b.retangulo.height - b.velocidadeY <= plat.y + 5) {
                        b.retangulo.y = plat.y - b.retangulo.height;
                        b.velocidadeY = 0;
                        b.velocidadeX = random.nextBoolean() ? 2 : -2;
                        b.emPlataforma = true;
                        b.plataformaIndex = i;
                        colidiu = true;
                        break;
                    }
                }
            }

            if (!colidiu) {
                b.emPlataforma = false;
                b.plataformaIndex = -1;
            }

            if (b.retangulo.y > ALTURA || b.retangulo.x < -20 || b.retangulo.x > LARGURA + 20) {
                remover.add(b);
            }

            if (b.retangulo.intersects(mario)) {
                vidas--;
                if (vidas <= 0) {
                    gameOver = true;
                } else {
                    reiniciarAposMorte();
                }
                return;
            }
        }
        barris.removeAll(remover);
    }

    public Rectangle getMario() {
        return mario;
    }

    public Rectangle getDonkeyKong() {
        return donkeyKong;
    }

    public Rectangle getPauline() {
        return pauline;
    }

    public List<Rectangle> getPlataformas() {
        return plataformas;
    }

    public List<Rectangle> getEscadas() {
        return escadas;
    }

    public List<Rectangle> getBarrisRect() {
        List<Rectangle> retangulos = new ArrayList<>();
        for (Barril b : barris) {
            retangulos.add(b.retangulo);
        }
        return retangulos;
    }

    public int getVidas() {
        return vidas;
    }

    public int getScore() {
        return score;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isVenceu() {
        return venceu;
    }

    public int getDirecao() {
        return direcao;
    }
}
