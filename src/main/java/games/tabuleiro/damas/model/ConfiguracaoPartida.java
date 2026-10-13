package games.tabuleiro.damas.model;

import java.util.Objects;

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
