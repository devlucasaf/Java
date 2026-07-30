package application.simuladores.memoriavirtual;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        List<Integer> refs = new ArrayList<>(List.of(7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2, 1, 2, 0, 1, 7, 0, 1));
        int frames = 3;

        System.out.println("=== SIMULADOR DE MEMORIA VIRTUAL ===\n");
        System.out.println("Sequencia: " + refs);
        System.out.println("Frames disponiveis: " + frames);
        System.out.println();
        System.out.printf("%-8s | %s%n", "Algoritmo", "Page Faults");
        System.out.println("----------------------");

        for (Algoritmo a : List.of(Algoritmo.fifo(), Algoritmo.lru(), Algoritmo.otimo())) {
            int f = a.simular(refs, frames);
            System.out.printf("%-8s | %d%n", a.getNome(), f);
        }

        System.out.println("\n=== TESTE ALEATORIO (1000 refs, 10 paginas, 4 frames) ===");
        Random r = new Random(42);
        List<Integer> aleatorio = new ArrayList<>();
        for (int i = 0; i < 1000; i++) aleatorio.add(r.nextInt(10));
        for (Algoritmo a : List.of(Algoritmo.fifo(), Algoritmo.lru(), Algoritmo.otimo())) {
            int f = a.simular(aleatorio, 4);
            System.out.printf("%-8s | %d faults (%.1f%%)%n", a.getNome(), f, 100.0 * f / aleatorio.size());
        }
    }
}

