package application.utilitarios.editorimagens;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedImage entrada;
        if (args.length > 0) {
            entrada = ImageIO.read(new File(args[0]));
        } else {
            entrada = gerarImagemDemo();
        }

        Path pastaSaida = Path.of("target", "filtros");
        pastaSaida.toFile().mkdirs();
        salvar(entrada, pastaSaida.resolve("00-original.png"));
        salvar(Filtros.grayscale(entrada), pastaSaida.resolve("01-grayscale.png"));
        salvar(Filtros.negativo(entrada), pastaSaida.resolve("02-negativo.png"));
        salvar(Filtros.sepia(entrada), pastaSaida.resolve("03-sepia.png"));
        salvar(Filtros.blur(entrada), pastaSaida.resolve("04-blur.png"));
        salvar(Filtros.sharpen(entrada), pastaSaida.resolve("05-sharpen.png"));
        salvar(Filtros.sobel(entrada), pastaSaida.resolve("06-sobel.png"));

        System.out.println("Filtros aplicados. Verifique: " + pastaSaida.toAbsolutePath());
    }

    private static void salvar(BufferedImage img, Path destino) throws IOException {
        ImageIO.write(img, "png", destino.toFile());
    }

    private static BufferedImage gerarImagemDemo() {
        BufferedImage img = new BufferedImage(400, 300, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor(new Color(80, 130, 200));
        g.fillRect(0, 0, 400, 300);
        Random r = new Random(1);

        for (int i = 0; i < 30; i++) {
            g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
            g.fillOval(r.nextInt(400), r.nextInt(300), 40, 40);
        }

        g.setColor(Color.WHITE);
        g.drawString("DEMO IMAGEM", 150, 150);
        g.dispose();

        return img;
    }
}

