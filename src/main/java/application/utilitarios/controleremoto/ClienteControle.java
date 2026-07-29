package application.utilitarios.controleremoto;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class ClienteControle {

    public static void main(String[] args) throws IOException {
        String host = args.length > 0 ? args[0] : "localhost";
        int porta = args.length > 1 ? Integer.parseInt(args[1]) : 5555;

        try (Socket s = new Socket(host, porta);
             DataInputStream in = new DataInputStream(s.getInputStream());
             DataOutputStream out = new DataOutputStream(s.getOutputStream());
             Scanner sc = new Scanner(System.in)) {

            System.out.println("Conectado a " + host + ":" + porta);
            System.out.println("Comandos: SCREEN | KEY <cod> | MOUSE <x> <y> | CLICK | SAIR");

            while (true) {
                System.out.print("> ");
                if (!sc.hasNextLine()) break;
                String cmd = sc.nextLine().trim();
                if (cmd.isEmpty()) continue;
                out.writeUTF(cmd);

                if (cmd.equalsIgnoreCase("SCREEN")) {
                    int tamanho = in.readInt();
                    byte[] bytes = in.readNBytes(tamanho);
                    BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytes));
                    Path saida = Path.of("target", "screenshot-" + System.currentTimeMillis() + ".png");
                    saida.getParent().toFile().mkdirs();
                    Files.write(saida, bytes);
                    System.out.println("Screenshot salvo em " + saida.toAbsolutePath()
                            + " (" + img.getWidth() + "x" + img.getHeight() + ")");
                } else {
                    String resposta = in.readUTF();
                    System.out.println("<- " + resposta);
                    if (resposta.equals("BYE")) break;
                }
            }
        }
    }
}

