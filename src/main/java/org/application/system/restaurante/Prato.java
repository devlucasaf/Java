package org.application.system.restaurante;

public class Prato extends ItemCardapio {
    private String          ingredientePrincipal;
    private boolean         vegetariano;
    private boolean         semGluten;
    private TamanhoPorcao   tamanho;

    public Prato(int id, String nome, String descricao, double preco, int tempoPreparo,
                 String ingredientePrincipal, boolean vegetariano, boolean semGluten, TamanhoPorcao tamanho) {
        super(id, nome, descricao, preco, tempoPreparo, CategoriaItem.PRATO_PRINCIPAL);
        setIngredientePrincipal(ingredientePrincipal);
        this.vegetariano = vegetariano;
        this.semGluten = semGluten;
        setTamanho(tamanho);
    }

    public String getIngredientePrincipal() {
        return ingredientePrincipal;
    }

    public void setIngredientePrincipal(String ingredientePrincipal) {
        if (ingredientePrincipal == null || ingredientePrincipal.isBlank()) {
            throw new IllegalArgumentException("O ingrediente principal não pode ser vazio");
        }
        this.ingredientePrincipal = ingredientePrincipal;
    }

    public boolean isVegetariano() {
        return vegetariano;
    }

    public void setVegetariano(boolean vegetariano) {
        this.vegetariano = vegetariano;
    }

    public boolean isSemGluten() {
        return semGluten;
    }

    public void setSemGluten(boolean semGluten) {
        this.semGluten = semGluten;
    }

    public TamanhoPorcao getTamanho() {
        return tamanho;
    }

    public void setTamanho(TamanhoPorcao tamanho) {
        if (tamanho == null) {
            throw new IllegalArgumentException("O tamanho não pode ser nulo");
        }
        this.tamanho = tamanho;
    }

    @Override
    public double calcularPrecoFinal() {
        return getPreco() * tamanho.getMultiplicador();
    }

    @Override
    public void exibirDetalhes() {
        System.out.printf("[PRATO] %s | Ingrediente: %s | %s | %s | Tamanho: %s | Preço final: R$%.2f%n",
                detalhesBase(),
                ingredientePrincipal,
                vegetariano ? "Vegetariano" : "Não-Vegetariano",
                semGluten ? "Sem Glúten" : "Com Glúten",
                tamanho.getDescricao(),
                calcularPrecoFinal()
        );
    }
}

