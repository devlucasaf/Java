package org.application.sistemaelevador;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Classe principal para executar a simulação
public class Main {
    public static void main(String[] args) {
        final int TOTAL_ANDARES = 100; // Número de andares do prédio
        Elevador elevador = new Elevador(TOTAL_ANDARES);

        List<Passageiro> passageiros = new ArrayList<>();
        Random random = new Random();

        // Cria 100 passageiros com origem e destino aleatórios
        for (int i = 0; i < 100; i++) {
            int origem = random.nextInt(TOTAL_ANDARES);
            int destino = random.nextInt(TOTAL_ANDARES);

            // Garante que origem e destino não sejam iguais
            while (destino == origem) {
                destino = random.nextInt(TOTAL_ANDARES);
            }

            passageiros.add(new Passageiro(origem, destino));
        }

        // Adiciona todos os passageiros como chamadas
        for (Passageiro p : passageiros) {
            elevador.adicionarChamada(p);
        }

        // Simula 20 movimentos do elevador
        for (int i = 0; i < 20; i++) {
            elevador.status();
            elevador.mover();
        }

        // Exibe status final
        elevador.status();
        System.out.println("Total de movimentos: " + elevador.getMovimentos());
    }
}
