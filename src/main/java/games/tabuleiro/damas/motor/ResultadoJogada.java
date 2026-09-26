package games.tabuleiro.damas.motor;

import games.tabuleiro.damas.modelo.EstadoPartida;
import games.tabuleiro.damas.modelo.Jogador;
import games.tabuleiro.damas.modelo.Movimento;

/**
 * Resumo do que aconteceu após uma jogada ser executada.
 *
 * @param autor     jogador que realizou a jogada
 * @param movimento movimento executado
 * @param promoveu  se a peça foi promovida a dama
 * @param estado    estado da partida após a jogada
 */
public record ResultadoJogada(Jogador autor, Movimento movimento, boolean promoveu, EstadoPartida estado) {
}
