package org.application.restaurante;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mesa {
    private int         numero;
    private int         capacidade;
    private StatusMesa  status;
    private final List<Pedido> pedidosAtivos = new ArrayList<>();

    public Mesa(int numero, int capacidade) {
        setNumero(numero);
        setCapacidade(capacidade);
        this.status = StatusMesa.LIVRE;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        if (numero <= 0) {
            throw new IllegalArgumentException("O número da mesa deve ser maior que zero");
        }
        this.numero = numero;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        if (capacidade <= 0) {
            throw new IllegalArgumentException("A capacidade deve ser maior que zero");
        }
        this.capacidade = capacidade;
    }

    public StatusMesa getStatus() {
        return status;
    }

    public void ocupar() {
        if (status != StatusMesa.LIVRE && status != StatusMesa.RESERVADA) {
            throw new IllegalStateException("Mesa " + numero + " não pode ser ocupada. Status atual: " + status.getDescricao());
        }
        this.status = StatusMesa.OCUPADA;
    }

    public void liberar() {
        this.status = StatusMesa.EM_LIMPEZA;
        pedidosAtivos.clear();
    }

    public void limpar() {
        if (status != StatusMesa.EM_LIMPEZA) {
            throw new IllegalStateException("Mesa " + numero + " não está em limpeza");
        }
        this.status = StatusMesa.LIVRE;
    }

    public void reservar() {
        if (status != StatusMesa.LIVRE) {
            throw new IllegalStateException("Mesa " + numero + " não está livre para reserva");
        }
        this.status = StatusMesa.RESERVADA;
    }

    public void adicionarPedido(Pedido pedido) {
        pedidosAtivos.add(pedido);
    }

    public List<Pedido> getPedidosAtivos() {
        return Collections.unmodifiableList(pedidosAtivos);
    }

    public double getContaTotal() {
        return pedidosAtivos.stream().mapToDouble(Pedido::calcularTotal).sum();
    }

    @Override
    public String toString() {
        return String.format("Mesa %d | Capacidade: %d | Status: %s",
                numero,
                capacidade,
                status.getDescricao()
        );
    }
}

