package games.tabuleiro.damas.telas;

import games.tabuleiro.damas.componentes.PainelInformacoes;
import games.tabuleiro.damas.componentes.PecaVisual;
import games.tabuleiro.damas.componentes.TabuleiroVisual;
import games.tabuleiro.damas.modelo.ConfiguracaoPartida;
import games.tabuleiro.damas.modelo.Jogador;
import games.tabuleiro.damas.util.Animacoes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class TelaPartida extends StackPane {

    private final TabuleiroVisual tabuleiroVisual = new TabuleiroVisual();
    private final PainelInformacoes painel;
    private final ModalFimPartida modal = new ModalFimPartida();
    private final Button botaoNovaPartida = new Button("Nova Partida");
    private final Button botaoMenu = new Button("Menu Principal");

    public TelaPartida(ConfiguracaoPartida configuracao) {
        getStyleClass().add("raiz-damas");

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
        rolagemPainel.getStyleClass().add("rolagem-transparente");
        rolagemPainel.setPrefWidth(310);
        rolagemPainel.setMinWidth(260);
        BorderPane.setMargin(rolagemPainel, new Insets(18, 18, 18, 0));
        conteudo.setRight(rolagemPainel);

        getChildren().addAll(conteudo, modal);
    }

    private HBox criarBarraSuperior(ConfiguracaoPartida configuracao) {
        Label titulo = new Label("Damas");
        titulo.getStyleClass().add("titulo-barra");
        Label subtitulo = new Label(configuracao.descricao());
        subtitulo.getStyleClass().add("texto-secundario");

        HBox icones = new HBox(-10,
                PecaVisual.criarIcone(Jogador.JOGADOR_1, true, 34),
                PecaVisual.criarIcone(Jogador.JOGADOR_2, false, 34));
        icones.setAlignment(Pos.CENTER);

        Region espaco = new Region();
        HBox.setHgrow(espaco, Priority.ALWAYS);

        botaoNovaPartida.getStyleClass().addAll("botao", "botao-primario");
        botaoMenu.getStyleClass().add("botao");
        Animacoes.aplicarEfeitoHover(botaoNovaPartida, 1.04);
        Animacoes.aplicarEfeitoHover(botaoMenu, 1.04);

        HBox barra = new HBox(14, icones, new VBox(0, titulo, subtitulo), espaco, botaoNovaPartida, botaoMenu);
        barra.setAlignment(Pos.CENTER_LEFT);
        barra.getStyleClass().add("barra-superior");
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
