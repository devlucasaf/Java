package application.exercicios.faculdade.cg;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class RunnerGame extends Application {

    private GraphicsContext gc;
    private Canvas          canvas;
    private Jogador         jogador;
    private Obstaculo       obstaculo;
    private Image           imagemFundo;
    private AnimationTimer  loop;

    private boolean gameOver = false;

    @Override
    public void start(Stage palco) {
        canvas = new Canvas(Constantes.LARGURA_JANELA, Constantes.ALTURA_JANELA);
        gc = canvas.getGraphicsContext2D();

        StackPane raiz = new StackPane(canvas);
        Scene cena = new Scene(raiz);

        try {
            Image imgJogador = new Image(getClass().getResourceAsStream("/player.png"));
            imagemFundo = new Image(getClass().getResourceAsStream("/bg.png"));
            Image imgObstaculo = new Image(getClass().getResourceAsStream("/obstaculo.png"));

            jogador = new Jogador(Constantes.X_JOGADOR,
                    Constantes.Y_CHAO - Constantes.ALTURA_PADRAO/2,
                    Constantes.LARGURA_PADRAO,
                    Constantes.ALTURA_PADRAO);
            jogador.setSpriteSheet(imgJogador, 4);

            obstaculo = new Obstaculo(700, Constantes.Y_CHAO - 30, 40, 50);
            obstaculo.setImagem(imgObstaculo);
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagens: " + e.getMessage());
            jogador = new Jogador(Constantes.X_JOGADOR,
                    Constantes.Y_CHAO - Constantes.ALTURA_PADRAO/2,
                    Constantes.LARGURA_PADRAO,
                    Constantes.ALTURA_PADRAO);
            obstaculo = new Obstaculo(700, Constantes.Y_CHAO - 30, 40, 50);
        }

        cena.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                palco.close();
            }

            if (!gameOver && event.getCode() == KeyCode.UP) {
                jogador.pular();
            }
        });

        loop = new AnimationTimer() {
            private long ultimoUpdate = 0;

            @Override
            public void handle(long agora) {
                double dt = (agora - ultimoUpdate) / 1_000_000_000.0;
                ultimoUpdate = agora;
                if (dt > 0.05) {
                    dt = 0.05;
                }

                atualizar(dt);
                renderizar();
            }
        };

        palco.setTitle("Runner OpenGL - JavaFX");
        palco.setScene(cena);
        palco.setResizable(false);
        palco.show();

        loop.start();
    }

    private void atualizar(double dt) {
        if (gameOver) {
            return;
        }

        jogador.atualizarFisica(dt);
        jogador.atualizarAnimacao(dt);

        obstaculo.atualizar(dt);

        if (VerificadorColisao.colidem(jogador, obstaculo)) {
            gameOver = true;
            System.out.println("GAME OVER!");
        }
    }

    private void renderizar() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, Constantes.LARGURA_JANELA, Constantes.ALTURA_JANELA);

        if (imagemFundo != null) {
            gc.drawImage(imagemFundo, 0, 0, Constantes.LARGURA_JANELA, Constantes.ALTURA_JANELA);
        } else {
            gc.setFill(Color.rgb(80, 130, 230));
            gc.fillRect(0, 0, Constantes.LARGURA_JANELA, Constantes.ALTURA_JANELA);
        }

        gc.setStroke(Color.GREEN);
        gc.setLineWidth(3);
        gc.strokeLine(0, Constantes.Y_CHAO, Constantes.LARGURA_JANELA, Constantes.Y_CHAO);

        obstaculo.desenhar(gc);

        jogador.desenhar(gc);

        if (gameOver) {
            gc.setFill(Color.RED);
            gc.setFont(javafx.scene.text.Font.font(50));
            gc.fillText("GAME OVER", 300, 300);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
