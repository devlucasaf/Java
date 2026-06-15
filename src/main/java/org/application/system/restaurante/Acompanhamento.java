package org.application.system.restaurante;

public class Acompanhamento extends ItemCardapio {
    private String  tipo;
    private boolean adicionalGratuito;

    public Acompanhamento(int id, String nome, String descricao, double preco, int tempoPreparo,
                          String tipo, boolean adicionalGratuito) {
        super(id, nome, descricao, preco, tempoPreparo, CategoriaItem.ACOMPANHAMENTO);
        setTipo(tipo);
        this.adicionalGratuito = adicionalGratuito;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("O tipo não pode ser vazio");
        }
        this.tipo = tipo;
    }

    public boolean isAdicionalGratuito() {
        return adicionalGratuito;
    }

    public void setAdicionalGratuito(boolean adicionalGratuito) {
        this.adicionalGratuito = adicionalGratuito;
    }

    @Override
    public double calcularPrecoFinal() {
        return adicionalGratuito ? 0 : getPreco();
    }

    @Override
    public void exibirDetalhes() {
        System.out.printf("[ACOMPANHAMENTO] %s | Tipo: %s | %s%n",
                detalhesBase(), tipo,
                adicionalGratuito ? "Gratuito como adicional" : "Cobrado separadamente");
    }
}

