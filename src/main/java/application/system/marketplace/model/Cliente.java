package application.system.marketplace.model;

public class Cliente {

    private final String id;
    private final String nome;
    private final String cep;

    public Cliente(String id, String nome, String cep) {
        this.id = id;
        this.nome = nome;
        this.cep = cep;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCep() {
        return cep;
    }
}
