package games.tabuleiro.damas;

import games.tabuleiro.damas.controle.NavegadorTelas;
import games.tabuleiro.damas.util.Constantes;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Aplicação JavaFX do jogo de Damas: configura a janela e abre o menu principal.
 */
public class AplicacaoDamas extends Application {

    private NavegadorTelas navegador;

    @Override
    public void start(Stage palco) {
        navegador = new NavegadorTelas(palco);
        palco.setMinWidth(Constantes.LARGURA_MINIMA);
        palco.setMinHeight(Constantes.ALTURA_MINIMA);
        palco.setResizable(true);
        navegador.mostrarMenu();
        palco.show();
    }

    @Override
    public void stop() {
        if (navegador != null) {
            navegador.liberarRecursos();
        }
    }
}
