package org.application.system.eventos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Banda {
    private String          nome;
    private LocalDate       dataFormacao;
    private EstiloMusical   estilo;
    private List<Artista>   membros;
    private double          cacheBanda;

    public Banda(String nome, LocalDate dataFormacao, EstiloMusical estilo, double cacheBanda) {
        this.nome = nome;
        this.dataFormacao = dataFormacao;
        this.estilo = estilo;
        this.cacheBanda = cacheBanda;
        this.membros = new ArrayList<>();
    }

    public void adicionarMembro(Artista artista) {
        if (artista != null && !membros.contains(artista)) {
            membros.add(artista);
            System.out.println(artista.getNomeArtistico() + " entrou para a banda " + nome);
        }
    }

    public void removerMembro(Artista artista) {
        if (membros.remove(artista)) {
            System.out.println(artista.getNomeArtistico() + " saiu da banda " + nome);
        }
    }

    public void tocarMusica(String musica) {
        System.out.println("A banda " + nome + " está executando '" + musica + "'");
        for (Artista a : membros) {
            a.apresentar();
        }
    }

    public void exibirInformacoes() {
        System.out.println("===== BANDA =====");
        System.out.println("Nome: " + nome);
        System.out.println("Formação: " + dataFormacao);
        System.out.println("Estilo: " + estilo);
        System.out.println("Cache da banda: R$" + cacheBanda);
        System.out.println("Membros (" + membros.size() + "):");

        for (Artista a : membros) {
            System.out.println("- " + a.getNomeArtistico() + " (" + a.getClass().getSimpleName() + ")");
        }
    }

    public String getNome() {
        return nome;
    }

    public double getCacheBanda() {
        return cacheBanda;
    }

    public List<Artista> getMembros() {
        return membros;
    }

    public void setCacheBanda(double cacheBanda) {
        this.cacheBanda = cacheBanda;
    }
}
