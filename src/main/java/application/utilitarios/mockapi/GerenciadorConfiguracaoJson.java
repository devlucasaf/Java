package application.utilitarios.mockapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorConfiguracaoJson {

    private final Gson gson;

    public GerenciadorConfiguracaoJson() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    // --- SALVA AS ROTAS EM UM ARQUIVO JSON ---
    public void salvar(List<ConfiguracaoRota> rotas, String caminhoArquivo) throws IOException {
        Path caminho = prepararCaminho(caminhoArquivo);
        Path diretorio = caminho.getParent();

        if (diretorio != null) {
            Files.createDirectories(diretorio);
        }

        try (Writer escritor = Files.newBufferedWriter(caminho, StandardCharsets.UTF_8)) {
            gson.toJson(rotas, escritor);
        }
    }

    // --- CARREGA AS ROTAS DE UM ARQUIVO JSON ---
    public List<ConfiguracaoRota> carregar(String caminhoArquivo) throws IOException {
        Path caminho = validarArquivo(caminhoArquivo);
        Type tipoLista = new TypeToken<List<ConfiguracaoRota>>() {
        }.getType();

        try (Reader leitor = Files.newBufferedReader(caminho, StandardCharsets.UTF_8)) {
            List<ConfiguracaoRota> rotas = gson.fromJson(leitor, tipoLista);
            return rotas == null ? new ArrayList<>() : rotas;
        } catch (JsonParseException excecao) {
            throw new IOException("O arquivo de configuração contém um JSON inválido.", excecao);
        }
    }

    // --- VERIFICA SE UM ARQUIVO EXISTE ---
    public boolean arquivoExiste(String caminhoArquivo) {
        return caminhoArquivo != null && !caminhoArquivo.trim().isEmpty() && Files.isRegularFile(Path.of(caminhoArquivo.trim()));
    }

    // --- PREPARA O CAMINHO DE DESTINO ---
    private Path prepararCaminho(String caminhoArquivo) {
        if (caminhoArquivo == null || caminhoArquivo.trim().isEmpty()) {
            throw new IllegalArgumentException("O caminho do arquivo não pode estar vazio.");
        }

        return Path.of(caminhoArquivo.trim()).toAbsolutePath();
    }

    // --- VALIDA O ARQUIVO DE CONFIGURAÇÃO ---
    private Path validarArquivo(String caminhoArquivo) throws IOException {
        Path caminho = prepararCaminho(caminhoArquivo);

        if (!Files.isRegularFile(caminho)) {
            throw new IOException("O arquivo de configuração não existe.");
        }

        if (!Files.isReadable(caminho)) {
            throw new IOException("O arquivo de configuração não pode ser lido.");
        }

        return caminho;
    }
}

