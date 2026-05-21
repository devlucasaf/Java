package org.application.petshop;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Venda {
    private static int contadorId = 1;
    private int                     id;
    private ClientePetshop          cliente;
    private List<ItemVenda>         itens;
    private LocalDate               dataVenda;
    private double                  valorTotal;
    private boolean                 usadoSaldoFidelidade;
    private FormaPagamentoPetshop   formaPagamento;

    public Venda(ClientePetshop cliente, FormaPagamentoPetshop formaPagamento) {
        this.id = contadorId++;
        this.cliente = cliente;
        this.itens = new ArrayList<>();
        this.dataVenda = LocalDate.now();
        this.valorTotal = 0.0;
        this.formaPagamento = formaPagamento;
        this.usadoSaldoFidelidade = false;
    }

    public void adicionarItem(Produto produto, int quantidade) {
        if (produto.reduzirEstoque(quantidade)) {
            ItemVenda item = new ItemVenda(produto, quantidade);
            itens.add(item);
            valorTotal += item.getSubtotal();
            System.out.println(quantidade + "x " + produto.getNome() + " adicionado à venda.");
        } else {
            System.out.println("Estoque insuficiente para " + produto.getNome());
        }
    }

    public void adicionarServico(Servico servico, Animal animal) {
        double precoServico = servico.calcularPreco(animal);
        valorTotal += precoServico;
        System.out.println("Serviço " + servico.getTipo() + " para " + animal.getNome() + " adicionado. Valor: R$" + precoServico);
    }

    public void aplicarSaldoFidelidade() {
        if (cliente.getSaldoFidelidade() > 0 && !usadoSaldoFidelidade) {
            double desconto = Math.min(valorTotal, cliente.getSaldoFidelidade());
            valorTotal -= desconto;
            cliente.usarSaldoFidelidade(desconto);
            usadoSaldoFidelidade = true;
            System.out.println("Desconto de R$" + desconto + " aplicado via saldo fidelidade.");
        }
    }

    public void finalizarVenda() {
        System.out.println("\n--- VENDA #" + id + " FINALIZADA ---");
        System.out.println("Cliente: " + cliente.getNome());
        System.out.println("Data: " + dataVenda);
        System.out.println("Itens:");

        for (ItemVenda item : itens) {
            item.exibirItem();
        }
        System.out.println("Valor total: R$" + valorTotal);
        System.out.println("Forma de pagamento: " + formaPagamento);

        if (usadoSaldoFidelidade) {
            System.out.println("Saldo fidelidade utilizado.");
        }
        cliente.adicionarPontosFidelidade(valorTotal);
    }

    public int getId() {
        return id;
    }

    public double getValorTotal() {
        return valorTotal;
    }
}
