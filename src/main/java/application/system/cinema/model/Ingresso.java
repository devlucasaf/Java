package application.system.cinema.model;

import application.system.cinema.enums.TipoIngresso;

public class Ingresso {
    private static int contadorId = 1;
    private int             id;
    private Sessao          sessao;
    private Assento         assento;
    private Cliente         cliente;
    private TipoIngresso tipo;
    private double          precoPago;
    private Compra          compra;

    public Ingresso(Sessao sessao, Assento assento, Cliente cliente, TipoIngresso tipo) {
        this.id = contadorId++;
        this.sessao = sessao;
        this.assento = assento;
        this.cliente = cliente;
        this.tipo = tipo;
        this.precoPago = sessao.calcularPrecoIngresso(tipo);
    }

    public void exibirInformacoes() {
        System.out.println("Ingresso #" + id);
        System.out.println("Filme: " + sessao.getFilme().getTitulo());
        System.out.println("Sessão: " + sessao.getId() + " - " + sessao.getHorario());
        System.out.println("Assento: " + assento.getCodigo());
        System.out.println("Tipo: " + tipo);
        System.out.println("Preço: R$" + precoPago);
    }

    public int getId() {
        return id;
    }

    public Sessao getSessao() {
        return sessao;
    }

    public Assento getAssento() {
        return assento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public TipoIngresso getTipo() {
        return tipo;
    }

    public double getPrecoPago() {
        return precoPago;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }
}