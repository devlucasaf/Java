package math.calculadora.completa;

import math.calculadora.completa.gui.frame.CalculadoraCompletaGUI;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new CalculadoraCompletaGUI();
        });
    }
}