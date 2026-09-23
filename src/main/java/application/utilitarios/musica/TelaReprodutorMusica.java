package application.utilitarios.musica;

import java.net.URL;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public final class TelaReprodutorMusica implements ObservadorReprodutor {

    private static final double LARGURA_INICIAL = 1100.0;
    private static final double ALTURA_INICIAL = 760.0;
    private static final double LARGURA_MINIMA = 880.0;
    private static final double ALTURA_MINIMA = 650.0;

    private final GerenciadorPlaylist       gerenciadorPlaylist;
    private final ReprodutorMusica          reprodutorMusica;
    private final PainelInformacoesMusica   painelInformacoesMusica;
    private final PainelListaReproducao     painelListaReproducao;
    private final PainelControles           painelControles;
    private final Label                     rotuloStatus;
    private Stage                           palcoPrincipal;

    public TelaReprodutorMusica() {
        gerenciadorPlaylist = new GerenciadorPlaylist();
        reprodutorMusica = new ReprodutorMusica(gerenciadorPlaylist);
        painelInformacoesMusica = new PainelInformacoesMusica();
        painelControles = new PainelControles(reprodutorMusica);

        painelListaReproducao = new PainelListaReproducao(gerenciadorPlaylist, this::selecionarMusicaParaReproducao);

        rotuloStatus = new Label("Pronto para reproduzir");

        reprodutorMusica.adicionarObservador(this);
        configurarRotuloStatus();
    }

    // --- EXIBE A TELA PRINCIPAL DO REPRODUTOR ---
    public void exibir(Stage palco) {
        palcoPrincipal = palco;

        BorderPane raiz = criarRaiz();
        Scene cena = new Scene(raiz, LARGURA_INICIAL, ALTURA_INICIAL);

        aplicarFolhaEstilos(cena);
        configurarPalco(palco, cena);
        palco.show();
    }

    // --- CRIA O CONTENEDOR PRINCIPAL DA INTERFACE ---
    private BorderPane criarRaiz() {
        BorderPane raiz = new BorderPane();
        raiz.getStyleClass().add("raiz");
        raiz.setCenter(criarConteudoPrincipal());
        raiz.setBottom(criarRodape());

        return raiz;
    }

    // --- CRIA A ÁREA CENTRAL DA INTERFACE ---
    private HBox criarConteudoPrincipal() {
        HBox conteudoPrincipal = new HBox(
                24.0,
                painelListaReproducao,
                painelInformacoesMusica
        );

        conteudoPrincipal.getStyleClass().add("conteudo-principal");
        conteudoPrincipal.setPadding(new Insets(24.0));
        conteudoPrincipal.setAlignment(Pos.CENTER);
        conteudoPrincipal.setFillHeight(true);

        painelListaReproducao.setPrefWidth(430.0);
        painelListaReproducao.setMinWidth(320.0);

        painelInformacoesMusica.setPrefWidth(420.0);
        painelInformacoesMusica.setMinWidth(320.0);

        HBox.setHgrow(painelListaReproducao, Priority.ALWAYS);
        HBox.setHgrow(painelInformacoesMusica, Priority.ALWAYS);

        return conteudoPrincipal;
    }

    // --- CRIA O RODAPÉ COM CONTROLES E STATUS ---
    private VBox criarRodape() {
        VBox rodape = new VBox(8.0);
        rodape.getStyleClass().add("rodape");
        rodape.setFillWidth(true);

        BorderPane faixaStatus = new BorderPane();
        faixaStatus.getStyleClass().add("faixa-status");
        faixaStatus.setLeft(rotuloStatus);
        faixaStatus.setPadding(new Insets(0.0, 24.0, 4.0, 24.0));

        rodape.getChildren().addAll(painelControles, faixaStatus);

        return rodape;
    }

    // --- CONFIGURA O ROTULO DA AREA DE STATUS ---
    private void configurarRotuloStatus() {
        rotuloStatus.getStyleClass().add("status");
        rotuloStatus.setText("Pronto para reproduzir");
        rotuloStatus.setWrapText(true);
        rotuloStatus.setMaxWidth(Double.MAX_VALUE);
        rotuloStatus.setAccessibleText("Estado da reproducao");
    }

    // --- APLICA A FOLHA DE ESTILOS DA APLICACAO ---
    private void aplicarFolhaEstilos(Scene cena) {
        URL enderecoEstilo = getClass().getResource("estilo.css");

        if (enderecoEstilo != null) {
            cena.getStylesheets().add(enderecoEstilo.toExternalForm());
        } else {
            System.err.println(
                    "Nao foi possivel localizar o arquivo estilo.css."
            );
        }
    }

    // --- CONFIGURA AS PROPRIEDADES DA JANELA PRINCIPAL ---
    private void configurarPalco(Stage palco, Scene cena) {
        palco.setTitle("Aurora Música");
        palco.setMinWidth(LARGURA_MINIMA);
        palco.setMinHeight(ALTURA_MINIMA);
        palco.setScene(cena);
        palco.centerOnScreen();
        palco.setOnCloseRequest(evento -> encerrarAplicacao());
    }

    // --- SELECIONA E REPRODUZ UMA MÚSICA DA PLAYLIST ---
    private void selecionarMusicaParaReproducao(Musica musica) {
        if (musica == null) {
            return;
        }

        gerenciadorPlaylist.selecionarMusica(musica);
        reprodutorMusica.carregarMusica(musica, true);
    }

    // --- ATUALIZA A INTERFACE QUANDO A MÚSICA FOR ALTERADA ---
    @Override
    public void aoTrocarMusica(Musica musica) {
        painelInformacoesMusica.atualizarInformacoes(musica);
        painelListaReproducao.destacarMusica(musica);
    }

    // --- ATUALIZA O TEMPO E O PROGRESSO DA REPRODUÇÃO ---
    @Override
    public void aoAtualizarTempo(Duration tempoAtual, Duration duracaoTotal) {
        painelControles.atualizarTempo(tempoAtual, duracaoTotal);
    }

    // --- ATUALIZA A ÁREA DE STATUS DA REPRODUÇÃO ---
    @Override
    public void aoAlterarEstado(EstadoReproducao estadoReproducao, String detalhe) {
        if (estadoReproducao == null) {
            return;
        }

        painelControles.atualizarEstado(estadoReproducao);
        atualizarEstiloStatus(estadoReproducao);

        String descricao = estadoReproducao.obterDescricao();
        String textoStatus = montarTextoStatus(descricao, detalhe);

        rotuloStatus.setText(textoStatus);
    }

    // --- MONTA O TEXTO APRESENTADO NA AREA DE STATUS ---
    private String montarTextoStatus(String descricao, String detalhe) {
        String descricaoSegura = descricao == null || descricao.isBlank()
                ? "Estado desconhecido"
                : descricao.strip();

        if (detalhe == null || detalhe.isBlank()) {
            return "●  " + descricaoSegura;
        }

        return "●  " + descricaoSegura + "  •  " + detalhe.strip();
    }

    // --- ATUALIZA O ESTILO VISUAL DO STATUS ---
    private void atualizarEstiloStatus(EstadoReproducao estadoReproducao) {
        switch (estadoReproducao) {
            case PRONTO -> rotuloStatus.setTextFill(CoresAplicacao.TEXTO_DISCRETO);
            case CARREGANDO -> rotuloStatus.setTextFill(CoresAplicacao.INFORMACAO);
            case REPRODUZINDO -> rotuloStatus.setTextFill(CoresAplicacao.VERDE_PRINCIPAL);
            case PAUSADO -> rotuloStatus.setTextFill(CoresAplicacao.AVISO);
            case PARADO -> rotuloStatus.setTextFill(CoresAplicacao.TEXTO_DISCRETO);
            case FINALIZADO -> rotuloStatus.setTextFill(CoresAplicacao.TEXTO_SECUNDARIO);
            case ERRO -> rotuloStatus.setTextFill(CoresAplicacao.ERRO);
        }
    }

    // --- RETORNA O GERENCIADOR DA PLAYLIST ---
    public GerenciadorPlaylist obterGerenciadorPlaylist() {
        return gerenciadorPlaylist;
    }

    // --- RETORNA O REPRODUTOR DE MÚSICA ---
    public ReprodutorMusica obterReprodutorMusica() {
        return reprodutorMusica;
    }

    // --- RETORNA O PALCO PRINCIPAL DA APLICAÇÃO ---
    public Stage obterPalcoPrincipal() {
        return palcoPrincipal;
    }

    // --- ENCERRA A APLICAÇÃO E LIBERA OS RECURSOS ---
    public void encerrarAplicacao() {
        reprodutorMusica.removerObservador(this);
        reprodutorMusica.encerrar();
    }
}
