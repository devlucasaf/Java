package games.tabuleiro.damas.components;

import games.tabuleiro.damas.model.Posicao;
import games.tabuleiro.damas.util.Paleta;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;

public class CasaVisual extends Region {

    private final Posicao   posicao;
    private final Rectangle fundo = new Rectangle();
    private final Rectangle camadaUltimoMovimento = criarCamada(Paleta.DESTAQUE_ULTIMO_MOVIMENTO);
    private final Rectangle camadaSelecao = criarCamada(Paleta.DESTAQUE_SELECAO);
    private final Rectangle camadaHover = criarCamada(Paleta.DESTAQUE_HOVER);
    private final Rectangle camadaErro = criarCamada(Paleta.DESTAQUE_ERRO);
    private final Rectangle bordaObrigatoria = new Rectangle();
    private final Circle    marcadorDestino = new Circle();

    private boolean destinoCaptura;

    public CasaVisual(Posicao posicao) {
        this.posicao = posicao;

        fundo.setFill(posicao.isCasaEscura() ? Paleta.CASA_ESCURA : Paleta.CASA_CLARA);

        bordaObrigatoria.setFill(Color.TRANSPARENT);
        bordaObrigatoria.setStroke(Paleta.CAPTURA_OBRIGATORIA);
        bordaObrigatoria.setStrokeType(StrokeType.INSIDE);
        bordaObrigatoria.setVisible(false);

        marcadorDestino.setVisible(false);
        marcadorDestino.setMouseTransparent(true);

        getChildren().addAll(fundo, camadaUltimoMovimento, camadaSelecao, camadaHover, camadaErro,
                bordaObrigatoria, marcadorDestino);
    }

    private static Rectangle criarCamada(Color cor) {
        Rectangle camada = new Rectangle();
        camada.setFill(cor);
        camada.setVisible(false);
        camada.setMouseTransparent(true);
        return camada;
    }

    @Override
    protected void layoutChildren() {
        double largura = getWidth();
        double altura = getHeight();
        for (Rectangle retangulo : new Rectangle[]{fundo, camadaUltimoMovimento, camadaSelecao,
                camadaHover, camadaErro, bordaObrigatoria}) {
            retangulo.setWidth(largura);
            retangulo.setHeight(altura);
        }
        bordaObrigatoria.setStrokeWidth(Math.max(2, largura * 0.06));

        marcadorDestino.setCenterX(largura / 2);
        marcadorDestino.setCenterY(altura / 2);
        if (destinoCaptura) {
            marcadorDestino.setRadius(largura * 0.36);
            marcadorDestino.setStrokeWidth(Math.max(2, largura * 0.07));
        } else {
            marcadorDestino.setRadius(largura * 0.15);
            marcadorDestino.setStrokeWidth(0);
        }
    }

    public Posicao getPosicao() {
        return posicao;
    }

    public void setSelecionada(boolean selecionada) {
        camadaSelecao.setVisible(selecionada);
    }

    public void setUltimoMovimento(boolean destacada) {
        camadaUltimoMovimento.setVisible(destacada);
    }

    public void setHoverAtivo(boolean ativo) {
        camadaHover.setVisible(ativo);
    }

    public void setCapturaObrigatoria(boolean obrigatoria) {
        bordaObrigatoria.setVisible(obrigatoria);
    }

    public void setDestino(boolean ativo, boolean captura) {
        boolean estavaVisivel = marcadorDestino.isVisible();
        destinoCaptura = captura;
        if (captura) {
            marcadorDestino.setFill(Color.TRANSPARENT);
            marcadorDestino.setStroke(Paleta.DESTINO_CAPTURA);
        } else {
            marcadorDestino.setFill(Paleta.DESTINO);
            marcadorDestino.setStroke(null);
        }
        marcadorDestino.setVisible(ativo);
        requestLayout();

        if (ativo && !estavaVisivel) {
            ScaleTransition surgir = new ScaleTransition(Duration.millis(160), marcadorDestino);
            surgir.setFromX(0.2);
            surgir.setFromY(0.2);
            surgir.setToX(1);
            surgir.setToY(1);
            surgir.play();
        }
    }

    public void limparDestaquesInterativos() {
        setSelecionada(false);
        setDestino(false, false);
        setHoverAtivo(false);
        setCapturaObrigatoria(false);
    }

    public void piscarErro() {
        camadaErro.setVisible(true);
        FadeTransition piscar = new FadeTransition(Duration.millis(140), camadaErro);
        piscar.setFromValue(1);
        piscar.setToValue(0);
        piscar.setCycleCount(3);
        piscar.setOnFinished(evento -> {
            camadaErro.setVisible(false);
            camadaErro.setOpacity(1);
        });
        piscar.play();
    }
}
