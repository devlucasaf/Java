package application.system.marketplace.model;

import java.util.*;

public class Carrinho {

    private final Cliente   cliente;
    private Cupom           cupomAplicado;
    private final Map<String, ItemCarrinho> itens = new LinkedHashMap<>();

    public Carrinho(Cliente cliente) {
        this.cliente = cliente;
    }

    public void adicionarProduto(Produto produto, int quantidade) {
        if (!produto.temEstoque(quantidade)) {
            throw new IllegalStateException("Estoque insuficiente para " + produto.getNome());
        }
        itens.merge(produto.getId(), new ItemCarrinho(produto, quantidade),
                (existente, novo) -> {
                    existente.adicionarQuantidade(quantidade);
                    return existente;
                });
    }

    public void aplicarCupom(Cupom cupom) {
        this.cupomAplicado = cupom;
    }

    public double getSubtotal() {
        return itens.values()
                .stream()
                .mapToDouble(ItemCarrinho::getSubtotal)
                .sum();
    }

    public double getPesoTotalKg() {
        return itens.values()
                .stream()
                .mapToDouble(ItemCarrinho::getPesoTotalKg)
                .sum();
    }

    public double getDesconto() {
        if (cupomAplicado == null) {
            return 0;
        }
        return cupomAplicado.calcularDesconto(getSubtotal());
    }

    public double calcularFrete() {
        double subtotal = getSubtotal();
        if (subtotal >= 300.0) {
            return 0.0;
        }

        double taxaBase = 12.0;
        double precoPorKg = 3.5;
        return taxaBase + (getPesoTotalKg() * precoPorKg);
    }

    public double getTotal() {
        return Math.max(0, getSubtotal() - getDesconto()) + calcularFrete();
    }

    public Collection<ItemCarrinho> getItens() {
        return itens.values();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Cupom getCupomAplicado() {
        return cupomAplicado;
    }

    public boolean isVazio() {
        return itens.isEmpty();
    }
}
