package application.utilitarios.musica;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public final class CelulaMusica extends ListCell<Musica> {

    private final Label lblRotuloIndice;
    private final Label lblRotuloTitulo;
    private final Label lblRotuloArtista;
    private final VBox  caixaInformacoes;
    private final HBox  conteudo;

    // --- INICIALIZA A CELULA VISUAL DA MUSICA ---
    public CelulaMusica() {
        lblRotuloIndice = new Label();
        lblRotuloTitulo = new Label();
        lblRotuloArtista = new Label();

        lblRotuloIndice.getStyleClass().add("indice-musica");
        lblRotuloTitulo.getStyleClass().add("titulo-item-musica");
        lblRotuloArtista.getStyleClass().add("artista-item-musica");

        caixaInformacoes = new VBox(3.0, lblRotuloTitulo, lblRotuloArtista);

        HBox.setHgrow(caixaInformacoes, Priority.ALWAYS);

        conteudo = new HBox(12.0, lblRotuloIndice, caixaInformacoes);
        conteudo.setAlignment(Pos.CENTER_LEFT);
        conteudo.setPadding(new Insets(4.0));
    }

    // --- ATUALIZA O CONTEUDO DA CELULA ---
    @Override
    protected void updateItem(Musica musica, boolean vazia) {
        super.updateItem(musica, vazia);

        if (vazia || musica == null) {
            setText(null);
            setGraphic(null);
            return;
        }

        lblRotuloIndice.setText(String.valueOf(getIndex() + 1));
        lblRotuloTitulo.setText(valorOuPadrao(musica.obterTitulo(), "Título desconhecido"));
        lblRotuloArtista.setText(valorOuPadrao(musica.obterArtista(), "Artista desconhecido"));

        setText(null);
        setGraphic(conteudo);
    }

    // --- RETORNA UM TEXTO PADRAO PARA VALORES INVALIDOS ---
    private String valorOuPadrao(String valor, String valorPadrao) {
        if (valor == null || valor.isBlank()) {
            return valorPadrao;
        }

        return valor.strip();
    }
}
