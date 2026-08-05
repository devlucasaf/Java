package application.exercicios.faculdade.cg;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Obstaculo extends Entidade {
    private Image imagem;

    public Obstaculo(float x, float y, float largura, float altura) {
        super(x, y, largura, altura);
    }

    public void setImagem(Image img) {
        this.imagem = img;
    }

    public void atualizar(double dt) {
        x -= Constantes.VEL_OBSTACULO * dt;

        if (x < -50) {
            x = Constantes.LARGURA_JANELA + 50;
        }
    }

    @Override
    public void desenhar(GraphicsContext gc) {
        if (imagem == null) {
            gc.setFill(javafx.scene.paint.Color.RED);
            gc.fillRect(x - largura/2, y - altura/2, largura, altura);
            return;
        }
        gc.drawImage(imagem,
                x - largura/2, y - altura/2,
                largura, altura);
    }
}
