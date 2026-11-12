package games.sorteio.truco;

public record Carta(int indice, Naipe naipe) {
    @Override
    public String toString() {
        return Truco.ORDEM[indice] + " de " + naipe.name().charAt(0) + naipe.name().substring(1).toLowerCase();
    }
}
