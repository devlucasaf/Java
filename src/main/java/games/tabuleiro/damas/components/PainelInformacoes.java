package games.tabuleiro.damas.components;

import games.tabuleiro.damas.model.ConfiguracaoPartida;
import games.tabuleiro.damas.model.EstadoPartida;
import games.tabuleiro.damas.model.Jogador;
import games.tabuleiro.damas.model.Movimento;
import games.tabuleiro.damas.util.Animacoes;
import games.tabuleiro.damas.util.Estilos;
import games.tabuleiro.damas.util.Paleta;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class PainelInformacoes extends VBox {

    private final ConfiguracaoPartida   configuracao;
    private final StackPane             iconeVez = new StackPane();
    private final Label                 lblRotuloVez = new Label();
    private final HBox                  cartaoVez;
    private final Arc                   indicadorPensando = new Arc(0, 0, 11, 11, 90, 270);
    private final RotateTransition      giroIndicador = new RotateTransition(Duration.millis(900), indicadorPensando);
    private final CartaoJogador         cartaoJogador1;
    private final CartaoJogador         cartaoJogador2;
    private final Label                 lblRotuloSituacao = new Label();
    private final Label                 lblRotuloStatus = new Label();
    private final Label                 lblRotuloJogadas = new Label();
    private final Label                 lblRotuloUltimoLance = new Label();

    public PainelInformacoes(ConfiguracaoPartida configuracao) {
        super(10);
        this.configuracao = configuracao;
        Estilos.painel(this, 16, new Insets(18));
        setPrefWidth(300);
        setMinWidth(250);

        Label lblTitulo = new Label("Informações da Partida");
        Estilos.rotulo(lblTitulo, 19, FontWeight.BOLD, Paleta.TEXTO);
        Label lblModo = new Label("Modo: " + configuracao.descricao());
        Estilos.textoSecundario(lblModo);

        // Jogador da vez
        Label lblRotuloDaVez = new Label("Vez de");
        Estilos.textoSecundario(lblRotuloDaVez);
        Estilos.rotulo(lblRotuloVez, 18, FontWeight.BOLD, Paleta.TEXTO);
        configurarIndicadorPensando();
        Region espaco = new Region();
        HBox.setHgrow(espaco, Priority.ALWAYS);
        iconeVez.setMinSize(40, 40);
        cartaoVez = new HBox(12, iconeVez, new VBox(2, lblRotuloDaVez, lblRotuloVez), espaco, indicadorPensando);
        cartaoVez.setAlignment(Pos.CENTER_LEFT);
        Estilos.cartao(cartaoVez, false);

        // Placar
        cartaoJogador1 = new CartaoJogador(Jogador.JOGADOR_1, configuracao.nomeExibicao(Jogador.JOGADOR_1));
        cartaoJogador2 = new CartaoJogador(Jogador.JOGADOR_2, configuracao.nomeExibicao(Jogador.JOGADOR_2));

        // Status
        Estilos.rotulo(lblRotuloSituacao, 14, FontWeight.BOLD, Paleta.TEXTO);
        lblRotuloStatus.setWrapText(true);
        lblRotuloStatus.setMaxWidth(Double.MAX_VALUE);
        lblRotuloStatus.setMinHeight(Region.USE_PREF_SIZE);

        Estilos.textoSecundario(lblRotuloJogadas);
        Estilos.textoSecundario(lblRotuloUltimoLance);

        Region expansor = new Region();
        VBox.setVgrow(expansor, Priority.ALWAYS);

        getChildren().addAll(
                lblTitulo, lblModo,
                criarTituloSecao("JOGADOR ATUAL"), cartaoVez,
                criarTituloSecao("PEÇAS RESTANTES"), cartaoJogador1, cartaoJogador2,
                criarTituloSecao("STATUS DA PARTIDA"), lblRotuloSituacao, lblRotuloStatus,
                lblRotuloJogadas, lblRotuloUltimoLance,
                expansor, criarLegenda());

        atualizarJogadas(0, null);
        atualizarSituacao(EstadoPartida.EM_ANDAMENTO);
        definirStatus("Boa partida!", TipoMensagem.INFORMACAO);
    }

    private void configurarIndicadorPensando() {
        indicadorPensando.setFill(Color.TRANSPARENT);
        indicadorPensando.setStroke(Paleta.AMARELO);
        indicadorPensando.setStrokeWidth(3);
        indicadorPensando.setStrokeLineCap(StrokeLineCap.ROUND);
        indicadorPensando.setVisible(false);
        giroIndicador.setByAngle(360);
        giroIndicador.setCycleCount(Animation.INDEFINITE);
        giroIndicador.setInterpolator(Interpolator.LINEAR);
    }

    private Label criarTituloSecao(String texto) {
        Label lblRotulo = new Label(texto);
        Estilos.tituloSecao(lblRotulo);
        return lblRotulo;
    }

    private VBox criarLegenda() {
        Label lblTitulo = criarTituloSecao("LEGENDA");
        Label lblDestino = criarItemLegenda("●  Verde: movimento válido", Paleta.VERDE);
        Label lblCaptura = criarItemLegenda("◯  Vermelho: casa de pouso da captura", Color.web("#ff6b6b"));
        Label lblObrigatoria = criarItemLegenda("▢  Laranja: peça com captura obrigatória", Color.web("#ffa726"));
        Label lblUltimo = criarItemLegenda("▢  Azul: último lance realizado", Color.web("#6aa7ff"));
        return new VBox(4, lblTitulo, lblDestino, lblCaptura, lblObrigatoria, lblUltimo);
    }

    private Label criarItemLegenda(String texto, Color cor) {
        Label lblItem = new Label(texto);
        Estilos.rotulo(lblItem, 12, FontWeight.NORMAL, cor);
        return lblItem;
    }

    public void atualizarVez(Jogador jogador) {
        iconeVez.getChildren().setAll(PecaVisual.criarIcone(jogador, false, 38));
        lblRotuloVez.setText(configuracao.nomeExibicao(jogador));
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
        Estilos.caixaStatus(lblRotuloStatus, tipo.getCor());
    }

    public void atualizarJogadas(int quantidade, Movimento ultimo) {
        lblRotuloJogadas.setText("Jogadas realizadas: " + quantidade);
        lblRotuloUltimoLance.setText("Último lance: " + (ultimo == null ? "—" : ultimo.getDescricao()));
    }

    public void mostrarPensando(boolean pensando) {
        indicadorPensando.setVisible(pensando);

        if (pensando) {
            giroIndicador.play();
        } else {
            giroIndicador.stop();
        }
    }

}
