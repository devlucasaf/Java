package games.tabuleiro.damas.util;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public final class Estilos {

    private static final String FAMILIA_FONTE = "Segoe UI";

    private Estilos() {}

    public static Font fonte(double tamanho, FontWeight peso) {
        return Font.font(FAMILIA_FONTE, peso, tamanho);
    }

    public static Background fundo(Paint cor, double raio) {
        return new Background(new BackgroundFill(cor, new CornerRadii(raio), Insets.EMPTY));
    }

    public static Border borda(Paint cor, double raio, double largura) {
        return new Border(new BorderStroke(cor, BorderStrokeStyle.SOLID, new CornerRadii(raio),
                new BorderWidths(largura)));
    }

    public static void rotulo(Label lblRotulo, double tamanho, FontWeight peso, Color cor) {
        lblRotulo.setFont(fonte(tamanho, peso));
        lblRotulo.setTextFill(cor);
    }

    public static void textoPrincipal(Label lblRotulo) {
        rotulo(lblRotulo, 14, FontWeight.NORMAL, Paleta.TEXTO);
    }

    public static void textoSecundario(Label lblRotulo) {
        rotulo(lblRotulo, 12.5, FontWeight.NORMAL, Paleta.TEXTO_SECUNDARIO);
    }

    public static void tituloSecao(Label lblRotulo) {
        rotulo(lblRotulo, 11.5, FontWeight.BOLD, Paleta.TEXTO_SECAO);
        lblRotulo.setPadding(new Insets(6, 0, 0, 0));
    }

    public static void fundoTela(Region regiao) {
        regiao.setBackground(fundo(Paleta.gradienteFundoTela(), 0));
    }

    public static void painel(Region regiao, double raio, Insets espacamento) {
        regiao.setBackground(fundo(Paleta.FUNDO_PAINEL, raio));
        regiao.setBorder(borda(Paleta.BORDA_SUAVE, raio, 1));
        regiao.setPadding(espacamento);
    }

    public static void cartao(Region regiao, boolean ativo) {
        regiao.setBackground(fundo(ativo ? Color.web("#f5c542", 0.12) : Paleta.FUNDO_CARTAO, 12));
        regiao.setBorder(borda(ativo ? Paleta.AMARELO : Color.TRANSPARENT, 12, 2));
        regiao.setPadding(new Insets(10, 12, 10, 12));
    }

    public static void caixaStatus(Label lblRotulo, Color cor) {
        lblRotulo.setBackground(fundo(Color.color(cor.getRed(), cor.getGreen(), cor.getBlue(), 0.16), 10));
        lblRotulo.setBorder(new Border(new BorderStroke(cor, BorderStrokeStyle.SOLID, new CornerRadii(10),
                new BorderWidths(0, 0, 0, 4))));
        lblRotulo.setPadding(new Insets(10, 12, 10, 12));
        lblRotulo.setTextFill(Paleta.TEXTO);
        lblRotulo.setFont(fonte(14, FontWeight.NORMAL));
    }

    public static void rolagemTransparente(ScrollPane rolagem) {
        rolagem.setBackground(Background.EMPTY);
        rolagem.setBorder(Border.EMPTY);
        rolagem.setPadding(Insets.EMPTY);
        rolagem.skinProperty().addListener((observavel, anterior, atual) -> {
            Node areaVisivel = rolagem.lookup(".viewport");
            if (areaVisivel instanceof Region regiao) {
                regiao.setBackground(Background.EMPTY);
            }
        });
    }

    public static void botao(Button botao, TipoBotao tipoBotao) {
        botao.setTextFill(Color.WHITE);
        botao.setFont(fonte(14, FontWeight.BOLD));
        botao.setPadding(new Insets(10, 18, 10, 18));
        botao.setCursor(Cursor.HAND);
        Runnable atualizar = () -> botao.setBackground(fundo(
                botao.isPressed() ? tipoBotao.pressionado : botao.isHover() ? tipoBotao.hover : tipoBotao.normal, 10));
        observarEstado(botao, atualizar);
    }

    public static void botaoModo(Button botao) {
        botao.setPadding(new Insets(16, 22, 16, 22));
        botao.setAlignment(Pos.CENTER_LEFT);
        botao.setCursor(Cursor.HAND);
        Runnable atualizar = () -> {
            boolean destaque = botao.isHover();
            botao.setBackground(fundo(destaque ? Color.web("#3d8bfd", 0.22) : Color.web("#ffffff", 0.06), 14));
            botao.setBorder(borda(destaque ? Paleta.AZUL : Color.web("#ffffff", 0.14), 14, 1));
        };
        observarEstado(botao, atualizar);
    }

    public static void alternador(ToggleButton opcao) {
        opcao.setPadding(new Insets(6, 14, 6, 14));
        opcao.setCursor(Cursor.HAND);
        Runnable atualizar = () -> {
            boolean selecionado = opcao.isSelected();
            Color cor = selecionado ? Paleta.AZUL
                    : Color.web("#ffffff", opcao.isHover() ? 0.14 : 0.07);
            opcao.setBackground(fundo(cor, 8));
            opcao.setTextFill(selecionado ? Color.WHITE : Color.web("#d5dce6"));
            opcao.setFont(fonte(13, selecionado ? FontWeight.BOLD : FontWeight.NORMAL));
        };
        opcao.selectedProperty().addListener((observavel, anterior, atual) -> atualizar.run());
        observarEstado(opcao, atualizar);
    }

    private static void observarEstado(ButtonBase botao, Runnable atualizar) {
        botao.hoverProperty().addListener((observavel, anterior, atual) -> atualizar.run());
        botao.pressedProperty().addListener((observavel, anterior, atual) -> atualizar.run());
        atualizar.run();
    }
}
