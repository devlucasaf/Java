package application.system.animal.petshop.model.servico;

import application.system.animal.petshop.model.animal.Animal;
import application.system.animal.petshop.model.enums.PorteAnimal;
import application.system.animal.petshop.model.enums.TipoServico;

public class Servico {
    private static int contadorId = 1;
    private int         id;
    private TipoServico tipo;
    private String      descricao;
    private double      precoBase;
    private int         duracaoMinutos;

    public Servico(TipoServico tipo, String descricao, double precoBase, int duracaoMinutos) {
        this.id = contadorId++;
        this.tipo = tipo;
        this.descricao = descricao;
        this.precoBase = precoBase;
        this.duracaoMinutos = duracaoMinutos;
    }

    public double calcularPreco(Animal animal) {
        double preco = precoBase;
        if (animal.getPorte() == PorteAnimal.GRANDE && tipo == TipoServico.BANHO) {
            preco *= 1.5;
        } else if (animal.getPorte() == PorteAnimal.PEQUENO && tipo == TipoServico.BANHO) {
            preco *= 0.8;
        }
        return preco;
    }

    public void exibirInformacoes() {
        System.out.println("Serviço: " + tipo + " - " + descricao + " | Preço base: R$" + precoBase);
    }

    public int getId() {
        return id;
    }

    public TipoServico getTipo() {
        return tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPrecoBase() {
        return precoBase;
    }
}
