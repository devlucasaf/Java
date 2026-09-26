package games.tabuleiro.damas.modelo;

public enum ModoJogo {

    JOGADOR_VS_JOGADOR("Jogador vs Jogador"),
    JOGADOR_VS_BOT("Jogador vs BOT");

    private final String descricao;

    ModoJogo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
