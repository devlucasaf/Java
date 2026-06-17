package application.system.dinheiro.financeiro;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transacao {
    private static int contadorId = 1;
    private int             id;
    private double          valor;
    private LocalDate       data;
    private String          descricao;
    private TipoTransacao   tipo;
    private FormaPagamento  formaPagamento;
    private Categoria       categoria;
    private Conta           conta;

    public Transacao(double valor, LocalDate data, String descricao, TipoTransacao tipo,
                     FormaPagamento formaPagamento, Categoria categoria, Conta conta) {
        this.id = contadorId++;
        this.valor = valor;
        this.data = data;
        this.descricao = descricao;
        this.tipo = tipo;
        this.formaPagamento = formaPagamento;
        this.categoria = categoria;
        this.conta = conta;
    }

    public boolean isDespesa() {
        return tipo == TipoTransacao.DESPESA;
    }

    public boolean isReceita() {
        return tipo == TipoTransacao.RECEITA;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return "[" + id + "] " + data.format(formatter) + " - " + descricao +
                " | " + tipo + " | R$" + valor + " | " + formaPagamento +
                " | Cat: " + categoria.getNome() + " | Conta: " + conta.getNome();
    }

    public double getValor() {
        return valor;
    }

    public LocalDate getData() {
        return data;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Conta getConta() {
        return conta;
    }
}
