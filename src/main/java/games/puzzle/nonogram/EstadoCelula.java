package games.puzzle.nonogram;

public enum EstadoCelula {
    DESCONHECIDA('.'),
    PREENCHIDA('#'),
    VAZIA('X');

    private final char simbolo;

    EstadoCelula(char simbolo) {
        this.simbolo = simbolo;
    }

    public char getSimbolo() {
        return simbolo;
    }
}

