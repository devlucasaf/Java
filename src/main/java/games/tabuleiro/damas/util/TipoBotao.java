package games.tabuleiro.damas.util;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public enum TipoBotao {

    PADRAO(Color.web("#34445c"), Color.web("#42587b"), Color.web("#2a374b")),
    PRIMARIO(Paleta.gradienteBotao(Color.web("#4a94ff"), Color.web("#2563eb")),
            Paleta.gradienteBotao(Color.web("#62a3ff"), Color.web("#3171f0")),
            Color.web("#1f55c9")),
    PERIGO(Paleta.gradienteBotao(Color.web("#ef4444"), Color.web("#b91c1c")),
            Paleta.gradienteBotao(Color.web("#f76060"), Color.web("#c92a2a")),
            Color.web("#991b1b"));

    public final Paint normal;
    public final Paint hover;
    public final Paint pressionado;

    TipoBotao(Paint normal, Paint hover, Paint pressionado) {
        this.normal = normal;
        this.hover = hover;
        this.pressionado = pressionado;
    }
}
