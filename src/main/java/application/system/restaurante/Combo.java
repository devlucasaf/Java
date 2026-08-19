package application.system.restaurante;

import application.system.restaurante.model.Promocional;

public class Combo extends ItemCardapio implements Promocional {
    private final ItemCardapio[]    itens;
    private double                  percentualDesconto;

    public Combo(int id, String nome, String descricao, int tempoPreparo, double percentualDesconto, ItemCardapio... itens) {
        super(id, nome, descricao, 0, tempoPreparo, CategoriaItem.PRATO_PRINCIPAL);
        if (itens == null || itens.length < 2) {
            throw new IllegalArgumentException("Um combo deve ter pelo menos 2 itens");
        }

        this.itens = itens;
        setPercentualDesconto(percentualDesconto);

        double precoTotal = 0;
        for (ItemCardapio item : itens) {
            precoTotal += item.calcularPrecoFinal();
        }
        setPreco(precoTotal);
    }

    public void setPercentualDesconto(double percentualDesconto) {
        if (percentualDesconto < 0 || percentualDesconto > 1) {
            throw new IllegalArgumentException("Percentual de desconto deve estar entre 0 e 1");
        }
        this.percentualDesconto = percentualDesconto;
    }

    public ItemCardapio[] getItensDoCombo() {
        return itens;
    }

    @Override
    public double calcularPrecoFinal() {
        return getPreco() * (1 - percentualDesconto);
    }

    @Override
    public double calcularDesconto() {
        return getPreco() * percentualDesconto;
    }

    @Override
    public String getDescricaoPromocao() {
        return String.format("Combo %s - %.0f%% de desconto!", getNome(), percentualDesconto * 100);
    }

    @Override
    public boolean isEmPromocao() {
        return percentualDesconto > 0;
    }

    @Override
    public void exibirDetalhes() {
        System.out.printf("[COMBO] %s | Itens: %d | Desconto: %.0f%% | De R$%.2f por R$%.2f%n",
                detalhesBase(), itens.length, percentualDesconto * 100, getPreco(), calcularPrecoFinal());
        for (ItemCardapio item : itens) {
            System.out.printf("    → %s (R$%.2f)%n", item.getNome(), item.calcularPrecoFinal());
        }
    }
}

