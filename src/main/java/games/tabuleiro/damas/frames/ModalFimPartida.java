package games.tabuleiro.damas.frames;

import games.tabuleiro.damas.components.PecaVisual;
import games.tabuleiro.damas.model.Jogador;
import games.tabuleiro.damas.util.Animacoes;
import games.tabuleiro.damas.util.Estilos;
import games.tabuleiro.damas.util.Paleta;
import games.tabuleiro.damas.util.TipoBotao;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class ModalFimPartida extends StackPane {

    private final VBox      cartao;
    private final HBox      areaIcone = new HBox(10);
    private final Label     lblRotuloResultado = new Label();
    private final Label     lblRotuloDetalhe = new Label();
    private final Button    botaoNovaPartida = new Button("Nova Partida");
    private final Button    botaoMenu = new Button("Voltar ao Menu Principal");
    private final Button    botaoEncerrar = new Button("Encerrar Jogo");

    public ModalFimPartida() {
        setBackground(Estilos.fundo(Color.rgb(0, 0, 0, 0.62), 0));

        Label titulo = new Label("Término de Partida");
        Estilos.rotulo(titulo, 28, FontWeight.BOLD, Paleta.AMARELO);
        Estilos.rotulo(lblRotuloResultado, 26, FontWeight.BOLD, Paleta.TEXTO);
        Estilos.rotulo(lblRotuloDetalhe, 14, FontWeight.NORMAL, Color.web("#c3cddb"));
        lblRotuloDetalhe.setTextAlignment(TextAlignment.CENTER);
        lblRotuloDetalhe.setWrapText(true);
        lblRotuloDetalhe.setMinHeight(Region.USE_PREF_SIZE);
        areaIcone.setAlignment(Pos.CENTER);

        Estilos.botao(botaoNovaPartida, TipoBotao.PRIMARIO);
        Estilos.botao(botaoMenu, TipoBotao.PADRAO);
        Estilos.botao(botaoEncerrar, TipoBotao.PERIGO);
        VBox botoes = new VBox(10, botaoNovaPartida, botaoMenu, botaoEncerrar);

        for (Button botao : new Button[]{botaoNovaPartida, botaoMenu, botaoEncerrar}) {
            botao.setMaxWidth(Double.MAX_VALUE);
            Animacoes.aplicarEfeitoHover(botao, 1.03);
        }

        cartao = new VBox(16, titulo, areaIcone, lblRotuloResultado, lblRotuloDetalhe, botoes);
        cartao.setAlignment(Pos.CENTER);
        cartao.setBackground(Estilos.fundo(Paleta.gradienteModal(), 20));
        cartao.setBorder(Estilos.borda(Color.web("#f5c542", 0.6), 20, 2));
        cartao.setPadding(new Insets(30, 36, 30, 36));
        cartao.setEffect(new DropShadow(30, 0, 8, Color.rgb(0, 0, 0, 0.6)));
        cartao.setMaxWidth(440);
        cartao.setMaxHeight(Region.USE_PREF_SIZE);

        getChildren().add(cartao);
        setVisible(false);
    }

    public void exibir(String resultado, String detalhe, Jogador vencedor) {
        lblRotuloResultado.setText(resultado);
        lblRotuloDetalhe.setText(detalhe);
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
