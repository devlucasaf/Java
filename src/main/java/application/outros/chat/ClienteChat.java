package application.outros.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteChat {

    public static void main(String[] args) throws IOException {
        String host = args.length > 0 ? args[0] : "localhost";
        int porta = args.length > 1 ? Integer.parseInt(args[1]) : 5000;

        try (Socket socket = new Socket(host, porta)) {
            System.out.println("Conectado ao servidor " + host + ":" + porta);

            BufferedReader entradaServidor = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter saidaServidor = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader entradaTeclado = new BufferedReader(new InputStreamReader(System.in));

            Thread threadRecebimento = new Thread(() -> {
                try {
                    String linha;
                    while ((linha = entradaServidor.readLine()) != null) {
                        System.out.println(linha);
                    }
                } catch (IOException e) {
                    System.out.println("Conexao com o servidor encerrada.");
                }
            });
            threadRecebimento.setDaemon(true);
            threadRecebimento.start();

            System.out.println("Digite mensagens (ou /nome, /sala, /usuarios, /sair):");
            String linhaDigitada;
            while ((linhaDigitada = entradaTeclado.readLine()) != null) {
                saidaServidor.println(linhaDigitada);
                if (linhaDigitada.equals("/sair")) {
                    break;
                }
            }
        }
    }
}
