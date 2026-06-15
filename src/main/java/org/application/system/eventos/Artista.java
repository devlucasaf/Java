package org.application.system.eventos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Artista {
    protected String        nome;
    protected String        nomeArtistico;
    protected LocalDate     dataNascimento;
    protected String        nacionalidade;
    protected double        cacheBase;
    protected List<String>  instrumentos;

    public Artista(String nome, String nomeArtistico, LocalDate dataNascimento, String nacionalidade, double cacheBase) {
        this.nome = nome;
        this.nomeArtistico = nomeArtistico;
        this.dataNascimento = dataNascimento;
        this.nacionalidade = nacionalidade;
        this.cacheBase = cacheBase;
        this.instrumentos = new ArrayList<>();
    }

    public void adicionarInstrumento(String instrumento) {
        instrumentos.add(instrumento);
        System.out.println(nomeArtistico + " agora toca " + instrumento);
    }

    public abstract double calcularCacheEvento();

    public abstract void apresentar();

    public void exibirInformacoes() {
        System.out.println("--- Artista ---");
        System.out.println("Nome: " + nome);
        System.out.println("Nome Artístico: " + nomeArtistico);
        System.out.println("Nacionalidade: " + nacionalidade);
        System.out.println("Idade: " + calcularIdade() + " anos");
        System.out.println("Cache Base: R$" + cacheBase);
        System.out.println("Instrumentos: " + (instrumentos.isEmpty() ? "Nenhum" : String.join(", ", instrumentos)));
    }

    public int calcularIdade() {
        return LocalDate.now().getYear() - dataNascimento.getYear();
    }

    public String getNome() {
        return nome;
    }

    public String getNomeArtistico() {
        return nomeArtistico;
    }

    public double getCacheBase() {
        return cacheBase;
    }

    public void setCacheBase(double cacheBase) {
        this.cacheBase = cacheBase;
    }

    public List<String> getInstrumentos() {
        return instrumentos;
    }
}
