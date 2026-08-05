package application.exercicios.faculdade.cg;

import javafx.scene.canvas.GraphicsContext;

public abstract class Entidade {
    protected float x;
    protected float y;
    protected float largura;
    protected float altura;

    public Entidade(float x, float y, float largura, float altura) {
        this.x = x;
        this.y = y;
        this.largura = largura;
        this.altura = altura;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getLargura() {
        return largura;
    }

    public float getAltura() {
        return altura;
    }

    public abstract void desenhar(GraphicsContext gc);

    public float[] getAABB() {
        float esquerda = x - largura/2;
        float direita  = x + largura/2;
        float topo     = y - altura/2;
        float base     = y + altura/2;
        return new float[]{esquerda, direita, topo, base};
    }
}
