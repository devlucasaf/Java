package games.sorteio.blackjack;

public class Carta {

    private final int   valor;
    private final Naipe naipe;

    public Carta(int valor, Naipe naipe) {
        if (valor < 1 || valor > 13) {
            throw new IllegalArgumentException("Valor de carta inválido: " + valor);
        }

        this.valor = valor;
        this.naipe = naipe;
    }

    public int getValor() {
        return valor;
    }

    public Naipe getNaipe() {
        return naipe;
    }

    public int getValorBlackjack() {
        if (valor == 1) {
            return 11;
        }

        if (valor >= 10) {
            return 10;
        }
        return valor;
    }

    public boolean isAs() {
        return valor == 1;
    }

    @Override
    public String toString() {
        String simboloValor = switch (valor) {
            case 1  -> "A";
            case 11 -> "J";
            case 12 -> "Q";
            case 13 -> "K";
            default -> String.valueOf(valor);
        };
        return simboloValor + naipe.getSimbolo();
    }
}

