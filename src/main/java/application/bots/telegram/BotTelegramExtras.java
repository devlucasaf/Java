package application.bots.telegram;

import application.bots.comum.ComandosExtras;

public class BotTelegramExtras {

    public static void main(String[] args) {
        String token = System.getenv("TELEGRAM_BOT_TOKEN");
        if (token == null || token.isBlank()) {
            System.err.println("Defina TELEGRAM_BOT_TOKEN. Crie um bot em @BotFather no Telegram.");
            return;
        }

        BotTelegram telegram = new BotTelegram(token);

        ComandosExtras extras = new ComandosExtras((chat, msg) -> {
            try {
                telegram.enviar(Long.parseLong(chat), msg);
            } catch (Exception e) {
                System.err.println("Falha ao enviar lembrete: " + e.getMessage());
            }
        });

        extras.todos().forEach((nome, fn) ->
                telegram.registrarComando(nome, (chatId, a) -> fn.apply(String.valueOf(chatId), a.trim())));

        telegram.registrarComando("/start", (c, a) -> "Ola! Eu sou o bot de utilidades e jogos.\n\n" + extras.executar("ajuda", "", ""));
        telegram.registrarComando("/help", (c, a) -> extras.executar("ajuda", "", ""));

        telegram.executar();
    }
}
