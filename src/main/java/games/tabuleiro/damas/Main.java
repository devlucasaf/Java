package games.tabuleiro.damas;

import javafx.application.Application;

/**
 * Ponto de entrada do jogo de Damas.
 * <p>
 * Esta classe não estende {@link Application} de propósito: assim o jogo pode ser executado
 * diretamente pelo classpath (IDE ou Maven) sem exigir configuração de módulos do JavaFX.
 */
public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        Application.launch(AplicacaoDamas.class, args);
    }
}
