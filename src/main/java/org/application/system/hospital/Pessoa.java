package org.application.system.hospital;

import java.time.LocalDate;

public abstract class Pessoa {
    protected String    nome;
    protected String    cpf;
    protected LocalDate dataNascimento;
    protected String    telefone;
    protected String    endereco;

    public Pessoa(String nome, String cpf, LocalDate dataNascimento, String telefone, String endereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public abstract void exibirInformacoes();

    public int calcularIdade() {
        return LocalDate.now().getYear() - dataNascimento.getYear();
    }
}
