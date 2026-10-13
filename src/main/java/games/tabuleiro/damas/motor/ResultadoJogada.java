package games.tabuleiro.damas.motor;

import games.tabuleiro.damas.model.EstadoPartida;
import games.tabuleiro.damas.model.Jogador;
import games.tabuleiro.damas.model.Movimento;

public record ResultadoJogada(Jogador autor, Movimento movimento, boolean promoveu, EstadoPartida estado) {
}
