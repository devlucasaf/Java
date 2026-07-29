package application.bots.telegram;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BotTelegram {

    private final String token;
    private final HttpClient http;
    private long ultimoUpdateId = 0;
    private final Map<String, ManipuladorComando> comandos = new HashMap<>();

    public BotTelegram(String token) {
        this.token = token;
        this.http = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
    }

    public void registrarComando(String comando, ManipuladorComando handler) {
        comandos.put(comando.startsWith("/") ? comando : "/" + comando, handler);
    }

    public void executar() {
        System.out.println("Bot Telegram iniciado. Aguardando updates...");
        while (true) {
            try {
                processarUpdates();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                System.err.println("Erro: " + e.getMessage());
            }
        }
    }

    private void processarUpdates() throws Exception {
        String url = "https://api.telegram.org/bot" + token
                + "/getUpdates?offset=" + (ultimoUpdateId + 1) + "&timeout=30";
        HttpResponse<String> r = http.send(
                HttpRequest.newBuilder(URI.create(url)).GET().build(),
                HttpResponse.BodyHandlers.ofString());

        Matcher mUpdate = Pattern.compile("\"update_id\":(\\d+)").matcher(r.body());
        Matcher mMensagem = Pattern.compile("\"chat\":\\{\"id\":(-?\\d+).*?\"text\":\"([^\"]*)\"").matcher(r.body());

        while (mUpdate.find() && mMensagem.find()) {
            long updateId = Long.parseLong(mUpdate.group(1));
            long chatId = Long.parseLong(mMensagem.group(1));
            String texto = mMensagem.group(2);
            ultimoUpdateId = Math.max(ultimoUpdateId, updateId);
            responder(chatId, texto);
        }
    }

    private void responder(long chatId, String texto) throws Exception {
        String[] partes = texto.trim().split("\\s+", 2);
        String comando = partes[0].toLowerCase();
        String argumentos = partes.length > 1 ? partes[1] : "";

        ManipuladorComando handler = comandos.get(comando);
        String resposta = handler != null
                ? handler.responder(chatId, argumentos)
                : "Comando desconhecido. Envie /help para ver a lista.";
        enviar(chatId, resposta);
    }

    public void enviar(long chatId, String texto) throws Exception {
        String url = "https://api.telegram.org/bot" + token + "/sendMessage";
        String body = "chat_id=" + chatId + "&text="
                + java.net.URLEncoder.encode(texto, java.nio.charset.StandardCharsets.UTF_8);
        http.send(HttpRequest.newBuilder(URI.create(url))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build(), HttpResponse.BodyHandlers.ofString());
    }

    @FunctionalInterface
    public interface ManipuladorComando {
        String responder(long chatId, String argumentos);
    }

    public static void main(String[] args) {
        String token = System.getenv("TELEGRAM_BOT_TOKEN");
        if (token == null || token.isBlank()) {
            System.err.println("Defina TELEGRAM_BOT_TOKEN. Crie um bot em @BotFather no Telegram.");
            return;
        }
        BotTelegram bot = new BotTelegram(token);
        bot.registrarComando("/start", (c, a) -> "Ola! Comandos: /help, /oi, /soma <a> <b>, /hora");
        bot.registrarComando("/help", (c, a) -> "Comandos: /start /oi /soma /hora");
        bot.registrarComando("/oi", (c, a) -> "Oi! Tudo bem?");
        bot.registrarComando("/hora", (c, a) -> "Agora sao " + java.time.LocalTime.now());
        bot.registrarComando("/soma", (c, a) -> {
            String[] p = a.split("\\s+");
            if (p.length < 2) return "Uso: /soma <a> <b>";
            try {
                return "Resultado: " + (Double.parseDouble(p[0]) + Double.parseDouble(p[1]));
            } catch (NumberFormatException e) {
                return "Numeros invalidos.";
            }
        });
        bot.executar();
    }
}

