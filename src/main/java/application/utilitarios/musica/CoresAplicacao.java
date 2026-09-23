package application.utilitarios.musica;

import javafx.scene.paint.Color;

public final class CoresAplicacao {

    public static final String  HEXADECIMAL_FUNDO_PRINCIPAL = "#121212";
    public static final String  HEXADECIMAL_FUNDO_PAINEL = "#181818";
    public static final String  HEXADECIMAL_FUNDO_COMPONENTE = "#242424";
    public static final String  HEXADECIMAL_FUNDO_HOVER = "#333333";
    public static final String  HEXADECIMAL_VERDE_PRINCIPAL = "#1ED760";
    public static final String  HEXADECIMAL_VERDE_HOVER = "#32E875";
    public static final String  HEXADECIMAL_VERDE_ESCURO = "#1F3B29";
    public static final String  HEXADECIMAL_TEXTO_PRINCIPAL = "#FFFFFF";
    public static final String  HEXADECIMAL_TEXTO_SECUNDARIO = "#D0D0D0";
    public static final String  HEXADECIMAL_TEXTO_DISCRETO = "#9A9A9A";
    public static final String  HEXADECIMAL_TEXTO_ESCURO = "#101010";
    public static final String  HEXADECIMAL_BARRA_INATIVA = "#555555";
    public static final String  HEXADECIMAL_BORDA = "#303030";
    public static final String  HEXADECIMAL_ERRO = "#FF6B6B";
    public static final String  HEXADECIMAL_AVISO = "#F5B942";
    public static final String  HEXADECIMAL_INFORMACAO = "#5DADE2";
    public static final Color   FUNDO_PRINCIPAL = Color.web(HEXADECIMAL_FUNDO_PRINCIPAL);
    public static final Color   FUNDO_PAINEL = Color.web(HEXADECIMAL_FUNDO_PAINEL);
    public static final Color   FUNDO_COMPONENTE = Color.web(HEXADECIMAL_FUNDO_COMPONENTE);
    public static final Color   FUNDO_HOVER = Color.web(HEXADECIMAL_FUNDO_HOVER);
    public static final Color   VERDE_PRINCIPAL = Color.web(HEXADECIMAL_VERDE_PRINCIPAL);
    public static final Color   VERDE_HOVER = Color.web(HEXADECIMAL_VERDE_HOVER);
    public static final Color   VERDE_ESCURO = Color.web(HEXADECIMAL_VERDE_ESCURO);
    public static final Color   TEXTO_PRINCIPAL = Color.web(HEXADECIMAL_TEXTO_PRINCIPAL);
    public static final Color   TEXTO_SECUNDARIO = Color.web(HEXADECIMAL_TEXTO_SECUNDARIO);
    public static final Color   TEXTO_DISCRETO = Color.web(HEXADECIMAL_TEXTO_DISCRETO);
    public static final Color   TEXTO_ESCURO = Color.web(HEXADECIMAL_TEXTO_ESCURO);
    public static final Color   BARRA_INATIVA = Color.web(HEXADECIMAL_BARRA_INATIVA);
    public static final Color   BORDA = Color.web(HEXADECIMAL_BORDA);
    public static final Color   ERRO = Color.web(HEXADECIMAL_ERRO);
    public static final Color   AVISO = Color.web(HEXADECIMAL_AVISO);
    public static final Color   INFORMACAO = Color.web(HEXADECIMAL_INFORMACAO);

    // --- IMPEDE A CRIACAO DE OBJETOS DESTA CLASSE ---
    private CoresAplicacao() {
    }

    // --- CONVERTE UMA COR JAVAFX PARA O FORMATO CSS RGBA ---
    public static String converterParaCss(Color cor) {
        if (cor == null) {
            return HEXADECIMAL_TEXTO_PRINCIPAL;
        }

        int vermelho = converterComponente(cor.getRed());
        int verde = converterComponente(cor.getGreen());
        int azul = converterComponente(cor.getBlue());
        double opacidade = limitarComponente(cor.getOpacity());

        return String.format(java.util.Locale.US, "rgba(%d, %d, %d, %.2f)", vermelho, verde, azul, opacidade);
    }

    // --- CONVERTE UMA COR JAVAFX PARA O FORMATO HEXADECIMAL ---
    public static String converterParaHexadecimal(Color cor) {
        if (cor == null) {
            return HEXADECIMAL_TEXTO_PRINCIPAL;
        }

        int vermelho = converterComponente(cor.getRed());
        int verde = converterComponente(cor.getGreen());
        int azul = converterComponente(cor.getBlue());

        return String.format("#%02X%02X%02X", vermelho, verde, azul);
    }

    // --- CONVERTE UM COMPONENTE PARA O INTERVALO DE ZERO A 255 ---
    private static int converterComponente(double componente) {
        return (int) Math.round(limitarComponente(componente) * 255.0);
    }

    // --- LIMITA UM COMPONENTE AO INTERVALO ENTRE ZERO E UM ---
    private static double limitarComponente(double componente) {
        return Math.max(0.0, Math.min(1.0, componente));
    }
}
