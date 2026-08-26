package application.system.delivery.model;

import application.system.delivery.enums.StatusPedido;

import java.util.Map;

public class Pedido {

    private static int proximoNumero = 5000;

    private final int                   numero;
    private final Cliente               cliente;
    private final Restaurante           restaurante;
    private final Map<String, Integer>  itens;
    private final double                valorTotal;
    private Entregador                  entregador;
    private StatusPedido                status;
    private double                      distanciaRotaKm;
    private double                      custoFrete;

    public Pedido(Cliente cliente, Restaurante restaurante, Map<String, Integer> itens) {
        this.numero = proximoNumero++;
        this.cliente = cliente;
        this.restaurante = restaurante;
        this.itens = itens;
        this.status = StatusPedido.RECEBIDO;

        double total = 0;
        for (Map.Entry<String, Integer> entrada : itens.entrySet()) {
            total += restaurante.getPrecoItem(entrada.getKey()) * entrada.getValue();
        }
        this.valorTotal = total;
    }

    public int getNumero() {
        return numero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void avancarStatus(StatusPedido novoStatus) {
        this.status = novoStatus;
    }

    public void atribuirEntregador(Entregador entregador) {
        this.entregador = entregador;
    }

    public Entregador getEntregador() {
        return entregador;
    }

    public void definirRota(double distanciaKm, double custoFrete) {
        this.distanciaRotaKm = distanciaKm;
        this.custoFrete = custoFrete;
    }

    public void imprimirResumo() {
        System.out.println("===== Pedido #" + numero + " =====");
        System.out.println("Cliente: " + cliente.getNome() + " (" + cliente.getBairro() + ")");
        System.out.println("Restaurante: " + restaurante.getNome() + " (" + restaurante.getBairro() + ")");
        for (Map.Entry<String, Integer> entrada : itens.entrySet()) {
            System.out.printf("  %dx %s%n", entrada.getValue(), entrada.getKey());
        }

        System.out.printf("Valor dos itens: R$ %.2f%n", valorTotal);
        System.out.printf("Distancia da rota: %.1f km | Frete: R$ %.2f%n", distanciaRotaKm, custoFrete);
        System.out.printf("Total: R$ %.2f%n", valorTotal + custoFrete);
        if (entregador != null) {
            System.out.println("Entregador: " + entregador.getNome());
        }
        System.out.println("Status: " + status);
    }
}

