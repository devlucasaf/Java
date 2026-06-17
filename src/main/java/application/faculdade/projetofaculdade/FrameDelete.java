package application.faculdade.projetofaculdade;

import javax.swing.*;
import java.awt.*;

public class FrameDelete extends JPanel {

    public FrameDelete() {
        setLayout(null);
        setBackground(new Color(0x664983));
        setBounds(0, 0, 1300, 600);

        criarComponentes();
    }

    private void criarComponentes() {

        JLabel titulo = new JLabel("Tela de Exclusão");
        titulo.setBounds(500, 50, 300, 30);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));

        add(titulo);
    }
}