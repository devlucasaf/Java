package org.games.tabuleiro.batalhanaval;

import java.util.ArrayList;
import java.util.List;

public abstract class Jogador {
    protected String        nome;
    protected Tabuleiro     tabuleiro;
    protected List<Navio>   frota;

    public Jogador(String nome) {
        this.nome = nome;
        this.tabuleiro = new Tabuleiro();
        this.frota = new ArrayList<>();
        inicializarFrota();
    }

    private void inicializarFrota() {
        frota.add(new Navio("Porta-Aviões", 5));
        frota.add(new Navio("Encouraçado", 4));
        frota.add(new Navio("Cruzador", 3));
        frota.add(new Navio("Submarino", 3));
        frota.add(new Navio("Destroyer", 2));
    }

    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public String getNome() {
        return nome;
    }

    public boolean perdeu() {
        for (Navio navio : frota) {
            if (!navio.estaDestruido()) {
                return false;
            }
        }
        return true;
    }

    public abstract void posicionarNavios();
    public abstract Coordenada escolherAtaque();
    public abstract void notificarResultadoAtaque(Coordenada alvo, String resultado);
}
