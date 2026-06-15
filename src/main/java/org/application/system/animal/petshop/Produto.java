package org.application.system.animal.petshop;

public class Produto {
    private static int contadorId = 1;
    private int             id;
    private int             estoque;
    private String          nome;
    private String          marca;
    private TipoProduto     tipo;
    private double          preco;

    public Produto(String nome, TipoProduto tipo, double preco, int estoque, String marca) {
        this.id = contadorId++;
        this.nome = nome;
        this.tipo = tipo;
        this.preco = preco;
        this.estoque = estoque;
        this.marca = marca;
    }

    public boolean reduzirEstoque(int quantidade) {
        if (quantidade <= estoque) {
            estoque -= quantidade;
            return true;
        }
        return false;
    }

    public void reporEstoque(int quantidade) {
        estoque += quantidade;
        System.out.println("Estoque de " + nome + " reposto. Agora: " + estoque);
    }

    public void exibirInformacoes() {
        System.out.println("Produto: " + nome + " (" + tipo + ") | Marca: " + marca + " | Preço: R$" + preco + " | Estoque: " + estoque);
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getEstoque() {
        return estoque;
    }
}