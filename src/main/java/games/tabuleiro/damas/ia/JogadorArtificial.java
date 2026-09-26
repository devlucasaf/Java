package games.tabuleiro.damas.ia;

import games.tabuleiro.damas.modelo.Jogador;
import games.tabuleiro.damas.modelo.Movimento;
import games.tabuleiro.damas.modelo.Tabuleiro;

/**
 * Contrato de qualquer estratégia automática capaz de jogar damas.
 * Permite trocar o algoritmo do BOT sem alterar o controlador da partida.
 */
public interface JogadorArtificial {

    /**
     * Escolhe uma jogada legal para o jogador informado.
     * O método pode ser demorado e deve ser executado fora da thread da interface.
     *
     * @return o movimento escolhido, ou {@code null} se não houver jogadas possíveis
     */
    Movimento escolherMovimento(Tabuleiro tabuleiro, Jogador jogador);
}
