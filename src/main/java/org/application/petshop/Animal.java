package org.application.petshop;

import java.time.LocalDate;
import java.time.Period;

public abstract class Animal {
    protected String        nome;
    protected String        raca;
    protected LocalDate     dataNascimento;
    protected TipoAnimal    tipo;
    protected PorteAnimal   porte;
    protected double        peso;
    protected String        cor;
    protected boolean       ativo;

    public Animal(String nome, String raca, LocalDate dataNascimento, TipoAnimal tipo,
                  PorteAnimal porte, double peso, String cor) {
        this.nome = nome;
        this.raca = raca;
        this.dataNascimento = dataNascimento;
        this.tipo = tipo;
        this.porte = porte;
        this.peso = peso;
        this.cor = cor;
        this.ativo = true;
    }

    public int calcularIdade() {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    public abstract void emitirSom();

    public void exibirInformacoes() {
        System.out.println("--- ANIMAL ---");
        System.out.println("Nome: " + nome);
        System.out.println("Raça: " + raca);
        System.out.println("Tipo: " + tipo);
        System.out.println("Porte: " + porte);
        System.out.println("Peso: " + peso + " kg");
        System.out.println("Cor: " + cor);
        System.out.println("Idade: " + calcularIdade() + " anos");
        System.out.println("Ativo: " + (ativo ? "Sim" : "Não"));
    }

    public String getNome() {
        return nome;
    }

    public String getRaca() {
        return raca;
    }

    public TipoAnimal getTipo() {
        return tipo;
    }

    public PorteAnimal getPorte() {
        return porte;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}