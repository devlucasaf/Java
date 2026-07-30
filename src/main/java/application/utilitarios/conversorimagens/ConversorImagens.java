package application.utilitarios.conversorimagens;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class ConversorImagens {

    public static void converter(Path entrada, Path saida, String formato, float qualidade) throws IOException {
        BufferedImage original = ImageIO.read(entrada.toFile());
        if (original == null) {
            throw new IOException("Formato de entrada nao suportado: " + entrada);
        }

        BufferedImage compativel = original;
        String f = formato.toLowerCase();
        if (f.equals("jpg") || f.equals("jpeg") || f.equals("bmp")) {
            compativel = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = compativel.createGraphics();
            g.drawImage(original, 0, 0, java.awt.Color.WHITE, null);
            g.dispose();
        }

        if (f.equals("jpg") || f.equals("jpeg")) {
            escreverComQualidade(compativel, saida.toFile(), "jpg", qualidade);
        } else {
            ImageIO.write(compativel, f, saida.toFile());
        }
    }

    private static void escreverComQualidade(BufferedImage img, File destino, String formato, float qualidade) throws IOException {
        ImageWriter writer = ImageIO.getImageWritersByFormatName(formato).next();
        try (ImageOutputStream ios = ImageIO.createImageOutputStream(destino)) {
            writer.setOutput(ios);
            ImageWriteParam param = writer.getDefaultWriteParam();
            if (param.canWriteCompressed()) {
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(qualidade);
            }
            writer.write(null, new IIOImage(img, null, null), param);
        } finally {
            writer.dispose();
        }
    }
}

