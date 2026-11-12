package application.estruturas.rubronegra;

public class No<T extends Comparable<T>> {

    public static final boolean VERMELHO = true;
    public static final boolean PRETO = false;

    public T       chave;
    public No<T>    esquerda;
    public No<T>    direita;
    public boolean  cor;

    public No(T chave, boolean cor) {
        this.chave = chave;
        this.cor = cor;
    }
}