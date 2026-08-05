package math.calculadora.completa.gui.painel;

import math.calculadora.completa.gui.tema.TemaEscuro;
import math.calculadora.completa.model.conversoes.ConversorCores;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CalculadoraCoresPainel extends JPanel {
    private JTextField  txtR;
    private JTextField  txtG;
    private JTextField  txtB;
    private JTextField  txtHex;
    private JTextField  txtH;
    private JTextField  txtS;
    private JTextField  txtL;
    private JTextField  txtC;
    private JTextField  txtM;
    private JTextField  txtY;
    private JTextField  txtK;
    private JPanel      preview;

    public CalculadoraCoresPainel() {
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
            int r = Integer.parseInt(txtR.getText().trim());
            int g = Integer.parseInt(txtG.getText().trim());
            int b = Integer.parseInt(txtB.getText().trim());
            if (r >= 0 && r <= 255 && g >= 0 && g <= 255 && b >= 0 && b <= 255) {
                setRGB(r, g, b);
                return;
            }
        } catch (NumberFormatException ignored) {}

        String hex = txtHex.getText().trim();
        try {
            int[] rgb = ConversorCores.hexToRgb(hex);
            setRGB(rgb[0], rgb[1], rgb[2]);
            return;
        } catch (Exception ignored) {}

        try {
            double h = Double.parseDouble(txtH.getText());
            double s = Double.parseDouble(txtS.getText()) / 100.0;
            double l = Double.parseDouble(txtL.getText()) / 100.0;
            if (h >= 0 && h <= 360 && s >= 0 && s <= 1 && l >= 0 && l <= 1) {
                Color cor = ConversorCores.hslToRgb(h, s, l);
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
                Color cor = ConversorCores.cmykToRgb(c, m, y, k);
                setRGB(cor.getRed(), cor.getGreen(), cor.getBlue());
            }
        } catch (NumberFormatException ignored) {}
    }

    private void setRGB(int r, int g, int b) {
        txtR.setText(String.valueOf(r));
        txtG.setText(String.valueOf(g));
        txtB.setText(String.valueOf(b));
        txtHex.setText(ConversorCores.rgbToHex(r, g, b));

        float[] hsl = ConversorCores.rgbToHsl(r, g, b);
        txtH.setText(String.format("%.1f", hsl[0]));
        txtS.setText(String.format("%.1f", hsl[1] * 100));
        txtL.setText(String.format("%.1f", hsl[2] * 100));

        float[] cmyk = ConversorCores.rgbToCmyk(r, g, b);
        txtC.setText(String.format("%.1f", cmyk[0] * 100));
        txtM.setText(String.format("%.1f", cmyk[1] * 100));
        txtY.setText(String.format("%.1f", cmyk[2] * 100));
        txtK.setText(String.format("%.1f", cmyk[3] * 100));

        preview.setBackground(new Color(r, g, b));
    }
}
