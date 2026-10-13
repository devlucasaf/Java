package games.tabuleiro.damas.model;

public enum EstadoPartida {
    EM_ANDAMENTO("Em andamento"),
    VITORIA_JOGADOR_1("Vitória do Jogador 1"),
    VITORIA_JOGADOR_2("Vitória do Jogador 2"),
    EMPATE("Empate");

    private final String descricao;

    EstadoPartida(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isEncerrada() {
        return this != EM_ANDAMENTO;
    }

    public Jogador getVencedor() {
        return switch (this) {
            case VITORIA_JOGADOR_1 -> Jogador.JOGADOR_1;
            case VITORIA_JOGADOR_2 -> Jogador.JOGADOR_2;
            default -> null;
        };
    }

    public static EstadoPartida vitoriaDe(Jogador jogador) {
        return jogador == Jogador.JOGADOR_1 ? VITORIA_JOGADOR_1 : VITORIA_JOGADOR_2;
    }
}
