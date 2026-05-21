package org.application.biblioteca;

import java.time.LocalDate;

public class DVD extends Publicacao {
    private String  diretor;
    private String  genero;
    private int     duracaoMinutos;

    public DVD(String titulo, String editora, LocalDate anoPublicacao, int numeroExemplares,
               String diretor, int duracaoMinutos, String genero) {
        super(titulo, editora, anoPublicacao, numeroExemplares);
        this.diretor = diretor;
        this.duracaoMinutos = duracaoMinutos;
        this.genero = genero;
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("--- DVD ---");
        System.out.println("Título: " + titulo);
        System.out.println("Diretor: " + diretor);
        System.out.println("Gênero: " + genero);
        System.out.println("Duração: " + duracaoMinutos + " min");
        System.out.println("Editora: " + editora);
        System.out.println("Ano: " + anoPublicacao);
        System.out.println("Exemplares: " + exemplaresDisponiveis + "/" + numeroExemplares);
    }
}