package application.utilitarios.snippet;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Snippet {

    private long            identificador;
    private String          titulo;
    private String          descricao;
    private String          linguagem;
    private String          codigo;
    private List<String>    tags;
    private LocalDateTime   dataCriacao;
    private LocalDateTime   dataAtualizacao;

    public Snippet() {
        this.tags = new ArrayList<>();
    }

    public Snippet(long identificador, String titulo, String descricao, String linguagem, String codigo, List<String> tags) {
        validarTextoObrigatorio(titulo, "O título");
        validarTextoObrigatorio(linguagem, "A linguagem");
        validarTextoObrigatorio(codigo, "O código");

        this.identificador = identificador;
        this.titulo = titulo.trim();
        this.descricao = descricao == null ? "" : descricao.trim();
        this.linguagem = linguagem.trim();
        this.codigo = codigo;
        this.tags = normalizarTags(tags);
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    // --- ATUALIZA AS INFORMAÇÕES DO SNIPPET ---
    public void atualizar(String titulo, String descricao, String linguagem, String codigo, List<String> tags) {
        validarTextoObrigatorio(titulo, "O título");
        validarTextoObrigatorio(linguagem, "A linguagem");
        validarTextoObrigatorio(codigo, "O código");

        this.titulo = titulo.trim();
        this.descricao = descricao == null ? "" : descricao.trim();
        this.linguagem = linguagem.trim();
        this.codigo = codigo;
        this.tags = normalizarTags(tags);
        this.dataAtualizacao = LocalDateTime.now();
    }

    // --- ADICIONA UMA TAG AO SNIPPET ---
    public boolean adicionarTag(String tag) {
        if (tag == null || tag.trim().isEmpty()) {
            return false;
        }

        String tagNormalizada = tag.trim().toLowerCase();

        if (tags.contains(tagNormalizada)) {
            return false;
        }

        tags.add(tagNormalizada);
        dataAtualizacao = LocalDateTime.now();
        return true;
    }

    // --- REMOVE UMA TAG DO SNIPPET ---
    public boolean removerTag(String tag) {
        if (tag == null) {
            return false;
        }

        boolean removida = tags.remove(tag.trim().toLowerCase());

        if (removida) {
            dataAtualizacao = LocalDateTime.now();
        }

        return removida;
    }

    // --- VERIFICA SE O SNIPPET CONTÉM A PALAVRA-CHAVE ---
    public boolean contemPalavraChave(String palavraChave) {
        if (palavraChave == null || palavraChave.trim().isEmpty()) {
            return true;
        }

        String termo = palavraChave.trim().toLowerCase();

        if (titulo.toLowerCase().contains(termo) || descricao.toLowerCase().contains(termo)
                || linguagem.toLowerCase().contains(termo) || codigo.toLowerCase().contains(termo)) {
            return true;
        }

        for (String tag : tags) {
            if (tag.toLowerCase().contains(termo)) {
                return true;
            }
        }

        return false;
    }

    // --- VERIFICA SE O SNIPPET POSSUI A LINGUAGEM INFORMADA ---
    public boolean possuiLinguagem(String linguagemPesquisada) {
        return linguagemPesquisada != null && linguagem.equalsIgnoreCase(linguagemPesquisada.trim());
    }

    // --- VERIFICA SE O SNIPPET POSSUI A TAG INFORMADA ---
    public boolean possuiTag(String tagPesquisada) {
        if (tagPesquisada == null) {
            return false;
        }

        for (String tag : tags) {
            if (tag.equalsIgnoreCase(tagPesquisada.trim())) {
                return true;
            }
        }

        return false;
    }

    // --- NORMALIZA A LISTA DE TAGS E REMOVE VALORES REPETIDOS ---
    private List<String> normalizarTags(List<String> tagsRecebidas) {
        List<String> tagsNormalizadas = new ArrayList<>();

        if (tagsRecebidas == null) {
            return tagsNormalizadas;
        }

        for (String tag : tagsRecebidas) {
            if (tag != null && !tag.trim().isEmpty()) {
                String tagNormalizada = tag.trim().toLowerCase();

                if (!tagsNormalizadas.contains(tagNormalizada)) {
                    tagsNormalizadas.add(tagNormalizada);
                }
            }
        }

        return tagsNormalizadas;
    }

    // --- VALIDA UM TEXTO OBRIGATÓRIO ---
    private void validarTextoObrigatorio(String texto, String nomeCampo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException(nomeCampo + " não pode estar vazio.");
        }
    }

    public long getIdentificador() {
        return identificador;
    }

    public void setIdentificador(long identificador) {
        this.identificador = identificador;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getLinguagem() {
        return linguagem;
    }

    public String getCodigo() {
        return codigo;
    }

    public List<String> getTags() {
        return Collections.unmodifiableList(tags);
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    // --- FORMATA A DATA DE CRIAÇÃO PARA EXIBIÇÃO ---
    public String getDataCriacaoFormatada() {
        return formatarData(dataCriacao);
    }

    // --- FORMATA A DATA DE ATUALIZAÇÃO PARA EXIBIÇÃO ---
    public String getDataAtualizacaoFormatada() {
        return formatarData(dataAtualizacao);
    }

    // --- FORMATA UMA DATA ---
    private String formatarData(LocalDateTime data) {
        if (data == null) {
            return "Não informada";
        }

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return data.format(formatador);
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (!(objeto instanceof Snippet)) {
            return false;
        }

        Snippet snippet = (Snippet) objeto;
        return identificador == snippet.identificador;
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }

    @Override
    public String toString() {
        return identificador + " - " + titulo + " [" + linguagem + "]";
    }
}

