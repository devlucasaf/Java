package games.plataforma.minigames.gui;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;

public class TemaEscuro {
    public static void aplicar() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {}

        // Definição das cores
        Color fundo = new Color(45, 45, 45);
        Color texto = new Color(240, 240, 240);
        Color botao = new Color(64, 64, 64);
        Color botaoHover = new Color(85, 85, 85);
        Color campo = new Color(58, 58, 58);
        Color tabuleiro = new Color(74, 74, 74);

        // Aplicar via UIManager
        UIManager.put("Panel.background", new ColorUIResource(fundo));
        UIManager.put("OptionPane.background", new ColorUIResource(fundo));
        UIManager.put("OptionPane.messageForeground", new ColorUIResource(texto));
        UIManager.put("Button.background", new ColorUIResource(botao));
        UIManager.put("Button.foreground", new ColorUIResource(texto));
        UIManager.put("Button.select", new ColorUIResource(botaoHover));
        UIManager.put("TextField.background", new ColorUIResource(campo));
        UIManager.put("TextField.foreground", new ColorUIResource(texto));
        UIManager.put("TextArea.background", new ColorUIResource(campo));
        UIManager.put("TextArea.foreground", new ColorUIResource(texto));
        UIManager.put("Label.foreground", new ColorUIResource(texto));
        UIManager.put("List.background", new ColorUIResource(campo));
        UIManager.put("List.foreground", new ColorUIResource(texto));
        UIManager.put("Frame.background", new ColorUIResource(fundo));
        UIManager.put("InternalFrame.background", new ColorUIResource(fundo));
        UIManager.put("TitledBorder.titleColor", new ColorUIResource(texto));
        UIManager.put("Menu.background", new ColorUIResource(botao));
        UIManager.put("Menu.foreground", new ColorUIResource(texto));
        UIManager.put("MenuItem.background", new ColorUIResource(botao));
        UIManager.put("MenuItem.foreground", new ColorUIResource(texto));
    }

    public static void configurarComponente(JComponent comp) {
        comp.setBackground(new Color(45, 45, 45));
        comp.setForeground(new Color(240, 240, 240));
    }
}
