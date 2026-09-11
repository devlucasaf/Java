package application.utilitarios.markdown;

import javax.swing.SwingUtilities;

public class PrincipalMarkdownEditor {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EditorMarkdownFrame frame = new EditorMarkdownFrame();
            frame.setVisible(true);
        });
    }
}
