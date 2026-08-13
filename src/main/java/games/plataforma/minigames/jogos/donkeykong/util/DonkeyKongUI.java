package games.plataforma.minigames.jogos.donkeykong.util;

import games.plataforma.minigames.gui.JanelaJogo;
import games.plataforma.minigames.jogos.donkeykong.model.DonkeyKong;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class DonkeyKongUI extends JanelaJogo {

    private DonkeyKong      model;
    private JPanel          gamePanel;
    private JLabel          infoLabel;
    private Timer           timer;
    private BufferedImage   imagemMario;
    private BufferedImage   imagemDonkeyKong;
    private BufferedImage   imagemPauline;
    private BufferedImage   imagemBarril;

    public DonkeyKongUI() {
        super("Donkey Kong");
        carregarImagens();
        inicializarComponentes();
    }

    private void carregarImagens() {
        try {
            URL urlMario = getClass().getResource("../../../resources/mario-bros-img.png");
            URL urlDonkeyKong = getClass().getResource("../../../resources/donkey-kong-img.png");
            URL urlPauline = getClass().getResource("../../../resources/pauline-img.png");
            URL urlBarril = getClass().getResource("../../../resources/barril-img.png");

            if (urlMario != null) {
                imagemMario = ImageIO.read(urlMario);
            } else {
                System.err.println("Imagem mario-bros-img.png não encontrada. Usando fallback.");
            }

            if (urlDonkeyKong != null) {
                imagemDonkeyKong = ImageIO.read(urlDonkeyKong);
            } else {
                System.err.println("Imagem donkey-kong-img.png não encontrada. Usando fallback.");
            }

            if (urlPauline != null) {
                imagemPauline = ImageIO.read(urlPauline);
            } else {
                System.err.println("Imagem pauline-img.png não encontrada. Usando fallback.");
            }

            if (urlBarril != null) {
                imagemBarril = ImageIO.read(urlBarril);
            } else {
                System.err.println("Imagem barril-img.png não encontrada. Usando fallback.");
            }

            System.out.println("Mario: " + urlMario);
            System.out.println("Donkey Kong: " + urlDonkeyKong);
            System.out.println("Pauline: " + urlPauline);
            System.out.println("Barril: " + urlBarril);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar imagens. Verifique se os arquivos estão em resources/images/",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    protected void inicializarComponentes() {
        model = new DonkeyKong();
        setLayout(new BorderLayout());

        // Painel do jogo
        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                desenharJogo(g);
            }
        };
        gamePanel.setPreferredSize(new Dimension(DonkeyKong.LARGURA, DonkeyKong.ALTURA));
        gamePanel.setBackground(new Color(45, 45, 45));
        gamePanel.setFocusable(true);
        gamePanel.addKeyListener(new TecladoAdapter());

        // Informações
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        infoPanel.setOpaque(false);
        infoLabel = new JLabel("Vidas: 3  |  Pontos: 0");
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        infoPanel.add(infoLabel);

        // Botões
        JPanel botoesPanel = criarPainelBotoesVoltar();
        JButton reiniciarBtn = new JButton("Reiniciar");
        reiniciarBtn.setBackground(new Color(64, 64, 64));
        reiniciarBtn.setForeground(Color.WHITE);
        reiniciarBtn.setFocusPainted(false);
        reiniciarBtn.addActionListener(e -> reiniciarJogo());
        botoesPanel.add(reiniciarBtn);

        add(infoPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);
        add(botoesPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);

        timer = new Timer(33, e -> {
            model.update();
            atualizarInfo();
            gamePanel.repaint();
            if (model.isGameOver() || model.isVenceu()) {
                timer.stop();
                mostrarFimDeJogo();
            }
        });
        timer.start();

        gamePanel.requestFocusInWindow();
    }

    private class TecladoAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    model.moverEsquerda();
                    break;
                case KeyEvent.VK_RIGHT:
                    model.moverDireita();
                    break;
                case KeyEvent.VK_UP:
                    model.subirEscada();
                    break;
                case KeyEvent.VK_SPACE:
                    model.pular();
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
                model.pararHorizontal();
            }

            if (key == KeyEvent.VK_UP) {
                model.pararSubir();
            }
        }
    }

    private void desenharJogo(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(new Color(120, 80, 50));
        for (Rectangle plat : model.getPlataformas()) {
            g2d.fillRect(plat.x, plat.y, plat.width, plat.height);
        }

        g2d.setColor(new Color(180, 140, 60));
        for (Rectangle esc : model.getEscadas()) {
            g2d.fillRect(esc.x, esc.y, esc.width, esc.height);
            g2d.setColor(Color.DARK_GRAY);
            for (int y = esc.y + 5; y < esc.y + esc.height; y += 15) {
                g2d.drawLine(esc.x + 2, y, esc.x + esc.width - 2, y);
            }
            g2d.setColor(new Color(180, 140, 60));
        }

        // Desenhar Donkey Kong
        Rectangle dk = model.getDonkeyKong();
        if (imagemDonkeyKong != null) {
            g2d.drawImage(imagemDonkeyKong, dk.x, dk.y, dk.width, dk.height, null);
        } else {
            g2d.setColor(new Color(100, 60, 30));
            g2d.fillOval(dk.x, dk.y, dk.width, dk.height);
            g2d.setColor(Color.BLACK);
            g2d.drawString("DK", dk.x + 15, dk.y + 30);
        }

        // Desenhar Pauline
        Rectangle pauline = model.getPauline();
        if (imagemPauline != null) {
            g2d.drawImage(imagemPauline, pauline.x, pauline.y, pauline.width, pauline.height, null);
        } else {
            g2d.setColor(Color.PINK);
            g2d.fillOval(pauline.x, pauline.y, pauline.width, pauline.height);
            g2d.setColor(Color.WHITE);
            g2d.drawString("♥", pauline.x + 5, pauline.y + 25);
        }

        // Desenhar barris
        g2d.setColor(new Color(150, 80, 30));
        for (Rectangle b : model.getBarrisRect()) {
            g2d.fillOval(b.x, b.y, b.width, b.height);
            g2d.setColor(Color.DARK_GRAY);
            g2d.drawOval(b.x, b.y, b.width, b.height);
            g2d.setColor(new Color(150, 80, 30));
        }

        // Desenhar Mario
        Rectangle mario = model.getMario();
        if (imagemMario != null) {
            g2d.drawImage(imagemMario, mario.x, mario.y, mario.width, mario.height, null);
        } else {
            g2d.setColor(Color.RED);
            g2d.fillRect(mario.x, mario.y, mario.width, mario.height);
            g2d.setColor(Color.BLUE);
            g2d.fillRect(mario.x + 2, mario.y - 5, mario.width - 4, 8);
            g2d.setColor(Color.WHITE);
            if (model.getDirecao() == 1) {
                g2d.fillOval(mario.x + 15, mario.y + 5, 5, 5);
                g2d.fillOval(mario.x + 5, mario.y + 5, 5, 5);
            } else {
                g2d.fillOval(mario.x + 5, mario.y + 5, 5, 5);
                g2d.fillOval(mario.x + 15, mario.y + 5, 5, 5);
            }
            g2d.setColor(Color.BLACK);
            if (model.getDirecao() == 1) {
                g2d.fillOval(mario.x + 17, mario.y + 7, 3, 3);
                g2d.fillOval(mario.x + 7, mario.y + 7, 3, 3);
            } else {
                g2d.fillOval(mario.x + 7, mario.y + 7, 3, 3);
                g2d.fillOval(mario.x + 17, mario.y + 7, 3, 3);
            }
        }

        // Mensagens de fim
        if (model.isGameOver()) {
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Arial", Font.BOLD, 40));
            g2d.drawString("GAME OVER", 180, 250);
        } else if (model.isVenceu()) {
            g2d.setColor(Color.GREEN);
            g2d.setFont(new Font("Arial", Font.BOLD, 40));
            g2d.drawString("VOCÊ VENCEU!", 150, 250);
        }
    }

    private void atualizarInfo() {
        infoLabel.setText("Vidas: " + model.getVidas() + "  |  Pontos: " + model.getScore());
    }

    private void mostrarFimDeJogo() {
        String msg = model.isVenceu() ? "Parabéns! Você resgatou a Pauline!" : "Que pena! Você perdeu todas as vidas.";
        int opcao = JOptionPane.showConfirmDialog(this, msg + "\nDeseja jogar novamente?", "Fim de Jogo",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (opcao == JOptionPane.YES_OPTION) {
            reiniciarJogo();
        } else {
            dispose();
        }
    }

    private void reiniciarJogo() {
        model.reiniciar();
        timer.restart();
        gamePanel.requestFocusInWindow();
        atualizarInfo();
        gamePanel.repaint();
    }
}
