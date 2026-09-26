package games.tabuleiro.damas.telas;

import games.tabuleiro.damas.componentes.PecaVisual;
import games.tabuleiro.damas.modelo.Jogador;
import games.tabuleiro.damas.util.Animacoes;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Janela sobreposta exibida ao término da partida, com o vencedor e as opções de continuação.
 */
public class ModalFimPartida extends StackPane {

    private final VBox cartao;
    private final HBox areaIcone = new HBox(10);
    private final Label rotuloResultado = new Label();
    private final Label rotuloDetalhe = new Label();
    private final Button botaoNovaPartida = new Button("Nova Partida");
    private final Button botaoMenu = new Button("Voltar ao Menu Principal");
    private final Button botaoEncerrar = new Button("Encerrar Jogo");

    public ModalFimPartida() {
        getStyleClass().add("modal-fundo");

        Label titulo = new Label("Término de Partida");
        titulo.getStyleClass().add("modal-titulo");
        rotuloResultado.getStyleClass().add("modal-resultado");
        rotuloDetalhe.getStyleClass().add("modal-detalhe");
        rotuloDetalhe.setWrapText(true);
        rotuloDetalhe.setMinHeight(Region.USE_PREF_SIZE);
        areaIcone.setAlignment(Pos.CENTER);

        botaoNovaPartida.getStyleClass().addAll("botao", "botao-primario");
        botaoMenu.getStyleClass().add("botao");
        botaoEncerrar.getStyleClass().addAll("botao", "botao-perigo");
        VBox botoes = new VBox(10, botaoNovaPartida, botaoMenu, botaoEncerrar);
        for (Button botao : new Button[]{botaoNovaPartida, botaoMenu, botaoEncerrar}) {
            botao.setMaxWidth(Double.MAX_VALUE);
            Animacoes.aplicarEfeitoHover(botao, 1.03);
        }

        cartao = new VBox(16, titulo, areaIcone, rotuloResultado, rotuloDetalhe, botoes);
        cartao.setAlignment(Pos.CENTER);
        cartao.getStyleClass().add("modal-cartao");
        cartao.setMaxWidth(440);
        cartao.setMaxHeight(Region.USE_PREF_SIZE);

        getChildren().add(cartao);
        setVisible(false);
    }

    /**
     * @param vencedor jogador vencedor, ou {@code null} em caso de empate
     */
    public void exibir(String resultado, String detalhe, Jogador vencedor) {
        rotuloResultado.setText(resultado);
        rotuloDetalhe.setText(detalhe);
        if (vencedor != null) {
            areaIcone.getChildren().setAll(PecaVisual.criarIcone(vencedor, true, 84));
        } else {
            areaIcone.getChildren().setAll(
                    PecaVisual.criarIcone(Jogador.JOGADOR_1, true, 64),
                    PecaVisual.criarIcone(Jogador.JOGADOR_2, true, 64));
        }

        setVisible(true);
        toFront();
        setOpacity(0);
        cartao.setScaleX(0.8);
        cartao.setScaleY(0.8);

        FadeTransition fade = new FadeTransition(Duration.millis(260), this);
        fade.setToValue(1);
        ScaleTransition escala = new ScaleTransition(Duration.millis(320), cartao);
        escala.setToX(1);
        escala.setToY(1);
        escala.setInterpolator(Interpolator.SPLINE(0.3, 1.4, 0.5, 1));
        new ParallelTransition(fade, escala).play();

        botaoNovaPartida.requestFocus();
    }

    public void ocultar() {
        setVisible(false);
    }

    public void setAoNovaPartida(Runnable acao) {
        botaoNovaPartida.setOnAction(evento -> acao.run());
    }

    public void setAoVoltarMenu(Runnable acao) {
        botaoMenu.setOnAction(evento -> acao.run());
    }

    public void setAoEncerrar(Runnable acao) {
        botaoEncerrar.setOnAction(evento -> acao.run());
    }
}
