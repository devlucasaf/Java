package org.application.loja;

public class PedidoItem {
    private final Item      item;
    private final int       quantidade;
    private final double    precoUnitario;

    public PedidoItem(Item item, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero");
        }

        this.item = item;
        this.quantidade = quantidade;
        this.precoUnitario = item.getPreco();
    }

    public Item getItem() {
        return item;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public double getSubTotal() {
        return precoUnitario * quantidade;
    }
}
