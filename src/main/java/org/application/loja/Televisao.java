package org.application.loja;

public class Televisao extends Item {
    private int     tamanhoTelaPolegadas;
    private String  resolucao;

    public Televisao(int id, String nome, double preco, int quantidadeEstoque, int tamanhoTelaPolegadas, String resolucao) {
        super(id, nome, preco, quantidadeEstoque);
        setTamanhoTelaPolegadas(tamanhoTelaPolegadas);
        setResolucao(resolucao);
    }

    public int getTamanhoTelaPolegadas() {
        return tamanhoTelaPolegadas;
    }

    public void setTamanhoTelaPolegadas(int tamanhoTelaPolegadas) {
        if (tamanhoTelaPolegadas <= 0) {
            throw new IllegalArgumentException("O tamanho da tela deve ser maior que zero");
        }
        this.tamanhoTelaPolegadas = tamanhoTelaPolegadas;
    }

    public String getResolucao() {
        return resolucao;
    }

    public void setResolucao(String resolucao) {
        if (resolucao == null || resolucao.isBlank()) {
            throw new IllegalArgumentException("A resolução não pode ser vazia");
        }
        this.resolucao = resolucao;
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("Televisão -> " + detalhesBase()
                + String.format(", tela=%d\", resolução=%s", tamanhoTelaPolegadas, resolucao));
    }
}
