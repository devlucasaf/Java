package org.application.biblioteca;

import java.time.LocalDate;

public class Revista extends Publicacao {
    private int     numeroEdicao;
    private String  periodicidade;

    public Revista(String titulo, String editora, LocalDate anoPublicacao, int numeroExemplares,
                   int numeroEdicao, String periodicidade) {
        super(titulo, editora, anoPublicacao, numeroExemplares);
        this.numeroEdicao = numeroEdicao;
        this.periodicidade = periodicidade;
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("--- REVISTA ---");
        System.out.println("Título: " + titulo);
        System.out.println("Edição: " + numeroEdicao);
        System.out.println("Periodicidade: " + periodicidade);
        System.out.println("Editora: " + editora);
        System.out.println("Ano: " + anoPublicacao);
        System.out.println("Exemplares: " + exemplaresDisponiveis + "/" + numeroExemplares);
    }
}