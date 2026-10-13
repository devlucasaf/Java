package games.tabuleiro.damas.components;

import games.tabuleiro.damas.util.Paleta;

import javafx.scene.paint.Color;

public enum TipoMensagem {

    INFORMACAO(Paleta.AZUL),
    SUCESSO(Paleta.VERDE),
    ALERTA(Paleta.LARANJA),
    ERRO(Paleta.VERMELHO);

    private final Color cor;

    TipoMensagem(Color cor) {
        this.cor = cor;
    }

    public Color getCor() {
        return cor;
    }
}
