package games.tabuleiro.batalhanaval;

public class Navio {
    private final String    nome;
    private final int       tamanho;
    private int             acertos;

    public Navio(String nome, int tamanho) {
        this.nome = nome;
        this.tamanho = tamanho;
        this.acertos = 0;
    }

    public String getNome() {
        return nome;
    }

    public int getTamanho() {
        return tamanho;
    }

    public void receberDano() {
        if (acertos < tamanho) acertos++;
    }

    public boolean estaDestruido() {
        return acertos >= tamanho;
    }
}
