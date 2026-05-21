package org.application.petshop;

public class ItemVenda {
    private Produto produto;
    private int     quantidade;
    private double  precoUnitario;

    public ItemVenda(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = produto.getPreco();
    }

    public double getSubtotal() {
        return precoUnitario * quantidade;
    }

    public void exibirItem() {
        System.out.println("  " + produto.getNome() + " x " + quantidade + " = R$" + getSubtotal());
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }
}