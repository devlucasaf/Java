package games.tabuleiro.damas.util;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public final class Animacoes {

    private Animacoes() {
    }

    public static void surgir(Node no) {
        no.setOpacity(0);
        FadeTransition fade = new FadeTransition(Duration.millis(350), no);
        fade.setToValue(1);
        TranslateTransition deslize = new TranslateTransition(Duration.millis(350), no);
        deslize.setFromY(14);
        deslize.setToY(0);
        deslize.setInterpolator(Interpolator.EASE_OUT);
        new ParallelTransition(fade, deslize).play();
    }

    public static void aplicarEfeitoHover(Node no, double escala) {
        ScaleTransition transicao = new ScaleTransition(Duration.millis(130), no);
        no.addEventHandler(MouseEvent.MOUSE_ENTERED, evento -> animarEscala(transicao, escala));
        no.addEventHandler(MouseEvent.MOUSE_EXITED, evento -> animarEscala(transicao, 1));
    }

    private static void animarEscala(ScaleTransition transicao, double escala) {
        transicao.stop();
        transicao.setToX(escala);
        transicao.setToY(escala);
        transicao.playFromStart();
    }

    public static void pulsar(Node no) {
        ScaleTransition pulso = new ScaleTransition(Duration.millis(140), no);
        pulso.setFromX(1);
        pulso.setFromY(1);
        pulso.setToX(1.25);
        pulso.setToY(1.25);
        pulso.setAutoReverse(true);
        pulso.setCycleCount(2);
        pulso.play();
    }
}
