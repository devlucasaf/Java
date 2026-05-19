package org.application.restaurante;

public abstract class Pagamento {
    private double valor;

    protected Pagamento(double valor) {
        setValor(valor);
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        if (valor < 0) {
            throw new IllegalArgumentException("O valor do pagamento não pode ser negativo");
        }
        this.valor = valor;
    }

    public abstract String processarPagamento();

    public abstract String getTipoPagamento();
}

