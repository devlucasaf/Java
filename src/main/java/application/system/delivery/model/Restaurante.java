package application.system.delivery.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class Restaurante {

    private final String                id;
    private final String                nome;
    private final String                bairro;
    private final Map<String, Double>   cardapio = new LinkedHashMap<>();
    private final int                   tempoPreparoMinutos;

    public Restaurante(String id, String nome, String bairro, int tempoPreparoMinutos) {
        this.id = id;
        this.nome = nome;
        this.bairro = bairro;
        this.tempoPreparoMinutos = tempoPreparoMinutos;
    }

    public void adicionarItemCardapio(String prato, double preco) {
        cardapio.put(prato, preco);
    }

    public double getPrecoItem(String prato) {
        Double preco = cardapio.get(prato);
        if (preco == null) {
            throw new NoSuchElementException("Item nao encontrado no cardapio: " + prato);
        }
        return preco;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getBairro() {
        return bairro;
    }

    public int getTempoPreparoMinutos() {
        return tempoPreparoMinutos;
    }

    public Map<String, Double> getCardapio() {
        return cardapio;
    }
}

