package application.system.restaurante;

import java.util.ArrayList;
import java.util.List;

public class FilaCozinha {
    private final List<Pedido> filaPreparo = new ArrayList<>();
    private final List<Pedido> prontos = new ArrayList<>();

    public void adicionarPedido(Pedido pedido) {
        pedido.enviarParaCozinha();
        filaPreparo.add(pedido);
    }

    public Pedido prepararProximo() {
        if (filaPreparo.isEmpty()) {
            throw new IllegalStateException("Não há pedidos na fila");
        }
        Pedido pedido = filaPreparo.remove(0);
        pedido.marcarComoPronto();
        prontos.add(pedido);
        return pedido;
    }

    public List<Pedido> getPedidosEmPreparo() {
        return new ArrayList<>(filaPreparo);
    }

    public List<Pedido> getPedidosProntos() {
        return new ArrayList<>(prontos);
    }

    public Pedido entregarPedido() {
        if (prontos.isEmpty()) {
            throw new IllegalStateException("Não há pedidos prontos para entrega");
        }
        Pedido pedido = prontos.remove(0);
        pedido.entregar();
        return pedido;
    }

    public int getTamanhoFila() {
        return filaPreparo.size();
    }

    public int getQuantidadeProntos() {
        return prontos.size();
    }

    public void exibirStatus() {
        System.out.println("\n=== FILA DA COZINHA ===");
        System.out.printf("Em preparo: %d | Prontos para entrega: %d%n", filaPreparo.size(), prontos.size());

        if (!filaPreparo.isEmpty()) {
            System.out.println("-- Em Preparo --");
            for (Pedido p : filaPreparo) {
                System.out.printf("  Pedido #%d (Mesa %d) - %d itens%n", p.getId(), p.getMesa().getNumero(), p.getItens().size());
            }
        }

        if (!prontos.isEmpty()) {
            System.out.println("-- Prontos --");
            for (Pedido p : prontos) {
                System.out.printf("  Pedido #%d (Mesa %d) - PRONTO!%n", p.getId(), p.getMesa().getNumero());
            }
        }
    }
}

