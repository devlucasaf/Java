package cursos.udemy.aulas.unidade9.aula83.entities;

public class Produto {
    public String   nome;
    public double   preco;
    public int      quantidade;

    public double totalValueInStock() {
        return preco * quantidade;
    }

    public void adicionarProdutos(int quantity) {
        this.quantidade += quantity;
    }

    public void removerProdutos(int quantity) {
        this.quantidade -= quantity;
    }

    public String toString() {
        return nome
                + ", $ "
                + String.format("%.2f", preco)
                + ", "
                + quantidade
                + " units, Total: $ "
                + String.format("%.2f", totalValueInStock());
    }
}
