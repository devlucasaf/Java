package application.system.delivery.service;

import application.estruturas.grafos.Dijkstra;
import application.estruturas.grafos.Grafo;
import application.estruturas.grafos.Resultado;
import application.system.delivery.enums.Status;
import application.system.delivery.enums.StatusPedido;
import application.system.delivery.model.Cliente;
import application.system.delivery.model.Entregador;
import application.system.delivery.model.Pedido;
import application.system.delivery.model.Restaurante;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SistemaDelivery {

    private final Grafo                     mapaBairros;
    private final Map<String, Restaurante>  restaurantes = new LinkedHashMap<>();
    private final Map<String, Entregador>   entregadores = new LinkedHashMap<>();
    private final List<Pedido>              pedidos = new ArrayList<>();

    private static final double PRECO_POR_KM = 2.5;
    private static final double TAXA_BASE_ENTREGA = 5.0;

    public SistemaDelivery(Grafo mapaBairros) {
        this.mapaBairros = mapaBairros;
    }

    public void cadastrarRestaurante(Restaurante restaurante) {
        restaurantes.put(restaurante.getId(), restaurante);
    }

    public void cadastrarEntregador(Entregador entregador) {
        entregadores.put(entregador.getId(), entregador);
    }

    public Restaurante buscarRestaurante(String id) {
        return restaurantes.get(id);
    }

    /** Cria o pedido, calcula a rota até o cliente e atribui o entregador disponível mais próximo. */
    public Pedido criarPedido(Cliente cliente, Restaurante restaurante, Map<String, Integer> itens) {
        Pedido pedido = new Pedido(cliente, restaurante, itens);

        Entregador escolhido = encontrarEntregadorMaisProximo(restaurante.getBairro());
        if (escolhido == null) {
            throw new IllegalStateException("Nenhum entregador disponivel no momento.");
        }

        Resultado rotaAteCliente = Dijkstra.calcular(mapaBairros, restaurante.getBairro(), cliente.getBairro());
        if (rotaAteCliente.caminho.isEmpty()) {
            throw new IllegalStateException("Nao ha rota disponivel entre o restaurante e o cliente.");
        }

        double custoFrete = TAXA_BASE_ENTREGA + rotaAteCliente.custoTotal * PRECO_POR_KM / 10.0;
        pedido.definirRota(rotaAteCliente.custoTotal, custoFrete);

        pedido.atribuirEntregador(escolhido);
        escolhido.setStatus(Status.EM_ENTREGA);

        pedidos.add(pedido);
        return pedido;
    }

    private Entregador encontrarEntregadorMaisProximo(String bairroRestaurante) {
        Entregador melhor = null;
        double menorDistancia = Double.POSITIVE_INFINITY;

        for (Entregador entregador : entregadores.values()) {
            if (entregador.getStatus() != Status.DISPONIVEL) continue;

            Resultado resultado = Dijkstra.calcular(mapaBairros, entregador.getBairroAtual(), bairroRestaurante);
            if (!resultado.caminho.isEmpty() && resultado.custoTotal < menorDistancia) {
                menorDistancia = resultado.custoTotal;
                melhor = entregador;
            }
        }
        return melhor;
    }

    public void concluirEntrega(Pedido pedido, double notaAvaliacao) {
        pedido.avancarStatus(StatusPedido.ENTREGUE);
        Entregador entregador = pedido.getEntregador();
        if (entregador != null) {
            entregador.moverPara(pedido.getCliente().getBairro());
            entregador.setStatus(Status.DISPONIVEL);
            entregador.registrarEntregaConcluida(notaAvaliacao);
        }
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }
}

