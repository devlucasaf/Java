package org.application.outros.elevador;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        final int TOTAL_ANDARES = 100;
        Elevador elevador = new Elevador(TOTAL_ANDARES);

        List<Passageiro> passageiros = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            int origem = random.nextInt(TOTAL_ANDARES);
            int destino = random.nextInt(TOTAL_ANDARES);

            while (destino == origem) {
                destino = random.nextInt(TOTAL_ANDARES);
            }

            passageiros.add(new Passageiro(origem, destino));
        }

        for (Passageiro p : passageiros) {
            elevador.adicionarChamada(p);
        }

        for (int i = 0; i < 20; i++) {
            elevador.status();
            elevador.mover();
        }

        elevador.status();
        System.out.println("Total de movimentos: " + elevador.getMovimentos());
    }
}
