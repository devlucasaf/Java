# Bot WhatsApp (Cloud API da Meta)

Sem dependencias extras (usa `HttpServer` do JDK + Gson). Classe: `BotWhatsApp`.

1. Crie um app em https://developers.facebook.com, adicione o produto **WhatsApp** e anote o *Phone Number ID* e um token de acesso.
2. Defina as variaveis: `WHATSAPP_TOKEN`, `WHATSAPP_PHONE_ID`, `WHATSAPP_VERIFY_TOKEN` (texto livre) e, de preferencia, `WHATSAPP_APP_SECRET` (valida a assinatura `X-Hub-Signature-256`).
3. Rode `BotWhatsApp` (porta `PORT`, padrao 8080) e exponha com HTTPS, por exemplo `ngrok http 8080`.
4. No painel, configure o webhook `https://<seu-host>/webhook` com o mesmo verify token e assine o campo `messages`.

Comandos (com ou sem `/` ou `!`): `ajuda`, `calc`, `quiz`, `resposta`, `placar`, `enquete`, `votar`, `resultado`, `lembrar`, `velha`, `moeda`, `sortear`.

Limite da Meta: o bot so responde texto livre dentro de 24h apos a ultima mensagem do usuario; lembretes fora dessa janela exigem templates aprovados.
