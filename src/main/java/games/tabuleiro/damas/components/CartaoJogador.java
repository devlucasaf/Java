package games.tabuleiro.damas.components;

import games.tabuleiro.damas.model.Jogador;
import games.tabuleiro.damas.util.Animacoes;
import games.tabuleiro.damas.util.Estilos;
import games.tabuleiro.damas.util.Paleta;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;

public final class CartaoJogador extends HBox {

    private final Label lblNumeroPecas = new Label();
    private final Label lblRotuloDamas = new Label();
    private int         pecasAtuais = -1;

    public CartaoJogador(Jogador jogador, String nome) {
        super(12);
        Estilos.cartao(this, false);
        setAlignment(Pos.CENTER_LEFT);

        Label lblRotuloNome = new Label(nome);
        Estilos.rotulo(lblRotuloNome, 15, FontWeight.BOLD, Paleta.TEXTO);
        Label lblDescricao = new Label(jogador.getDescricaoPecas());
        Estilos.textoSecundario(lblDescricao);

        Estilos.rotulo(lblNumeroPecas, 26, FontWeight.BOLD, Paleta.TEXTO);
        Estilos.textoSecundario(lblRotuloDamas);
        VBox contagem = new VBox(0, lblNumeroPecas, lblRotuloDamas);
        contagem.setAlignment(Pos.CENTER_RIGHT);

        Region espaco = new Region();
        HBox.setHgrow(espaco, Priority.ALWAYS);
        getChildren().addAll(PecaVisual.criarIcone(jogador, false, 34),
                new VBox(2, lblRotuloNome, lblDescricao), espaco, contagem);
    }

    public void atualizar(int pecas, int damas) {
        lblNumeroPecas.setText(String.valueOf(pecas));
        lblRotuloDamas.setText(damas == 1 ? "1 dama" : damas + " damas");
        if (pecasAtuais >= 0 && pecas != pecasAtuais) {
            Animacoes.pulsar(lblNumeroPecas);
        }
        pecasAtuais = pecas;
    }

    public void setAtivo(boolean ativo) {
        Estilos.cartao(this, ativo);
    }
}
