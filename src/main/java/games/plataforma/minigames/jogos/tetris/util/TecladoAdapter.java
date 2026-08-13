package games.plataforma.minigames.jogos.tetris.util;

import games.plataforma.minigames.jogos.tetris.model.Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TecladoAdapter extends KeyAdapter {
    private final Tetris    model;
    private final Timer     timer;
    private final JPanel    gamePanel;
    private final Component parent;
    private final Runnable  atualizarInfo;

    public TecladoAdapter(Tetris model, Timer timer, JPanel gamePanel, Component parent, Runnable atualizarInfo) {
        this.model = model;
        this.timer = timer;
        this.gamePanel = gamePanel;
        this.parent = parent;
        this.atualizarInfo = atualizarInfo;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (model.isGameOver()) {
            return;
        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                model.moverEsquerda();
                break;
            case KeyEvent.VK_RIGHT:
                model.moverDireita();
                break;
            case KeyEvent.VK_DOWN:
                model.moverBaixo();
                break;
            case KeyEvent.VK_UP:
                model.rotacionar();
                break;
            case KeyEvent.VK_SPACE:
                model.drop();
                break;
        }
        atualizarInfo.run();
        gamePanel.repaint();
        if (model.isGameOver()) {
            timer.stop();
            JOptionPane.showMessageDialog(parent, "Game Over! Pontuação: " + model.getPontuacao());
        }
    }
}