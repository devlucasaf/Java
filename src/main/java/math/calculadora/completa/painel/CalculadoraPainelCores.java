package math.calculadora.completa.painel;

import math.calculadora.completa.tema.TemaEscuro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraPainelCores extends JPanel {

    private JTextField  txtR;
    private JTextField  txtB;
    private JTextField  txtHex;
    private JTextField  txtH;
    private JTextField  txtS;
    private JTextField  txtL;
    private JTextField  txtC;
    private JTextField  txtM;
    private JTextField  txtY;
    private JTextField  txtK;
    private JTextField  txtG;
    private JPanel      preview;

    public CalculadoraPainelCores() {
        super(new GridBagLayout());
        setBackground(TemaEscuro.FUNDO);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        addLabel("R (0-255):", 0, row, gbc);
        txtR = addTextField("255", 1, row++, gbc);
        addLabel("G (0-255):", 0, row, gbc);
        txtG = addTextField("255", 1, row++, gbc);
        addLabel("B (0-255):", 0, row, gbc);
        txtB = addTextField("255", 1, row++, gbc);

        addLabel("HEX (#):", 0, row, gbc);
        txtHex = addTextField("#FFFFFF", 1, row++, gbc);

        addLabel("H (0-360):", 0, row, gbc);
        txtH = addTextField("0", 1, row++, gbc);
        addLabel("S (0-100%):", 0, row, gbc);
        txtS = addTextField("0", 1, row++, gbc);
        addLabel("L (0-100%):", 0, row, gbc);
        txtL = addTextField("100", 1, row++, gbc);

        addLabel("C (0-100%):", 0, row, gbc);
        txtC = addTextField("0", 1, row++, gbc);
        addLabel("M (0-100%):", 0, row, gbc);
        txtM = addTextField("0", 1, row++, gbc);
        addLabel("Y (0-100%):", 0, row, gbc);
        txtY = addTextField("0", 1, row++, gbc);
        addLabel("K (0-100%):", 0, row, gbc);
        txtK = addTextField("0", 1, row++, gbc);

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setBackground(TemaEscuro.BOTAO);
        btnAtualizar.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        add(btnAtualizar, gbc);
        row++;

        preview = new JPanel();
        preview.setPreferredSize(new Dimension(100, 50));
        preview.setBackground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        add(preview, gbc);

        btnAtualizar.addActionListener(e -> atualizarTudo());
    }

    private void addLabel(String texto, int x, int y, GridBagConstraints gbc) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = x; gbc.gridy = y; gbc.gridwidth = 1;
        add(lbl, gbc);
    }

    private JTextField addTextField(String valor, int x, int y, GridBagConstraints gbc) {
        JTextField tf = new JTextField(valor, 5);

        tf.setBackground(TemaEscuro.CAMPO);
        tf.setForeground(TemaEscuro.TEXTO);
        gbc.gridx = x; gbc.gridy = y; gbc.gridwidth = 1;
        add(tf, gbc);

        return tf;
    }

    private void atualizarTudo() {
        try {
            int r = parseInt(txtR.getText());
            int g = parseInt(txtG.getText());
            int b = parseInt(txtB.getText());
            if (r >= 0 && r <= 255 && g >= 0 && g <= 255 && b >= 0 && b <= 255) {
                setRGB(r, g, b);
                return;
            }
        } catch (NumberFormatException ignored) {}

        String hex = txtHex.getText().trim();
        if (hex.startsWith("#")) {
            hex = hex.substring(1);
        }

        if (hex.length() == 6) {
            try {
                int r = Integer.parseInt(hex.substring(0, 2), 16);
                int g = Integer.parseInt(hex.substring(2, 4), 16);
                int b = Integer.parseInt(hex.substring(4, 6), 16);
                setRGB(r, g, b);
                return;
            } catch (NumberFormatException ignored) {}
        }

        try {
            double h = Double.parseDouble(txtH.getText());
            double s = Double.parseDouble(txtS.getText()) / 100.0;
            double l = Double.parseDouble(txtL.getText()) / 100.0;
            if (h >= 0 && h <= 360 && s >= 0 && s <= 1 && l >= 0 && l <= 1) {
                Color cor = hslToRgb(h, s, l);
                setRGB(cor.getRed(), cor.getGreen(), cor.getBlue());
                return;
            }
        } catch (NumberFormatException ignored) {}

        try {
            double c = Double.parseDouble(txtC.getText()) / 100.0;
            double m = Double.parseDouble(txtM.getText()) / 100.0;
            double y = Double.parseDouble(txtY.getText()) / 100.0;
            double k = Double.parseDouble(txtK.getText()) / 100.0;
            if (c >= 0 && c <= 1 && m >= 0 && m <= 1 && y >= 0 && y <= 1 && k >= 0 && k <= 1) {
                Color cor = cmykToRgb(c, m, y, k);
                setRGB(cor.getRed(), cor.getGreen(), cor.getBlue());
            }
        } catch (NumberFormatException ignored) {}
    }

    private void setRGB(int r, int g, int b) {
        txtR.setText(String.valueOf(r));
        txtG.setText(String.valueOf(g));
        txtB.setText(String.valueOf(b));
        txtHex.setText(String.format("#%02X%02X%02X", r, g, b));

        float[] hsl = rgbToHsl(r, g, b);
        txtH.setText(String.format("%.1f", hsl[0]));
        txtS.setText(String.format("%.1f", hsl[1] * 100));
        txtL.setText(String.format("%.1f", hsl[2] * 100));

        float[] cmyk = rgbToCmyk(r, g, b);
        txtC.setText(String.format("%.1f", cmyk[0] * 100));
        txtM.setText(String.format("%.1f", cmyk[1] * 100));
        txtY.setText(String.format("%.1f", cmyk[2] * 100));
        txtK.setText(String.format("%.1f", cmyk[3] * 100));

        preview.setBackground(new Color(r, g, b));
    }

    private int parseInt(String s) throws NumberFormatException {
        return Integer.parseInt(s.trim());
    }

    private float[] rgbToHsl(int r, int g, int b) {
        float fr = r / 255f, fg = g / 255f, fb = b / 255f;
        float max = Math.max(fr, Math.max(fg, fb));
        float min = Math.min(fr, Math.min(fg, fb));
        float h, s, l = (max + min) / 2;
        if (max == min) {
            h = 0; s = 0;
        } else {
            float d = max - min;
            s = l > 0.5 ? d / (2 - max - min) : d / (max + min);
            if (max == fr) {
                h = (fg - fb) / d + (fg < fb ? 6 : 0);
            } else if (max == fg) {
                h = (fb - fr) / d + 2;
            } else {
                h = (fr - fg) / d + 4;
            }
            h /= 6;
        }
        return new float[]{h * 360, s, l};
    }

    private Color hslToRgb(double h, double s, double l) {
        double c = (1 - Math.abs(2 * l - 1)) * s;
        double x = c * (1 - Math.abs((h / 60) % 2 - 1));
        double m = l - c / 2;
        double r, g, b;
        if (h < 60) {
            r = c;
            g = x;
            b = 0;
        } else if (h < 120) {
            r = x;
            g = c;
            b = 0;
        } else if (h < 180) {
            r = 0;
            g = c;
            b = x;
        } else if (h < 240) {
            r = 0;
            g = x;
            b = c;
        } else if (h < 300) {
            r = x;
            g = 0;
            b = c;
        } else {
            r = c;
            g = 0;
            b = x;
        }
        return new Color((int)((r + m) * 255), (int)((g + m) * 255), (int)((b + m) * 255));
    }

    private float[] rgbToCmyk(int r, int g, int b) {
        float fr = r / 255f, fg = g / 255f, fb = b / 255f;
        float k = 1 - Math.max(fr, Math.max(fg, fb));
        if (k == 1) {
            return new float[]{0, 0, 0, 1};
        }
        float c = (1 - fr - k) / (1 - k);
        float m = (1 - fg - k) / (1 - k);
        float y = (1 - fb - k) / (1 - k);
        return new float[]{c, m, y, k};
    }

    private Color cmykToRgb(double c, double m, double y, double k) {
        int r = (int)(255 * (1 - c) * (1 - k));
        int g = (int)(255 * (1 - m) * (1 - k));
        int b = (int)(255 * (1 - y) * (1 - k));
        return new Color(clamp(r), clamp(g), clamp(b));
    }

    private int clamp(int v) {
        return Math.max(0, Math.min(255, v));
    }
}