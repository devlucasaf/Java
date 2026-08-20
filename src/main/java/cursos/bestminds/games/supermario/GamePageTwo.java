package cursos.bestminds.games.supermario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;

public class GamePageTwo {
    private int     x = 0;
    private int     y = 0;
    private JButton buttonImageMario;

    public GamePageTwo() {

        JFrame frame = new JFrame("Movimentar Mario");
        frame.setSize(750, 480); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        frame.setLocationRelativeTo(null); 
        frame.setLayout(null); 
        frame.setResizable(false); 

        Image fundo = carregarImagem("resources/fundo-mario-bros.png");
        JPanel panel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (fundo != null) {
                    g.drawImage(fundo, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        panel.setBounds(0, 0, 750, 480);
        panel.setBackground(Color.BLACK);
        frame.add(panel);

        ImageIcon marioImage = new ImageIcon(carregarImagem("resources/mario-bros.png"));
        ImageIcon resizedImage = new ImageIcon(marioImage.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
        JButton buttonImageMario = new JButton(resizedImage);
        buttonImageMario.setBorder(null);
        buttonImageMario.setContentAreaFilled(false);
        buttonImageMario.setOpaque(false);
        buttonImageMario.setBounds(0, 0, 60, 60);
        panel.add(buttonImageMario);

        ImageIcon mushroomImage = new ImageIcon(carregarImagem("resources/cogumelo.png"));
        ImageIcon resizedImageMushroom = new ImageIcon(mushroomImage.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
        JButton buttonImageMushroom = new JButton(resizedImageMushroom);
        buttonImageMushroom.setBounds(480, 340, 60, 60);
        buttonImageMushroom.setContentAreaFilled(false);
        buttonImageMushroom.setOpaque(false);
        buttonImageMushroom.setBorder(null);
        panel.add(buttonImageMushroom);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
                    y -= 10;
                } else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
                    y += 10;
                } else if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
                    x -= 10;
                } else if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
                    x += 10;
                }

                buttonImageMario.setBounds(x, y, 60, 60);

                Rectangle marioBounds       = buttonImageMario.getBounds();
                Rectangle mushroomBounds    = buttonImageMushroom.getBounds();

                if (marioBounds.intersects(mushroomBounds)) {
                    frame.dispose();
                }

                if (x < 0) {
                    x = 0;
                }

                if (y < 0) {
                    y = 0;
                }

                if (x > 750 - 100) {
                    x = 750 - 100;
                }

                if (y > 480 - 100) {
                    y = 480 - 100;
                }
            }
        });

        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.setVisible(true);
    }

    private Image carregarImagem(String caminho) {
        URL url = getClass().getResource(caminho);
        if (url != null) {
            return new ImageIcon(url).getImage();
        }

        java.io.File arquivo = new java.io.File("src/main/java/games/bestminds/supermario/" + caminho);
        if (arquivo.exists()) {
            return new ImageIcon(arquivo.getAbsolutePath()).getImage();
        }

        System.err.println("Imagem nao encontrada: " + caminho);
        return null;
    }
}