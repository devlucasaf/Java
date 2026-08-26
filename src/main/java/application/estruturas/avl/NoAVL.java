package application.estruturas.avl;

public class NoAVL {
    int     chave;
    NoAVL   esquerda;
    NoAVL   direita;
    int     altura;

    public NoAVL(int chave) {
        this.chave = chave;
        this.altura = 1;
    }
}
