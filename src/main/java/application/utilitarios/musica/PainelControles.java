package application.utilitarios.musica;

import java.util.Objects;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public final class PainelControles extends VBox {

    private static final double VOLUME_MINIMO = 0.0;
    private static final double VOLUME_MAXIMO = 100.0;
    private static final double VOLUME_INICIAL = 65.0;

    private final ReprodutorMusica  reprodutorMusica;

    private final Slider            barraProgresso;
    private final Slider            barraVolume;

    private final Label             rotuloTempoAtual;
    private final Label             rotuloDuracaoTotal;
    private final Label             rotuloVolume;

    private final Button            botaoAnterior;
    private final Button            botaoReproduzir;
    private final Button            botaoPausar;
    private final Button            botaoProxima;
    private final Button            botaoParar;

    private boolean                 atualizandoBarraProgresso;

    // --- INICIALIZA O PAINEL DE CONTROLES ---
    public PainelControles(ReprodutorMusica reprodutorMusica) {
        this.reprodutorMusica = Objects.requireNonNull(reprodutorMusica, "O reprodutor de música não pode ser nulo.");

        barraProgresso = new Slider();
        barraVolume = new Slider(VOLUME_MINIMO, VOLUME_MAXIMO, VOLUME_INICIAL);

        rotuloTempoAtual = new Label("0:00");
        rotuloDuracaoTotal = new Label("0:00");
        rotuloVolume = new Label("65%");

        botaoAnterior = criarBotaoControle("⏮", "Música anterior");
        botaoReproduzir = criarBotaoControle("▶", "Reproduzir");
        botaoPausar = criarBotaoControle("Ⅱ", "Pausar");
        botaoProxima = criarBotaoControle("⏭", "Próxima música");
        botaoParar = criarBotaoControle("■", "Parar");

        atualizandoBarraProgresso = false;

        configurarPainel();
        configurarBarraProgresso();
        configurarBarraVolume();
        configurarBotoes();
        configurarEventos();
        montarInterface();
    }

    // --- CONFIGURA A APARÊNCIA DO PAINEL ---
    private void configurarPainel() {
        getStyleClass().add("painel-controles");
        setAlignment(Pos.CENTER);
        setSpacing(12.0);
        setFillWidth(true);
    }

    // --- CONFIGURA A BARRA DE PROGRESSO ---
    private void configurarBarraProgresso() {
        barraProgresso.setMin(0.0);
        barraProgresso.setMax(1.0);
        barraProgresso.setValue(0.0);
        barraProgresso.setBlockIncrement(5.0);
        barraProgresso.setDisable(true);
        barraProgresso.getStyleClass().add("barra-progresso");

        HBox.setHgrow(barraProgresso, Priority.ALWAYS);
    }

    // --- CONFIGURA A BARRA DE VOLUME ---
    private void configurarBarraVolume() {
        barraVolume.setBlockIncrement(5.0);
        barraVolume.setPrefWidth(150.0);
        barraVolume.getStyleClass().add("barra-volume");

        reprodutorMusica.definirVolume(VOLUME_INICIAL / 100.0);
    }

    // --- CONFIGURA OS BOTÕES DO REPRODUTOR ---
    private void configurarBotoes() {
        botaoReproduzir.getStyleClass().add("botao-principal");

        botaoAnterior.setFocusTraversable(false);
        botaoReproduzir.setFocusTraversable(false);
        botaoPausar.setFocusTraversable(false);
        botaoProxima.setFocusTraversable(false);
        botaoParar.setFocusTraversable(false);
    }

    // --- CONFIGURA OS EVENTOS DOS BOTÕES E DAS BARRAS ---
    private void configurarEventos() {
        botaoAnterior.setOnAction(evento -> reprodutorMusica.reproduzirMusicaAnterior());
        botaoReproduzir.setOnAction(evento -> reprodutorMusica.reproduzir());
        botaoPausar.setOnAction(evento -> reprodutorMusica.pausar());
        botaoProxima.setOnAction(evento -> reprodutorMusica.reproduzirProximaMusica());
        botaoParar.setOnAction(evento -> reprodutorMusica.parar());
        barraVolume.valueProperty().addListener((observavel, valorAnterior, valorAtual) -> {
                    double volumePercentual = valorAtual.doubleValue();
                    double volumeNormalizado = volumePercentual / 100.0;

                    reprodutorMusica.definirVolume(volumeNormalizado);
                    rotuloVolume.setText(Math.round(volumePercentual) + "%");
                }
        );

        barraProgresso.valueChangingProperty().addListener(
                (observavel, estavaAlterando, estaAlterando) -> {
                    if (!estaAlterando && !atualizandoBarraProgresso) {
                        alterarPosicaoReproducao();
                    }
                }
        );

        barraProgresso.setOnMouseReleased(evento -> {
            if (!atualizandoBarraProgresso) {
                alterarPosicaoReproducao();
            }
        });
    }

    // --- MONTA OS COMPONENTES VISUAIS DO PAINEL ---
    private void montarInterface() {
        getChildren().addAll(criarFaixaProgresso(), criarFaixaBotoes(), criarFaixaVolume());
    }

    // --- CRIA A FAIXA DA BARRA DE PROGRESSO ---
    private HBox criarFaixaProgresso() {
        rotuloTempoAtual.getStyleClass().add("tempo-reproducao");
        rotuloDuracaoTotal.getStyleClass().add("tempo-reproducao");

        HBox faixaProgresso = new HBox(10.0, rotuloTempoAtual, barraProgresso, rotuloDuracaoTotal);

        faixaProgresso.setAlignment(Pos.CENTER);
        faixaProgresso.setFillHeight(true);

        return faixaProgresso;
    }

    // --- CRIA A FAIXA DOS BOTÕES DE REPRODUÇÃO ---
    private HBox criarFaixaBotoes() {
        HBox faixaBotoes = new HBox(14.0, botaoAnterior, botaoReproduzir, botaoPausar, botaoProxima, botaoParar);

        faixaBotoes.setAlignment(Pos.CENTER);

        return faixaBotoes;
    }

    // --- CRIA A FAIXA DO CONTROLE DE VOLUME ---
    private HBox criarFaixaVolume() {
        Label lblSimboloVolume = new Label("🔊");
        lblSimboloVolume.getStyleClass().add("simbolo-volume");
        rotuloVolume.getStyleClass().add("valor-volume");

        Region espacadorEsquerdo = new Region();
        Region espacadorDireito = new Region();

        HBox.setHgrow(espacadorEsquerdo, Priority.ALWAYS);
        HBox.setHgrow(espacadorDireito, Priority.ALWAYS);

        HBox faixaVolume = new HBox(10.0, espacadorEsquerdo, lblSimboloVolume, barraVolume, rotuloVolume, espacadorDireito);

        faixaVolume.setAlignment(Pos.CENTER);

        return faixaVolume;
    }

    // --- CRIA UM BOTÃO REUTILIZÁVEL DO REPRODUTOR ---
    private Button criarBotaoControle(String texto, String descricao) {
        Button botao = new Button(texto);

        botao.getStyleClass().add("botao-controle");
        botao.setTooltip(new Tooltip(descricao));
        botao.setAccessibleText(descricao);
        botao.setMinSize(44.0, 44.0);
        botao.setPrefSize(44.0, 44.0);

        return botao;
    }

    // --- ALTERA A POSIÇÃO DA REPRODUÇÃO ---
    private void alterarPosicaoReproducao() {
        if (barraProgresso.isDisable()) {
            return;
        }

        reprodutorMusica.alterarPosicao(barraProgresso.getValue());
    }

    // --- ATUALIZA O TEMPO E A BARRA DE PROGRESSO ---
    public void atualizarTempo(Duration tempoAtual, Duration duracaoTotal) {
        Duration tempoSeguro = normalizarDuracao(tempoAtual);
        Duration duracaoSegura = normalizarDuracao(duracaoTotal);

        atualizandoBarraProgresso = true;

        double totalSegundos = duracaoSegura.toSeconds();
        double atualSegundos = tempoSeguro.toSeconds();
        boolean possuiDuracao = totalSegundos > 0.0;

        barraProgresso.setDisable(!possuiDuracao);
        barraProgresso.setMax(possuiDuracao ? totalSegundos : 1.0);

        if (!barraProgresso.isValueChanging()) {
            barraProgresso.setValue(possuiDuracao ? Math.min(atualSegundos, totalSegundos) : 0.0);
        }

        rotuloTempoAtual.setText(formatarDuracao(tempoSeguro));
        rotuloDuracaoTotal.setText(formatarDuracao(duracaoSegura));

        atualizandoBarraProgresso = false;
    }

    // --- ATUALIZA OS CONTROLES CONFORME O ESTADO DO REPRODUTOR ---
    public void atualizarEstado(EstadoReproducao estadoReproducao) {
        if (estadoReproducao == null) {
            return;
        }

        boolean carregando = estadoReproducao.estaCarregando();
        boolean reproduzindo = estadoReproducao.estaReproduzindo();
        boolean pausado = estadoReproducao.estaPausado();

        botaoAnterior.setDisable(carregando);
        botaoProxima.setDisable(carregando);
        botaoParar.setDisable(carregando);
        botaoReproduzir.setDisable(carregando || reproduzindo);
        botaoPausar.setDisable(carregando || pausado || !reproduzindo);
    }

    // --- REINICIA A BARRA E OS TEXTOS DE TEMPO ---
    public void reiniciarProgresso() {
        atualizandoBarraProgresso = true;

        barraProgresso.setValue(0.0);
        barraProgresso.setMax(1.0);
        barraProgresso.setDisable(true);
        rotuloTempoAtual.setText("0:00");
        rotuloDuracaoTotal.setText("0:00");

        atualizandoBarraProgresso = false;
    }

    // --- NORMALIZA UMA DURAÇÃO INVÁLIDA ---
    private Duration normalizarDuracao(Duration duracao) {
        if (duracao == null || duracao.isUnknown() || duracao.isIndefinite() || duracao.lessThan(Duration.ZERO)) {
            return Duration.ZERO;
        }

        return duracao;
    }

    // --- FORMATA A DURAÇÃO NO PADRÃO HORAS MINUTOS E SEGUNDOS ---
    private String formatarDuracao(Duration duracao) {
        long segundosTotais = (long) Math.floor(duracao.toSeconds());

        long horas = segundosTotais / 3600;
        long minutos = (segundosTotais % 3600) / 60;
        long segundos = segundosTotais % 60;

        if (horas > 0) {
            return String.format("%d:%02d:%02d", horas, minutos, segundos);
        }

        return String.format("%d:%02d", minutos, segundos);
    }
}
