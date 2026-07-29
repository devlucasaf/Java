# Bot Discord com JDA

Scaffold pronto para integrar com [JDA (Java Discord API)](https://github.com/discord-jda/JDA).

## Configurar

1. Crie uma aplicacao em https://discord.com/developers/applications e habilite o bot.
2. Copie o token do bot.
3. Defina `DISCORD_BOT_TOKEN` como variavel de ambiente.
4. Adicione a dependencia JDA no `pom.xml`:

```xml
<dependency>
    <groupId>net.dv8tion</groupId>
    <artifactId>JDA</artifactId>
    <version>5.0.0-beta.24</version>
</dependency>
```

## Integrar

No `BotDiscord.iniciar()`, substitua o `System.out` por:

```java
JDA jda = JDABuilder.createDefault(token)
    .enableIntents(GatewayIntent.MESSAGE_CONTENT)
    .addEventListeners(new ListenerAdapter() {
        @Override
        public void onMessageReceived(MessageReceivedEvent event) {
            String texto = event.getMessage().getContentRaw();
            if (!texto.startsWith("!")) return;
            String[] partes = texto.substring(1).split("\\s+", 2);
            ManipuladorComando h = comandos.get(partes[0]);
            if (h != null) {
                String resposta = h.responder(
                    event.getAuthor().getName(),
                    partes.length > 1 ? partes[1] : "");
                event.getChannel().sendMessage(resposta).queue();
            }
        }
    })
    .build();
```

## Comandos ja registrados

- `!ping` — Pong!
- `!oi` — Ola, usuario!
- `!dado <n>` — Rola um dado de N lados

