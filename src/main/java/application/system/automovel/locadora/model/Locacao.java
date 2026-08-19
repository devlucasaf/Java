package application.system.automovel.locadora.model;

import application.system.automovel.locadora.enums.FormaPagamento;
import application.system.automovel.locadora.enums.StatusLocacao;
import application.system.automovel.locadora.enums.TipoSeguro;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Locacao {
    private static int contadorId = 1;
    private int             id;
    private Veiculo         veiculo;
    private Cliente         cliente;
    private LocalDate       dataRetirada;
    private LocalDate       dataDevolucaoPrevista;
    private LocalDate       dataDevolucaoReal;
    private double          valorTotal;
    private StatusLocacao   status;
    private TipoSeguro      seguro;
    private FormaPagamento  formaPagamento;

    public Locacao(Veiculo veiculo, Cliente cliente, LocalDate dataRetirada,
                   LocalDate dataDevolucaoPrevista, TipoSeguro seguro, FormaPagamento formaPagamento) {
        this.id = contadorId++;
        this.veiculo = veiculo;
        this.cliente = cliente;
        this.dataRetirada = dataRetirada;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
        this.dataDevolucaoReal = null;
        this.valorTotal = 0.0;
        this.status = StatusLocacao.ATIVA;
        this.seguro = seguro;
        this.formaPagamento = formaPagamento;
    }

    public long calcularDiasPrevistos() {
        return ChronoUnit.DAYS.between(dataRetirada, dataDevolucaoPrevista);
    }

    public long calcularDiasEfetivos() {
        if (dataDevolucaoReal == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(dataRetirada, dataDevolucaoReal);
    }

    public void finalizar(LocalDate dataDevolucaoReal, double valorTotal) {
        this.dataDevolucaoReal = dataDevolucaoReal;
        this.valorTotal = valorTotal;
        this.status = StatusLocacao.CONCLUIDA;
        this.veiculo.devolver(0);
    }

    public void cancelar() {
        this.status = StatusLocacao.CANCELADA;
        this.veiculo.devolver(0);
    }

    public void exibirInformacoes() {
        System.out.println("--- LOCAÇÃO #" + id + " ---");
        System.out.println("Cliente: " + cliente.getNome());
        System.out.println("Veículo: " + veiculo.getModelo() + " (" + veiculo.getPlaca() + ")");
        System.out.println("Retirada: " + dataRetirada);
        System.out.println("Devolução prevista: " + dataDevolucaoPrevista);
        System.out.println("Devolução real: " + (dataDevolucaoReal != null ? dataDevolucaoReal : "Pendente"));
        System.out.println("Seguro: " + seguro);
        System.out.println("Forma de pagamento: " + formaPagamento);
        System.out.println("Valor total: R$" + valorTotal);
        System.out.println("Status: " + status);
    }

    public int getId() {
        return id;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDate getDataRetirada() {
        return dataRetirada;
    }

    public LocalDate getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public LocalDate getDataDevolucaoReal() {
        return dataDevolucaoReal;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public StatusLocacao getStatus() {
        return status;
    }

    public TipoSeguro getSeguro() {
        return seguro;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

}
