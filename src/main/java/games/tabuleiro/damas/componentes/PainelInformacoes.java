package games.tabuleiro.damas.componentes;

import games.tabuleiro.damas.modelo.ConfiguracaoPartida;
import games.tabuleiro.damas.modelo.EstadoPartida;
import games.tabuleiro.damas.modelo.Jogador;
import games.tabuleiro.damas.modelo.Movimento;
import games.tabuleiro.damas.util.Animacoes;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class PainelInformacoes extends VBox {

    private final ConfiguracaoPartida   configuracao;
    private final StackPane             iconeVez = new StackPane();
    private final Label                 rotuloVez = new Label();
    private final HBox                  cartaoVez;
    private final ProgressIndicator     indicadorPensando = new ProgressIndicator();
    private final CartaoJogador         cartaoJogador1;
    private final CartaoJogador         cartaoJogador2;
    private final Label                 lblRotuloSituacao = new Label();
    private final Label                 lblRotuloStatus = new Label();
    private final Label                 lblRotuloJogadas = new Label();
    private final Label                 lblRotuloUltimoLance = new Label();

    public PainelInformacoes(ConfiguracaoPartida configuracao) {
        this.configuracao = configuracao;
        getStyleClass().add("painel-info");
        setPrefWidth(300);
        setMinWidth(250);

        Label titulo = new Label("Informações da Partida");
        titulo.getStyleClass().add("titulo-painel");
        Label modo = new Label("Modo: " + configuracao.descricao());
        modo.getStyleClass().add("texto-secundario");

        // Jogador da vez
        Label lblRotuloDaVez = new Label("Vez de");
        lblRotuloDaVez.getStyleClass().add("texto-secundario");
        rotuloVez.getStyleClass().add("rotulo-vez");
        indicadorPensando.setPrefSize(26, 26);
        indicadorPensando.setVisible(false);
        Region espaco = new Region();
        HBox.setHgrow(espaco, Priority.ALWAYS);
        iconeVez.setMinSize(40, 40);
        cartaoVez = new HBox(12, iconeVez, new VBox(2, lblRotuloDaVez, rotuloVez), espaco, indicadorPensando);
        cartaoVez.setAlignment(Pos.CENTER_LEFT);
        cartaoVez.getStyleClass().addAll("cartao", "cartao-vez");

        // Placar
        cartaoJogador1 = new CartaoJogador(Jogador.JOGADOR_1, configuracao.nomeExibicao(Jogador.JOGADOR_1));
        cartaoJogador2 = new CartaoJogador(Jogador.JOGADOR_2, configuracao.nomeExibicao(Jogador.JOGADOR_2));

        // Status
        lblRotuloSituacao.getStyleClass().add("rotulo-situacao");
        lblRotuloStatus.getStyleClass().add("status");
        lblRotuloStatus.setWrapText(true);
        lblRotuloStatus.setMaxWidth(Double.MAX_VALUE);
        lblRotuloStatus.setMinHeight(Region.USE_PREF_SIZE);

        lblRotuloJogadas.getStyleClass().add("texto-secundario");
        lblRotuloUltimoLance.getStyleClass().add("texto-secundario");

        Region expansor = new Region();
        VBox.setVgrow(expansor, Priority.ALWAYS);

        getChildren().addAll(
                titulo, modo,
                criarTituloSecao("JOGADOR ATUAL"), cartaoVez,
                criarTituloSecao("PEÇAS RESTANTES"), cartaoJogador1, cartaoJogador2,
                criarTituloSecao("STATUS DA PARTIDA"), lblRotuloSituacao, lblRotuloStatus,
                lblRotuloJogadas, lblRotuloUltimoLance,
                expansor, criarLegenda());

        atualizarJogadas(0, null);
        atualizarSituacao(EstadoPartida.EM_ANDAMENTO);
        definirStatus("Boa partida!", TipoMensagem.INFORMACAO);
    }

    private Label criarTituloSecao(String texto) {
        Label rotulo = new Label(texto);
        rotulo.getStyleClass().add("titulo-secao");
        return rotulo;
    }

    private VBox criarLegenda() {
        Label lblTitulo = criarTituloSecao("LEGENDA");
        Label lblDestino = new Label("●  Verde: movimento válido");
        lblDestino.getStyleClass().addAll("legenda", "legenda-lblDestino");
        Label lblCaptura = new Label("◯  Vermelho: casa de pouso da lblCaptura");
        lblCaptura.getStyleClass().addAll("legenda", "legenda-lblCaptura");
        Label lblObrigatoria = new Label("▢  Laranja: peça com lblCaptura obrigatória");
        lblObrigatoria.getStyleClass().addAll("legenda", "legenda-lblObrigatoria");
        Label lblUltimo = new Label("▢  Azul: último lance realizado");
        lblUltimo.getStyleClass().addAll("legenda", "legenda-lblUltimo");
        return new VBox(4, lblTitulo, lblDestino, lblCaptura, lblObrigatoria, lblUltimo);
    }

    public void atualizarVez(Jogador jogador) {
        iconeVez.getChildren().setAll(PecaVisual.criarIcone(jogador, false, 38));
        rotuloVez.setText(configuracao.nomeExibicao(jogador));
        cartaoJogador1.setAtivo(jogador == Jogador.JOGADOR_1);
        cartaoJogador2.setAtivo(jogador == Jogador.JOGADOR_2);
        Animacoes.pulsar(iconeVez);
    }

    public void atualizarPlacar(int pecasJogador1, int damasJogador1, int pecasJogador2, int damasJogador2) {
        cartaoJogador1.atualizar(pecasJogador1, damasJogador1);
        cartaoJogador2.atualizar(pecasJogador2, damasJogador2);
    }

    public void atualizarSituacao(EstadoPartida estado) {
        lblRotuloSituacao.setText("Situação: " + estado.getDescricao());
    }

    public void definirStatus(String mensagem, TipoMensagem tipo) {
        lblRotuloStatus.setText(mensagem);
        for (TipoMensagem outro : TipoMensagem.values()) {
            lblRotuloStatus.getStyleClass().remove(outro.getClasseCss());
        }
        lblRotuloStatus.getStyleClass().add(tipo.getClasseCss());
    }

    public void atualizarJogadas(int quantidade, Movimento ultimo) {
        lblRotuloJogadas.setText("Jogadas realizadas: " + quantidade);
        lblRotuloUltimoLance.setText("Último lance: " + (ultimo == null ? "—" : ultimo.getDescricao()));
    }

    public void mostrarPensando(boolean pensando) {
        indicadorPensando.setVisible(pensando);
    }

}
