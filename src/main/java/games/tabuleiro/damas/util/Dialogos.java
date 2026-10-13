package games.tabuleiro.damas.util;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.FontWeight;
import javafx.stage.Window;

public final class Dialogos {

    private Dialogos() {
    }

    public static boolean confirmar(Window dono, String titulo, String mensagem) {
        ButtonType btnSim = new ButtonType("Sim", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnNao = new ButtonType("Não", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.getButtonTypes().setAll(btnSim, btnNao);
        prepararDialogo(alerta, dono, titulo, titulo, mensagem);
        return alerta.showAndWait().filter(resposta -> resposta == btnSim).isPresent();
    }

    public static void informar(Window dono, String titulo, String cabecalho, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.getButtonTypes().setAll(new ButtonType("Entendi", ButtonBar.ButtonData.OK_DONE));
        prepararDialogo(alerta, dono, titulo, cabecalho, mensagem);
        alerta.showAndWait();
    }

    private static void prepararDialogo(Dialog<?> dialogo, Window dono, String titulo, String cabecalho, String mensagem) {
        if (dono != null) {
            dialogo.initOwner(dono);
        }
        dialogo.setTitle(titulo);

        DialogPane painel = dialogo.getDialogPane();
        painel.setBackground(Estilos.fundo(Paleta.FUNDO_DIALOGO, 0));

        Label lblRotuloCabecalho = new Label(cabecalho);
        Estilos.rotulo(lblRotuloCabecalho, 17, FontWeight.BOLD, Paleta.TEXTO);
        StackPane areaCabecalho = new StackPane(lblRotuloCabecalho);
        areaCabecalho.setAlignment(Pos.CENTER_LEFT);
        areaCabecalho.setPadding(new Insets(16, 20, 16, 20));
        areaCabecalho.setBackground(Estilos.fundo(Paleta.FUNDO_CABECALHO_DIALOGO, 0));
        painel.setHeader(areaCabecalho);

        Label lblConteudo = new Label(mensagem);
        Estilos.textoPrincipal(lblConteudo);
        lblConteudo.setWrapText(true);
        lblConteudo.setPrefWidth(540);
        lblConteudo.setPadding(new Insets(16, 20, 8, 20));
        painel.setContent(lblConteudo);

        for (ButtonType tipo : painel.getButtonTypes()) {
            if (painel.lookupButton(tipo) instanceof Button botao) {
                boolean principal = tipo.getButtonData() == ButtonBar.ButtonData.OK_DONE;
                Estilos.botao(botao, principal ? TipoBotao.PRIMARIO : TipoBotao.PADRAO);
            }
        }
    }
}
