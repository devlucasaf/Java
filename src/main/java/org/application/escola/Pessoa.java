package org.application.escola;

import java.time.LocalDate;
import java.time.Period;

public abstract class Pessoa {
    protected String    nome;
    protected String    cpf;
    protected LocalDate dataNascimento;
    protected String    telefone;
    protected String    email;

    public Pessoa(String nome, String cpf, LocalDate dataNascimento, String telefone, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.email = email;
    }

    public int calcularIdade() {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    public abstract void exibirInformacoes();

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}