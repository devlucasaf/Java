package org.application.loja;

public class Acessorio extends Item {
    private String  categoria;
    private boolean semFio;

    public Acessorio(int id, String nome, double preco, int quantidadeEstoque, String categoria, boolean semFio) {
        super(id, nome, preco, quantidadeEstoque);
        setCategoria(categoria);
        this.semFio = semFio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        if (categoria == null || categoria.isBlank()) {
            throw new IllegalArgumentException("A categoria não pode ser vazia");
        }
        this.categoria = categoria;
    }

    public boolean isSemFio() {
        return semFio;
    }

    public void setSemFio(boolean semFio) {
        this.semFio = semFio;
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("Acessório -> " + detalhesBase()
                + String.format(", categoria=%s, semFio=%s", categoria, semFio));
    }
}
