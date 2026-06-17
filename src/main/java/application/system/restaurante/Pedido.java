package application.system.restaurante;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa um pedido feito em uma mesa do restaurante.
 */
public class Pedido {
    private static int sequencia = 1;

    private final int               id;
    private final Cliente           cliente;
    private final Garcom            garcom;
    private final Mesa              mesa;
    private final LocalDateTime     dataHora;
    private final List<PedidoItem>  itens;
    private StatusPedido            status;
    private Pagamento               pagamento;
    private boolean                 finalizado;
    private String                  observacaoGeral;

    public Pedido(Cliente cliente, Garcom garcom, Mesa mesa) {
        if (cliente == null) {
            throw new IllegalArgumentException("O cliente não pode ser nulo");
        }

        if (garcom == null) {
            throw new IllegalArgumentException("O garçom não pode ser nulo");
        }

        if (mesa == null) {
            throw new IllegalArgumentException("A mesa não pode ser nula");
        }

        this.id = sequencia++;
        this.cliente = cliente;
        this.garcom = garcom;
        this.mesa = mesa;
        this.dataHora = LocalDateTime.now();
        this.itens = new ArrayList<>();
        this.status = StatusPedido.ABERTO;
        this.finalizado = false;
        this.observacaoGeral = "";
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Garcom getGarcom() {
        return garcom;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public List<PedidoItem> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public String getObservacaoGeral() {
        return observacaoGeral;
    }

    public void setObservacaoGeral(String observacaoGeral) {
        this.observacaoGeral = observacaoGeral != null ? observacaoGeral : "";
    }

    public void adicionarItem(ItemCardapio item, int quantidade) {
        adicionarItem(item, quantidade, "");
    }

    public void adicionarItem(ItemCardapio item, int quantidade, String observacao) {
        if (finalizado) {
            throw new IllegalStateException("Não é possível alterar um pedido finalizado");
        }

        if (!item.isDisponivel()) {
            throw new IllegalStateException("Item indisponível: " + item.getNome());
        }
        itens.add(new PedidoItem(item, quantidade, observacao));
    }

    public void removerItem(int indice) {
        if (finalizado) {
            throw new IllegalStateException("Não é possível alterar um pedido finalizado");
        }

        if (indice < 0 || indice >= itens.size()) {
            throw new IndexOutOfBoundsException("Índice inválido");
        }
        itens.remove(indice);
    }

    public double calcularTotal() {
        return itens.stream().mapToDouble(PedidoItem::getSubTotal).sum();
    }

    public double calcularTotalComGorjeta(double percentualGorjeta) {
        return calcularTotal() * (1 + percentualGorjeta);
    }

    public void enviarParaCozinha() {
        if (itens.isEmpty()) {
            throw new IllegalStateException("O pedido deve ter pelo menos um item");
        }

        if (status != StatusPedido.ABERTO) {
            throw new IllegalStateException("Pedido já enviado para cozinha");
        }
        this.status = StatusPedido.EM_PREPARO;
    }

    public void marcarComoPronto() {
        if (status != StatusPedido.EM_PREPARO) {
            throw new IllegalStateException("Pedido não está em preparo");
        }
        this.status = StatusPedido.PRONTO;
    }

    public void entregar() {
        if (status != StatusPedido.PRONTO) {
            throw new IllegalStateException("Pedido não está pronto");
        }
        this.status = StatusPedido.ENTREGUE;
    }

    public void cancelar() {
        if (finalizado) {
            throw new IllegalStateException("Pedido já finalizado");
        }
        this.status = StatusPedido.CANCELADO;
        this.finalizado = true;
    }

    public String finalizarPedido(Restaurante restaurante, Pagamento pagamento) {
        if (finalizado) {
            throw new IllegalStateException("Pedido já finalizado");
        }

        if (itens.isEmpty()) {
            throw new IllegalStateException("O pedido deve ter pelo menos um item");
        }

        double total = calcularTotal();
        pagamento.setValor(total);
        this.pagamento = pagamento;
        this.status = StatusPedido.FINALIZADO;
        this.finalizado = true;

        garcom.registrarAtendimento(total);
        cliente.adicionarPedido(this);
        cliente.adicionarPontos((int) (total / 10)); // 1 ponto a cada R$10
        restaurante.registrarPedido(this);

        return pagamento.processarPagamento();
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public int getTempoPreparoEstimado() {
        return itens.stream()
                .mapToInt(pi -> pi.getItem().getTempoPreparo())
                .max()
                .orElse(0);
    }

    public void exibirResumo() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        System.out.printf(" Pedido #%d  Mesa %d | %s%n",
                id,
                mesa.getNumero(),
                dataHora.format(fmt)
        );

        System.out.printf(" Cliente: %s  Garçom: %s%n",
                cliente.getNome(),
                garcom.getNome()
        );

        System.out.printf(" Status: %s%n",
                status.getDescricao()
        );

        for (PedidoItem item : itens) {
            System.out.println("" + item);
        }

        System.out.printf(" TOTAL: R$%.2f%n", calcularTotal());
        System.out.printf(" Tempo estimado: %d min%n", getTempoPreparoEstimado());

        if (!observacaoGeral.isEmpty()) {
            System.out.printf(" Obs: %s%n", observacaoGeral);
        }

    }
}

