package application.utilitarios.musica;

import javafx.application.Application;
import javafx.stage.Stage;

public final class AplicacaoMusica extends Application {

    // --- INICIALIZA A INTERFACE PRINCIPAL DA APLICAÇÃO ---
    @Override
    public void start(Stage palcoPrincipal) {
        TelaReprodutorMusica telaReprodutorMusica = new TelaReprodutorMusica();

        telaReprodutorMusica.exibir(palcoPrincipal);
    }

    // --- INICIA A EXECUÇÃO DA APLICAÇÃO ---
    public static void main(String[] argumentos) {
        launch(argumentos);
    }
}

