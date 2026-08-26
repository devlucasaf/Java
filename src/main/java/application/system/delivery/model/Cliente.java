package application.system.delivery.model;

public class Cliente {

    private final String id;
    private final String nome;
    private final String bairro;

    public Cliente(String id, String nome, String bairro) {
        this.id = id;
        this.nome = nome;
        this.bairro = bairro;
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
}
