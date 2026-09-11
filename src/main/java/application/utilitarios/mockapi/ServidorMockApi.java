package application.utilitarios.mockapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorMockApi {

    private final RepositorioRotas  repositorioRotas;
    private final Gson              gson;
    private HttpServer              servidor;
    private ExecutorService         executor;
    private String                  endereco;
    private int                     porta;

    public ServidorMockApi(RepositorioRotas repositorioRotas) {
        if (repositorioRotas == null) {
            throw new IllegalArgumentException("O repositório de rotas não pode ser nulo.");
        }

        this.repositorioRotas = repositorioRotas;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    // --- INICIA O SERVIDOR SOMENTE NO ENDEREÇO LOCAL ---
    public synchronized void iniciar(int porta) throws IOException {
        iniciar("127.0.0.1", porta);
    }

    // --- INICIA O SERVIDOR NO ENDEREÇO INFORMADO ---
    public synchronized void iniciar(String endereco, int porta) throws IOException {
        if (isExecutando()) {
            throw new IllegalStateException("O servidor já está em execução.");
        }

        if (endereco == null || endereco.trim().isEmpty()) {
            throw new IllegalArgumentException("O endereço do servidor não pode estar vazio.");
        }

        if (!endereco.equals("127.0.0.1") && !endereco.equals("localhost")) {
            throw new IllegalArgumentException("Por segurança, o servidor mock somente pode utilizar localhost ou 127.0.0.1.");
        }

        if (porta < 1 || porta > 65535) {
            throw new IllegalArgumentException("A porta deve estar entre 1 e 65535.");
        }

        this.endereco = endereco.trim();
        this.porta = porta;
        this.servidor = HttpServer.create(new InetSocketAddress(this.endereco, porta), 0);
        this.executor = Executors.newVirtualThreadPerTaskExecutor();

        servidor.createContext("/", this::processarRequisicao);
        servidor.setExecutor(executor);
        servidor.start();

        System.out.println("Servidor iniciado em http://" + this.endereco + ":" + porta + ".");
    }

    // --- INTERROMPE O SERVIDOR ---
    public synchronized void parar() {
        if (servidor != null) {
            servidor.stop(0);
            servidor = null;
        }

        if (executor != null) {
            executor.shutdownNow();
            executor = null;
        }

        System.out.println("Servidor mock interrompido.");
    }

    // --- PROCESSA UMA REQUISIÇÃO HTTP ---
    private void processarRequisicao(HttpExchange troca) {
        long inicio = System.currentTimeMillis();
        String metodo = troca.getRequestMethod();
        String caminho = troca.getRequestURI().getPath();

        try {
            adicionarCabecalhosCors(troca.getResponseHeaders());

            if ("OPTIONS".equalsIgnoreCase(metodo)) {
                enviarResposta(troca, new RespostaMock(204, "text/plain; charset=UTF-8", "", Map.of(), 0));
                return;
            }

            ConfiguracaoRota rota = repositorioRotas.buscarCorrespondente(metodo, caminho);

            if (rota == null) {
                enviarErro(troca, 404, "Rota não encontrada.", metodo, caminho);
                return;
            }

            Map<String, String> parametrosCaminho = rota.extrairParametrosCaminho(caminho);
            Map<String, List<String>> parametrosConsulta = extrairParametrosConsulta(troca.getRequestURI().getRawQuery());
            Map<String, List<String>> cabecalhos = copiarCabecalhos(troca.getRequestHeaders());
            String corpoRequisicao = lerCorpo(troca.getRequestBody());

            ContextoRequisicao contexto = new ContextoRequisicao(metodo, caminho, corpoRequisicao, parametrosCaminho, parametrosConsulta, cabecalhos);
            RespostaMock resposta = criarResposta(rota, contexto);

            enviarResposta(troca, resposta);
        } catch (IllegalArgumentException excecao) {
            enviarErroSeguro(troca, 400, excecao.getMessage(), metodo, caminho);
        } catch (Exception excecao) {
            enviarErroSeguro(troca, 500, "O servidor mock não conseguiu processar a requisição.", metodo, caminho);
            excecao.printStackTrace();
        } finally {
            long duracao = System.currentTimeMillis() - inicio;
            registrarRequisicao(metodo, caminho, duracao);
            troca.close();
        }
    }

    // --- CRIA A RESPOSTA A PARTIR DA CONFIGURAÇÃO DA ROTA ---
    private RespostaMock criarResposta(ConfiguracaoRota rota, ContextoRequisicao contexto) {
        String corpo = substituirVariaveis(rota.getCorpoResposta(), contexto);
        return new RespostaMock(rota.getStatusResposta(), rota.getTipoConteudo(), corpo, rota.getCabecalhosResposta(), rota.getAtrasoMilissegundos());
    }

    // --- SUBSTITUI VARIÁVEIS DINÂMICAS NO CORPO DA RESPOSTA ---
    private String substituirVariaveis(String corpoOriginal, ContextoRequisicao contexto) {
        String corpo = corpoOriginal == null ? "" : corpoOriginal;

        for (Map.Entry<String, String> parametro : contexto.getParametrosCaminho().entrySet()) {
            corpo = corpo.replace("{{caminho." + parametro.getKey() + "}}", escaparParaJson(parametro.getValue()));
        }

        for (Map.Entry<String, List<String>> parametro : contexto.getParametrosConsulta().entrySet()) {
            String valor = parametro.getValue().isEmpty() ? "" : parametro.getValue().get(0);
            corpo = corpo.replace("{{consulta." + parametro.getKey() + "}}", escaparParaJson(valor));
        }

        corpo = corpo.replace("{{metodo}}", escaparParaJson(contexto.getMetodo()));
        corpo = corpo.replace("{{caminho}}", escaparParaJson(contexto.getCaminho()));
        corpo = corpo.replace("{{corpo}}", escaparParaJson(contexto.getCorpo()));
        corpo = corpo.replace("{{dataHora}}", escaparParaJson(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));

        return corpo;
    }

    // --- ESCAPA UM VALOR PARA UTILIZAÇÃO DENTRO DE UMA STRING JSON ---
    private String escaparParaJson(String valor) {
        String json = gson.toJson(valor == null ? "" : valor);
        return json.length() >= 2 ? json.substring(1, json.length() - 1) : "";
    }

    // --- ENVIA UMA RESPOSTA HTTP ---
    private void enviarResposta(HttpExchange troca, RespostaMock resposta) throws IOException {
        aplicarAtraso(resposta.getAtrasoMilissegundos());

        Headers cabecalhos = troca.getResponseHeaders();
        cabecalhos.set("Content-Type", resposta.getTipoConteudo());
        adicionarCabecalhosCors(cabecalhos);

        for (Map.Entry<String, String> cabecalho : resposta.getCabecalhos().entrySet()) {
            if (!cabecalho.getKey().equalsIgnoreCase("Content-Length")) {
                cabecalhos.set(cabecalho.getKey(), cabecalho.getValue());
            }
        }

        byte[] conteudo = resposta.getCorpo().getBytes(StandardCharsets.UTF_8);

        if (resposta.getStatus() == 204 || resposta.getStatus() == 304) {
            troca.sendResponseHeaders(resposta.getStatus(), -1);
            return;
        }

        troca.sendResponseHeaders(resposta.getStatus(), conteudo.length);

        try (OutputStream saida = troca.getResponseBody()) {
            saida.write(conteudo);
        }
    }

    // --- ENVIA UMA RESPOSTA DE ERRO EM JSON ---
    private void enviarErro(HttpExchange troca, int status, String mensagem, String metodo, String caminho) throws IOException {
        Map<String, Object> erro = new LinkedHashMap<>();
        erro.put("status", status);
        erro.put("erro", mensagem);
        erro.put("metodo", metodo);
        erro.put("caminho", caminho);
        erro.put("dataHora", LocalDateTime.now().toString());

        enviarResposta(troca, new RespostaMock(status, "application/json; charset=UTF-8", gson.toJson(erro), Map.of(), 0));
    }

    // --- TENTA ENVIAR UM ERRO SEM INTERROMPER O SERVIDOR ---
    private void enviarErroSeguro(HttpExchange troca, int status, String mensagem, String metodo, String caminho) {
        try {
            enviarErro(troca, status, mensagem, metodo, caminho);
        } catch (IOException excecao) {
            excecao.printStackTrace();
        }
    }

    // --- LÊ O CORPO DA REQUISIÇÃO COM LIMITE DE TAMANHO ---
    private String lerCorpo(InputStream entrada) throws IOException {
        int limiteBytes = 1024 * 1024;
        byte[] conteudo = entrada.readNBytes(limiteBytes + 1);

        if (conteudo.length > limiteBytes) {
            throw new IllegalArgumentException("O corpo da requisição não pode ultrapassar 1 MB.");
        }

        return new String(conteudo, StandardCharsets.UTF_8);
    }

    // --- EXTRAI OS PARÂMETROS DA CONSULTA ---
    private Map<String, List<String>> extrairParametrosConsulta(String consulta) {
        Map<String, List<String>> parametros = new LinkedHashMap<>();

        if (consulta == null || consulta.isEmpty()) {
            return parametros;
        }

        for (String parte : consulta.split("&")) {
            String[] componentes = parte.split("=", 2);
            String nome = decodificarUrl(componentes[0]);
            String valor = componentes.length > 1 ? decodificarUrl(componentes[1]) : "";
            parametros.computeIfAbsent(nome, chave -> new ArrayList<>()).add(valor);
        }

        return parametros;
    }

    // --- DECODIFICA UM VALOR RECEBIDO PELA URL ---
    private String decodificarUrl(String valor) {
        return URLDecoder.decode(valor, StandardCharsets.UTF_8);
    }

    // --- COPIA OS CABEÇALHOS DA REQUISIÇÃO ---
    private Map<String, List<String>> copiarCabecalhos(Headers cabecalhosOriginais) {
        Map<String, List<String>> copia = new LinkedHashMap<>();

        for (Map.Entry<String, List<String>> cabecalho : cabecalhosOriginais.entrySet()) {
            copia.put(cabecalho.getKey(), List.copyOf(cabecalho.getValue()));
        }

        return copia;
    }

    // --- ADICIONA CABEÇALHOS CORS PARA TESTES LOCAIS ---
    private void adicionarCabecalhosCors(Headers cabecalhos) {
        cabecalhos.set("Access-Control-Allow-Origin", "*");
        cabecalhos.set("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE, OPTIONS");
        cabecalhos.set("Access-Control-Allow-Headers", "Content-Type, Authorization, Accept");
    }

    // --- APLICA O ATRASO CONFIGURADO ---
    private void aplicarAtraso(long atrasoMilissegundos) {
        if (atrasoMilissegundos <= 0) {
            return;
        }

        try {
            Thread.sleep(atrasoMilissegundos);
        } catch (InterruptedException excecao) {
            Thread.currentThread().interrupt();
        }
    }

    // --- REGISTRA A REQUISIÇÃO NO TERMINAL ---
    private void registrarRequisicao(String metodo, String caminho, long duracao) {
        String horario = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        System.out.println("[" + horario + "] " + metodo + " " + caminho + " - " + duracao + " ms");
    }

    public synchronized boolean isExecutando() {
        return servidor != null;
    }

    public String getEndereco() {
        return endereco;
    }

    public int getPorta() {
        return porta;
    }
}

