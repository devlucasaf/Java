package org.application.loja;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Loja {
    private String                  nome;
    private final Estoque           estoque;
    private final List<Vendedor>    vendedores;
    private final List<Pedido>      pedidos;

    public Loja(String nome) {
        setNome(nome);
        this.estoque = new Estoque();
        this.vendedores = new ArrayList<>();
        this.pedidos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome da loja não pode ser vazio");
        }
        this.nome = nome;
    }

    public Estoque getEstoque() {
        return estoque;
    }

    public void adicionarVendedor(Vendedor vendedor) {
        vendedores.add(vendedor);
    }

    public void registrarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public List<Vendedor> getVendedores() {
        return Collections.unmodifiableList(vendedores);
    }

    public List<Pedido> getPedidos() {
        return Collections.unmodifiableList(pedidos);
    }
}
