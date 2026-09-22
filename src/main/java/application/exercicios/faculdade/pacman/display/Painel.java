package application.exercicios.faculdade.pacman.display;

import application.exercicios.faculdade.pacman.EstadoAgente;
import application.exercicios.faculdade.pacman.EstadoJogo;
import application.exercicios.faculdade.pacman.Layout;
import application.exercicios.faculdade.pacman.Posicao;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

public class Painel extends JPanel {

    static final int TAMANHO_CELULA = 32;

    private EstadoJogo estado;

    public Painel(EstadoJogo estado) {
        this.estado = estado;
        setBackground(Color.BLACK);
    }

    public void setEstado(EstadoJogo estado) {
        this.estado = estado;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Layout layout = estado.getLayout();
        boolean[][] comidas = estado.getComidas();
        List<Posicao> capsulas = estado.getCapsulas();
        Posicao pacman = estado.getPosicaoPacman();
        List<EstadoAgente> fantasmas = estado.getEstadosFantasmas();

        int alturaTotal = layout.getAltura() * TAMANHO_CELULA;

        for (int x = 0; x < layout.getLargura(); x++) {
            for (int y = 0; y < layout.getAltura(); y++) {
                int px = x * TAMANHO_CELULA;
                int py = alturaTotal - (y + 1) * TAMANHO_CELULA;
                if (layout.ehParede(x, y)) {
                    g2.setColor(new Color(30, 30, 200));
                    g2.fillRect(px, py, TAMANHO_CELULA, TAMANHO_CELULA);
                } else if (comidas[x][y]) {
                    g2.setColor(Color.WHITE);
                    g2.fillOval(px + TAMANHO_CELULA / 2 - 2,
                            py + TAMANHO_CELULA / 2 - 2, 4, 4);
                }
            }
        }

        g2.setColor(Color.WHITE);
        for (Posicao c : capsulas) {
            int px = c.getX() * TAMANHO_CELULA;
            int py = alturaTotal - (c.getY() + 1) * TAMANHO_CELULA;
            g2.fillOval(px + TAMANHO_CELULA / 2 - 6, py + TAMANHO_CELULA / 2 - 6, 12, 12);
        }

        int px = pacman.getX() * TAMANHO_CELULA;
        int py = alturaTotal - (pacman.getY() + 1) * TAMANHO_CELULA;
        g2.setColor(Color.YELLOW);
        g2.fillOval(px + 3, py + 3, TAMANHO_CELULA - 6, TAMANHO_CELULA - 6);

        Color[] cores = {
                Color.RED,
                Color.CYAN,
                Color.PINK,
                Color.ORANGE
        };

        int i = 0;
        for (EstadoAgente f : fantasmas) {
            int fx = f.getPosicao().getX() * TAMANHO_CELULA;
            int fy = alturaTotal - (f.getPosicao().getY() + 1) * TAMANHO_CELULA;
            g2.setColor(f.estaAssustado() ? Color.BLUE : cores[i % cores.length]);
            g2.fillRoundRect(fx + 4, fy + 4,
                    TAMANHO_CELULA - 8, TAMANHO_CELULA - 8, 12, 12);
            i++;
        }

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 14));
        g2.drawString("Pontuacao: " + estado.getPontuacao() + "   Comida: " + estado.getComidaRestante(), 10, alturaTotal + 20);
    }
}
