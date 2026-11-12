package application.bots.whatsapp;

import application.bots.comum.ComandosExtras;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.Map;
import java.util.concurrent.Executors;

public class BotWhatsApp {

    private static final String API = "https://graph.facebook.com/v21.0/";

    private final String            token;
    private final String            phoneId;
    private final String            verifyToken;
    private final String            appSecret;
    private final HttpClient        http = HttpClient.newHttpClient();
    private final ComandosExtras    comandos;

    public BotWhatsApp(String token, String phoneId, String verifyToken, String appSecret) {
        this.token = token;
        this.phoneId = phoneId;
        this.verifyToken = verifyToken;
        this.appSecret = appSecret;
        this.comandos = new ComandosExtras((numero, msg) -> enviar(numero, msg));
    }

    public void iniciar(int porta) throws IOException {
        HttpServer servidor = HttpServer.create(new InetSocketAddress(porta), 0);
        servidor.createContext("/webhook", this::tratar);
        servidor.setExecutor(Executors.newFixedThreadPool(4));
        servidor.start();
        System.out.println("Webhook do WhatsApp ouvindo em http://localhost:" + porta + "/webhook");
        System.out.println("Exponha com HTTPS (ex.: ngrok http " + porta + ") e cadastre a URL no painel da Meta.");
    }

    // --------------------------------------------------------------- webhook

    private void tratar(HttpExchange ex) throws IOException {
        try {
            if (ex.getRequestMethod().equals("GET")) {
                verificar(ex);
            } else if (ex.getRequestMethod().equals("POST")) {
                receber(ex);
            } else {
                responder(ex, 405, "");
            }
        } finally {
            ex.close();
        }
    }

    /** Handshake de verificação do webhook (hub.mode / hub.verify_token / hub.challenge). */
    private void verificar(HttpExchange ex) throws IOException {
        Map<String, String> q = parametros(ex.getRequestURI().getRawQuery());
        boolean ok = "subscribe".equals(q.get("hub.mode"))
                && verifyToken != null
                && MessageDigest.isEqual(verifyToken.getBytes(StandardCharsets.UTF_8),
                        q.getOrDefault("hub.verify_token", "").getBytes(StandardCharsets.UTF_8));
        if (ok) {
            responder(ex, 200, q.getOrDefault("hub.challenge", ""));
        } else {
            responder(ex, 403, "verificacao falhou");
        }
    }

    private void receber(HttpExchange ex) throws IOException {
        byte[] corpo = ex.getRequestBody().readAllBytes();

        if (appSecret != null && !assinaturaValida(corpo, ex.getRequestHeaders().getFirst("X-Hub-Signature-256"))) {
            responder(ex, 403, "assinatura invalida");
            return;
        }
        responder(ex, 200, "ok");

        try {
            JsonObject raiz = JsonParser.parseString(new String(corpo, StandardCharsets.UTF_8)).getAsJsonObject();
            for (JsonElement entry : array(raiz, "entry")) {
                for (JsonElement change : array(entry.getAsJsonObject(), "changes")) {
                    JsonObject value = change.getAsJsonObject().getAsJsonObject("value");
                    if (value == null) {
                        continue;
                    }

                    for (JsonElement m : array(value, "messages")) {
                        mensagem(m.getAsJsonObject());
                    }
                }
            }
        } catch (RuntimeException e) {
            System.err.println("Payload invalido: " + e.getMessage());
        }
    }

    private void mensagem(JsonObject m) {
        if (!"text".equals(m.get("type").getAsString())) {
            return;
        }
        String numero = m.get("from").getAsString();
        String texto = m.getAsJsonObject("text").get("body").getAsString();

        String[] p = ComandosExtras.separar(texto, "/", "!");
        String resposta = switch (p[0]) {
            case "oi", "ola", "start" -> "Ola! Digite 'ajuda' para ver os comandos.";
            case "ping" -> "Pong!";
            default -> comandos.executar(p[0], numero, p[1]);
        };
        enviar(numero, resposta != null ? resposta : "Nao entendi. Digite 'ajuda' para ver os comandos.");
    }

    // ------------------------------------------------------------------ envio

    public void enviar(String para, String texto) {
        JsonObject corpo = new JsonObject();

        corpo.addProperty("messaging_product", "whatsapp");
        corpo.addProperty("to", para);
        corpo.addProperty("type", "text");

        JsonObject t = new JsonObject();

        t.addProperty("body", texto);
        corpo.add("text", t);

        HttpRequest req = HttpRequest.newBuilder(URI.create(API + phoneId + "/messages"))
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(corpo.toString()))
                .build();
        http.sendAsync(req, HttpResponse.BodyHandlers.ofString()).thenAccept(r -> {
            if (r.statusCode() >= 300) {
                System.err.println("WhatsApp API " + r.statusCode() + ": " + r.body());
            }
        });
    }

    // ---------------------------------------------------------------- auxiliares

    private boolean assinaturaValida(byte[] corpo, String cabecalho) {
        if (cabecalho == null || !cabecalho.startsWith("sha256=")) {
            return false;
        }

        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(appSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            String esperado = HexFormat.of().formatHex(mac.doFinal(corpo));
            return MessageDigest.isEqual(esperado.getBytes(StandardCharsets.UTF_8),
                    cabecalho.substring(7).toLowerCase().getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            return false;
        }
    }

    private static JsonArray array(JsonObject o, String chave) {
        return o.has(chave) && o.get(chave).isJsonArray() ? o.getAsJsonArray(chave) : new JsonArray();
    }

    private static Map<String, String> parametros(String query) {
        Map<String, String> mapa = new HashMap<>();

        if (query == null) {
            return mapa;
        }

        for (String par : query.split("&")) {
            String[] kv = par.split("=", 2);
            mapa.put(URLDecoder.decode(kv[0], StandardCharsets.UTF_8),
                    kv.length > 1 ? URLDecoder.decode(kv[1], StandardCharsets.UTF_8) : "");
        }
        return mapa;
    }

    private static void responder(HttpExchange ex, int status, String texto) throws IOException {
        byte[] b = texto.getBytes(StandardCharsets.UTF_8);
        ex.sendResponseHeaders(status, b.length == 0 ? -1 : b.length);
        if (b.length > 0) {
            ex.getResponseBody().write(b);
        }
    }

    public static void main(String[] args) throws IOException {
        String token = System.getenv("WHATSAPP_TOKEN");
        String phoneId = System.getenv("WHATSAPP_PHONE_ID");
        String verify = System.getenv("WHATSAPP_VERIFY_TOKEN");

        if (token == null || phoneId == null || verify == null) {
            System.err.println("Defina WHATSAPP_TOKEN, WHATSAPP_PHONE_ID e WHATSAPP_VERIFY_TOKEN (e, de preferencia, WHATSAPP_APP_SECRET).");
            return;
        }

        String secret = System.getenv("WHATSAPP_APP_SECRET");
        if (secret == null) {
            System.err.println("Aviso: WHATSAPP_APP_SECRET ausente - assinaturas do webhook NAO serao validadas.");
        }

        int porta;

        if (System.getenv("PORT") != null) {
            porta = Integer.parseInt(System.getenv("PORT"));
        } else {
            porta = 8080;
        }

        new BotWhatsApp(token, phoneId, verify, secret).iniciar(porta);
    }
}
