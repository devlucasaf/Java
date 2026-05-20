package org.application.federacaofutebol;

import java.time.LocalDate;
import java.time.Period;

public abstract class Pessoa {
    protected String    nome;
    protected String    cpf;
    protected LocalDate dataNascimento;
    protected String    nacionalidade;

    public Pessoa(String nome, String cpf, LocalDate dataNascimento, String nacionalidade) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.nacionalidade = nacionalidade;
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

    public String getNacionalidade() {
        return nacionalidade;
    }
}
