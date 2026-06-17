package application.system.eventos;

import java.time.LocalDate;

public class Guitarrista extends Instrumentista {
    private int     numeroCordas;
    private String  modeloGuitarra;

    public Guitarrista(String nome, String nomeArtistico, LocalDate dataNascimento, String nacionalidade,
                       double cacheBase, String instrumentoPrincipal, int numeroCordas, String modeloGuitarra) {
        super(nome, nomeArtistico, dataNascimento, nacionalidade, cacheBase, instrumentoPrincipal);
        this.numeroCordas = numeroCordas;
        this.modeloGuitarra = modeloGuitarra;
    }

    @Override
    public void apresentar() {
        System.out.println(nomeArtistico + " está tocando riffs incríveis na guitarra " + modeloGuitarra);
    }

    public void fazerSolo() {
        System.out.println(nomeArtistico + " faz um solo de guitarra eletrizante!");
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.println("Guitarra: " + modeloGuitarra + " (" + numeroCordas + " cordas)");
    }
}