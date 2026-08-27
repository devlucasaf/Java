package application.utilitarios.snippet;

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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorJson {

    private final Gson gson;

    public GerenciadorJson() {
        this.gson = criarGson();
    }

    // --- CRIA O CONVERSOR JSON COM SUPORTE PARA LOCALDATETIME ---
    private Gson criarGson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new AdaptadorLocalDateTime())
                .create();
    }

    // --- EXPORTA OS SNIPPETS PARA UM ARQUIVO JSON ---
    public void exportar(List<Snippet> snippets, String caminhoArquivo) throws IOException {
        validarCaminho(caminhoArquivo);

        Path caminho = Path.of(caminhoArquivo).toAbsolutePath();
        Path diretorio = caminho.getParent();

        if (diretorio != null) {
            Files.createDirectories(diretorio);
        }

        try (Writer escritor = Files.newBufferedWriter(caminho, StandardCharsets.UTF_8)) {
            gson.toJson(snippets, escritor);
        }
    }

    // --- IMPORTA OS SNIPPETS DE UM ARQUIVO JSON ---
    public List<Snippet> importar(String caminhoArquivo) throws IOException {
        validarCaminho(caminhoArquivo);

        Path caminho = Path.of(caminhoArquivo);

        if (!Files.exists(caminho)) {
            throw new IOException("O arquivo informado não existe.");
        }

        Type tipoLista = new TypeToken<List<Snippet>>() {
        }.getType();

        try (Reader leitor = Files.newBufferedReader(caminho, StandardCharsets.UTF_8)) {
            List<Snippet> snippets = gson.fromJson(leitor, tipoLista);
            return snippets == null ? new ArrayList<>() : snippets;
        } catch (JsonParseException excecao) {
            throw new IOException("O arquivo JSON possui conteúdo inválido.", excecao);
        }
    }

    // --- VERIFICA SE O ARQUIVO INFORMADO EXISTE ---
    public boolean arquivoExiste(String caminhoArquivo) {
        return caminhoArquivo != null && !caminhoArquivo.trim().isEmpty() && Files.exists(Path.of(caminhoArquivo));
    }

    // --- VALIDA O CAMINHO DO ARQUIVO ---
    private void validarCaminho(String caminhoArquivo) {
        if (caminhoArquivo == null || caminhoArquivo.trim().isEmpty()) {
            throw new IllegalArgumentException("O caminho do arquivo não pode estar vazio.");
        }
    }
}

