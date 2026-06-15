package org.games.sorteio.blackjack;

public enum Naipe {
    OUROS    ("♦"),
    ESPADAS  ("♠"),
    COPAS    ("♥"),
    PAUS     ("♣");

    private final String simbolo;

    Naipe(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getSimbolo() {
        return simbolo;
    }
}

