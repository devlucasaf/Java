package org.games.batalhanaval;

public class Coordenada {
    private final int linha;
    private final int coluna;

    public Coordenada(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    public static Coordenada interpretar(String entrada) throws IllegalArgumentException {
        if (entrada == null || entrada.length() < 2 || entrada.length() > 3) {
            throw new IllegalArgumentException("Formato inválido. Use Letra+Número (ex: A5).");
        }

        char letraLinha = Character.toUpperCase(entrada.charAt(0));
        if (letraLinha < 'A' || letraLinha > 'J') {
            throw new IllegalArgumentException("Linha inválida. Use de A até J.");
        }

        int linha = letraLinha - 'A';
        int coluna;

        try {
            coluna = Integer.parseInt(entrada.substring(1)) - 1;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Coluna inválida. Use de 1 até 10.");
        }

        if (coluna < 0 || coluna > 9) {
            throw new IllegalArgumentException("Coluna fora do limite. Use de 1 até 10.");
        }

        return new Coordenada(linha, coluna);
    }
}
