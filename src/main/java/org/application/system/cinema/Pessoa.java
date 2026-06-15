package org.application.system.cinema;

public abstract class Pessoa {
    protected String nome;
    protected String cpf;
    protected String telefone;
    protected String email;

    public Pessoa(String nome, String cpf, String telefone, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }

    public abstract void exibirInformacoes();

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }
}
