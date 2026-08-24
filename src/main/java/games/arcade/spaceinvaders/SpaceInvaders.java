package games.arcade.spaceinvaders;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class SpaceInvaders extends JPanel {
    private static final int LARGURA = 800;
    private static final int ALTURA = 600;
    private static final int FPS = 60;

    private final Nave          jogador = new Nave(LARGURA / 2.0 - 20, ALTURA - 60);
    private final List<Inimigo> inimigos = new ArrayList<>();
    private final List<Tiro>    tiros = new ArrayList<>();
    private final Random        random = new Random();

    private int     pontuacao = 0;
    private int     onda = 1;
    private double  dirInimigos = 60;
    private long    ultimoTiroInimigo = 0;
    private long    ultimoTiroJogador = 0;
    private boolean fimDeJogo = false;
    private long    tempoAnterior = System.nanoTime();

    public SpaceInvaders() {
        setPreferredSize(new Dimension(LARGURA, ALTURA));
        setBackground(Color.BLACK);
        setFocusable(true);
        criarOnda();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (fimDeJogo && e.getKeyCode() == KeyEvent.VK_R) {
                    reiniciar();
                }

                if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
                    jogador.moverEsquerda();
                }

                if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
                    jogador.moverDireita();
                }

                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    tentarAtirar();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A
                        || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
                    jogador.parar();
                }
            }
        });

        new Timer(1000 / FPS, e -> gameLoop()).start();
    }

    private void criarOnda() {
        inimigos.clear();
        for (int linha = 0; linha < 5; linha++) {
            for (int col = 0; col < 10; col++) {
                int pontos = (5 - linha) * 10;
                inimigos.add(new Inimigo(80 + col * 55, 50 + linha * 40, pontos));
            }
        }
        dirInimigos = 60 + (onda - 1) * 15;
    }

    private void reiniciar() {
        pontuacao = 0;
        onda = 1;
        fimDeJogo = false;
        tiros.clear();
        criarOnda();
    }

    private void tentarAtirar() {
        long agora = System.currentTimeMillis();
        if (agora - ultimoTiroJogador > 300) {
            tiros.add(jogador.atirar());
            ultimoTiroJogador = agora;
        }
    }

    private void gameLoop() {
        long agora = System.nanoTime();
        double dt = (agora - tempoAnterior) / 1_000_000_000.0;
        tempoAnterior = agora;
        if (!fimDeJogo) {
            atualizar(dt);
        }
        repaint();
    }

    private void atualizar(double dt) {
        jogador.atualizar(dt);

        double minX = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        for (Inimigo i : inimigos) {
            if (!i.isAtivo()) {
                continue;
            }
            i.velocidadeX = dirInimigos;
            i.atualizar(dt);
            minX = Math.min(minX, i.getX());
            maxX = Math.max(maxX, i.getX() + i.getLargura());
            maxY = Math.max(maxY, i.getY() + i.getAltura());
        }

        if (maxX > LARGURA - 20 && dirInimigos > 0) {
            dirInimigos = -Math.abs(dirInimigos);
            for (Inimigo i : inimigos) {
                i.y += 20;
            }
        } else if (minX < 20 && dirInimigos < 0) {
            dirInimigos = Math.abs(dirInimigos);
            for (Inimigo i : inimigos) {
                i.y += 20;
            }
        }

        long agora = System.currentTimeMillis();
        if (agora - ultimoTiroInimigo > 800 - onda * 50 && !inimigos.isEmpty()) {
            List<Inimigo> ativos = new ArrayList<>();
            for (Inimigo i : inimigos) {
                if (i.isAtivo()) {
                    ativos.add(i);
                }
            }

            if (!ativos.isEmpty()) {
                tiros.add(ativos.get(random.nextInt(ativos.size())).atirar());
                ultimoTiroInimigo = agora;
            }
        }

        for (Iterator<Tiro> it = tiros.iterator(); it.hasNext(); ) {
            Tiro tiro = it.next();
            tiro.atualizar(dt);
            if (!tiro.isAtivo()) {
                it.remove();
                continue;
            }

            if (tiro.isDoJogador()) {
                for (Inimigo i : inimigos) {
                    if (i.isAtivo() && tiro.colideCom(i)) {
                        pontuacao += i.getPontos();
                        i.destruir();
                        tiro.destruir();
                        break;
                    }
                }
            } else if (tiro.colideCom(jogador)) {
                jogador.perderVida();
                tiro.destruir();
                if (!jogador.isAtivo()) {
                    fimDeJogo = true;
                }
            }
        }

        boolean todosMortos = inimigos.stream().noneMatch(Entidade::isAtivo);
        if (todosMortos) {
            onda++;
            criarOnda();
        }

        if (maxY > jogador.getY()) {
            fimDeJogo = true;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.WHITE);
        for (int i = 0; i < 100; i++) {
            int px = (i * 137) % LARGURA;
            int py = (i * 91) % ALTURA;
            g2.fillRect(px, py, 1, 1);
        }

        if (jogador.isAtivo()) {
            g2.setColor(Color.GREEN);
            g2.fillRect((int) jogador.getX(), (int) jogador.getY(), (int) jogador.getLargura(), (int) jogador.getAltura());
            g2.fillRect((int) jogador.getX() + 17, (int) jogador.getY() - 6, 6, 6);
        }

        for (Inimigo i : inimigos) {
            if (!i.isAtivo()) {
                continue;
            }
            g2.setColor(i.getPontos() >= 40 ? Color.MAGENTA : i.getPontos() >= 20 ? Color.CYAN : Color.YELLOW);
            g2.fillRect((int) i.getX(), (int) i.getY(), (int) i.getLargura(), (int) i.getAltura());
        }

        for (Tiro t : tiros) {
            g2.setColor(t.isDoJogador() ? Color.WHITE : Color.RED);
            g2.fillRect((int) t.getX(), (int) t.getY(), (int) t.getLargura(), (int) t.getAltura());
        }

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Monospaced", Font.BOLD, 18));
        g2.drawString("PONTOS: " + pontuacao, 10, 25);
        g2.drawString("ONDA: " + onda, 350, 25);
        g2.drawString("VIDAS: " + jogador.getVidas(), 680, 25);

        if (fimDeJogo) {
            g2.setColor(Color.RED);
            g2.setFont(new Font("Monospaced", Font.BOLD, 48));
            g2.drawString("FIM DE JOGO", LARGURA / 2 - 160, ALTURA / 2);
            g2.setFont(new Font("Monospaced", Font.BOLD, 20));
            g2.setColor(Color.WHITE);
            g2.drawString("Pressione R para reiniciar", LARGURA / 2 - 150, ALTURA / 2 + 40);
        }
    }

    public static void main(String[] args) {
        JFrame janela = new JFrame("Space Invaders");
        SpaceInvaders jogo = new SpaceInvaders();

        janela.add(jogo);
        janela.pack();
        janela.setResizable(false);
        janela.setLocationRelativeTo(null);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setVisible(true);
        jogo.requestFocusInWindow();
    }
}

