package application.utilitarios.conversorimagens;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws IOException {
        Path pasta = Path.of("target", "conversao");
        pasta.toFile().mkdirs();

        Path origem = pasta.resolve("origem.png");
        ImageIO.write(gerarDemo(), "png", origem.toFile());
        System.out.println("Origem: " + origem + " (" + Files.size(origem) + " bytes)");

        System.out.println("\n=== CONVERSOES ===");

        Path jpg100 = pasta.resolve("saida-q100.jpg");
        ConversorImagens.converter(origem, jpg100, "jpg", 1.0f);
        System.out.println("JPG qualidade 100%: " + Files.size(jpg100) + " bytes");

        Path jpg50 = pasta.resolve("saida-q50.jpg");
        ConversorImagens.converter(origem, jpg50, "jpg", 0.5f);
        System.out.println("JPG qualidade 50%:  " + Files.size(jpg50) + " bytes");

        Path jpg20 = pasta.resolve("saida-q20.jpg");
        ConversorImagens.converter(origem, jpg20, "jpg", 0.2f);
        System.out.println("JPG qualidade 20%:  " + Files.size(jpg20) + " bytes");

        Path bmp = pasta.resolve("saida.bmp");
        ConversorImagens.converter(origem, bmp, "bmp", 1.0f);
        System.out.println("BMP:                " + Files.size(bmp) + " bytes");

        Path pngDeJpg = pasta.resolve("volta-para-png.png");
        ConversorImagens.converter(jpg50, pngDeJpg, "png", 1.0f);
        System.out.println("PNG a partir do JPG q50%: " + Files.size(pngDeJpg) + " bytes");

        System.out.println("\nArquivos em: " + pasta.toAbsolutePath());
    }

    private static BufferedImage gerarDemo() {
        BufferedImage img = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        Random r = new Random(1);
        for (int y = 0; y < 600; y += 20) {
            g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
            g.fillRect(0, y, 800, 20);
        }
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(48f));
        g.drawString("CONVERSOR", 200, 300);
        g.dispose();
        return img;
    }
}

