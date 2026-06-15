package org.math.calculadora.cientifica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BotaoArredondado extends JButton {
    private Color corFundo;
    private Color corDestaque;
    private boolean destacado = false;
    private int     raio = 20;

    // Cores do tema escuro (referência ao tema)
    private static final Color COR_TEXTO = Color.WHITE;
    private static final Color COR_BOTAO_NORMAL = new Color(45, 45, 50);
    private static final Color COR_BOTAO_DESTAQUE = new Color(0, 120, 215);

    public BotaoArredondado(String texto) {
        super(texto);
        this.corFundo = COR_BOTAO_NORMAL;
        this.corDestaque = COR_BOTAO_DESTAQUE;

        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(COR_TEXTO);
        setOpaque(false);

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                destacado = true;
                repaint();
            }
            public void mouseExited(MouseEvent e) {
                destacado = false;
                repaint();
            }
        });
    }

    public void setCorFundo(Color cor) {
        this.corFundo = cor;
        repaint();
    }

    public void setCorDestaque(Color cor) {
        this.corDestaque = cor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D grafico = (Graphics2D) g.create();
        grafico.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color corAtual = destacado ? corDestaque : corFundo;
        grafico.setColor(corAtual);
        grafico.fillRoundRect(0, 0, getWidth(), getHeight(), raio, raio);

        super.paintComponent(grafico);
        grafico.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension dimensao = super.getPreferredSize();

        dimensao.width = Math.max(dimensao.width, 60);
        dimensao.height = Math.max(dimensao.height, 50);

        return dimensao;
    }
}