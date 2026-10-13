package games.tabuleiro.damas.util;

import games.tabuleiro.damas.model.Jogador;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

public final class Paleta {

    public static final Color CASA_CLARA = Color.web("#f1f0ea");
    public static final Color CASA_ESCURA = Color.web("#1c1c1f");
    public static final Color MOLDURA_BORDA = Color.web("#4a5568");
    public static final Color COORDENADA = Color.web("#c9d2de");

    public static final Color DESTAQUE_SELECAO = Color.web("#f5c542", 0.50);
    public static final Color DESTAQUE_ULTIMO_MOVIMENTO = Color.web("#3d8bfd", 0.33);
    public static final Color DESTAQUE_HOVER = Color.web("#ffffff", 0.13);
    public static final Color DESTAQUE_ERRO = Color.web("#ff3b30", 0.60);
    public static final Color DESTINO = Color.web("#3ddc84", 0.90);
    public static final Color DESTINO_CAPTURA = Color.web("#ff5252", 0.95);
    public static final Color CAPTURA_OBRIGATORIA = Color.web("#ff9800");

    public static final Color OURO_CLARO = Color.web("#ffe38a");
    public static final Color OURO = Color.web("#f4b400");
    public static final Color OURO_ESCURO = Color.web("#9c6500");
    public static final Color IMPACTO_CAPTURA = Color.web("#ff5a4f");

    public static final Color TEXTO = Color.web("#e7ecf3");
    public static final Color TEXTO_SECUNDARIO = Color.web("#a3afc0");
    public static final Color TEXTO_SECAO = Color.web("#8fa0b8");
    public static final Color AMARELO = Color.web("#f5c542");
    public static final Color AZUL = Color.web("#3d8bfd");
    public static final Color VERDE = Color.web("#3ddc84");
    public static final Color LARANJA = Color.web("#ff9800");
    public static final Color VERMELHO = Color.web("#ff5252");
    public static final Color FUNDO_PAINEL = Color.web("#0a0e16", 0.55);
    public static final Color FUNDO_CARTAO = Color.web("#ffffff", 0.05);
    public static final Color BORDA_SUAVE = Color.web("#ffffff", 0.08);
    public static final Color FUNDO_DIALOGO = Color.web("#1b2433");
    public static final Color FUNDO_CABECALHO_DIALOGO = Color.web("#10151f");

    private Paleta() {
    }

    public static Paint gradienteDaPeca(Jogador jogador) {
        Color claro = jogador == Jogador.JOGADOR_1 ? Color.web("#fffaf0") : Color.web("#ff6f6f");
        Color escuro = jogador == Jogador.JOGADOR_1 ? Color.web("#cbbd9c") : Color.web("#9b1c1c");
        return new RadialGradient(0, 0, 0.38, 0.32, 0.8, true, CycleMethod.NO_CYCLE,
                new Stop(0, claro),
                new Stop(1, escuro));
    }

    public static Color contornoDaPeca(Jogador jogador) {
        return jogador == Jogador.JOGADOR_1 ? Color.web("#8f8165") : Color.web("#5e0f0f");
    }

    public static Color sulcoDaPeca(Jogador jogador) {
        return jogador == Jogador.JOGADOR_1 ? Color.web("#a8997a", 0.9) : Color.web("#6d1414", 0.85);
    }

    public static Paint gradienteOuro() {
        return new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, OURO_CLARO), new Stop(0.55, OURO),
                new Stop(1, OURO_ESCURO));
    }

    public static Paint gradienteMoldura() {
        return new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#2d3544")),
                new Stop(1, Color.web("#151920")));
    }

    public static Paint gradienteFundoTela() {
        return new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#10151f")),
                new Stop(0.55, Color.web("#1b2433")),
                new Stop(1, Color.web("#243248")));
    }

    public static Paint gradienteModal() {
        return new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#1f2a3c")), new Stop(1, Color.web("#151c28")));
    }

    public static Paint gradienteBotao(Color topo, Color base) {
        return new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, new Stop(0, topo), new Stop(1, base));
    }
}
