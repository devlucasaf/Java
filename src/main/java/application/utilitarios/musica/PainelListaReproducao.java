package application.utilitarios.musica;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public final class PainelListaReproducao extends BorderPane {

    private final GerenciadorPlaylist   gerenciadorPlaylist;
    private final Consumer<Musica>      acaoSelecionarMusica;
    private final ListView<Musica>      listaMusicas;
    private final Label                 rotuloQuantidade;
    private final Button                botaoAdicionar;
    private final Button                botaoRemover;
    private final Button                botaoLimpar;

    // --- INICIALIZA O PAINEL DA LISTA DE REPRODUCAO ---
    public PainelListaReproducao(GerenciadorPlaylist gerenciadorPlaylist, Consumer<Musica> acaoSelecionarMusica) {
        this.gerenciadorPlaylist = Objects.requireNonNull(gerenciadorPlaylist, "O gerenciador da playlist nao pode ser nulo.");

        this.acaoSelecionarMusica = Objects.requireNonNull(acaoSelecionarMusica, "A acao de selecao nao pode ser nula.");

        listaMusicas = new ListView<>();
        rotuloQuantidade = new Label();
        botaoAdicionar = new Button("+ Adicionar musicas");
        botaoRemover = new Button("Remover");
        botaoLimpar = new Button("Limpar lista");

        configurarPainel();
        configurarListaMusicas();
        configurarBotoes();
        configurarEventos();
        montarInterface();
        atualizarQuantidadeMusicas();
        atualizarEstadoBotoes();
    }

    // --- CONFIGURA A APARÊNCIA DO PAINEL ---
    private void configurarPainel() {
        getStyleClass().add("painel-lista");
        setPadding(new Insets(18.0));
        setMinWidth(320.0);
        setPrefWidth(430.0);
    }

    // --- CONFIGURA A LISTA VISUAL DE MÚSICAS ---
    private void configurarListaMusicas() {
        listaMusicas.setItems(gerenciadorPlaylist.obterMusicas());
        listaMusicas.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listaMusicas.setPlaceholder(criarMensagemListaVazia());
        listaMusicas.setCellFactory(controle -> new CelulaMusica());
        listaMusicas.getStyleClass().add("lista-musicas");

        BorderPane.setMargin(listaMusicas, new Insets(14.0, 0.0, 14.0, 0.0));
    }

    // --- CRIA A MENSAGEM EXIBIDA QUANDO A PLAYLIST ESTIVER VAZIA ---
    private VBox criarMensagemListaVazia() {
        Label simbolo = new Label("♫");
        simbolo.getStyleClass().add("simbolo-lista-vazia");

        Label lblTitulo = new Label("Sua playlist esta vazia");
        lblTitulo.getStyleClass().add("titulo-lista-vazia");

        Label descricao = new Label("Adicione arquivos de áudio para iniciar a reprodução.");
        descricao.getStyleClass().add("descricao-lista-vazia");
        descricao.setWrapText(true);
        descricao.setMaxWidth(260.0);
        descricao.setAlignment(Pos.CENTER);

        VBox mensagem = new VBox(8.0, simbolo, lblTitulo, descricao);
        mensagem.setAlignment(Pos.CENTER);

        return mensagem;
    }

    // --- CONFIGURA OS BOTOES DO PAINEL ---
    private void configurarBotoes() {
        botaoAdicionar.getStyleClass().add("botao-adicionar");
        botaoRemover.getStyleClass().add("botao-secundario");
        botaoLimpar.getStyleClass().add("botao-secundario");
        botaoAdicionar.setTooltip(new Tooltip("Selecionar arquivos de audio"));
        botaoRemover.setTooltip(new Tooltip("Remover a música selecionada"));
        botaoLimpar.setTooltip(new Tooltip("Remover todas as músicas da playlist"));
    }

    // --- CONFIGURA OS EVENTOS DA LISTA E DOS BOTOES ---
    private void configurarEventos() {
        botaoAdicionar.setOnAction(evento -> abrirSeletorArquivos());
        botaoRemover.setOnAction(evento -> removerMusicaSelecionada());
        botaoLimpar.setOnAction(evento -> limparListaReproducao());

        listaMusicas.setOnMouseClicked(evento -> {
            boolean cliquePrincipal = evento.getButton() == MouseButton.PRIMARY;
            boolean cliqueDuplo = evento.getClickCount() == 2;

            if (cliquePrincipal && cliqueDuplo) {
                reproduzirMusicaSelecionada();
            }
        });

        listaMusicas.setOnKeyPressed(evento -> {
            if (evento.getCode() == KeyCode.ENTER) {
                reproduzirMusicaSelecionada();
                evento.consume();
                return;
            }

            if (evento.getCode() == KeyCode.DELETE) {
                removerMusicaSelecionada();
                evento.consume();
            }
        });

        listaMusicas.getSelectionModel()
                .selectedItemProperty()
                .addListener((observavel, anterior, atual) ->
                        atualizarEstadoBotoes()
                );

        gerenciadorPlaylist.obterMusicas().addListener(
                (javafx.collections.ListChangeListener<Musica>) alteracao -> {
                    atualizarQuantidadeMusicas();
                    atualizarEstadoBotoes();
                }
        );
    }

    // --- MONTA OS COMPONENTES VISUAIS DO PAINEL ---
    private void montarInterface() {
        setTop(criarCabecalho());
        setCenter(listaMusicas);
        setBottom(criarRodape());
    }

    // --- CRIA O CABECALHO DA LISTA DE REPRODUCAO ---
    private VBox criarCabecalho() {
        Label titulo = new Label("Sua biblioteca");
        titulo.getStyleClass().add("titulo-secao");

        rotuloQuantidade.getStyleClass().add("quantidade-musicas");

        Region espacador = new Region();
        HBox.setHgrow(espacador, Priority.ALWAYS);

        HBox linhaTitulo = new HBox(10.0, titulo, espacador, rotuloQuantidade);

        linhaTitulo.setAlignment(Pos.CENTER_LEFT);

        VBox cabecalho = new VBox(12.0, linhaTitulo, botaoAdicionar, new Separator());

        botaoAdicionar.setMaxWidth(Double.MAX_VALUE);

        return cabecalho;
    }

    // --- CRIA O RODAPE COM AS ACOES DA PLAYLIST ---
    private HBox criarRodape() {
        Region espacador = new Region();
        HBox.setHgrow(espacador, Priority.ALWAYS);

        HBox rodape = new HBox(10.0, botaoRemover, espacador, botaoLimpar);

        rodape.setAlignment(Pos.CENTER_LEFT);

        return rodape;
    }

    // --- ABRE O SELETOR DE ARQUIVOS DE AUDIO ---
    private void abrirSeletorArquivos() {
        FileChooser seletorArquivos = new FileChooser();
        seletorArquivos.setTitle("Adicionar musicas");

        seletorArquivos.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Arquivos de áudio", "*.mp3", "*.m4a", "*.wav", "*.aiff", "*.aif", "*.aac"),
                new FileChooser.ExtensionFilter("Todos os arquivos", "*.*")
        );

        Window janela = getScene() == null ? null : getScene().getWindow();

        List<File> arquivosSelecionados = seletorArquivos.showOpenMultipleDialog(janela);

        if (arquivosSelecionados == null || arquivosSelecionados.isEmpty()) {
            return;
        }

        List<Path> caminhos = arquivosSelecionados
                .stream()
                .map(File::toPath)
                .toList();

        gerenciadorPlaylist.adicionarMusicas(caminhos);

        if (listaMusicas.getSelectionModel().getSelectedItem() == null && !gerenciadorPlaylist.estaVazia()) {
            listaMusicas.getSelectionModel().selectFirst();
        }
    }

    // --- EXECUTA A ACAO DA MUSICA SELECIONADA ---
    private void reproduzirMusicaSelecionada() {
        Musica musicaSelecionada = listaMusicas.getSelectionModel().getSelectedItem();

        if (musicaSelecionada == null) {
            return;
        }

        gerenciadorPlaylist.selecionarMusica(musicaSelecionada);
        acaoSelecionarMusica.accept(musicaSelecionada);
    }

    // --- REMOVE A MUSICA SELECIONADA ---
    private void removerMusicaSelecionada() {
        Musica musicaSelecionada = listaMusicas.getSelectionModel().getSelectedItem();

        if (musicaSelecionada == null) {
            return;
        }

        int indiceSelecionado = listaMusicas.getSelectionModel().getSelectedIndex();

        gerenciadorPlaylist.removerMusica(musicaSelecionada);

        if (!gerenciadorPlaylist.estaVazia()) {
            int novoIndice = Math.min(indiceSelecionado, gerenciadorPlaylist.obterQuantidadeMusicas() - 1);

            listaMusicas.getSelectionModel().select(novoIndice);
        }
    }

    // --- REMOVE TODAS AS MUSICAS DA PLAYLIST ---
    private void limparListaReproducao() {
        listaMusicas.getSelectionModel().clearSelection();
        gerenciadorPlaylist.limparPlaylist();
    }

    // --- ATUALIZA A QUANTIDADE DE MUSICAS EXIBIDA ---
    private void atualizarQuantidadeMusicas() {
        int quantidade = gerenciadorPlaylist.obterQuantidadeMusicas();

        String texto = quantidade == 1 ? "1 musica" : quantidade + " musicas";

        rotuloQuantidade.setText(texto);
    }

    // --- ATUALIZA A DISPONIBILIDADE DOS BOTOES ---
    private void atualizarEstadoBotoes() {
        boolean playlistVazia = gerenciadorPlaylist.estaVazia();
        boolean possuiSelecao = listaMusicas.getSelectionModel().getSelectedItem() != null;

        botaoRemover.setDisable(!possuiSelecao);
        botaoLimpar.setDisable(playlistVazia);
    }

    // --- DESTACA A MUSICA ATUAL NA LISTA ---
    public void destacarMusica(Musica musica) {
        if (musica == null) {
            listaMusicas.getSelectionModel().clearSelection();
            return;
        }

        listaMusicas.getSelectionModel().select(musica);
        listaMusicas.scrollTo(musica);
    }

    // --- RETORNA A MUSICA SELECIONADA NA LISTA ---
    public Musica obterMusicaSelecionada() {
        return listaMusicas.getSelectionModel().getSelectedItem();
    }
}

