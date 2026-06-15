package org.application.system.cinema;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Compra {
    private static int contadorId = 1;
    private int             id;
    private Cliente         cliente;
    private List<Ingresso>  ingressos;
    private double          valorTotal;
    private LocalDate       dataCompra;
    private FormaPagamento  formaPagamento;
    private boolean         finalizada;

    public Compra(Cliente cliente, FormaPagamento formaPagamento) {
        this.id = contadorId++;
        this.cliente = cliente;
        this.ingressos = new ArrayList<>();
        this.valorTotal = 0.0;
        this.dataCompra = LocalDate.now();
        this.formaPagamento = formaPagamento;
        this.finalizada = false;
    }

    public void adicionarIngresso(Ingresso ingresso) {
        ingressos.add(ingresso);
        valorTotal += ingresso.getPrecoPago();
    }

    public boolean finalizar() {
        if (finalizada) {
            return false;
        }

        if (ingressos.isEmpty()) {
            return false;
        }

        finalizada = true;
        for (Ingresso i : ingressos) {
            i.setCompra(this);
            i.getSessao().venderIngresso(i);
        }

        if (cliente != null) {
            cliente.adicionarCompra(this);
            int pontos = (int) (valorTotal / 10);
            cliente.adicionarPontos(pontos);
        }
        System.out.println("Compra #" + id + " finalizada. Total: R$" + valorTotal + " | Pagamento: " + formaPagamento);
        return true;
    }

    public void exibirResumo() {
        System.out.println("--- COMPRA #" + id + " ---");
        System.out.println("Cliente: " + (cliente != null ? cliente.getNome() : "Não identificado"));
        System.out.println("Data: " + dataCompra);
        System.out.println("Ingressos: " + ingressos.size());

        for (Ingresso i : ingressos) {
            System.out.println(
                    "  - Sessão: " + i.getSessao().getId()
                    + " | Assento: " + i.getAssento().getCodigo()
                    + " | Tipo: " + i.getTipo()
                    + " | R$" + i.getPrecoPago()
            );
        }
        System.out.println("Valor total: R$" + valorTotal);
        System.out.println("Forma pagamento: " + formaPagamento);
    }

    public int getId() {
        return id;
    }

    public double getValorTotal() {
        return valorTotal;
    }
}