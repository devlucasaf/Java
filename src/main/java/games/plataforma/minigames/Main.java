package games.plataforma.minigames;

import javax.swing.SwingUtilities;
import games.plataforma.minigames.gui.MenuPrincipal;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MenuPrincipal().setVisible(true);
        });
    }
}
