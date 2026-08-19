package application.system.eventos.model.artista;

import java.time.LocalDate;

public class Baterista extends Instrumentista {
    private int     numeroTambores;
    private boolean temPratoEspecial;

    public Baterista(String nome, String nomeArtistico, LocalDate dataNascimento, String nacionalidade,
                     double cacheBase, String instrumentoPrincipal, int numeroTambores, boolean temPratoEspecial) {
        super(nome, nomeArtistico, dataNascimento, nacionalidade, cacheBase, instrumentoPrincipal);
        this.numeroTambores = numeroTambores;
        this.temPratoEspecial = temPratoEspecial;
    }

    @Override
    public void apresentar() {
        System.out.println(nomeArtistico + " comanda a bateria com " + numeroTambores + " tambores!");
    }

    public void fazerRufo() {
        System.out.println(nomeArtistico + " executa um rufo impressionante.");
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.println("Bateria: " + numeroTambores + " peças");
        System.out.println("Prato especial: " + (temPratoEspecial ? "Sim" : "Não"));
    }
}
