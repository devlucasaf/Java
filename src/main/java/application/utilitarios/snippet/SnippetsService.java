package application.utilitarios.snippet;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class SnippetsService {

    private final SnippetsRepository repositorio;
    private final GerenciadorJson gerenciadorJson;
    private final Path caminhoArquivoPadrao;

    public SnippetsService() {
        this(Path.of("snippets.json"));
    }

    public SnippetsService(String caminhoArquivoPadrao) {
        this(Path.of(validarCaminhoTexto(caminhoArquivoPadrao)));
    }

    public SnippetsService(Path caminhoArquivoPadrao) {
        if (caminhoArquivoPadrao == null) {
            throw new IllegalArgumentException("O caminho do arquivo padrão não pode ser nulo.");
        }

        this.repositorio = new SnippetsRepository();
        this.gerenciadorJson = new GerenciadorJson();
        this.caminhoArquivoPadrao = caminhoArquivoPadrao;
    }

    // --- CARREGA OS SNIPPETS DO ARQUIVO PADRÃO ---
    public void carregarDados() throws IOException {
        if (!gerenciadorJson.arquivoExiste(caminhoArquivoPadrao.toString())) {
            return;
        }

        List<Snippet> snippetsCarregados = gerenciadorJson.importar(caminhoArquivoPadrao.toString());
        repositorio.substituirTodos(snippetsCarregados);
    }

    // --- CADASTRA UM NOVO SNIPPET E SALVA OS DADOS ---
    public Snippet cadastrar(String titulo, String descricao, String linguagem, String codigo, List<String> tags) throws IOException {
        validarDadosSnippet(titulo, linguagem, codigo);

        Snippet snippet = repositorio.adicionar(titulo.trim(), normalizarDescricao(descricao), linguagem.trim(), codigo, tags);

        try {
            salvarDados();
            return snippet;
        } catch (IOException excecao) {
            repositorio.remover(snippet.getIdentificador());
            throw new IOException("Não foi possível salvar o novo snippet.", excecao);
        }
    }

    // --- ATUALIZA UM SNIPPET EXISTENTE E SALVA OS DADOS ---
    public boolean atualizar(long identificador, String titulo, String descricao, String linguagem, String codigo, List<String> tags) throws IOException {
        validarIdentificador(identificador);
        validarDadosSnippet(titulo, linguagem, codigo);

        Snippet snippet = repositorio.buscarPorIdentificador(identificador);

        if (snippet == null) {
            return false;
        }

        String tituloAnterior = snippet.getTitulo();
        String descricaoAnterior = snippet.getDescricao();
        String linguagemAnterior = snippet.getLinguagem();
        String codigoAnterior = snippet.getCodigo();
        List<String> tagsAnteriores = List.copyOf(snippet.getTags());

        snippet.atualizar(titulo.trim(), normalizarDescricao(descricao), linguagem.trim(), codigo, tags);

        try {
            salvarDados();
            return true;
        } catch (IOException excecao) {
            snippet.atualizar(tituloAnterior, descricaoAnterior, linguagemAnterior, codigoAnterior, tagsAnteriores);
            throw new IOException("Não foi possível salvar as alterações do snippet.", excecao);
        }
    }

    // --- REMOVE UM SNIPPET E SALVA OS DADOS ---
    public boolean remover(long identificador) throws IOException {
        validarIdentificador(identificador);

        Snippet snippet = repositorio.buscarPorIdentificador(identificador);

        if (snippet == null) {
            return false;
        }

        boolean removido = repositorio.remover(identificador);

        if (!removido) {
            return false;
        }

        try {
            salvarDados();
            return true;
        } catch (IOException excecao) {
            repositorio.adicionarImportado(snippet);
            throw new IOException("Não foi possível salvar a remoção do snippet.", excecao);
        }
    }

    // --- BUSCA UM SNIPPET PELO IDENTIFICADOR ---
    public Snippet buscarPorIdentificador(long identificador) {
        validarIdentificador(identificador);
        return repositorio.buscarPorIdentificador(identificador);
    }

    // --- PESQUISA SNIPPETS POR PALAVRA-CHAVE ---
    public List<Snippet> pesquisarPorPalavraChave(String palavraChave) {
        if (palavraChave == null || palavraChave.trim().isEmpty()) {
            return listarTodos();
        }

        return repositorio.pesquisarPorPalavraChave(palavraChave.trim());
    }

    // --- PESQUISA SNIPPETS PELA LINGUAGEM ---
    public List<Snippet> pesquisarPorLinguagem(String linguagem) {
        if (linguagem == null || linguagem.trim().isEmpty()) {
            return listarTodos();
        }

        return repositorio.pesquisarPorLinguagem(linguagem.trim());
    }

    // --- PESQUISA SNIPPETS POR TAG ---
    public List<Snippet> pesquisarPorTag(String tag) {
        if (tag == null || tag.trim().isEmpty()) {
            return listarTodos();
        }

        return repositorio.pesquisarPorTag(tag.trim());
    }

    // --- REALIZA UMA PESQUISA COMBINANDO PALAVRA-CHAVE, LINGUAGEM E TAG ---
    public List<Snippet> pesquisar(String palavraChave, String linguagem, String tag) {
        return repositorio.listarTodos().stream()
                .filter(snippet -> palavraChave == null || palavraChave.trim().isEmpty() || snippet.contemPalavraChave(palavraChave))
                .filter(snippet -> linguagem == null || linguagem.trim().isEmpty() || snippet.possuiLinguagem(linguagem))
                .filter(snippet -> tag == null || tag.trim().isEmpty() || snippet.possuiTag(tag))
                .toList();
    }

    // --- LISTA TODOS OS SNIPPETS ---
    public List<Snippet> listarTodos() {
        return repositorio.listarTodos();
    }

    // --- SALVA OS SNIPPETS NO ARQUIVO PADRÃO ---
    public void salvarDados() throws IOException {
        gerenciadorJson.exportar(repositorio.listarTodos(), caminhoArquivoPadrao.toString());
    }

    // --- EXPORTA OS SNIPPETS PARA OUTRO ARQUIVO JSON ---
    public void exportar(String caminhoArquivo) throws IOException {
        exportar(Path.of(validarCaminhoTexto(caminhoArquivo)));
    }

    // --- EXPORTA OS SNIPPETS PARA OUTRO ARQUIVO JSON ---
    public void exportar(Path caminhoArquivo) throws IOException {
        if (caminhoArquivo == null) {
            throw new IllegalArgumentException("O caminho de exportação não pode ser nulo.");
        }

        gerenciadorJson.exportar(repositorio.listarTodos(), caminhoArquivo.toString());
    }

    // --- IMPORTA SNIPPETS PRESERVANDO OS REGISTROS EXISTENTES ---
    public int importarEAdicionar(String caminhoArquivo) throws IOException {
        return importarEAdicionar(Path.of(validarCaminhoTexto(caminhoArquivo)));
    }

    // --- IMPORTA SNIPPETS PRESERVANDO OS REGISTROS EXISTENTES ---
    public int importarEAdicionar(Path caminhoArquivo) throws IOException {
        validarCaminho(caminhoArquivo);

        List<Snippet> snippetsImportados = gerenciadorJson.importar(caminhoArquivo.toString());
        int quantidadeAdicionada = 0;

        for (Snippet snippet : snippetsImportados) {
            if (snippet != null && repositorio.adicionarImportado(snippet)) {
                quantidadeAdicionada++;
            }
        }

        salvarDados();
        return quantidadeAdicionada;
    }

    // --- IMPORTA SNIPPETS E SUBSTITUI OS REGISTROS EXISTENTES ---
    public int importarESubstituir(String caminhoArquivo) throws IOException {
        return importarESubstituir(Path.of(validarCaminhoTexto(caminhoArquivo)));
    }

    // --- IMPORTA SNIPPETS E SUBSTITUI OS REGISTROS EXISTENTES ---
    public int importarESubstituir(Path caminhoArquivo) throws IOException {
        validarCaminho(caminhoArquivo);

        List<Snippet> snippetsImportados = gerenciadorJson.importar(caminhoArquivo.toString());
        List<Snippet> snippetsAnteriores = repositorio.listarTodos();

        repositorio.substituirTodos(snippetsImportados);

        try {
            salvarDados();
            return snippetsImportados.size();
        } catch (IOException excecao) {
            repositorio.substituirTodos(snippetsAnteriores);
            throw new IOException("Não foi possível salvar os snippets importados.", excecao);
        }
    }

    // --- ADICIONA UMA TAG A UM SNIPPET ---
    public boolean adicionarTag(long identificador, String tag) throws IOException {
        validarIdentificador(identificador);

        if (tag == null || tag.trim().isEmpty()) {
            throw new IllegalArgumentException("A tag não pode estar vazia.");
        }

        Snippet snippet = repositorio.buscarPorIdentificador(identificador);

        if (snippet == null || !snippet.adicionarTag(tag)) {
            return false;
        }

        salvarDados();
        return true;
    }

    // --- REMOVE UMA TAG DE UM SNIPPET ---
    public boolean removerTag(long identificador, String tag) throws IOException {
        validarIdentificador(identificador);

        if (tag == null || tag.trim().isEmpty()) {
            throw new IllegalArgumentException("A tag não pode estar vazia.");
        }

        Snippet snippet = repositorio.buscarPorIdentificador(identificador);

        if (snippet == null || !snippet.removerTag(tag)) {
            return false;
        }

        salvarDados();
        return true;
    }

    // --- RETORNA A QUANTIDADE DE SNIPPETS CADASTRADOS ---
    public int getQuantidadeSnippets() {
        return repositorio.getQuantidade();
    }

    // --- VERIFICA SE O REPOSITÓRIO ESTÁ VAZIO ---
    public boolean isVazio() {
        return repositorio.isVazio();
    }

    // --- RETORNA O CAMINHO DO ARQUIVO PADRÃO ---
    public Path getCaminhoArquivoPadrao() {
        return caminhoArquivoPadrao;
    }

    // --- VALIDA OS DADOS OBRIGATÓRIOS DE UM SNIPPET ---
    private void validarDadosSnippet(String titulo, String linguagem, String codigo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("O título não pode estar vazio.");
        }

        if (linguagem == null || linguagem.trim().isEmpty()) {
            throw new IllegalArgumentException("A linguagem não pode estar vazia.");
        }

        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("O código não pode estar vazio.");
        }
    }

    // --- VALIDA UM IDENTIFICADOR ---
    private void validarIdentificador(long identificador) {
        if (identificador <= 0) {
            throw new IllegalArgumentException("O identificador deve ser maior que zero.");
        }
    }

    // --- VALIDA UM CAMINHO RECEBIDO COMO TEXTO ---
    private static String validarCaminhoTexto(String caminhoArquivo) {
        if (caminhoArquivo == null || caminhoArquivo.trim().isEmpty()) {
            throw new IllegalArgumentException("O caminho do arquivo não pode estar vazio.");
        }

        return caminhoArquivo.trim();
    }

    // --- VALIDA UM CAMINHO DE ARQUIVO ---
    private void validarCaminho(Path caminhoArquivo) {
        if (caminhoArquivo == null) {
            throw new IllegalArgumentException("O caminho do arquivo não pode ser nulo.");
        }
    }

    // --- NORMALIZA A DESCRIÇÃO DO SNIPPET ---
    private String normalizarDescricao(String descricao) {
        return descricao == null ? "" : descricao.trim();
    }
}

