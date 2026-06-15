package org.games.tabuleiro.grid.model;

import java.util.List;

public class Jogador {

    private String        nome;
    private String        pais;
    private List<String>  clubes;

    public Jogador(String nome, String pais, List<String> clubes) {
        this.nome = nome;
        this.pais = pais;
        this.clubes = clubes;
    }

    public String getNome() {
        return nome;
    }

    public String getPais() {
        return pais;
    }

    public List<String> getClubes() {
        return clubes;
    }
}
