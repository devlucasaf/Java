package org.application.system.restaurante;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cliente {
    private String  nome;
    private String  telefone;
    private String  cpf;
    private int     pontosFidelidade;

    private final List<Pedido> historicoPedidos = new ArrayList<>();

    public Cliente(String nome, String telefone, String cpf) {
        setNome(nome);
        setTelefone(telefone);
        setCpf(cpf);
        this.pontosFidelidade = 0;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome não pode ser vazio");
        }
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        if (telefone == null || telefone.isBlank()) {
            throw new IllegalArgumentException("O telefone não pode ser vazio");
        }
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("O CPF não pode ser vazio");
        }
        this.cpf = cpf;
    }

    public int getPontosFidelidade() {
        return pontosFidelidade;
    }

    public void adicionarPontos(int pontos) {
        if (pontos < 0) {
            throw new IllegalArgumentException("Pontos não pode ser negativo");
        }
        this.pontosFidelidade += pontos;
    }

    public boolean resgatarPontos(int pontos) {
        if (pontos > pontosFidelidade) {
            return false;
        }
        this.pontosFidelidade -= pontos;
        return true;
    }

    public void adicionarPedido(Pedido pedido) {
        historicoPedidos.add(pedido);
    }

    public List<Pedido> getHistoricoPedidos() {
        return Collections.unmodifiableList(historicoPedidos);
    }

    public double getTotalGasto() {
        return historicoPedidos.stream()
                .filter(Pedido::isFinalizado)
                .mapToDouble(Pedido::calcularTotal)
                .sum();
    }
}

