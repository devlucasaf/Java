package application.system.marketplace.model;

import application.system.marketplace.enums.Status;

import java.util.*;

public class Pedido {

    private static int proximoNumero = 1000;

    private final int                   numero;
    private final Cliente               cliente;
    private final List<ItemCarrinho>    itens;
    private final double                subtotal;
    private final double                desconto;
    private final double                frete;
    private final double                total;
    private Status                      status;

    public Pedido(Carrinho carrinho) {
        this.numero = proximoNumero++;
        this.cliente = carrinho.getCliente();
        this.itens = new ArrayList<>(carrinho.getItens());
        this.subtotal = carrinho.getSubtotal();
        this.desconto = carrinho.getDesconto();
        this.frete = carrinho.calcularFrete();
        this.total = carrinho.getTotal();
        this.status = Status.AGUARDANDO_PAGAMENTO;
    }

    public int getNumero() {
        return numero;
    }

    public Status getStatus() {
        return status;
    }

    public void avancarStatus(Status novoStatus) {
        this.status = novoStatus;
    }

    public double getTotal() {
        return total;
    }

    public void imprimirResumo() {
        System.out.println("===== Pedido #" + numero + " =====");
        System.out.println("Cliente: " + cliente.getNome());
        for (ItemCarrinho item : itens) {
            System.out.printf("  %dx %s - R$ %.2f%n", item.getQuantidade(), item.getProduto().getNome(), item.getSubtotal());
        }
        System.out.printf("Subtotal: R$ %.2f%n", subtotal);
        System.out.printf("Desconto: R$ %.2f%n", desconto);
        System.out.printf("Frete: R$ %.2f%n", frete);
        System.out.printf("Total: R$ %.2f%n", total);
        System.out.println("Status: " + status);
    }
}
