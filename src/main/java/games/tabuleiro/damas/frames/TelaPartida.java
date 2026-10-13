package games.tabuleiro.damas.frames;

import games.tabuleiro.damas.components.PainelInformacoes;
import games.tabuleiro.damas.components.PecaVisual;
import games.tabuleiro.damas.components.TabuleiroVisual;
import games.tabuleiro.damas.model.ConfiguracaoPartida;
import games.tabuleiro.damas.model.Jogador;
import games.tabuleiro.damas.util.Animacoes;
import games.tabuleiro.damas.util.Estilos;
import games.tabuleiro.damas.util.Paleta;
import games.tabuleiro.damas.util.TipoBotao;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;

public class TelaPartida extends StackPane {

    private final TabuleiroVisual   tabuleiroVisual = new TabuleiroVisual();
    private final PainelInformacoes painel;
    private final ModalFimPartida   modal = new ModalFimPartida();
    private final Button            botaoNovaPartida = new Button("Nova Partida");
    private final Button            botaoMenu = new Button("Menu Principal");

    public TelaPartida(ConfiguracaoPartida configuracao) {
        Estilos.fundoTela(this);

        painel = new PainelInformacoes(configuracao);

        BorderPane conteudo = new BorderPane();
        conteudo.setTop(criarBarraSuperior(configuracao));

        StackPane areaTabuleiro = new StackPane(tabuleiroVisual);
        areaTabuleiro.setPadding(new Insets(18));
        conteudo.setCenter(areaTabuleiro);

        ScrollPane rolagemPainel = new ScrollPane(painel);
        rolagemPainel.setFitToWidth(true);
        rolagemPainel.setFitToHeight(true);
        rolagemPainel.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        Estilos.rolagemTransparente(rolagemPainel);
        rolagemPainel.setPrefWidth(310);
        rolagemPainel.setMinWidth(260);
        BorderPane.setMargin(rolagemPainel, new Insets(18, 18, 18, 0));
        conteudo.setRight(rolagemPainel);

        getChildren().addAll(conteudo, modal);
    }

    private HBox criarBarraSuperior(ConfiguracaoPartida configuracao) {
        Label lblTitulo = new Label("Damas");
        Estilos.rotulo(lblTitulo, 22, FontWeight.BOLD, Paleta.TEXTO);
        Label lblSubtitulo = new Label(configuracao.descricao());
        Estilos.textoSecundario(lblSubtitulo);

        HBox icones = new HBox(-10,
                PecaVisual.criarIcone(Jogador.JOGADOR_1, true, 34),
                PecaVisual.criarIcone(Jogador.JOGADOR_2, false, 34));
        icones.setAlignment(Pos.CENTER);

        Region espaco = new Region();
        HBox.setHgrow(espaco, Priority.ALWAYS);

        Estilos.botao(botaoNovaPartida, TipoBotao.PRIMARIO);
        Estilos.botao(botaoMenu, TipoBotao.PADRAO);
        Animacoes.aplicarEfeitoHover(botaoNovaPartida, 1.04);
        Animacoes.aplicarEfeitoHover(botaoMenu, 1.04);

        HBox barra = new HBox(14, icones, new VBox(0, lblTitulo, lblSubtitulo), espaco, botaoNovaPartida, botaoMenu);
        barra.setAlignment(Pos.CENTER_LEFT);
        barra.setPadding(new Insets(12, 20, 12, 20));
        barra.setBackground(Estilos.fundo(Color.web("#080b12", 0.6), 0));
        barra.setBorder(new Border(new BorderStroke(null, null, Paleta.BORDA_SUAVE, null,
                null, null, BorderStrokeStyle.SOLID, null,
                CornerRadii.EMPTY, new BorderWidths(0, 0, 1, 0), null))
        );
        return barra;
    }

    public TabuleiroVisual getTabuleiroVisual() {
        return tabuleiroVisual;
    }

    public PainelInformacoes getPainel() {
        return painel;
    }

    public ModalFimPartida getModal() {
        return modal;
    }

    public void setAoSolicitarNovaPartida(Runnable acao) {
        botaoNovaPartida.setOnAction(evento -> acao.run());
    }

    public void setAoSolicitarMenu(Runnable acao) {
        botaoMenu.setOnAction(evento -> acao.run());
    }

    public void exibirFimDePartida(String resultado, String detalhe, Jogador vencedor) {
        modal.exibir(resultado, detalhe, vencedor);
    }

    public void ocultarFimDePartida() {
        modal.ocultar();
    }
}
