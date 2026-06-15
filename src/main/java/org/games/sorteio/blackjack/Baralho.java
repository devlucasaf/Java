package org.games.sorteio.blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Baralho {

    private final List<Carta> cartas = new ArrayList<>(52);

    public Baralho() {
        recriar();
    }

    public void recriar() {
        cartas.clear();
        for (Naipe naipe : Naipe.values()) {
            for (int valor = 1; valor <= 13; valor++) {
                cartas.add(new Carta(valor, naipe));
            }
        }
        embaralhar();
    }

    public void embaralhar() {
        Collections.shuffle(cartas);
    }

    public Carta comprar() {
        if (cartas.isEmpty()) {
            recriar();
        }
        return cartas.remove(cartas.size() - 1);
    }

    public int cartasRestantes() {
        return cartas.size();
    }
}

