package games.tabuleiro.damas.modelo;

import java.util.Objects;

/**
 * Opções escolhidas no menu principal para iniciar uma partida.
 *
 * @param modo  modo de jogo
 * @param nivel dificuldade do BOT (ignorada no modo Jogador vs Jogador)
 */
public record ConfiguracaoPartida(ModoJogo modo, NivelDificuldade nivel) {

    public ConfiguracaoPartida {
        Objects.requireNonNull(modo, "modo");
        Objects.requireNonNull(nivel, "nivel");
    }

    public boolean isContraBot() {
        return modo == ModoJogo.JOGADOR_VS_BOT;
    }

    public boolean isControladoPeloBot(Jogador jogador) {
        return isContraBot() && jogador == Jogador.JOGADOR_2;
    }

    public String nomeExibicao(Jogador jogador) {
        return isControladoPeloBot(jogador) ? jogador.getNome() + " (BOT)" : jogador.getNome();
    }

    public String descricao() {
        return isContraBot() ? modo.getDescricao() + " • " + nivel.getDescricao() : modo.getDescricao();
    }
}
