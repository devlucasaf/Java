package application.system.dinheiro.financeiro;

import java.util.ArrayList;
import java.util.List;

public class Conta {
    private static int contadorId = 1;
    private int             id;
    private String          nome;
    private TipoConta       tipo;
    private double          saldo;
    private List<Transacao> transacoes;

    public Conta(String nome, TipoConta tipo, double saldoInicial) {
        this.id = contadorId++;
        this.nome = nome;
        this.tipo = tipo;
        this.saldo = saldoInicial;
        this.transacoes = new ArrayList<>();
    }

    public void adicionarTransacao(Transacao transacao) {
        transacoes.add(transacao);
        if (transacao.getTipo() == TipoTransacao.RECEITA) {
            saldo += transacao.getValor();
        } else {
            saldo -= transacao.getValor();
        }
        System.out.println("Transação registrada na conta " + nome + ". Saldo atual: R$" + saldo);
    }

    public void exibirExtrato() {
        System.out.println("\n--- EXTRATO DA CONTA: " + nome + " ---");
        System.out.println("Saldo atual: R$" + saldo);
        System.out.println("Transações:");

        for (Transacao transacao : transacoes) {
            System.out.println(transacao);
        }
    }

    public double getSaldo() {
        return saldo;
    }

    public String getNome() {
        return nome;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    @Override
    public String toString() {
        return "Conta{" + nome + ", " + tipo + ", saldo=R$" + saldo + "}";
    }
}