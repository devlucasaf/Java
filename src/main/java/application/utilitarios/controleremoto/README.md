# Controle Remoto de PC

Servidor + cliente TCP para controle remoto.

## Executar

1. No PC que sera controlado: rodar `ServidorControle` (porta padrao 5555).
2. No PC controlador: rodar `ClienteControle <host> <porta>`.

## Comandos

- `SCREEN` — captura screenshot do servidor e salva em `target/`
- `KEY <codigo>` — pressiona tecla (ex: 65 = 'A', 27 = ESC)
- `MOUSE <x> <y>` — move mouse
- `CLICK` — clique com botao esquerdo
- `SAIR` — desconecta

## Observacao de seguranca

Nao use em producao — nao ha autenticacao nem criptografia. Serve apenas como demonstracao de sockets + `java.awt.Robot`.

