package games.tabuleiro.damas.util;

/**
 * Constantes de configuração da interface (dimensões da janela, durações de animação, recursos).
 */
public final class Constantes {

    public static final String TITULO_JOGO = "Damas";

    public static final double LARGURA_INICIAL = 1120;
    public static final double ALTURA_INICIAL = 780;
    public static final double LARGURA_MINIMA = 820;
    public static final double ALTURA_MINIMA = 620;

    public static final double DURACAO_PASSO_MS = 230;
    public static final double DURACAO_PASSO_EXTRA_POR_CASA_MS = 45;
    public static final double DURACAO_REMOCAO_MS = 320;
    public static final double DURACAO_PROMOCAO_MS = 560;
    public static final double PAUSA_ENTRE_PASSOS_MS = 90;
    public static final double PAUSA_ANTES_JOGADA_BOT_MS = 320;
    public static final double ATRASO_MINIMO_BOT_MS = 550;
    public static final double ATRASO_MODAL_FIM_MS = 650;

    public static final String CAMINHO_ESTILOS = "/games/tabuleiro/damas/recursos/damas.css";

    private Constantes() {
    }
}
