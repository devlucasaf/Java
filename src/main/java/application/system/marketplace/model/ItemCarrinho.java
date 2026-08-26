package application.system.marketplace.model;

public class ItemCarrinho {

    private final Produto   produto;
    private int             quantidade;

    public ItemCarrinho(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void adicionarQuantidade(int quantidade) {
        this.quantidade += quantidade;
    }

    public double getSubtotal() {
        return produto.getPreco() * quantidade;
    }

    public double getPesoTotalKg() {
        return produto.getPesoKg() * quantidade;
    }
}
