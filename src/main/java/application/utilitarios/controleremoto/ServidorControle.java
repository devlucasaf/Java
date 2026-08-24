package application.utilitarios.controleremoto;

import javax.imageio.ImageIO;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorControle {

    private final int           porta;
    private final Robot         robot;
    private volatile boolean    rodando = true;

    public ServidorControle(int porta) throws AWTException {
        this.porta = porta;
        this.robot = new Robot();
    }

    public void iniciar() throws IOException {
        ServerSocket servidor = new ServerSocket(porta);

        try {
            System.out.println("Servidor de controle na porta " + porta);
            System.out.println("Comandos: SCREEN | KEY <codigo> | MOUSE <x> <y> | CLICK | SAIR");
            while (rodando) {
                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado: " + cliente.getInetAddress());
                new Thread(() -> tratar(cliente)).start();
            }
        } finally {
            servidor.close();
        }
    }

    private void tratar(Socket cliente) {
        DataInputStream in = null;
        DataOutputStream out = null;

        try {
            in = new DataInputStream(cliente.getInputStream());
            out = new DataOutputStream(cliente.getOutputStream());

            while (!cliente.isClosed()) {
                String comando = in.readUTF();
                System.out.println("<- " + comando);
                String[] partes = comando.split("\\s+");
                switch (partes[0].toUpperCase()) {
                    case "SCREEN":
                        enviarScreen(out);
                        break;
                    case "KEY":
                        robot.keyPress(Integer.parseInt(partes[1]));
                        robot.keyRelease(Integer.parseInt(partes[1]));
                        out.writeUTF("OK");
                        break;
                    case "MOUSE":
                        robot.mouseMove(Integer.parseInt(partes[1]), Integer.parseInt(partes[2]));
                        out.writeUTF("OK");
                        break;
                    case "CLICK":
                        robot.mousePress(java.awt.event.InputEvent.BUTTON1_DOWN_MASK);
                        robot.mouseRelease(java.awt.event.InputEvent.BUTTON1_DOWN_MASK);
                        out.writeUTF("OK");
                        break;
                    case "SAIR":
                        out.writeUTF("BYE");
                        return;
                    default:
                        out.writeUTF("ERRO comando desconhecido");
                }
            }
        } catch (IOException e) {
            System.out.println("Cliente desconectou: " + e.getMessage());
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ignorado) {
                }
            }

            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignorado) {
                }
            }

            try {
                cliente.close();
            } catch (IOException ignorado) {
            }
        }
    }

    private void enviarScreen(DataOutputStream out) throws IOException {
        Rectangle tela = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage img = robot.createScreenCapture(tela);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ImageIO.write(img, "png", buffer);
        byte[] bytes = buffer.toByteArray();
        out.writeInt(bytes.length);
        out.write(bytes);
        System.out.println("-> screenshot enviado (" + bytes.length + " bytes)");
    }

    public static void main(String[] args) throws Exception {
        int porta = args.length > 0 ? Integer.parseInt(args[0]) : 5555;
        new ServidorControle(porta).iniciar();
    }
}

