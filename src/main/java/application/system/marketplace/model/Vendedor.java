package application.system.marketplace.model;

public class Vendedor {

    private final String id;
    private final String nome;
    private double      avaliacaoMedia;
    private double      taxaComissaoPercentual;

    public Vendedor(String id, String nome, double taxaComissaoPercentual) {
        this.id = id;
        this.nome = nome;
        this.taxaComissaoPercentual = taxaComissaoPercentual;
        this.avaliacaoMedia = 5.0;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getTaxaComissaoPercentual() {
        return taxaComissaoPercentual;
    }

    public double getAvaliacaoMedia() {
        return avaliacaoMedia;
    }

    public void receberAvaliacao(double nota) {
        // Média simples ponderada só para efeito de demonstração
        this.avaliacaoMedia = (this.avaliacaoMedia + nota) / 2;
    }
}
