package games.tabuleiro.damas.util;

import javafx.scene.Parent;
import javafx.scene.Scene;

import java.net.URL;

/**
 * Acesso aos recursos gráficos do jogo (folha de estilos).
 * Se o arquivo CSS não for encontrado, o jogo continua funcionando com o visual padrão do JavaFX.
 */
public final class Recursos {

    private static String urlEstilos;
    private static boolean avisoEmitido;

    private Recursos() {
    }

    public static void aplicarEstilos(Scene cena) {
        String url = obterUrlEstilos();
        if (url != null && !cena.getStylesheets().contains(url)) {
            cena.getStylesheets().add(url);
        }
    }

    public static void aplicarEstilos(Parent raiz) {
        String url = obterUrlEstilos();
        if (url != null && !raiz.getStylesheets().contains(url)) {
            raiz.getStylesheets().add(url);
        }
    }

    private static String obterUrlEstilos() {
        if (urlEstilos == null) {
            URL url = Recursos.class.getResource(Constantes.CAMINHO_ESTILOS);
            if (url == null) {
                if (!avisoEmitido) {
                    System.err.println("Aviso: folha de estilos não encontrada em " + Constantes.CAMINHO_ESTILOS);
                    avisoEmitido = true;
                }
                return null;
            }
            urlEstilos = url.toExternalForm();
        }
        return urlEstilos;
    }
}
