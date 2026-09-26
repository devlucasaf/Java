package games.tabuleiro.damas.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.stage.Window;

/**
 * Caixas de diálogo padronizadas, com textos e botões em português.
 */
public final class Dialogos {

    private Dialogos() {
    }

    /** Pergunta de confirmação com os botões "Sim" e "Não". */
    public static boolean confirmar(Window dono, String titulo, String mensagem) {
        ButtonType sim = new ButtonType("Sim", ButtonBar.ButtonData.OK_DONE);
        ButtonType nao = new ButtonType("Não", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION, mensagem, sim, nao);
        alerta.setTitle(titulo);
        alerta.setHeaderText(titulo);
        prepararDialogo(alerta, dono);
        return alerta.showAndWait().filter(resposta -> resposta == sim).isPresent();
    }

    /** Mensagem informativa com texto longo e quebra de linha automática. */
    public static void informar(Window dono, String titulo, String cabecalho, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.getButtonTypes().setAll(new ButtonType("Entendi", ButtonBar.ButtonData.OK_DONE));
        alerta.setTitle(titulo);
        alerta.setHeaderText(cabecalho);
        Label conteudo = new Label(mensagem);
        conteudo.setWrapText(true);
        conteudo.setPrefWidth(540);
        alerta.getDialogPane().setContent(conteudo);
        prepararDialogo(alerta, dono);
        alerta.showAndWait();
    }

    private static void prepararDialogo(Dialog<?> dialogo, Window dono) {
        if (dono != null) {
            dialogo.initOwner(dono);
        }
        Recursos.aplicarEstilos(dialogo.getDialogPane());
        dialogo.getDialogPane().getStyleClass().add("dialogo-damas");
    }
}
