package games.tabuleiro.damas.componentes;

import games.tabuleiro.damas.modelo.Movimento;
import games.tabuleiro.damas.modelo.Peca;
import games.tabuleiro.damas.modelo.Posicao;
import games.tabuleiro.damas.modelo.Tabuleiro;
import games.tabuleiro.damas.util.Constantes;
import games.tabuleiro.damas.util.Paleta;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Bounds;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Tabuleiro gráfico e redimensionável: desenha casas, coordenadas e peças,
 * trata cliques e hover do mouse e executa as animações das jogadas.
 * <p>
 * O componente não conhece as regras do jogo; ele apenas exibe o que o controlador pede
 * e repassa os cliques por meio de {@link #setAoClicarCasa(Consumer)}.
 */
public class TabuleiroVisual extends Region {

    /** Espaço reservado em volta do tabuleiro para as coordenadas, em "casas". */
    private static final double MARGEM_EM_CASAS = 0.55;
    private static final int TAMANHO = Tabuleiro.TAMANHO;

    private final Rectangle moldura = new Rectangle();
    private final CasaVisual[][] casas = new CasaVisual[TAMANHO][TAMANHO];
    private final Text[][] rotulosColunas = new Text[2][TAMANHO]; // [0] topo, [1] base
    private final Text[][] rotulosLinhas = new Text[2][TAMANHO];  // [0] esquerda, [1] direita
    private final Group camadaPecas = new Group();
    private final Group camadaEfeitos = new Group();

    private final Map<Posicao, PecaVisual> pecas = new HashMap<>();
    private final Set<Posicao> casasInterativas = new HashSet<>();
    private final List<Posicao> casasUltimoMovimento = new ArrayList<>();
    private final List<Animation> animacoesAtivas = new ArrayList<>();

    private Consumer<Posicao> aoClicarCasa = posicao -> { };
    private PecaVisual pecaSelecionada;
    private double tamanhoCasa;
    private double origemX;
    private double origemY;
    private double tamanhoFonteAtual = -1;

    public TabuleiroVisual() {
        getStyleClass().add("tabuleiro-visual");

        moldura.setFill(Paleta.gradienteMoldura());
        moldura.setStroke(Paleta.MOLDURA_BORDA);
        moldura.setStrokeWidth(1.5);
        moldura.setArcWidth(18);
        moldura.setArcHeight(18);
        moldura.setEffect(new DropShadow(28, 0, 10, Color.rgb(0, 0, 0, 0.55)));
        getChildren().add(moldura);

        for (int linha = 0; linha < TAMANHO; linha++) {
            for (int coluna = 0; coluna < TAMANHO; coluna++) {
                CasaVisual casa = new CasaVisual(new Posicao(linha, coluna));
                casa.setOnMouseClicked(evento -> aoClicarCasa.accept(casa.getPosicao()));
                casa.setOnMouseEntered(evento -> atualizarHover(casa, true));
                casa.setOnMouseExited(evento -> atualizarHover(casa, false));
                casas[linha][coluna] = casa;
                getChildren().add(casa);
            }
        }

        for (int i = 0; i < TAMANHO; i++) {
            String letra = String.valueOf((char) ('a' + i));
            String numero = String.valueOf(TAMANHO - i);
            for (int lado = 0; lado < 2; lado++) {
                rotulosColunas[lado][i] = criarRotulo(letra);
                rotulosLinhas[lado][i] = criarRotulo(numero);
            }
        }

        camadaPecas.setMouseTransparent(true);
        camadaEfeitos.setMouseTransparent(true);
        getChildren().addAll(camadaPecas, camadaEfeitos);

        setMinSize(260, 260);
        setPrefSize(680, 680);
    }

    private Text criarRotulo(String texto) {
        Text rotulo = new Text(texto);
        rotulo.setFill(Paleta.COORDENADA);
        rotulo.setTextOrigin(VPos.CENTER);
        rotulo.setMouseTransparent(true);
        getChildren().add(rotulo);
        return rotulo;
    }

    public void setAoClicarCasa(Consumer<Posicao> aoClicarCasa) {
        this.aoClicarCasa = aoClicarCasa == null ? posicao -> { } : aoClicarCasa;
    }

    // ------------------------------------------------------------------ layout responsivo

    @Override
    protected void layoutChildren() {
        double largura = getWidth() - snappedLeftInset() - snappedRightInset();
        double altura = getHeight() - snappedTopInset() - snappedBottomInset();
        double unidades = TAMANHO + 2 * MARGEM_EM_CASAS;
        tamanhoCasa = Math.floor(Math.min(largura, altura) / unidades);
        if (tamanhoCasa <= 0) {
            return;
        }

        double lado = tamanhoCasa * TAMANHO;
        double margem = tamanhoCasa * MARGEM_EM_CASAS;
        origemX = snappedLeftInset() + Math.round((largura - lado) / 2);
        origemY = snappedTopInset() + Math.round((altura - lado) / 2);

        moldura.setX(origemX - margem);
        moldura.setY(origemY - margem);
        moldura.setWidth(lado + 2 * margem);
        moldura.setHeight(lado + 2 * margem);

        for (int linha = 0; linha < TAMANHO; linha++) {
            for (int coluna = 0; coluna < TAMANHO; coluna++) {
                casas[linha][coluna].resizeRelocate(origemX + coluna * tamanhoCasa,
                        origemY + linha * tamanhoCasa, tamanhoCasa, tamanhoCasa);
            }
        }

        posicionarCoordenadas(lado, margem);

        for (Map.Entry<Posicao, PecaVisual> entrada : pecas.entrySet()) {
            if (!entrada.getValue().isAnimando()) {
                posicionarPeca(entrada.getValue(), entrada.getKey());
            }
        }
    }

    private void posicionarCoordenadas(double lado, double margem) {
        double tamanhoFonte = Math.max(10, tamanhoCasa * 0.25);
        if (Math.abs(tamanhoFonte - tamanhoFonteAtual) > 0.5) {
            Font fonte = Font.font("Segoe UI", FontWeight.BOLD, tamanhoFonte);
            for (int lado2 = 0; lado2 < 2; lado2++) {
                for (int i = 0; i < TAMANHO; i++) {
                    rotulosColunas[lado2][i].setFont(fonte);
                    rotulosLinhas[lado2][i].setFont(fonte);
                }
            }
            tamanhoFonteAtual = tamanhoFonte;
        }

        for (int i = 0; i < TAMANHO; i++) {
            double centro = (i + 0.5) * tamanhoCasa;
            centralizar(rotulosColunas[0][i], origemX + centro, origemY - margem / 2);
            centralizar(rotulosColunas[1][i], origemX + centro, origemY + lado + margem / 2);
            centralizar(rotulosLinhas[0][i], origemX - margem / 2, origemY + centro);
            centralizar(rotulosLinhas[1][i], origemX + lado + margem / 2, origemY + centro);
        }
    }

    private static void centralizar(Text texto, double centroX, double centroY) {
        Bounds limites = texto.getLayoutBounds();
        texto.setLayoutX(centroX - limites.getMinX() - limites.getWidth() / 2);
        texto.setLayoutY(centroY);
    }

    private void posicionarPeca(PecaVisual peca, Posicao posicao) {
        peca.setTamanho(tamanhoCasa);
        peca.setLayoutX(centroX(posicao) - PecaVisual.TAMANHO_BASE / 2);
        peca.setLayoutY(centroY(posicao) - PecaVisual.TAMANHO_BASE / 2);
    }

    private double centroX(Posicao posicao) {
        return origemX + (posicao.coluna() + 0.5) * tamanhoCasa;
    }

    private double centroY(Posicao posicao) {
        return origemY + (posicao.linha() + 0.5) * tamanhoCasa;
    }

    private CasaVisual casa(Posicao posicao) {
        return casas[posicao.linha()][posicao.coluna()];
    }

    // ------------------------------------------------------------------ peças

    /** Remove todas as peças e monta novamente a partir do tabuleiro, com animação de entrada. */
    public void reconstruir(Tabuleiro tabuleiro) {
        cancelarAnimacoes();
        camadaPecas.getChildren().clear();
        pecas.clear();
        pecaSelecionada = null;

        int ordem = 0;
        for (int linha = 0; linha < TAMANHO; linha++) {
            for (int coluna = 0; coluna < TAMANHO; coluna++) {
                Peca peca = tabuleiro.getPeca(linha, coluna);
                if (peca != null) {
                    PecaVisual visual = adicionarPeca(new Posicao(linha, coluna), peca);
                    visual.animacaoEntrada(Duration.millis(ordem++ * 18)).play();
                }
            }
        }
        requestLayout();
    }

    /**
     * Ajusta as peças exibidas ao estado do tabuleiro, preservando as que já estão corretas.
     */
    public void sincronizar(Tabuleiro tabuleiro) {
        Iterator<Map.Entry<Posicao, PecaVisual>> iterador = pecas.entrySet().iterator();
        while (iterador.hasNext()) {
            Map.Entry<Posicao, PecaVisual> entrada = iterador.next();
            Peca peca = tabuleiro.getPeca(entrada.getKey());
            PecaVisual visual = entrada.getValue();
            if (peca == null || peca.getJogador() != visual.getJogador() || peca.isDama() != visual.isDama()) {
                camadaPecas.getChildren().remove(visual);
                iterador.remove();
            }
        }
        for (int linha = 0; linha < TAMANHO; linha++) {
            for (int coluna = 0; coluna < TAMANHO; coluna++) {
                Posicao posicao = new Posicao(linha, coluna);
                Peca peca = tabuleiro.getPeca(posicao);
                if (peca != null && !pecas.containsKey(posicao)) {
                    adicionarPeca(posicao, peca);
                }
            }
        }
        requestLayout();
    }

    private PecaVisual adicionarPeca(Posicao posicao, Peca peca) {
        PecaVisual visual = new PecaVisual(peca.getJogador(), peca.isDama());
        pecas.put(posicao, visual);
        camadaPecas.getChildren().add(visual);
        if (tamanhoCasa > 0) {
            posicionarPeca(visual, posicao);
        }
        return visual;
    }

    // ------------------------------------------------------------------ destaques

    /** Mostra o anel de seleção na peça da posição informada ({@code null} remove a seleção). */
    public void definirPecaSelecionada(Posicao posicao) {
        if (pecaSelecionada != null) {
            pecaSelecionada.setSelecionada(false);
        }
        pecaSelecionada = posicao == null ? null : pecas.get(posicao);
        if (pecaSelecionada != null) {
            pecaSelecionada.setSelecionada(true);
        }
    }

    public void destacarSelecao(Posicao posicao) {
        casa(posicao).setSelecionada(true);
    }

    public void destacarDestinos(Collection<Posicao> destinos, boolean captura) {
        for (Posicao destino : destinos) {
            casa(destino).setDestino(true, captura);
        }
    }

    public void destacarCapturasObrigatorias(Collection<Posicao> posicoes) {
        for (Posicao posicao : posicoes) {
            casa(posicao).setCapturaObrigatoria(true);
        }
    }

    public void limparDestaquesInterativos() {
        for (CasaVisual[] linha : casas) {
            for (CasaVisual casa : linha) {
                casa.limparDestaquesInterativos();
            }
        }
    }

    /** Define as casas clicáveis (cursor de mão e efeito de hover). */
    public void definirCasasInterativas(Collection<Posicao> posicoes) {
        casasInterativas.clear();
        casasInterativas.addAll(posicoes);
        for (CasaVisual[] linha : casas) {
            for (CasaVisual casa : linha) {
                atualizarHover(casa, casa.isHover());
            }
        }
    }

    private void atualizarHover(CasaVisual casa, boolean mouseSobre) {
        boolean interativa = casasInterativas.contains(casa.getPosicao());
        casa.setCursor(interativa ? Cursor.HAND : Cursor.DEFAULT);
        casa.setHoverAtivo(mouseSobre && interativa);
        PecaVisual peca = pecas.get(casa.getPosicao());
        if (peca != null) {
            peca.setElevada(mouseSobre && interativa);
        }
    }

    public void destacarUltimoMovimento(Movimento movimento) {
        limparUltimoMovimento();
        casasUltimoMovimento.add(movimento.getOrigem());
        casasUltimoMovimento.addAll(movimento.getCaminho());
        for (Posicao posicao : casasUltimoMovimento) {
            casa(posicao).setUltimoMovimento(true);
        }
    }

    public void limparUltimoMovimento() {
        for (Posicao posicao : casasUltimoMovimento) {
            casa(posicao).setUltimoMovimento(false);
        }
        casasUltimoMovimento.clear();
    }

    /** Feedback de jogada inválida: a casa pisca em vermelho e a peça (se houver) treme. */
    public void sinalizarErro(Posicao posicao) {
        casa(posicao).piscarErro();
        PecaVisual peca = pecas.get(posicao);
        if (peca != null && !peca.isAnimando()) {
            peca.sacudir();
        }
    }

    // ------------------------------------------------------------------ animações de jogada

    /**
     * Anima um único passo de uma jogada. Se houver peça capturada neste passo,
     * ela é marcada (desbotada) no meio do salto e removida apenas ao concluir a jogada.
     */
    public void animarPasso(Posicao de, Posicao para, Posicao capturada, Runnable aoTerminar) {
        PecaVisual peca = pecas.get(de);
        if (peca == null) {
            aoTerminar.run();
            return;
        }
        peca.toFront();
        peca.setAnimando(true);

        int distancia = Math.abs(para.linha() - de.linha());
        Duration duracao = Duration.millis(Constantes.DURACAO_PASSO_MS
                + Constantes.DURACAO_PASSO_EXTRA_POR_CASA_MS * (distancia - 1));

        TranslateTransition deslocamento = new TranslateTransition(duracao, peca);
        deslocamento.setFromX(0);
        deslocamento.setFromY(0);
        deslocamento.setToX(centroX(para) - centroX(de));
        deslocamento.setToY(centroY(para) - centroY(de));
        deslocamento.setInterpolator(Interpolator.EASE_BOTH);

        ParallelTransition passo = new ParallelTransition(deslocamento);
        if (capturada != null) {
            passo.getChildren().add(peca.animacaoSalto(duracao));
            PauseTransition meioDoSalto = new PauseTransition(duracao.divide(2));
            meioDoSalto.setOnFinished(evento -> {
                PecaVisual alvo = pecas.get(capturada);
                if (alvo != null) {
                    alvo.marcarCapturada();
                }
                efeitoAnel(capturada, Paleta.IMPACTO_CAPTURA, 2.6);
            });
            passo.getChildren().add(meioDoSalto);
        }

        executar(passo, () -> {
            pecas.remove(de);
            pecas.put(para, peca);
            peca.setTranslateX(0);
            peca.setTranslateY(0);
            peca.setAnimando(false);
            posicionarPeca(peca, para);
            aoTerminar.run();
        });
    }

    /** Anima todos os passos de uma jogada em sequência (usado nas jogadas do BOT). */
    public void animarMovimento(Movimento movimento, Runnable aoTerminar) {
        animarPassos(movimento, 0, movimento.getOrigem(), aoTerminar);
    }

    private void animarPassos(Movimento movimento, int indice, Posicao atual, Runnable aoTerminar) {
        if (indice >= movimento.getCaminho().size()) {
            aoTerminar.run();
            return;
        }
        Posicao proxima = movimento.getCaminho().get(indice);
        animarPasso(atual, proxima, movimento.getCapturaDoPasso(indice), () -> {
            if (indice + 1 < movimento.getCaminho().size()) {
                PauseTransition pausa = new PauseTransition(Duration.millis(Constantes.PAUSA_ENTRE_PASSOS_MS));
                executar(pausa, () -> animarPassos(movimento, indice + 1, proxima, aoTerminar));
            } else {
                aoTerminar.run();
            }
        });
    }

    /** Remove as peças capturadas e, se for o caso, anima a promoção a dama. */
    public void concluirMovimento(Movimento movimento, boolean promoveu, Runnable aoTerminar) {
        ParallelTransition conclusao = new ParallelTransition();
        List<Node> pecasRemovidas = new ArrayList<>();

        for (Posicao capturada : movimento.getCapturadas()) {
            PecaVisual peca = pecas.remove(capturada);
            if (peca != null) {
                pecasRemovidas.add(peca);
                conclusao.getChildren().add(peca.animacaoRemocao());
            }
        }
        if (promoveu) {
            PecaVisual promovida = pecas.get(movimento.getDestino());
            if (promovida != null) {
                conclusao.getChildren().add(promovida.animacaoPromocao());
                efeitoAnel(movimento.getDestino(), Paleta.OURO, 3.2);
            }
        }

        if (conclusao.getChildren().isEmpty()) {
            aoTerminar.run();
            return;
        }
        executar(conclusao, () -> {
            camadaPecas.getChildren().removeAll(pecasRemovidas);
            aoTerminar.run();
        });
    }

    /** Anel que se expande e desaparece, usado como feedback de captura e promoção. */
    private void efeitoAnel(Posicao posicao, Color cor, double escalaFinal) {
        Circle anel = new Circle(centroX(posicao), centroY(posicao), tamanhoCasa * 0.18, Color.TRANSPARENT);
        anel.setStroke(cor);
        anel.setStrokeWidth(Math.max(2, tamanhoCasa * 0.05));
        camadaEfeitos.getChildren().add(anel);

        Duration duracao = Duration.millis(450);
        ScaleTransition expandir = new ScaleTransition(duracao, anel);
        expandir.setToX(escalaFinal);
        expandir.setToY(escalaFinal);
        FadeTransition sumir = new FadeTransition(duracao, anel);
        sumir.setFromValue(1);
        sumir.setToValue(0);
        ParallelTransition efeito = new ParallelTransition(expandir, sumir);
        efeito.setOnFinished(evento -> camadaEfeitos.getChildren().remove(anel));
        efeito.play();
    }

    private void executar(Animation animacao, Runnable aoTerminar) {
        animacoesAtivas.add(animacao);
        animacao.setOnFinished(evento -> {
            animacoesAtivas.remove(animacao);
            aoTerminar.run();
        });
        animacao.play();
    }

    /** Interrompe as animações de jogada em andamento (ao reiniciar ou sair da partida). */
    public void cancelarAnimacoes() {
        for (Animation animacao : new ArrayList<>(animacoesAtivas)) {
            animacao.stop();
        }
        animacoesAtivas.clear();
        camadaEfeitos.getChildren().clear();
        for (PecaVisual peca : pecas.values()) {
            peca.setTranslateX(0);
            peca.setTranslateY(0);
            peca.setAnimando(false);
        }
    }
}
