package org.games.sorteio.blackjack;

import java.util.ArrayList;
import java.util.List;

public class Mao {

    private final List<Carta> cartas = new ArrayList<>();

    public void receber(Carta carta) {
        cartas.add(carta);
    }

    public List<Carta> getCartas() {
        return cartas;
    }

    public void limpar() {
        cartas.clear();
    }

    public int valor() {
        int total = 0;
        int ases = 0;
        for (Carta c : cartas) {
            total += c.getValorBlackjack();
            if (c.isAs()) {
                ases++;
            }
        }
        while (total > 21 && ases > 0) {
            total -= 10;
            ases--;
        }
        return total;
    }

    public boolean estourou() {
        return valor() > 21;
    }

    public boolean isBlackjack() {
        return cartas.size() == 2 && valor() == 21;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Carta c : cartas) {
            sb.append("[").append(c).append("] ");
        }
        sb.append("(").append(valor()).append(")");
        return sb.toString();
    }

    public String toStringOcultando() {
        if (cartas.isEmpty()) {
            return "(vazia)";
        }

        StringBuilder sb = new StringBuilder("[??] ");
        for (int i = 1; i < cartas.size(); i++) {
            sb.append("[").append(cartas.get(i)).append("] ");
        }
        return sb.toString();
    }
}

