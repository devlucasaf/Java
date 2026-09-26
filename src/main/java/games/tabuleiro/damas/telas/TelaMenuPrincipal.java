package games.tabuleiro.damas.telas;

import games.tabuleiro.damas.componentes.PecaVisual;
import games.tabuleiro.damas.modelo.ConfiguracaoPartida;
import games.tabuleiro.damas.modelo.Jogador;
import games.tabuleiro.damas.modelo.ModoJogo;
import games.tabuleiro.damas.modelo.NivelDificuldade;
import games.tabuleiro.damas.util.Animacoes;
import games.tabuleiro.damas.util.Dialogos;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

/**
 * Tela inicial: escolha do modo de jogo, dificuldade do BOT, regras e saída.
 */
public class TelaMenuPrincipal extends StackPane {

    private static final String TEXTO_REGRAS = """
            • O Jogador 1 (peças claras) sempre inicia a partida.
            • As peças andam na diagonal, uma casa por vez, sempre para frente e apenas pelas casas pretas.
            • Para capturar, salte sobre a peça adversária até a casa vazia logo atrás dela. \
            Peças comuns podem capturar para frente e para trás.
            • A captura é obrigatória. Havendo mais de uma opção, deve-se escolher a sequência \
            que captura o maior número de peças.
            • Várias capturas podem ser feitas em sequência na mesma jogada.
            • Ao terminar a jogada na última linha do adversário, a peça é promovida a dama.
            • A dama anda quantas casas quiser na diagonal, para frente e para trás, e pode capturar à distância.
            • Vence quem capturar todas as peças adversárias ou deixar o adversário sem movimentos válidos.
            • Após 20 lances seguidos de cada jogador apenas com damas e sem capturas, a partida termina empatada.

            Como jogar: clique em uma de suas peças para selecioná-la e depois em uma das casas destacadas.""";

    private final ToggleGroup grupoDificuldade = new ToggleGroup();

    public TelaMenuPrincipal(Consumer<ConfiguracaoPartida> aoIniciarPartida, Runnable aoSair) {
        getStyleClass().add("raiz-damas");

        HBox decoracao = new HBox(14,
                PecaVisual.criarIcone(Jogador.JOGADOR_1, false, 52),
                PecaVisual.criarIcone(Jogador.JOGADOR_2, false, 52),
                PecaVisual.criarIcone(Jogador.JOGADOR_1, true, 52),
                PecaVisual.criarIcone(Jogador.JOGADOR_2, true, 52));
        decoracao.setAlignment(Pos.CENTER);

        Label titulo = new Label("DAMAS");
        titulo.getStyleClass().add("titulo-menu");
        Label subtitulo = new Label("O clássico jogo de tabuleiro, com as regras oficiais brasileiras");
        subtitulo.getStyleClass().add("subtitulo-menu");

        Label rotuloModo = new Label("ESCOLHA O MODO DE JOGO");
        rotuloModo.getStyleClass().add("titulo-secao");

        Button botaoJogadorVsJogador = criarBotaoModo(ModoJogo.JOGADOR_VS_JOGADOR.getDescricao(),
                "Dois jogadores se revezam no mesmo computador.");
        Button botaoJogadorVsBot = criarBotaoModo(ModoJogo.JOGADOR_VS_BOT.getDescricao(),
                "Enfrente a inteligência artificial (algoritmo Minimax).");
        botaoJogadorVsJogador.setOnAction(evento -> aoIniciarPartida.accept(
                new ConfiguracaoPartida(ModoJogo.JOGADOR_VS_JOGADOR, nivelSelecionado())));
        botaoJogadorVsBot.setOnAction(evento -> aoIniciarPartida.accept(
                new ConfiguracaoPartida(ModoJogo.JOGADOR_VS_BOT, nivelSelecionado())));

        Button botaoRegras = new Button("Como Jogar");
        botaoRegras.getStyleClass().add("botao");
        botaoRegras.setOnAction(evento -> Dialogos.informar(getScene().getWindow(),
                "Como Jogar", "Regras das Damas", TEXTO_REGRAS));
        Button botaoSair = new Button("Sair");
        botaoSair.getStyleClass().addAll("botao", "botao-perigo");
        botaoSair.setOnAction(evento -> aoSair.run());

        HBox rodape = new HBox(10, botaoRegras, botaoSair);
        rodape.setAlignment(Pos.CENTER);
        for (Button botao : new Button[]{botaoRegras, botaoSair}) {
            botao.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(botao, Priority.ALWAYS);
            Animacoes.aplicarEfeitoHover(botao, 1.04);
        }

        VBox cartao = new VBox(14, rotuloModo, botaoJogadorVsJogador, botaoJogadorVsBot,
                criarSeletorDificuldade(), rodape);
        cartao.getStyleClass().add("cartao-menu");
        cartao.setMaxWidth(480);

        Label informacao = new Label("Jogador 1: peças claras (inicia a partida)   •   Jogador 2: peças vermelhas");
        informacao.getStyleClass().add("texto-secundario");

        VBox conteudo = new VBox(16, decoracao, titulo, subtitulo, cartao, informacao);
        conteudo.setAlignment(Pos.CENTER);
        conteudo.setPadding(new Insets(28));

        ScrollPane rolagem = new ScrollPane(conteudo);
        rolagem.setFitToWidth(true);
        rolagem.setFitToHeight(true);
        rolagem.getStyleClass().add("rolagem-transparente");
        getChildren().add(rolagem);

        Animacoes.surgir(conteudo);
    }

    private Button criarBotaoModo(String titulo, String descricao) {
        Label rotuloTitulo = new Label(titulo);
        rotuloTitulo.getStyleClass().add("titulo-botao-modo");
        Label rotuloDescricao = new Label(descricao);
        rotuloDescricao.getStyleClass().add("descricao-botao-modo");
        rotuloDescricao.setWrapText(true);

        Button botao = new Button();
        botao.setGraphic(new VBox(4, rotuloTitulo, rotuloDescricao));
        botao.getStyleClass().add("botao-modo");
        botao.setMaxWidth(Double.MAX_VALUE);
        Animacoes.aplicarEfeitoHover(botao, 1.02);
        return botao;
    }

    private HBox criarSeletorDificuldade() {
        Label rotulo = new Label("Dificuldade do BOT:");
        rotulo.getStyleClass().add("texto-secundario");
        HBox seletor = new HBox(8, rotulo);
        seletor.setAlignment(Pos.CENTER_LEFT);

        for (NivelDificuldade nivel : NivelDificuldade.values()) {
            ToggleButton opcao = new ToggleButton(nivel.getDescricao());
            opcao.setUserData(nivel);
            opcao.setToggleGroup(grupoDificuldade);
            opcao.getStyleClass().add("alternador");
            opcao.setSelected(nivel == NivelDificuldade.MEDIO);
            seletor.getChildren().add(opcao);
        }
        // Mantém sempre uma dificuldade escolhida.
        grupoDificuldade.selectedToggleProperty().addListener((observavel, anterior, atual) -> {
            if (atual == null && anterior != null) {
                anterior.setSelected(true);
            }
        });
        return seletor;
    }

    private NivelDificuldade nivelSelecionado() {
        return grupoDificuldade.getSelectedToggle() == null
                ? NivelDificuldade.MEDIO
                : (NivelDificuldade) grupoDificuldade.getSelectedToggle().getUserData();
    }
}
