package application.utilitarios.mockapi;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class ConfiguracaoRota {
    private long                identificador;
    private String              nome;
    private String              metodo;
    private String              caminho;
    private int                 statusResposta;
    private String              tipoConteudo;
    private String              corpoResposta;
    private long                atrasoMilissegundos;
    private boolean             ativa;
    private Map<String, String> cabecalhosResposta;

    public ConfiguracaoRota() {
        this.cabecalhosResposta = new LinkedHashMap<>();
        this.statusResposta = 200;
        this.tipoConteudo = "application/json; charset=UTF-8";
        this.corpoResposta = "{}";
        this.ativa = true;
    }

    public ConfiguracaoRota(long identificador, String nome, String metodo, String caminho, int statusResposta, String tipoConteudo, String corpoResposta, long atrasoMilissegundos) {
        this();
        this.identificador = identificador;
        definirNome(nome);
        definirMetodo(metodo);
        definirCaminho(caminho);
        definirStatusResposta(statusResposta);
        definirTipoConteudo(tipoConteudo);
        definirCorpoResposta(corpoResposta);
        definirAtrasoMilissegundos(atrasoMilissegundos);
    }

    // --- VERIFICA SE A ROTA CORRESPONDE AO MÉTODO E AO CAMINHO RECEBIDOS ---
    public boolean corresponde(String metodoRecebido, String caminhoRecebido) {
        if (!ativa || metodoRecebido == null || caminhoRecebido == null || !metodo.equalsIgnoreCase(metodoRecebido.trim())) {
            return false;
        }

        return extrairParametrosCaminho(caminhoRecebido) != null;
    }

    // --- EXTRAI OS PARÂMETROS VARIÁVEIS DO CAMINHO ---
    public Map<String, String> extrairParametrosCaminho(String caminhoRecebido) {
        if (caminhoRecebido == null) {
            return null;
        }

        String[] partesConfiguradas = separarCaminho(caminho);
        String[] partesRecebidas = separarCaminho(normalizarCaminho(caminhoRecebido));

        if (partesConfiguradas.length != partesRecebidas.length) {
            return null;
        }

        Map<String, String> parametros = new LinkedHashMap<>();

        for (int indice = 0; indice < partesConfiguradas.length; indice++) {
            String parteConfigurada = partesConfiguradas[indice];
            String parteRecebida = partesRecebidas[indice];

            if (parteConfigurada.startsWith("{") && parteConfigurada.endsWith("}") && parteConfigurada.length() > 2) {
                parametros.put(parteConfigurada.substring(1, parteConfigurada.length() - 1), parteRecebida);
            } else if (!parteConfigurada.equals(parteRecebida)) {
                return null;
            }
        }

        return parametros;
    }

    // --- SEPARA UM CAMINHO EM SEGMENTOS ---
    private String[] separarCaminho(String caminhoSeparado) {
        if ("/".equals(caminhoSeparado)) {
            return new String[0];
        }

        return caminhoSeparado.substring(1).split("/");
    }

    // --- NORMALIZA O CAMINHO DA ROTA ---
    private String normalizarCaminho(String caminhoRecebido) {
        if (caminhoRecebido == null || caminhoRecebido.trim().isEmpty()) {
            throw new IllegalArgumentException("O caminho da rota não pode estar vazio.");
        }

        String caminhoNormalizado = caminhoRecebido.trim();

        if (!caminhoNormalizado.startsWith("/")) {
            caminhoNormalizado = "/" + caminhoNormalizado;
        }

        while (caminhoNormalizado.length() > 1 && caminhoNormalizado.endsWith("/")) {
            caminhoNormalizado = caminhoNormalizado.substring(0, caminhoNormalizado.length() - 1);
        }

        return caminhoNormalizado;
    }

    // --- VALIDA O MÉTODO HTTP ---
    private String normalizarMetodo(String metodoRecebido) {
        if (metodoRecebido == null || metodoRecebido.trim().isEmpty()) {
            throw new IllegalArgumentException("O método HTTP não pode estar vazio.");
        }

        String metodoNormalizado = metodoRecebido.trim().toUpperCase();

        switch (metodoNormalizado) {
            case "GET":
            case "POST":
            case "PUT":
            case "PATCH":
            case "DELETE":
            case "OPTIONS":
                return metodoNormalizado;
            default:
                throw new IllegalArgumentException("Método HTTP não permitido: " + metodoNormalizado + ".");
        }
    }

    public long getIdentificador() {
        return identificador;
    }

    public void setIdentificador(long identificador) {
        if (identificador < 0) {
            throw new IllegalArgumentException("O identificador não pode ser negativo.");
        }

        this.identificador = identificador;
    }

    public String getNome() {
        return nome;
    }

    public void definirNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da rota não pode estar vazio.");
        }

        this.nome = nome.trim();
    }

    public String getMetodo() {
        return metodo;
    }

    public void definirMetodo(String metodo) {
        this.metodo = normalizarMetodo(metodo);
    }

    public String getCaminho() {
        return caminho;
    }

    public void definirCaminho(String caminho) {
        this.caminho = normalizarCaminho(caminho);
    }

    public int getStatusResposta() {
        return statusResposta;
    }

    public void definirStatusResposta(int statusResposta) {
        if (statusResposta < 100 || statusResposta > 599) {
            throw new IllegalArgumentException("O status HTTP deve estar entre 100 e 599.");
        }

        this.statusResposta = statusResposta;
    }

    public String getTipoConteudo() {
        return tipoConteudo;
    }

    public void definirTipoConteudo(String tipoConteudo) {
        this.tipoConteudo = tipoConteudo == null || tipoConteudo.trim().isEmpty() ? "application/json; charset=UTF-8" : tipoConteudo.trim();
    }

    public String getCorpoResposta() {
        return corpoResposta;
    }

    public void definirCorpoResposta(String corpoResposta) {
        this.corpoResposta = corpoResposta == null ? "" : corpoResposta;
    }

    public long getAtrasoMilissegundos() {
        return atrasoMilissegundos;
    }

    public void definirAtrasoMilissegundos(long atrasoMilissegundos) {
        if (atrasoMilissegundos < 0 || atrasoMilissegundos > 60000) {
            throw new IllegalArgumentException("O atraso deve estar entre 0 e 60000 milissegundos.");
        }

        this.atrasoMilissegundos = atrasoMilissegundos;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public Map<String, String> getCabecalhosResposta() {
        if (cabecalhosResposta == null) {
            cabecalhosResposta = new LinkedHashMap<>();
        }

        return new LinkedHashMap<>(cabecalhosResposta);
    }

    public void setCabecalhosResposta(Map<String, String> cabecalhosResposta) {
        this.cabecalhosResposta = cabecalhosResposta == null ? new LinkedHashMap<>() : new LinkedHashMap<>(cabecalhosResposta);
    }

    // --- ADICIONA UM CABEÇALHO À RESPOSTA ---
    public void adicionarCabecalhoResposta(String nomeCabecalho, String valor) {
        if (nomeCabecalho == null || nomeCabecalho.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do cabeçalho não pode estar vazio.");
        }

        if (cabecalhosResposta == null) {
            cabecalhosResposta = new LinkedHashMap<>();
        }

        cabecalhosResposta.put(nomeCabecalho.trim(), valor == null ? "" : valor.trim());
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (!(objeto instanceof ConfiguracaoRota)) {
            return false;
        }

        ConfiguracaoRota rota = (ConfiguracaoRota) objeto;
        return identificador == rota.identificador;
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }

    @Override
    public String toString() {
        return identificador + " - " + metodo + " " + caminho + " - " + nome;
    }
}

