package games.puzzle.memoria.genius;

public class Genius {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            TelaPrincipal telaPrincipal = new TelaPrincipal();
            telaPrincipal.setVisible(true);
        });
    }
}