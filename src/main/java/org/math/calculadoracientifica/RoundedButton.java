package org.math.calculadoracientifica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RoundedButton extends JButton {
    private Color bgColor;
    private Color hoverColor;
    private boolean hover = false;
    private int     radius = 20;

    // Cores do tema escuro (referência ao tema)
    private static final Color COR_TEXTO = Color.WHITE;
    private static final Color COR_BOTAO_NORMAL = new Color(45, 45, 50);
    private static final Color COR_BOTAO_HOVER = new Color(0, 120, 215);

    public RoundedButton(String text) {
        super(text);
        this.bgColor = COR_BOTAO_NORMAL;
        this.hoverColor = COR_BOTAO_HOVER;

        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(COR_TEXTO);
        setOpaque(false);

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                hover = true;
                repaint();
            }
            public void mouseExited(MouseEvent e) {
                hover = false;
                repaint();
            }
        });
    }

    public void setBgColor(Color color) {
        this.bgColor = color;
        repaint();
    }

    public void setHoverColor(Color color) {
        this.hoverColor = color;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D grafico = (Graphics2D) g.create();
        grafico.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color corAtual = hover ? hoverColor : bgColor;
        grafico.setColor(corAtual);
        grafico.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        super.paintComponent(grafico);
        grafico.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension dimension = super.getPreferredSize();

        dimension.width = Math.max(dimension.width, 60);
        dimension.height = Math.max(dimension.height, 50);

        return dimension;
    }
}