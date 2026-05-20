package org.application.eventos;

import java.time.LocalDate;

public class Baixista extends Instrumentista {
    private boolean usaPalheta;
    private int     numeroTrastes;

    public Baixista(String nome, String nomeArtistico, LocalDate dataNascimento, String nacionalidade,
                    double cacheBase, String instrumentoPrincipal, boolean usaPalheta, int numeroTrastes) {
        super(nome, nomeArtistico, dataNascimento, nacionalidade, cacheBase, instrumentoPrincipal);
        this.usaPalheta = usaPalheta;
        this.numeroTrastes = numeroTrastes;
    }

    @Override
    public void apresentar() {
        System.out.println(nomeArtistico + " mantém a levada no baixo, " + (usaPalheta ? "com palheta" : "com dedos"));
    }

    public void criarGroove() {
        System.out.println(nomeArtistico + " cria um groove marcante!");
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.println("Baixo com " + numeroTrastes + " trastes");
        System.out.println("Uso de palheta: " + (usaPalheta ? "Sim" : "Não"));
    }
}
