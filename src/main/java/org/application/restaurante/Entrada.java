package org.application.restaurante;

public class Entrada extends ItemCardapio {
    private boolean paraCompartilhar;
    private int     porcoes;

    public Entrada(int id, String nome, String descricao, double preco, int tempoPreparo,
                   boolean paraCompartilhar, int porcoes) {
        super(id, nome, descricao, preco, tempoPreparo, CategoriaItem.ENTRADA);
        this.paraCompartilhar = paraCompartilhar;
        setPorcoes(porcoes);
    }

    public boolean isParaCompartilhar() {
        return paraCompartilhar;
    }

    public void setParaCompartilhar(boolean paraCompartilhar) {
        this.paraCompartilhar = paraCompartilhar;
    }

    public int getPorcoes() {
        return porcoes;
    }

    public void setPorcoes(int porcoes) {
        if (porcoes <= 0) {
            throw new IllegalArgumentException("Porções deve ser maior que zero");
        }
        this.porcoes = porcoes;
    }

    @Override
    public double calcularPrecoFinal() {
        return getPreco();
    }

    @Override
    public void exibirDetalhes() {
        System.out.printf("[ENTRADA] %s | %s | Serve %d pessoa(s)%n",
                detalhesBase(),
                paraCompartilhar ? "Para compartilhar" : "Individual",
                porcoes);
    }
}

