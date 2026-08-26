package application.system.streaming.model;

import application.system.streaming.enums.Tipo;

import java.util.*;

public class Titulo {
    private final String        id;
    private final String        nome;
    private final Tipo          tipo;
    private final Set<String>   generos;
    private final int           classificacaoIndicativa;
    private double              notaMedia;
    private int                 totalAvaliacoes;

    public Titulo(String id, String nome, Tipo tipo, Set<String> generos, int classificacaoIndicativa) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.generos = generos;
        this.classificacaoIndicativa = classificacaoIndicativa;
        this.notaMedia = 0;
        this.totalAvaliacoes = 0;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Set<String> getGeneros() {
        return generos;
    }

    public int getClassificacaoIndicativa() {
        return classificacaoIndicativa;
    }

    public void avaliar(double nota) {
        notaMedia = (notaMedia * totalAvaliacoes + nota) / (totalAvaliacoes + 1);
        totalAvaliacoes++;
    }

    public double getNotaMedia() {
        return notaMedia;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) | generos: %s | classificacao: %d anos | nota: %.1f",
                nome, tipo, generos, classificacaoIndicativa, notaMedia);
    }
}
