package org.application.system.loja;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cliente {
    private String              nome;
    private String              cpf;
    private final List<Pedido>  historicoCompras = new ArrayList<>();

    public Cliente(String nome, String cpf) {
        setNome(nome);
        setCpf(cpf);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do cliente não pode ser vazio");
        }
        this.nome = nome;
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

    public void adicionarCompra(Pedido pedido) {
        historicoCompras.add(pedido);
    }

    public List<Pedido> getHistoricoCompras() {
        return Collections.unmodifiableList(historicoCompras);
    }
}
