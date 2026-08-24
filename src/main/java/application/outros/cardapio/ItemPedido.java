package application.outros.cardapio;

import application.system.restaurante.ItemCardapio;

public class ItemPedido {
    final ItemCardapio  item;
    int                 quantidade;

    public ItemPedido(ItemCardapio item, int quantidade) {
        this.item = item;
        this.quantidade = quantidade;
    }
}
