package org.application.cinema;

import java.time.LocalDate;

public class Filme {
    private static int contadorId = 1;
    private int                     id;
    private String                  titulo;
    private String                  diretor;
    private GeneroFilme             genero;
    private int                     duracaoMinutos;
    private ClassificacaoIndicativa classificacao;
    private String                  sinopse;
    private LocalDate               dataLancamento;
    private boolean                 ativo;

    public Filme(String titulo, String diretor, GeneroFilme genero, int duracaoMinutos,
                 ClassificacaoIndicativa classificacao, String sinopse, LocalDate dataLancamento) {
        this.id = contadorId++;
        this.titulo = titulo;
        this.diretor = diretor;
        this.genero = genero;
        this.duracaoMinutos = duracaoMinutos;
        this.classificacao = classificacao;
        this.sinopse = sinopse;
        this.dataLancamento = dataLancamento;
        this.ativo = true;
    }

    public void exibirInformacoes() {
        System.out.println("--- FILME ---");
        System.out.println("ID: " + id);
        System.out.println("Título: " + titulo);
        System.out.println("Diretor: " + diretor);
        System.out.println("Gênero: " + genero);
        System.out.println("Duração: " + duracaoMinutos + " min");
        System.out.println("Classificação: " + classificacao);
        System.out.println("Lançamento: " + dataLancamento);
        System.out.println("Sinopse: " + sinopse);
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public ClassificacaoIndicativa getClassificacao() {
        return classificacao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
