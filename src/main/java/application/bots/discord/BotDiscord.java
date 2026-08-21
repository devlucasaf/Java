package application.bots.discord;

import java.util.HashMap;
import java.util.Map;

public class BotDiscord {

    private final String token;
    private final Map<String, ManipuladorComando> comandos = new HashMap<>();

    public BotDiscord(String token) {
        this.token = token;
    }

    public void registrarComando(String nome, ManipuladorComando handler) {
        comandos.put(nome, handler);
    }

    public void iniciar() {
        System.out.println("Bot Discord aguardando: adicione a biblioteca JDA para conectar.");
        System.out.println("Token configurado: " + (token == null ? "AUSENTE" : "OK"));
        System.out.println("Comandos registrados: " + comandos.keySet());
        System.out.println("\nVer README.md para instrucoes de dependencia.");
    }

    public static void main(String[] args) {
        String token = System.getenv("DISCORD_BOT_TOKEN");
        BotDiscord discord = new BotDiscord(token);
        discord.registrarComando("ping", (u, a) -> "Pong!");
        discord.registrarComando("oi", (u, a) -> "Ola, " + u + "!");
        discord.registrarComando("dado", (u, a) -> {
            int lados = a.isBlank() ? 6 : Integer.parseInt(a);
            return "Rolou " + (1 + (int) (Math.random() * lados));
        });
        discord.iniciar();
    }
}

