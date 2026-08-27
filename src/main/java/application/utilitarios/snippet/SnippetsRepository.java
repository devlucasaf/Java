package application.utilitarios.snippet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SnippetsRepository {

    private final List<Snippet> snippets;
    private long                proximoIdentificador;

    public SnippetsRepository() {
        this.snippets = new ArrayList<>();
        this.proximoIdentificador = 1;
    }

    // --- ADICIONA UM NOVO SNIPPET AO REPOSITÓRIO ---
    public Snippet adicionar(String titulo, String descricao, String linguagem, String codigo, List<String> tags) {
        Snippet snippet = new Snippet(proximoIdentificador, titulo, descricao, linguagem, codigo, tags);
        snippets.add(snippet);
        proximoIdentificador++;
        return snippet;
    }

    // --- ADICIONA UM SNIPPET IMPORTADO AO REPOSITÓRIO ---
    public boolean adicionarImportado(Snippet snippet) {
        if (snippet == null) {
            return false;
        }

        if (snippet.getIdentificador() <= 0 || buscarPorIdentificador(snippet.getIdentificador()) != null) {
            snippet.setIdentificador(proximoIdentificador);
        }

        snippets.add(snippet);
        atualizarProximoIdentificador();
        return true;
    }

    // --- SUBSTITUI TODOS OS SNIPPETS DO REPOSITÓRIO ---
    public void substituirTodos(List<Snippet> novosSnippets) {
        snippets.clear();
        proximoIdentificador = 1;

        if (novosSnippets != null) {
            for (Snippet snippet : novosSnippets) {
                adicionarImportado(snippet);
            }
        }

        atualizarProximoIdentificador();
    }

    // --- BUSCA UM SNIPPET PELO IDENTIFICADOR ---
    public Snippet buscarPorIdentificador(long identificador) {
        for (Snippet snippet : snippets) {
            if (snippet.getIdentificador() == identificador) {
                return snippet;
            }
        }

        return null;
    }

    // --- PESQUISA SNIPPETS POR PALAVRA-CHAVE ---
    public List<Snippet> pesquisarPorPalavraChave(String palavraChave) {
        List<Snippet> resultados = new ArrayList<>();

        for (Snippet snippet : snippets) {
            if (snippet.contemPalavraChave(palavraChave)) {
                resultados.add(snippet);
            }
        }

        ordenarPorTitulo(resultados);
        return resultados;
    }

    // --- PESQUISA SNIPPETS POR LINGUAGEM ---
    public List<Snippet> pesquisarPorLinguagem(String linguagem) {
        List<Snippet> resultados = new ArrayList<>();

        for (Snippet snippet : snippets) {
            if (snippet.possuiLinguagem(linguagem)) {
                resultados.add(snippet);
            }
        }

        ordenarPorTitulo(resultados);
        return resultados;
    }

    // --- PESQUISA SNIPPETS POR TAG ---
    public List<Snippet> pesquisarPorTag(String tag) {
        List<Snippet> resultados = new ArrayList<>();

        for (Snippet snippet : snippets) {
            if (snippet.possuiTag(tag)) {
                resultados.add(snippet);
            }
        }

        ordenarPorTitulo(resultados);
        return resultados;
    }

    // --- REMOVE UM SNIPPET PELO IDENTIFICADOR ---
    public boolean remover(long identificador) {
        Snippet snippet = buscarPorIdentificador(identificador);

        if (snippet == null) {
            return false;
        }

        return snippets.remove(snippet);
    }

    // --- RETORNA TODOS OS SNIPPETS ORDENADOS PELO TÍTULO ---
    public List<Snippet> listarTodos() {
        List<Snippet> copia = new ArrayList<>(snippets);
        ordenarPorTitulo(copia);
        return Collections.unmodifiableList(copia);
    }

    // --- ORDENA UMA LISTA DE SNIPPETS PELO TÍTULO ---
    private void ordenarPorTitulo(List<Snippet> lista) {
        lista.sort(Comparator.comparing(Snippet::getTitulo, String.CASE_INSENSITIVE_ORDER));
    }

    // --- ATUALIZA O PRÓXIMO IDENTIFICADOR DISPONÍVEL ---
    private void atualizarProximoIdentificador() {
        long maiorIdentificador = 0;

        for (Snippet snippet : snippets) {
            if (snippet.getIdentificador() > maiorIdentificador) {
                maiorIdentificador = snippet.getIdentificador();
            }
        }

        proximoIdentificador = maiorIdentificador + 1;
    }

    public int getQuantidade() {
        return snippets.size();
    }

    public boolean isVazio() {
        return snippets.isEmpty();
    }
}

