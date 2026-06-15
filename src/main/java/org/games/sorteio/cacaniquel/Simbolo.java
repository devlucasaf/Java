package org.games.sorteio.cacaniquel;

public enum Simbolo {
    CEREJA  ("🍒", 30, 5),
    LIMAO   ("🍋", 25, 10),
    UVA     ("🍇", 20, 20),
    SINO    ("🔔", 15, 50),
    ESTRELA ("⭐", 7,  100),
    SETE    ("7️⃣ ", 3,  500);

    private final String emoji;
    private final int    peso;
    private final int    multiplicador;

    Simbolo(String emoji, int peso, int multiplicador) {
        this.emoji = emoji;
        this.peso = peso;
        this.multiplicador = multiplicador;
    }

    public String getEmoji() {
        return emoji;
    }

    public int getPeso() {
        return peso;
    }

    public int getMultiplicador() {
        return multiplicador;
    }

    public static int somaPesos() {
        int soma = 0;
        for (Simbolo s : values()) {
            soma += s.peso;
        }
        return soma;
    }
}

