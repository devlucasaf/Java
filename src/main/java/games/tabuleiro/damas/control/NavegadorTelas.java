package games.tabuleiro.damas.control;

import games.tabuleiro.damas.model.ConfiguracaoPartida;
import games.tabuleiro.damas.frames.TelaMenuPrincipal;
import games.tabuleiro.damas.frames.TelaPartida;
import games.tabuleiro.damas.util.Animacoes;
import games.tabuleiro.damas.util.Constantes;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class NavegadorTelas {

    private final Stage         palco;
    private final Scene         cena;
    private ControladorPartida  controladorAtual;

    public NavegadorTelas(Stage palco) {
        this.palco = palco;
        this.cena = new Scene(new StackPane(), Constantes.LARGURA_INICIAL, Constantes.ALTURA_INICIAL);
        palco.setScene(cena);
    }

    public void mostrarMenu() {
        finalizarPartidaAtual();
        trocarTela(new TelaMenuPrincipal(this::iniciarPartida, this::encerrarJogo));
        palco.setTitle(Constantes.TITULO_JOGO + " — Menu Principal");
    }

    public void iniciarPartida(ConfiguracaoPartida configuracao) {
        finalizarPartidaAtual();
        TelaPartida tela = new TelaPartida(configuracao);
        controladorAtual = new ControladorPartida(configuracao, tela, this::mostrarMenu, this::encerrarJogo);
        trocarTela(tela);
        palco.setTitle(Constantes.TITULO_JOGO + " — " + configuracao.descricao());
        controladorAtual.iniciarNovaPartida();
    }

    public void encerrarJogo() {
        liberarRecursos();
        Platform.exit();
    }

    public void liberarRecursos() {
        finalizarPartidaAtual();
    }

    private void finalizarPartidaAtual() {
        if (controladorAtual != null) {
            controladorAtual.finalizar();
            controladorAtual = null;
        }
    }

    private void trocarTela(Parent raiz) {
        cena.setRoot(raiz);
        Animacoes.surgir(raiz);
    }
}
