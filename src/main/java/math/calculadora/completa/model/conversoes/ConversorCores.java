package math.calculadora.completa.model.conversoes;

import java.awt.Color;

public class ConversorCores {

    public static int[] hexadecimalParaRGB(String hexadecimal) {
        if (hexadecimal.startsWith("#")) {
            hexadecimal = hexadecimal.substring(1);
        }

        if (hexadecimal.length() != 6) {
            throw new IllegalArgumentException("HEX deve ter 6 caracteres");
        }
        int r = Integer.parseInt(hexadecimal.substring(0, 2), 16);
        int g = Integer.parseInt(hexadecimal.substring(2, 4), 16);
        int b = Integer.parseInt(hexadecimal.substring(4, 6), 16);
        return new int[]{r, g, b};
    }

    public static String rgbParaHexadecimal(int r, int g, int b) {
        return String.format("#%02X%02X%02X", limitar(r), limitar(g), limitar(b));
    }

    public static float[] rgbParaHsl(int r, int g, int b) {
        float fr = r / 255f;
        float fg = g / 255f;
        float fb = b / 255f;
        float max = Math.max(fr, Math.max(fg, fb));
        float min = Math.min(fr, Math.min(fg, fb));
        float h = (max + min) / 2;
        float s = (max + min) / 2;
        float l = (max + min) / 2;

        if (max == min) {
            h = 0;
            s = 0;
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

    public static Color hslParaRgb(double h, double s, double l) {
        double c = (1 - Math.abs(2 * l - 1)) * s;
        double x = c * (1 - Math.abs((h / 60) % 2 - 1));
        double m = l - c / 2;
        double r;
        double g;
        double b;

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

    public static float[] rgbToCmyk(int r, int g, int b) {
        float fr = r / 255f;
        float fg = g / 255f;
        float fb = b / 255f;
        float k = 1 - Math.max(fr, Math.max(fg, fb));
        if (k == 1) {
            return new float[]{0, 0, 0, 1};
        }

        float c = (1 - fr - k) / (1 - k);
        float m = (1 - fg - k) / (1 - k);
        float y = (1 - fb - k) / (1 - k);
        return new float[]{c, m, y, k};
    }

    public static Color cmykParaRgb(double c, double m, double y, double k) {
        int r = (int)(255 * (1 - c) * (1 - k));
        int g = (int)(255 * (1 - m) * (1 - k));
        int b = (int)(255 * (1 - y) * (1 - k));
        return new Color(limitar(r), limitar(g), limitar(b));
    }

    private static int limitar(int v) {
        return Math.max(0, Math.min(255, v));
    }
}
