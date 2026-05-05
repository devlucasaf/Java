package org.application.loja;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pedido {
    private static int              sequencia = 1;

    private final int               id;
    private final Cliente           cliente;
    private final Vendedor          vendedor;
    private final LocalDateTime     data;
    private final List<PedidoItem>  itens;
    private Pagamento               pagamento;
    private boolean                 finalizado;

    public Pedido(Cliente cliente, Vendedor vendedor) {
        if (cliente == null) {
            throw new IllegalArgumentException("O cliente não pode ser nulo");
        }

        if (vendedor == null) {
            throw new IllegalArgumentException("O vendedor não pode ser nulo");
        }

        this.id = sequencia++;
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.data = LocalDateTime.now();
        this.itens = new ArrayList<>();
        this.finalizado = false;
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public LocalDateTime getData() {
        return data;
    }

    public List<PedidoItem> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public void adicionarItem(Item item, int quantidade) {
        if (finalizado) {
            throw new IllegalStateException("Não é possível alterar um pedido finalizado");
        }
        itens.add(new PedidoItem(item, quantidade));
    }

    public double calcularTotal() {
        return itens.stream().mapToDouble(PedidoItem::getSubTotal).sum();
    }

    public String finalizarPedido(Loja loja, Pagamento pagamento) {
        if (finalizado) {
            throw new IllegalStateException("Pedido já finalizado");
        }

        if (itens.isEmpty()) {
            throw new IllegalStateException("O pedido deve ter pelo menos um item");
        }

        Estoque estoque = loja.getEstoque();
        for (PedidoItem itemPedido : itens) {
            if (!estoque.temEstoqueSuficiente(itemPedido.getItem(), itemPedido.getQuantidade())) {
                throw new IllegalStateException(
                        "Estoque insuficiente para o item: " + itemPedido.getItem().getNome());
            }
        }

        for (PedidoItem itemPedido : itens) {
            estoque.deduzirEstoque(itemPedido.getItem(), itemPedido.getQuantidade());
        }

        double total = calcularTotal();
        pagamento.setValor(total);
        this.pagamento = pagamento;
        this.finalizado = true;

        vendedor.registrarVenda(total);
        cliente.adicionarCompra(this);
        loja.registrarPedido(this);

        return pagamento.processarPagamento();
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public boolean isFinalizado() {
        return finalizado;
    }
}
