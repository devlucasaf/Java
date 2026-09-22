package application.exercicios.faculdade.pacman.display;

import application.exercicios.faculdade.pacman.EstadoJogo;
import application.exercicios.faculdade.pacman.Layout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.Dimension;

public class DisplayGrafico implements Display {
    private JFrame janela;
    private Painel painel;

    @Override
    public void iniciar(EstadoJogo estado) {
        Layout layout = estado.getLayout();
        try {
            SwingUtilities.invokeAndWait(() -> {
                janela = new JFrame("Pac-Man - IA");
                painel = new Painel(estado);
                painel.setPreferredSize(new Dimension(
                        layout.getLargura() * Painel.TAMANHO_CELULA,
                        layout.getAltura() * Painel.TAMANHO_CELULA + 30
                ));
                janela.add(painel);
                janela.pack();
                janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                janela.setLocationRelativeTo(null);
                janela.setResizable(false);
                janela.setVisible(true);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void atualizar(EstadoJogo estado) {
        if (painel == null) {
            return;
        }

        SwingUtilities.invokeLater(() -> {
            painel.setEstado(estado);
            painel.repaint();
        });
    }

    @Override
    public void finalizar(EstadoJogo estado) {
        atualizar(estado);
    }
}

