package math.calculadora.completa.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HistoricoService {

    private static final Path ARQUIVO_HISTORICO = Paths.get(
            System.getProperty("user.home"), ".calculadora_historico.txt"
    );

    public void salvar(List<String> historico) throws IOException {
        Files.write(ARQUIVO_HISTORICO, historico);
    }

    public List<String> carregar() throws IOException {
        if (!Files.exists(ARQUIVO_HISTORICO)) {
            return new ArrayList<>();
        }
        return Files.readAllLines(ARQUIVO_HISTORICO);
    }

    public void adicionarEntrada(String novaEntrada) throws IOException {
        List<String> historico = carregar();
        historico.add(novaEntrada);
        salvar(historico);
    }

    public void limpar() throws IOException {
        if (Files.exists(ARQUIVO_HISTORICO)) {
            Files.delete(ARQUIVO_HISTORICO);
        }
    }
}
