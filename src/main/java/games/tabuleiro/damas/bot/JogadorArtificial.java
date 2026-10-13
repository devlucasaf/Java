package games.tabuleiro.damas.bot;

import games.tabuleiro.damas.model.Jogador;
import games.tabuleiro.damas.model.Movimento;
import games.tabuleiro.damas.model.Tabuleiro;

public interface JogadorArtificial {
    Movimento escolherMovimento(Tabuleiro tabuleiro, Jogador jogador);
}
