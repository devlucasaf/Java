package org.application.restaurante;

public class Bebida extends ItemCardapio {
    private int     volumeMl;
    private boolean gelada;
    private boolean alcoolica;
    private double  teorAlcoolico; // percentual

    public Bebida(int id, String nome, String descricao, double preco, int tempoPreparo,
                  int volumeMl, boolean gelada, boolean alcoolica, double teorAlcoolico) {
        super(id, nome, descricao, preco, tempoPreparo,
                alcoolica ? CategoriaItem.BEBIDA_ALCOOLICA : CategoriaItem.BEBIDA);
        setVolumeMl(volumeMl);
        this.gelada = gelada;
        this.alcoolica = alcoolica;
        setTeorAlcoolico(teorAlcoolico);
    }

    public int getVolumeMl() {
        return volumeMl;
    }

    public void setVolumeMl(int volumeMl) {
        if (volumeMl <= 0) {
            throw new IllegalArgumentException("O volume deve ser maior que zero");
        }
        this.volumeMl = volumeMl;
    }

    public boolean isGelada() {
        return gelada;
    }

    public void setGelada(boolean gelada) {
        this.gelada = gelada;
    }

    public boolean isAlcoolica() {
        return alcoolica;
    }

    public double getTeorAlcoolico() {
        return teorAlcoolico;
    }

    public void setTeorAlcoolico(double teorAlcoolico) {
        if (teorAlcoolico < 0 || teorAlcoolico > 100) {
            throw new IllegalArgumentException("Teor alcoólico deve estar entre 0 e 100");
        }
        this.teorAlcoolico = teorAlcoolico;
    }

    @Override
    public double calcularPrecoFinal() {
        return alcoolica ? getPreco() * 1.10 : getPreco();
    }

    @Override
    public void exibirDetalhes() {
        System.out.printf("[BEBIDA] %s | %dml | %s | %s | Preço final: R$%.2f%n",
                detalhesBase(),
                volumeMl,
                gelada ? "Gelada" : "Natural",
                alcoolica ? String.format("Alcoólica (%.1f%%)", teorAlcoolico) : "Não-Alcoólica",
                calcularPrecoFinal());
    }
}

