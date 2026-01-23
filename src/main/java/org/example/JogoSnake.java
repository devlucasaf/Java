import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

// Classe principal do jogo Snake, que herda JPanel para desenhar e implementa ActionListener para controlar o loop do jogo
public class JogoSnake extends JPanel implements ActionListener {

    // Dimensões da tela do jogo
    private final int largura = 1200;
    private final int altura = 800;
    private final int tamanhoQuadrado = 20; // Tamanho de cada bloco da cobra e da comida
    private final int velocidade = 15;      // Velocidade de atualização do jogo (frames por segundo)

    // Lista que guarda os pontos (pixels) que representam o corpo da cobra
    private final ArrayList<Point> pixels = new ArrayList<>();
    private int tamanhoCobra = 1; // Tamanho inicial da cobra
    private int x, y;             // Posição atual da cabeça da cobra
    private int velocidadeX = 0;  // Velocidade horizontal da cobra
    private int velocidadeY = 0;  // Velocidade vertical da cobra
    private Point comida;         // Posição da comida

    private boolean fimDeJogo = false; // Flag para indicar se o jogo acabou
    private Timer timer;               // Timer que controla o loop do jogo

    // Construtor: configura painel e inicia o jogo
    public JogoSnake() {
        this.setPreferredSize(new Dimension(largura, altura)); // Define tamanho da tela
        this.setBackground(Color.BLACK);                       // Cor de fundo
        this.setFocusable(true);                               // Permite capturar eventos de teclado
        this.addKeyListener(new ControleTeclado());            // Adiciona controle de teclado
        
        iniciarJogo(); // Inicializa variáveis e começa o jogo
    }

    // Método para iniciar o jogo
    private void iniciarJogo() {
        x = largura / 2; // Cobra começa no centro da tela
        y = altura / 2;
        gerarComida();   // Gera a primeira comida
        
        // Timer que chama actionPerformed a cada intervalo
        timer = new Timer(1000 / velocidade, this);
        timer.start();
    }

    // Método para gerar comida em posição aleatória
    private void gerarComida() {
        Random rand = new Random();
        int rX = rand.nextInt(largura / tamanhoQuadrado) * tamanhoQuadrado;
        int rY = rand.nextInt(altura / tamanhoQuadrado) * tamanhoQuadrado;
        comida = new Point(rX, rY);
    }

    // Método que desenha os elementos gráficos do jogo
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!fimDeJogo) {
            // Desenha a comida em vermelho
            g.setColor(Color.RED);
            g.fillRect(comida.x, comida.y, tamanhoQuadrado, tamanhoQuadrado);

            // Desenha a cobra em verde
            g.setColor(Color.GREEN);
            for (Point p : pixels) {
                g.fillRect(p.x, p.y, tamanhoQuadrado, tamanhoQuadrado);
            }

            // Mostra pontuação (quantidade de comidas comidas)
            g.setColor(Color.RED);
            g.setFont(new Font("Helvetica", Font.BOLD, 35));
            g.drawString("Pontos: " + (tamanhoCobra - 1), 10, 35);
        } else {
            // Se o jogo acabou, mostra mensagem de fim
            mostrarFimDeJogo(g);
        }
    }

    // Método para mostrar mensagem de fim de jogo
    private void mostrarFimDeJogo(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Helvetica", Font.BOLD, 50));
        String msg = "Fim de Jogo!";
        g.drawString(msg, largura / 3, altura / 2);
    }

    // Método chamado pelo Timer a cada "tick" do jogo
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!fimDeJogo) {
            // Atualiza posição da cobra
            x += velocidadeX;
            y += velocidadeY;

            // Verifica colisão com bordas da tela
            if (x < 0 || x >= largura || y < 0 || y >= altura) {
                fimDeJogo = true;
            }

            // Adiciona nova posição da cabeça da cobra
            pixels.add(new Point(x, y));
            if (pixels.size() > tamanhoCobra) {
                pixels.remove(0); // Remove último ponto para manter tamanho correto
            }

            // Verifica colisão da cobra com ela mesma
            for (int i = 0; i < pixels.size() - 1; i++) {
                if (pixels.get(i).equals(new Point(x, y))) {
                    fimDeJogo = true;
                }
            }

            // Verifica se a cobra comeu a comida
            if (x == comida.x && y == comida.y) {
                tamanhoCobra++; // Aumenta tamanho da cobra
                gerarComida();  // Gera nova comida
            }
        }
        repaint(); // Redesenha a tela
    }

    // Classe interna para controlar o teclado
    private class ControleTeclado extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int tecla = e.getKeyCode();

            // Movimentos da cobra (não permite inverter diretamente para evitar bug)
            if (tecla == KeyEvent.VK_UP && velocidadeY == 0) {
                velocidadeX = 0;
                velocidadeY = -tamanhoQuadrado;
            } else if (tecla == KeyEvent.VK_DOWN && velocidadeY == 0) {
                velocidadeX = 0;
                velocidadeY = tamanhoQuadrado;
            } else if (tecla == KeyEvent.VK_LEFT && velocidadeX == 0) {
                velocidadeX = -tamanhoQuadrado;
                velocidadeY = 0;
            } else if (tecla == KeyEvent.VK_RIGHT && velocidadeX == 0) {
                velocidadeX = tamanhoQuadrado;
                velocidadeY = 0;
            }
        }
    }

    // Método principal que cria a janela e inicia o jogo
    public static void main(String[] args) {
        JFrame frame = new JFrame("Jogo Snake em Java"); // Cria janela
        JogoSnake gamePanel = new JogoSnake();           // Cria painel do jogo
        
        frame.add(gamePanel);                            // Adiciona painel à janela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fecha programa ao fechar janela
        frame.pack();                                    // Ajusta tamanho da janela
        frame.setLocationRelativeTo(null);               // Centraliza janela na tela
        frame.setVisible(true);                          // Torna janela visível
    }
}
