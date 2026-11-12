package application.bots.discord;

import application.bots.comum.ComandosExtras;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.WebSocket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class BotDiscordGateway implements WebSocket.Listener {

    private static final String GATEWAY = "wss://gateway.discord.gg/?v=10&encoding=json";
    private static final String API = "https://discord.com/api/v10";
    private static final int INTENTS = (1 << 0) | (1 << 9) | (1 << 12) | (1 << 15);

    private final String token;
    private final HttpClient http = HttpClient.newHttpClient();
    private final ScheduledExecutorService agendador = Executors.newScheduledThreadPool(2);
    private final Map<String, String> ultimoCanal = new ConcurrentHashMap<>();
    private final ComandosExtras comandos;
    private final StringBuilder buffer = new StringBuilder();

    private volatile Integer sequencia;
    private volatile ScheduledFuture<?> batimento;

    public BotDiscordGateway(String token) {
        this.token = token;
        this.comandos = new ComandosExtras((usuario, msg) -> {
            String canal = ultimoCanal.get(usuario);
            if (canal != null) {
                enviar(canal, usuario + ", " + msg);
            }
        });
    }

    public void conectar() {
        http.newWebSocketBuilder().buildAsync(URI.create(GATEWAY), this).exceptionally(e -> {
            System.err.println("Falha ao conectar: " + e.getMessage() + " (nova tentativa em 5s)");
            agendador.schedule(this::conectar, 5, TimeUnit.SECONDS);
            return null;
        });
    }

    @Override
    public CompletionStage<?> onText(WebSocket ws, CharSequence dados, boolean ultima) {
        buffer.append(dados);
        if (ultima) {
            String json = buffer.toString();
            buffer.setLength(0);
            try {
                tratar(ws, JsonParser.parseString(json).getAsJsonObject());
            } catch (RuntimeException e) {
                System.err.println("Erro ao tratar evento: " + e.getMessage());
            }
        }
        ws.request(1);
        return null;
    }

    private void tratar(WebSocket ws, JsonObject msg) {
        if (msg.has("s") && !msg.get("s").isJsonNull()) {
            sequencia = msg.get("s").getAsInt();
        }
        int op = msg.get("op").getAsInt();

        switch (op) {
            case 10 -> {
                long intervalo = msg.getAsJsonObject("d").get("heartbeat_interval").getAsLong();
                if (batimento != null) {
                    batimento.cancel(false);
                }

                batimento = agendador.scheduleAtFixedRate(
                        () -> ws.sendText("{\"op\":1,\"d\":" + sequencia + "}", true),
                        intervalo,
                        intervalo,
                        TimeUnit.MILLISECONDS
                );
                ws.sendText("{\"op\":2,\"d\":{\"token\":\"" + token + "\",\"intents\":" + INTENTS
                        + ",\"properties\":{\"os\":\"java\",\"browser\":\"java-http\",\"device\":\"java-http\"}}}", true);
            }
            case 7, 9 -> ws.sendClose(1000, "reconectando");
            case 0 -> {
                String tipo = msg.get("t").getAsString();
                if (tipo.equals("READY")) {
                    System.out.println("Bot Discord conectado.");
                }

                if (tipo.equals("MESSAGE_CREATE")) {
                    mensagem(msg.getAsJsonObject("d"));
                }
            }
            default -> { }
        }
    }

    private void mensagem(JsonObject d) {
        JsonObject autor = d.getAsJsonObject("author");
        if (autor.has("bot") && autor.get("bot").getAsBoolean()) {
            return;
        }

        String texto = d.get("content").getAsString();
        if (!texto.startsWith("!")) {
            return;
        }

        String usuario = autor.get("username").getAsString();
        String canal = d.get("channel_id").getAsString();
        ultimoCanal.put(usuario, canal);

        String[] p = ComandosExtras.separar(texto, "!");
        String resposta = switch (p[0]) {
            case "ping" -> "Pong!";
            case "oi" -> "Ola, " + usuario + "!";
            case "dado" -> {
                try {
                    int lados;

                    if (p[1].isBlank()) {
                        lados = 6;
                    } else {
                        lados = Math.max(2, Math.min(1000, Integer.parseInt(p[1].trim())));
                    }

                    yield "Rolou " + (1 + (int) (Math.random() * lados));
                } catch (NumberFormatException e) {
                    yield "Uso: !dado <lados>";
                }
            }
            default -> comandos.executar(p[0], usuario, p[1]);
        };

        if (resposta != null) {
            enviar(canal, resposta);
        }
    }

    private void enviar(String canal, String texto) {
        if (texto.length() > 1900) {
            texto = texto.substring(0, 1900) + "...";
        }

        JsonObject corpo = new JsonObject();
        corpo.addProperty("content", "```\n" + texto.replace("```", "'''") + "\n```");
        HttpRequest request = HttpRequest.newBuilder(URI.create(API + "/channels/" + canal + "/messages"))
                .header("Authorization", "Bot " + token)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(corpo.toString()))
                .build();
        http.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept(r -> {
            if (r.statusCode() >= 300) {
                System.err.println("Discord respondeu " + r.statusCode() + ": " + r.body());
            }
        });
    }

    @Override
    public CompletionStage<?> onClose(WebSocket ws, int codigo, String motivo) {
        System.out.println("Conexao encerrada (" + codigo + "). Reconectando em 5s...");
        if (batimento != null) {
            batimento.cancel(false);
        }
        agendador.schedule(this::conectar, 5, TimeUnit.SECONDS);
        return null;
    }

    @Override
    public void onError(WebSocket ws, Throwable erro) {
        System.err.println("Erro no WebSocket: " + erro.getMessage());
        onClose(ws, -1, "erro");
    }

    public static void main(String[] args) throws InterruptedException {
        String token = System.getenv("DISCORD_BOT_TOKEN");
        if (token == null || token.isBlank()) {
            System.err.println("Defina DISCORD_BOT_TOKEN (e habilite Message Content Intent no Developer Portal).");
            return;
        }
        new BotDiscordGateway(token).conectar();
        Thread.currentThread().join();
    }
}
