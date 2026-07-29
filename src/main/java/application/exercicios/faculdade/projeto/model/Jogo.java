package application.exercicios.faculdade.projeto.model;

public class Jogo {

    private int     id;
    private String  nome;
    private double  preco;
    private String  dataLancamento;
    private int     faixaEtaria;
    private String  desenvolvedor;
    private String  plataformas;
    private String  genero;

    public Jogo() {
    }

    public Jogo(String nome, double preco, String dataLancamento,
                int faixaEtaria, String desenvolvedor,
                String plataformas, String genero) {
        this.nome           = nome;
        this.preco          = preco;
        this.dataLancamento = dataLancamento;
        this.faixaEtaria    = faixaEtaria;
        this.desenvolvedor  = desenvolvedor;
        this.plataformas    = plataformas;
        this.genero         = genero;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(String dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public int getFaixaEtaria() {
        return faixaEtaria;
    }

    public void setFaixaEtaria(int faixaEtaria) {
        this.faixaEtaria = faixaEtaria;
    }

    public String getDesenvolvedor() {
        return desenvolvedor;
    }

    public void setDesenvolvedor(String desenvolvedor) {
        this.desenvolvedor = desenvolvedor;
    }

    public String getPlataformas() {
        return plataformas;
    }

    public void setPlataformas(String plataformas) {
        this.plataformas = plataformas;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        return String.format("Jogo[id=%d, nome=%s, preco=%.2f, genero=%s, faixa=%d]",
                id, nome, preco, genero, faixaEtaria);
    }
}

