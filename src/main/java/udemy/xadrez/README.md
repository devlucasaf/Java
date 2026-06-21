<img
    width=100%
    src="https://capsule-render.vercel.app/api?type=waving&color=A020F0&height=120&section=header"
/>

<h1 align="center">♟️ Chess System</h1>

<p align="center">
  Projeto desenvolvido durante o curso <strong>Java COMPLETO — Programação Orientada a Objetos + Projetos</strong> da Udemy, ministrado pelo professor Nélio Alves.
</p>

---

## 📖 Sobre o Projeto

O **Chess System** é um jogo de xadrez executado no terminal, desenvolvido com foco na aplicação dos conceitos de **Programação Orientada a Objetos (POO)** em Java. O projeto abrange desde a modelagem das peças e do tabuleiro até a lógica completa de movimentação, xeque e xeque-mate.

### Conceitos aplicados:

- Encapsulamento, Herança e Polimorfismo
- Classes abstratas e interfaces
- Tratamento de exceções customizadas
- Coleções e matrizes (Arrays 2D)
- Camadas de domínio e aplicação (separação de responsabilidades)

---

## 🎮 Funcionalidades

- Tabuleiro 8x8 com representação visual no terminal
- Movimentação de todas as peças: Rei, Rainha, Torre, Bispo, Cavalo e Peão
- Verificação de movimentos válidos e ilegais
- Detecção de **xeque** e **xeque-mate**
- Movimentos especiais:
    - Roque (Castling)
    - En Passant
    - Promoção do Peão
- Registro de peças capturadas
- Controle de turnos por jogador

---

## 🛠️ Tecnologias Utilizadas

<div style="display: flex; gap: 10px;">
  <img
      align="center"
      alt="Java"
      tittle="Java"
      height="40" 
      style="padding-right: 10px;"
      href="https://skillicons.dev"
      src="https://skillicons.dev/icons?i=java"
  />
  <img
      align="center"
      alt="intellij"
      tittle="Intellij IDEA"
      height="40" 
      style="padding-right: 10px;" 
      src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/intellij/intellij-original.svg"
  />
  <img
      align="center"
      alt="github"
      tittle="GitHub"
      height="40" 
      style="padding-right: 10px;" 
      href="https://skillicons.dev"
      src="https://skillicons.dev/icons?i=github"
  />
</div>

<br/>

| Tecnologia | Descrição |
|---|---|
| Java | Linguagem principal utilizada no desenvolvimento |
| IntelliJ IDEA | IDE utilizada para codificação e depuração |
| GitHub | Controle de versão e hospedagem do repositório |

---

## 🚀 Como Executar

### Pré-requisitos

- [Java JDK 11+](https://www.oracle.com/java/technologies/javase-downloads.html) instalado
- Terminal compatível com cores ANSI (recomendado: Git Bash no Windows)

---

## 📁 Estrutura do Projeto

```
chess-system/
│
├── src/
│   ├── application/
│   │   └── Program.java          # Ponto de entrada da aplicação
│   │   └── UI.java               # Interface do usuário no terminal
│   │
│   ├── boardgame/
│   │   ├── Board.java            # Tabuleiro genérico
│   │   ├── Piece.java            # Peça genérica
│   │   └── Position.java        # Posição no tabuleiro
│   │
│   └── chess/
│       ├── ChessMatch.java       # Lógica principal da partida
│       ├── ChessPiece.java       # Peça de xadrez
│       ├── ChessPosition.java    # Posição no formato xadrez (ex: a1, e4)
│       ├── ChessException.java   # Exceção customizada
│       ├── Color.java            # Enum de cores (BLACK/WHITE)
│       │
│       └── pieces/
│           ├── King.java
│           ├── Queen.java
│           ├── Rook.java
│           ├── Bishop.java
│           ├── Knight.java
│           └── Pawn.java
```

---

## 📚 Curso

Este projeto foi desenvolvido como parte do curso:

> **Java COMPLETO — Programação Orientada a Objetos + Projetos**  
> Plataforma: [Udemy](https://www.udemy.com/)  
> Instrutor: Nélio Alves

---

<img
    width=100%
    src="https://capsule-render.vercel.app/api?type=waving&color=A020F0&height=120&section=footer"
/>