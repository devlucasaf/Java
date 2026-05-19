package org.application.restaurante;

public abstract class ItemCardapio {
    private int             id;
    private int             tempoPreparo;
    private String          nome;
    private String          descricao;
    private double          preco;
    private boolean         disponivel;
    private CategoriaItem   categoria;

    public ItemCardapio(int id, String nome, String descricao, double preco, int tempoPreparo, CategoriaItem categoria) {
        setId(id);
        setNome(nome);
        setDescricao(descricao);
        setPreco(preco);
        setTempoPreparo(tempoPreparo);
        setCategoria(categoria);
        this.disponivel = true;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        if (descricao == null) {
            throw new IllegalArgumentException("A descrição não pode ser nula");
        }
        this.descricao = descricao;
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

    public int getTempoPreparo() {
        return tempoPreparo;
    }

    public void setTempoPreparo(int tempoPreparo) {
        if (tempoPreparo < 0) {
            throw new IllegalArgumentException("O tempo de preparo não pode ser negativo");
        }
        this.tempoPreparo = tempoPreparo;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public CategoriaItem getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaItem categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("A categoria não pode ser nula");
        }
        this.categoria = categoria;
    }

    protected String detalhesBase() {
        return String.format("id=%d, nome=%s, preço=R$%.2f, tempo=%dmin, disponível=%s",
                id,
                nome,
                preco,
                tempoPreparo,
                disponivel ? "Sim" : "Não"
        );
    }

    public abstract void exibirDetalhes();

    public abstract double calcularPrecoFinal();
}

