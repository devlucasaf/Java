package application.system.restaurante;

public class Sobremesa extends ItemCardapio {
    private int     calorias;
    private boolean contemLactose;
    private boolean contemNozes;
    private String  sabor;

    public Sobremesa(int id, String nome, String descricao, double preco, int tempoPreparo,
                    int calorias, boolean contemLactose, boolean contemNozes, String sabor) {
        super(id, nome, descricao, preco, tempoPreparo, CategoriaItem.SOBREMESA);
        setCalorias(calorias);
        this.contemLactose = contemLactose;
        this.contemNozes = contemNozes;
        setSabor(sabor);
    }

    public int getCalorias() {
        return calorias;
    }

    public void setCalorias(int calorias) {
        if (calorias < 0) {
            throw new IllegalArgumentException("Calorias não pode ser negativo");
        }
        this.calorias = calorias;
    }

    public boolean isContemLactose() {
        return contemLactose;
    }

    public void setContemLactose(boolean contemLactose) {
        this.contemLactose = contemLactose;
    }

    public boolean isContemNozes() {
        return contemNozes;
    }

    public void setContemNozes(boolean contemNozes) {
        this.contemNozes = contemNozes;
    }

    public String getSabor() {
        return sabor;
    }

    public void setSabor(String sabor) {
        if (sabor == null || sabor.isBlank()) {
            throw new IllegalArgumentException("O sabor não pode ser vazio");
        }
        this.sabor = sabor;
    }

    @Override
    public double calcularPrecoFinal() {
        return getPreco();
    }

    @Override
    public void exibirDetalhes() {
        System.out.printf("[SOBREMESA] %s | Sabor: %s | %d kcal | Lactose: %s | Nozes: %s%n",
                detalhesBase(),
                sabor,
                calorias,
                contemLactose ? "Sim" : "Não",
                contemNozes ? "Sim" : "Não");
    }
}

