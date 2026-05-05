package org.application.loja;

public class Vendedor {
    private String nome;
    private String idFuncionario;
    private double taxaComissao;
    private double totalVendas;

    public Vendedor(String nome, String idFuncionario, double taxaComissao) {
        setNome(nome);
        setIdFuncionario(idFuncionario);
        setTaxaComissao(taxaComissao);
        this.totalVendas = 0;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do vendedor não pode ser vazio");
        }
        this.nome = nome;
    }

    public String getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(String idFuncionario) {
        if (idFuncionario == null || idFuncionario.isBlank()) {
            throw new IllegalArgumentException("O id do funcionário não pode ser vazio");
        }
        this.idFuncionario = idFuncionario;
    }

    public double getTaxaComissao() {
        return taxaComissao;
    }

    public void setTaxaComissao(double taxaComissao) {
        if (taxaComissao < 0 || taxaComissao > 1) {
            throw new IllegalArgumentException("A taxa de comissão deve estar entre 0 e 1");
        }
        this.taxaComissao = taxaComissao;
    }

    public double getTotalVendas() {
        return totalVendas;
    }

    public void registrarVenda(double valorVenda) {
        if (valorVenda < 0) {
            throw new IllegalArgumentException("O valor da venda não pode ser negativo");
        }
        totalVendas += valorVenda;
    }

    public double calcularComissao() {
        return totalVendas * taxaComissao;
    }
}
