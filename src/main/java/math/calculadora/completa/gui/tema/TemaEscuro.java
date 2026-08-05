package math.calculadora.completa.gui.tema;

import javax.swing.UIManager;
import java.awt.Color;

public class TemaEscuro {
    public static final Color FUNDO = Color.DARK_GRAY;
    public static final Color TEXTO = Color.WHITE;
    public static final Color BOTAO = new Color(60, 60, 60);
    public static final Color CAMPO = new Color(80, 80, 80);

    public static void aplicar() {
        UIManager.put("Panel.background", FUNDO);
        UIManager.put("Label.foreground", TEXTO);
        UIManager.put("Button.background", BOTAO);
        UIManager.put("Button.foreground", TEXTO);
        UIManager.put("TextField.background", CAMPO);
        UIManager.put("TextField.foreground", TEXTO);
        UIManager.put("ComboBox.background", BOTAO);
        UIManager.put("ComboBox.foreground", TEXTO);
        UIManager.put("TabbedPane.background", FUNDO);
        UIManager.put("TabbedPane.foreground", TEXTO);
    }
}
