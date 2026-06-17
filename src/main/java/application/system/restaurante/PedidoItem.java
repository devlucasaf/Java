package application.system.restaurante;

public class PedidoItem {
    private final ItemCardapio  item;
    private final int           quantidade;
    private final double        precoUnitario;
    private String              observacao;

    public PedidoItem(ItemCardapio item, int quantidade, String observacao) {
        if (item == null) {
            throw new IllegalArgumentException("O item não pode ser nulo");
        }

        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero");
        }
        this.item = item;
        this.quantidade = quantidade;
        this.precoUnitario = item.calcularPrecoFinal();
        this.observacao = observacao != null ? observacao : "";
    }

    public PedidoItem(ItemCardapio item, int quantidade) {
        this(item, quantidade, "");
    }

    public ItemCardapio getItem() {
        return item;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao != null ? observacao : "";
    }

    public double getSubTotal() {
        return precoUnitario * quantidade;
    }

    @Override
    public String toString() {
        String obs = observacao.isEmpty() ? "" : " [" + observacao + "]";
        return String.format("  %dx %s (R$%.2f) = R$%.2f%s",
                quantidade,
                item.getNome(),
                precoUnitario,
                getSubTotal(),
                obs
        );
    }
}

