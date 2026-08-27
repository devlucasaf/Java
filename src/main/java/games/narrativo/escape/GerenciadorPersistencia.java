package games.narrativo.escape;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GerenciadorPersistencia {

    // --- SALVA O ESTADO COMPLETO DO JOGO EM UM ARQUIVO ---
    public void salvar(EstadoJogo estado, String caminhoArquivo) throws IOException {
        if (estado == null) {
            throw new IllegalArgumentException("O estado do jogo não pode ser nulo.");
        }

        try (ObjectOutputStream saida = new ObjectOutputStream(new FileOutputStream(caminhoArquivo))) {
            saida.writeObject(estado);
        }
    }

    // --- CARREGA O ESTADO COMPLETO DO JOGO DE UM ARQUIVO ---
    public EstadoJogo carregar(String caminhoArquivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(caminhoArquivo))) {
            Object objeto = entrada.readObject();

            if (!(objeto instanceof EstadoJogo)) {
                throw new IOException("O arquivo não contém um estado de jogo válido.");
            }

            return (EstadoJogo) objeto;
        }
    }
}

