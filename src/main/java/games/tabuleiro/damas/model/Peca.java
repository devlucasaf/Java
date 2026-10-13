package games.tabuleiro.damas.model;

import java.util.Objects;

public final class Peca {

    private final Jogador   jogador;
    private final TipoPeca  tipo;

    public Peca(Jogador jogador, TipoPeca tipo) {
        this.jogador = Objects.requireNonNull(jogador, "jogador");
        this.tipo = Objects.requireNonNull(tipo, "tipo");
    }

    public Jogador getJogador() {
        return jogador;
    }

    public TipoPeca getTipo() {
        return tipo;
    }

    public boolean isDama() {
        return tipo == TipoPeca.DAMA;
    }

    public Peca promover() {
        return isDama() ? this : new Peca(jogador, TipoPeca.DAMA);
    }

    @Override
    public boolean equals(Object outro) {
        if (this == outro) {
            return true;
        }

        if (!(outro instanceof Peca peca)) {
            return false;
        }
        return jogador == peca.jogador && tipo == peca.tipo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(jogador, tipo);
    }

    @Override
    public String toString() {
        return (isDama() ? "Dama" : "Peça") + " do " + jogador.getNome();
    }
}
