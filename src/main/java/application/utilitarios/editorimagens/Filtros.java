package application.utilitarios.editorimagens;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Filtros {

    public static BufferedImage grayscale(BufferedImage in) {
        BufferedImage out = criar(in);
        for (int y = 0; y < in.getHeight(); y++) {
            for (int x = 0; x < in.getWidth(); x++) {
                Color c = new Color(in.getRGB(x, y), true);
                int cinza = (int) (0.299 * c.getRed() + 0.587 * c.getGreen() + 0.114 * c.getBlue());
                out.setRGB(x, y, new Color(cinza, cinza, cinza, c.getAlpha()).getRGB());
            }
        }
        return out;
    }

    public static BufferedImage negativo(BufferedImage in) {
        BufferedImage out = criar(in);
        for (int y = 0; y < in.getHeight(); y++) {
            for (int x = 0; x < in.getWidth(); x++) {
                Color c = new Color(in.getRGB(x, y), true);
                out.setRGB(x, y, new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue(), c.getAlpha()).getRGB());
            }
        }
        return out;
    }

    public static BufferedImage sepia(BufferedImage in) {
        BufferedImage out = criar(in);
        for (int y = 0; y < in.getHeight(); y++) {
            for (int x = 0; x < in.getWidth(); x++) {
                Color c = new Color(in.getRGB(x, y), true);
                int r = (int) Math.min(255, 0.393 * c.getRed() + 0.769 * c.getGreen() + 0.189 * c.getBlue());
                int g = (int) Math.min(255, 0.349 * c.getRed() + 0.686 * c.getGreen() + 0.168 * c.getBlue());
                int b = (int) Math.min(255, 0.272 * c.getRed() + 0.534 * c.getGreen() + 0.131 * c.getBlue());
                out.setRGB(x, y, new Color(r, g, b, c.getAlpha()).getRGB());
            }
        }
        return out;
    }

    public static BufferedImage blur(BufferedImage in) {
        return convolucao(in, new double[][]{
                {1.0 / 9, 1.0 / 9, 1.0 / 9},
                {1.0 / 9, 1.0 / 9, 1.0 / 9},
                {1.0 / 9, 1.0 / 9, 1.0 / 9}
        });
    }

    public static BufferedImage sharpen(BufferedImage in) {
        return convolucao(in, new double[][]{
                {0, -1, 0},
                {-1, 5, -1},
                {0, -1, 0}
        });
    }

    public static BufferedImage sobel(BufferedImage in) {
        BufferedImage cinza = grayscale(in);
        BufferedImage out = criar(in);
        double[][] gx = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        double[][] gy = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
        for (int y = 1; y < in.getHeight() - 1; y++) {
            for (int x = 1; x < in.getWidth() - 1; x++) {
                double sx = 0, sy = 0;
                for (int j = -1; j <= 1; j++) {
                    for (int i = -1; i <= 1; i++) {
                        int c = new Color(cinza.getRGB(x + i, y + j)).getRed();
                        sx += c * gx[j + 1][i + 1];
                        sy += c * gy[j + 1][i + 1];
                    }
                }
                int g = (int) Math.min(255, Math.sqrt(sx * sx + sy * sy));
                out.setRGB(x, y, new Color(g, g, g).getRGB());
            }
        }
        return out;
    }

    private static BufferedImage convolucao(BufferedImage in, double[][] kernel) {
        BufferedImage out = criar(in);
        int n = kernel.length / 2;
        for (int y = n; y < in.getHeight() - n; y++) {
            for (int x = n; x < in.getWidth() - n; x++) {
                double r = 0, g = 0, b = 0;
                for (int j = -n; j <= n; j++) {
                    for (int i = -n; i <= n; i++) {
                        Color c = new Color(in.getRGB(x + i, y + j));
                        double k = kernel[j + n][i + n];
                        r += c.getRed() * k;
                        g += c.getGreen() * k;
                        b += c.getBlue() * k;
                    }
                }
                int rr = Math.max(0, Math.min(255, (int) r));
                int gg = Math.max(0, Math.min(255, (int) g));
                int bb = Math.max(0, Math.min(255, (int) b));
                out.setRGB(x, y, new Color(rr, gg, bb).getRGB());
            }
        }
        return out;
    }

    private static BufferedImage criar(BufferedImage base) {
        return new BufferedImage(base.getWidth(), base.getHeight(), BufferedImage.TYPE_INT_ARGB);
    }
}

