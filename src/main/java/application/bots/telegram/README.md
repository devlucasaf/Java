# Bot Telegram

Bot funcional em Java **puro** (usa `HttpClient` do JDK, sem dependencias externas).

## Como usar

1. Fale com `@BotFather` no Telegram e crie um bot (`/newbot`).
2. Copie o token gerado.
3. Defina a variavel de ambiente:
   - Windows PowerShell: `$env:TELEGRAM_BOT_TOKEN="seu-token-aqui"`
   - Linux/Mac: `export TELEGRAM_BOT_TOKEN="seu-token-aqui"`
4. Rode `BotTelegram`.
5. No Telegram, envie `/start` para o seu bot.

## Comandos ja registrados

- `/start` — mensagem de boas-vindas
- `/help` — lista comandos
- `/oi` — resposta simples
- `/soma <a> <b>` — soma dois numeros
- `/hora` — retorna hora atual

## Versao com biblioteca oficial

Para recursos mais avancados (botoes inline, arquivos, etc), use [rubenlagus/TelegramBots](https://github.com/rubenlagus/TelegramBots):

```xml
<dependency>
    <groupId>org.telegram</groupId>
    <artifactId>telegrambots</artifactId>
    <version>6.9.7.1</version>
</dependency>
```

