package org.application.system.esporte.academia;

import java.time.LocalDate;

public class Pagamento {
    private static int contadorId = 1;
    private int                     id;
    private Aluno                   aluno;
    private LocalDate               dataVencimento;
    private LocalDate               dataPagamento;
    private double                  valor;
    private StatusPagamento         status;
    private FormaPagamentoAcademia  formaPagamento;

    public Pagamento(Aluno aluno, LocalDate dataVencimento, double valor, FormaPagamentoAcademia formaPagamento) {
        this.id = contadorId++;
        this.aluno = aluno;
        this.dataVencimento = dataVencimento;
        this.valor = valor;
        this.formaPagamento = formaPagamento;
        this.status = StatusPagamento.PENDENTE;
        this.dataPagamento = null;
    }

    public void efetuarPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
        this.status = StatusPagamento.PAGO;
        System.out.println("Pagamento de R$" + valor + " efetuado em " + dataPagamento);
    }

    public void marcarComoAtrasado() {
        if (status == StatusPagamento.PENDENTE && dataVencimento.isBefore(LocalDate.now())) {
            this.status = StatusPagamento.ATRASADO;
            System.out.println("Pagamento do aluno " + aluno.getNome() + " está ATRASADO!");
        }
    }

    public Aluno getAluno() {
        return aluno;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public double getValor() {
        return valor;
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public FormaPagamentoAcademia getFormaPagamento() {
        return formaPagamento;
    }
}
