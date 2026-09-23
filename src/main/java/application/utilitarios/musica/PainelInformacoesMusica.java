package application.utilitarios.musica;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public final class PainelInformacoesMusica extends VBox {

    private static final double LARGURA_CAPA = 270.0;
    private static final double ALTURA_CAPA = 270.0;
    private static final double ARREDONDAMENTO_CAPA = 24.0;

    private final ImageView visualizadorCapa;
    private final Label     lblRotuloTitulo;
    private final Label     lblRotuloArtista;
    private final Label     lblRotuloAlbum;
    private final Label     lblSimboloCapaPadrao;

    // --- INICIALIZA OS COMPONENTES DO PAINEL ---
    public PainelInformacoesMusica() {
        visualizadorCapa = new ImageView();
        lblRotuloTitulo = new Label();
        lblRotuloArtista = new Label();
        lblRotuloAlbum = new Label();
        lblSimboloCapaPadrao = new Label("♫");

        configurarPainel();
        configurarVisualizadorCapa();
        configurarRotulos();
        inicializarConteudo();
    }

    // --- CONFIGURA A APARÊNCIA E O ALINHAMENTO DO PAINEL ---
    private void configurarPainel() {
        getStyleClass().add("painel-informacoes");
        setAlignment(Pos.CENTER);
        setSpacing(10.0);
        setMinWidth(320.0);
        setPrefWidth(420.0);
        setFillWidth(true);
    }

    // --- CONFIGURA O VISUALIZADOR DA CAPA DA MÚSICA ---
    private void configurarVisualizadorCapa() {
        visualizadorCapa.setFitWidth(LARGURA_CAPA);
        visualizadorCapa.setFitHeight(ALTURA_CAPA);
        visualizadorCapa.setPreserveRatio(false);
        visualizadorCapa.setSmooth(true);
        visualizadorCapa.setCache(true);

        Rectangle recorteCapa = new Rectangle(LARGURA_CAPA, ALTURA_CAPA);

        recorteCapa.setArcWidth(ARREDONDAMENTO_CAPA);
        recorteCapa.setArcHeight(ARREDONDAMENTO_CAPA);

        visualizadorCapa.setClip(recorteCapa);
    }

    // --- CONFIGURA OS RÓTULOS DAS INFORMAÇÕES DA MÚSICA ---
    private void configurarRotulos() {
        lblRotuloTitulo.getStyleClass().add("titulo-musica");
        lblRotuloArtista.getStyleClass().add("artista-musica");
        lblRotuloAlbum.getStyleClass().add("album-musica");
        lblSimboloCapaPadrao.getStyleClass().add("simbolo-capa");

        lblRotuloTitulo.setWrapText(true);
        lblRotuloArtista.setWrapText(true);
        lblRotuloAlbum.setWrapText(true);

        lblRotuloTitulo.setMaxWidth(LARGURA_CAPA);
        lblRotuloArtista.setMaxWidth(LARGURA_CAPA);
        lblRotuloAlbum.setMaxWidth(LARGURA_CAPA);

        lblRotuloTitulo.setAlignment(Pos.CENTER);
        lblRotuloArtista.setAlignment(Pos.CENTER);
        lblRotuloAlbum.setAlignment(Pos.CENTER);
    }

    // --- INICIALIZA O CONTEÚDO VISUAL DO PAINEL ---
    private void inicializarConteudo() {
        StackPane molduraCapa = criarMolduraCapa();

        getChildren().addAll(molduraCapa, lblRotuloTitulo, lblRotuloArtista, lblRotuloAlbum);

        limparInformacoes();
    }

    // --- CRIA A MOLDURA VISUAL DA CAPA ---
    private StackPane criarMolduraCapa() {
        Rectangle fundoCapa = new Rectangle(LARGURA_CAPA, ALTURA_CAPA, Color.web("#242424"));

        fundoCapa.setArcWidth(ARREDONDAMENTO_CAPA);
        fundoCapa.setArcHeight(ARREDONDAMENTO_CAPA);

        StackPane molduraCapa = new StackPane(fundoCapa, lblSimboloCapaPadrao, visualizadorCapa);

        molduraCapa.getStyleClass().add("moldura-capa");
        molduraCapa.setAlignment(Pos.CENTER);
        molduraCapa.setMinSize(LARGURA_CAPA, ALTURA_CAPA);
        molduraCapa.setPrefSize(LARGURA_CAPA, ALTURA_CAPA);
        molduraCapa.setMaxSize(LARGURA_CAPA, ALTURA_CAPA);

        return molduraCapa;
    }

    // --- ATUALIZA AS INFORMAÇÕES DA MÚSICA ---
    public void atualizarInformacoes(Musica musica) {
        if (musica == null) {
            limparInformacoes();
            return;
        }

        lblRotuloTitulo.setText(valorOuPadrao(musica.obterTitulo(), "Título desconhecido"));
        lblRotuloArtista.setText(valorOuPadrao(musica.obterArtista(), "Artista desconhecido"));
        lblRotuloAlbum.setText(valorOuPadrao(musica.obterAlbum(), "Álbum desconhecido"));

        atualizarCapa(musica.obterCapa());
    }

    // --- ATUALIZA APENAS A CAPA DA MÚSICA ---
    public void atualizarCapa(Image capa) {
        visualizadorCapa.setImage(capa);

        boolean possuiCapa = capa != null;
        visualizadorCapa.setVisible(possuiCapa);
        visualizadorCapa.setManaged(possuiCapa);
        lblSimboloCapaPadrao.setVisible(!possuiCapa);
        lblSimboloCapaPadrao.setManaged(!possuiCapa);
    }

    // --- REMOVE AS INFORMAÇÕES DA MÚSICA ATUAL ---
    public void limparInformacoes() {
        lblRotuloTitulo.setText("Nenhuma música selecionada");
        lblRotuloArtista.setText("Adicione arquivos para começar");
        lblRotuloAlbum.setText("Biblioteca local");
        atualizarCapa(null);
    }

    // --- RETORNA UM TEXTO PADRÃO QUANDO O VALOR FOR INVÁLIDO ---
    private String valorOuPadrao(String valor, String valorPadrao) {
        if (valor == null || valor.isBlank()) {
            return valorPadrao;
        }

        return valor.strip();
    }
}
