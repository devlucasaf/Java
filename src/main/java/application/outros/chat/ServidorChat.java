package application.outros.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorChat {

    private final int porta;
    private final ExecutorService pool = Executors.newCachedThreadPool();

    private final Map<String, Set<ManipuladorCliente>> salas = new ConcurrentHashMap<>();

    public ServidorChat(int porta) {
        this.porta = porta;
    }

    public void iniciar() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(porta)) {
            System.out.println("Servidor de chat ouvindo na porta " + porta + "...");

            while (true) {
                Socket socket = serverSocket.accept();
                ManipuladorCliente manipulador = new ManipuladorCliente(socket, this);
                pool.execute(manipulador);
            }
        }
    }

    void entrarNaSala(String sala, ManipuladorCliente cliente) {
        salas.computeIfAbsent(sala, s -> ConcurrentHashMap.newKeySet()).add(cliente);
    }

    void sairDaSala(String sala, ManipuladorCliente cliente) {
        Set<ManipuladorCliente> membros = salas.get(sala);
        if (membros != null) {
            membros.remove(cliente);
        }
    }

    List<String> listarUsuariosDaSala(String sala) {
        List<String> nomes = new ArrayList<>();
        Set<ManipuladorCliente> membros = salas.get(sala);
        if (membros != null) {
            for (ManipuladorCliente c : membros) {
                nomes.add(c.getNomeUsuario());
            }
        }
        return nomes;
    }

    void enviarParaSala(String sala, String mensagem, ManipuladorCliente remetente) {
        Set<ManipuladorCliente> membros = salas.get(sala);
        if (membros == null) {
            return;
        }

        for (ManipuladorCliente cliente : membros) {
            if (cliente != remetente) {
                cliente.enviar(mensagem);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        int porta = args.length > 0 ? Integer.parseInt(args[0]) : 5000;
        new ServidorChat(porta).iniciar();
    }
}
