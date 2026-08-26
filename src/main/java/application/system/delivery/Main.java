package application.system.delivery;

import application.estruturas.grafos.Grafo;
import application.system.delivery.enums.StatusPedido;
import application.system.delivery.model.Cliente;
import application.system.delivery.model.Entregador;
import application.system.delivery.model.Pedido;
import application.system.delivery.model.Restaurante;
import application.system.delivery.service.SistemaDelivery;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Grafo mapa = new Grafo();
        mapa.adicionarVertice("Centro", 0, 0);
        mapa.adicionarVertice("Norte", 0, 3);
        mapa.adicionarVertice("Sul", 0, -3);
        mapa.adicionarVertice("Leste", 3, 0);
        mapa.adicionarVertice("Oeste", -3, 0);

        mapa.adicionarAresta("Centro", "Norte", 3.0);
        mapa.adicionarAresta("Centro", "Sul", 3.5);
        mapa.adicionarAresta("Centro", "Leste", 2.5);
        mapa.adicionarAresta("Centro", "Oeste", 4.0);
        mapa.adicionarAresta("Norte", "Leste", 4.2);
        mapa.adicionarAresta("Sul", "Oeste", 5.0);

        SistemaDelivery sistema = new SistemaDelivery(mapa);

        Restaurante pizzaria = new Restaurante("R1", "Pizzaria Bella", "Centro", 25);
        pizzaria.adicionarItemCardapio("Pizza Marguerita", 45.90);
        pizzaria.adicionarItemCardapio("Refrigerante 2L", 12.00);
        sistema.cadastrarRestaurante(pizzaria);

        sistema.cadastrarEntregador(new Entregador("E1", "Joao", "Norte"));
        sistema.cadastrarEntregador(new Entregador("E2", "Carla", "Leste"));
        sistema.cadastrarEntregador(new Entregador("E3", "Pedro", "Centro"));

        Cliente cliente = new Cliente("C1", "Fernanda Lima", "Sul");

        Map<String, Integer> itensPedido = new LinkedHashMap<>();
        itensPedido.put("Pizza Marguerita", 2);
        itensPedido.put("Refrigerante 2L", 1);

        System.out.println("=== Criando pedido ===");
        Pedido pedido = sistema.criarPedido(cliente, pizzaria, itensPedido);
        pedido.imprimirResumo();

        System.out.println();
        System.out.println("O sistema escolheu automaticamente o entregador mais proximo do");
        System.out.println("restaurante usando o algoritmo de Dijkstra sobre o grafo de bairros.");

        pedido.avancarStatus(StatusPedido.EM_PREPARO);
        pedido.avancarStatus(StatusPedido.A_CAMINHO);

        System.out.println();
        System.out.println("=== Concluindo entrega ===");
        sistema.concluirEntrega(pedido, 5.0);
        pedido.imprimirResumo();

        System.out.println();
        System.out.println("Entregador agora disponivel em: " + pedido.getEntregador().getBairroAtual());
        System.out.println("Avaliacao media do entregador: " + pedido.getEntregador().getAvaliacaoMedia());
    }
}
