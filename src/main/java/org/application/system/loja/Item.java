package org.application.system.loja;

public abstract class Item {
    private int     id;
    private String  nome;
    private double  preco;
    private int     quantidadeEstoque;

    public Item(int id, String nome, double preco, int quantidadeEstoque) {
        setId(id);
        setNome(nome);
        setPreco(preco);
        setQuantidadeEstoque(quantidadeEstoque);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("O id deve ser maior que zero");
        }
        this.id = id;
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

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        if (preco < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo");
        }
        this.preco = preco;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        if (quantidadeEstoque < 0) {
            throw new IllegalArgumentException("A quantidade em estoque não pode ser negativa");
        }
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public void aumentarEstoque(int quantidade) {
        validarQuantidadePositiva(quantidade);
        quantidadeEstoque += quantidade;
    }

    public void diminuirEstoque(int quantidade) {
        validarQuantidadePositiva(quantidade);
        if (quantidade > quantidadeEstoque) {
            throw new IllegalArgumentException("Estoque insuficiente para o item id " + id);
        }
        quantidadeEstoque -= quantidade;
    }

    protected String detalhesBase() {
        return String.format("id=%d, nome=%s, preco=%.2f, estoque=%d", id, nome, preco, quantidadeEstoque);
    }

    private void validarQuantidadePositiva(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero");
        }
    }

    public abstract void exibirDetalhes();
}
