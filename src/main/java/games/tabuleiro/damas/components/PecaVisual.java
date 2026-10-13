package games.tabuleiro.damas.components;

import games.tabuleiro.damas.model.Jogador;
import games.tabuleiro.damas.util.Constantes;
import games.tabuleiro.damas.util.Paleta;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineJoin;
import javafx.util.Duration;

public class PecaVisual extends Group {

    public static final double      TAMANHO_BASE = 100;
    private static final double     CENTRO = TAMANHO_BASE / 2;
    private static final double     RAIO = 38;
    private static final double     ESCALA_ELEVADA = 1.08;

    private final Jogador           jogador;
    private final Group             corpo = new Group();
    private final Circle            base;
    private final Group             coroa;
    private final Circle            anelSelecao;

    private boolean                 dama;
    private boolean                 selecionada;
    private boolean                 animando;
    private Animation               pulsoSelecao;
    private final ScaleTransition   transicaoElevacao = new ScaleTransition(Duration.millis(120), corpo);

    public PecaVisual(Jogador jogador, boolean dama) {
        this.jogador = jogador;

        Rectangle ancora = new Rectangle(TAMANHO_BASE, TAMANHO_BASE, Color.TRANSPARENT);

        Ellipse sombra = new Ellipse(CENTRO + 2, CENTRO + 5, RAIO, RAIO);
        sombra.setFill(Color.rgb(0, 0, 0, 0.45));

        base = new Circle(CENTRO, CENTRO, RAIO, Paleta.gradienteDaPeca(jogador));

        Circle sulcoExterno = new Circle(CENTRO, CENTRO, RAIO * 0.78, Color.TRANSPARENT);
        sulcoExterno.setStroke(Paleta.sulcoDaPeca(jogador));
        sulcoExterno.setStrokeWidth(2.2);

        Circle sulcoInterno = new Circle(CENTRO, CENTRO, RAIO * 0.5, Color.TRANSPARENT);
        sulcoInterno.setStroke(Paleta.sulcoDaPeca(jogador));
        sulcoInterno.setStrokeWidth(1.4);
        sulcoInterno.setOpacity(0.6);

        Ellipse brilho = new Ellipse(CENTRO - 10, CENTRO - 15, RAIO * 0.45, RAIO * 0.24);
        brilho.setFill(Color.rgb(255, 255, 255, 0.35));
        brilho.setRotate(-30);

        coroa = criarCoroa();

        anelSelecao = new Circle(CENTRO, CENTRO, 45, Color.TRANSPARENT);
        anelSelecao.setStroke(Paleta.OURO);
        anelSelecao.setStrokeWidth(4.5);
        anelSelecao.setVisible(false);

        corpo.getChildren().addAll(ancora, sombra, base, sulcoExterno, sulcoInterno, brilho, coroa, anelSelecao);
        getChildren().add(corpo);
        setMouseTransparent(true);

        definirDama(dama);
    }

    public static Node criarIcone(Jogador jogador, boolean dama, double tamanho) {
        PecaVisual peca = new PecaVisual(jogador, dama);
        peca.setTamanho(tamanho);
        return new Group(peca); // o grupo externo passa a considerar a escala no layout
    }

    private Group criarCoroa() {
        Polygon pontas = new Polygon(31, 62, 29, 39, 40, 50, 50, 33, 60, 50, 71, 39, 69, 62);
        pontas.setFill(Paleta.gradienteOuro());
        pontas.setStroke(Paleta.OURO_ESCURO);
        pontas.setStrokeWidth(1.8);
        pontas.setStrokeLineJoin(StrokeLineJoin.ROUND);

        Rectangle faixa = new Rectangle(31, 62, 38, 6);
        faixa.setFill(Paleta.gradienteOuro());
        faixa.setStroke(Paleta.OURO_ESCURO);
        faixa.setStrokeWidth(1.4);

        Group grupo = new Group(pontas, faixa, criarJoia(29, 39), criarJoia(50, 33), criarJoia(71, 39));
        grupo.setEffect(new DropShadow(4, Color.rgb(0, 0, 0, 0.55)));
        return grupo;
    }

    private Circle criarJoia(double x, double y) {
        Circle joia = new Circle(x, y, 3.4, Paleta.OURO_CLARO);
        joia.setStroke(Paleta.OURO_ESCURO);
        joia.setStrokeWidth(1);
        return joia;
    }

    private void definirDama(boolean dama) {
        this.dama = dama;
        coroa.setVisible(dama);
        base.setStroke(dama ? Paleta.OURO : Paleta.contornoDaPeca(jogador));
        base.setStrokeWidth(dama ? 4 : 2.5);
    }

    public void setTamanho(double tamanhoCasa) {
        double escala = tamanhoCasa / TAMANHO_BASE;
        setScaleX(escala);
        setScaleY(escala);
    }

    public Jogador getJogador() {
        return jogador;
    }

    public boolean isDama() {
        return dama;
    }

    public boolean isAnimando() {
        return animando;
    }

    public void setAnimando(boolean animando) {
        this.animando = animando;
        if (animando) {
            transicaoElevacao.stop();
            corpo.setScaleX(1);
            corpo.setScaleY(1);
        }
    }

    public void setSelecionada(boolean selecionada) {
        if (this.selecionada == selecionada) {
            return;
        }

        this.selecionada = selecionada;
        anelSelecao.setVisible(selecionada);
        if (pulsoSelecao != null) {
            pulsoSelecao.stop();
            anelSelecao.setOpacity(1);
        }

        if (selecionada) {
            FadeTransition pulso = new FadeTransition(Duration.millis(650), anelSelecao);
            pulso.setFromValue(1);
            pulso.setToValue(0.35);
            pulso.setAutoReverse(true);
            pulso.setCycleCount(Animation.INDEFINITE);
            pulsoSelecao = pulso;
            pulso.play();
        }
    }

    public void setElevada(boolean elevada) {
        if (animando) {
            return;
        }
        transicaoElevacao.stop();
        double escala = elevada ? ESCALA_ELEVADA : 1;
        transicaoElevacao.setToX(escala);
        transicaoElevacao.setToY(escala);
        transicaoElevacao.playFromStart();
    }

    public Animation animacaoSalto(Duration duracao) {
        ScaleTransition salto = new ScaleTransition(duracao.divide(2), corpo);
        salto.setFromX(1);
        salto.setFromY(1);
        salto.setToX(1.2);
        salto.setToY(1.2);
        salto.setAutoReverse(true);
        salto.setCycleCount(2);
        return salto;
    }

    public void marcarCapturada() {
        corpo.setEffect(new ColorAdjust(0, -0.7, -0.25, 0));
        FadeTransition fade = new FadeTransition(Duration.millis(180), corpo);
        fade.setToValue(0.5);
        fade.play();
    }

    public Animation animacaoRemocao() {
        Duration duracao = Duration.millis(Constantes.DURACAO_REMOCAO_MS);
        FadeTransition fade = new FadeTransition(duracao, corpo);
        fade.setToValue(0);
        ScaleTransition encolher = new ScaleTransition(duracao, corpo);
        encolher.setToX(0.25);
        encolher.setToY(0.25);
        encolher.setInterpolator(Interpolator.EASE_IN);
        return new ParallelTransition(fade, encolher);
    }

    public Animation animacaoPromocao() {
        definirDama(true);
        Duration duracao = Duration.millis(Constantes.DURACAO_PROMOCAO_MS);

        coroa.setScaleX(0.1);
        coroa.setScaleY(0.1);
        ScaleTransition surgirCoroa = new ScaleTransition(duracao, coroa);
        surgirCoroa.setToX(1);
        surgirCoroa.setToY(1);
        surgirCoroa.setInterpolator(Interpolator.SPLINE(0.3, 1.6, 0.5, 1));

        RotateTransition giro = new RotateTransition(duracao, corpo);
        giro.setFromAngle(0);
        giro.setToAngle(360);
        giro.setInterpolator(Interpolator.EASE_BOTH);

        return new ParallelTransition(surgirCoroa, giro);
    }

    public Animation animacaoEntrada(Duration atraso) {
        corpo.setOpacity(0);
        corpo.setScaleX(0.3);
        corpo.setScaleY(0.3);
        FadeTransition fade = new FadeTransition(Duration.millis(260), corpo);
        fade.setToValue(1);
        ScaleTransition crescer = new ScaleTransition(Duration.millis(260), corpo);
        crescer.setToX(1);
        crescer.setToY(1);
        crescer.setInterpolator(Interpolator.EASE_OUT);
        ParallelTransition entrada = new ParallelTransition(fade, crescer);
        entrada.setDelay(atraso);
        return entrada;
    }

    public void sacudir() {
        TranslateTransition tremor = new TranslateTransition(Duration.millis(45), corpo);
        tremor.setFromX(0);
        tremor.setByX(5);
        tremor.setAutoReverse(true);
        tremor.setCycleCount(6);
        tremor.setOnFinished(evento -> corpo.setTranslateX(0));
        tremor.play();
    }
}
