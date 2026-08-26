package application.system.marketplace.model;

public class Produto {
    private final String    id;
    private final String    nome;
    private final String    categoria;
    private double          preco;
    private int             estoque;
    private final Vendedor  vendedor;
    private double          pesoKg;

    public Produto(String id, String nome, String categoria, double preco, int estoque, Vendedor vendedor, double pesoKg) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.estoque = estoque;
        this.vendedor = vendedor;
        this.pesoKg = pesoKg;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getPreco() {
        return preco;
    }

    public int getEstoque() {
        return estoque;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public double getPesoKg() {
        return pesoKg;
    }

    public boolean temEstoque(int quantidade) {
        return estoque >= quantidade;
    }

    public void reduzirEstoque(int quantidade) {
        if (quantidade > estoque) {
            throw new IllegalStateException("Estoque insuficiente para o produto " + nome);
        }
        estoque -= quantidade;
    }

    @Override
    public String toString() {
        return String.format("%s | %s | R$ %.2f | estoque: %d | vendedor: %s",
                nome, categoria, preco, estoque, vendedor.getNome());
    }
}
