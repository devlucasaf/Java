package org.application.system.esporte.cbf;

import java.util.ArrayList;
import java.util.List;

public class Federacao {
    private String              nome;
    private String              sigla;
    private String              pais;
    private String              presidente;
    private List<Campeonato>    campeonatosOrganizados;
    private List<Arbitro>       arbitrosFiliados;

    public Federacao(String nome, String sigla, String pais, String presidente) {
        this.nome = nome;
        this.sigla = sigla;
        this.pais = pais;
        this.presidente = presidente;
        this.campeonatosOrganizados = new ArrayList<>();
        this.arbitrosFiliados = new ArrayList<>();
    }

    public void organizarCampeonato(Campeonato campeonato) {
        campeonatosOrganizados.add(campeonato);
        System.out.println("Federação " + nome + " organiza o campeonato " + campeonato.getNome());
    }

    public void filiarArbitro(Arbitro arbitro) {
        arbitrosFiliados.add(arbitro);
        System.out.println("Árbitro " + arbitro.getNome() + " filiado à " + nome);
    }

    public void exibirInformacoes() {
        System.out.println("--- FEDERAÇÃO ---");
        System.out.println("Nome: " + nome + " (" + sigla + ")");
        System.out.println("País: " + pais);
        System.out.println("Presidente: " + presidente);
        System.out.println("Campeonatos organizados: " + campeonatosOrganizados.size());
        System.out.println("Árbitros filiados: " + arbitrosFiliados.size());
    }
}
