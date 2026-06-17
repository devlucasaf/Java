package application.faculdade.corridalebre;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {

    private static final String[] CORES_LEBRES = {
            "#FF5733",
            "#33FF57",
            "#3357FF",
            "#FF33F6",
            "#33FFF9"
    };

    private static final int NUM_LEBRES = 5;

    public static void main(String[] args) {
        List<Lebre>  lebres = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();

        System.out.println("=== CORRIDA DAS LEBRES (Distância: 20m) ===");

        for (int i = 0; i < NUM_LEBRES; i++) {
            String nome = "Lebre-" + (i + 1);
            String corHex = CORES_LEBRES[i];

            Lebre lebre = new Lebre(nome, corHex);
            lebres.add(lebre);

            Thread thread = new Thread(lebre);
            threads.add(thread);
            thread.start();
        }

        try {
            for (Thread t : threads) {
                t.join();
            }
        } catch (InterruptedException e) {
            System.err.println("A corrida foi interrompida!");
            Thread.currentThread().interrupt();
        }

        System.out.println("\n=== RESULTADO FINAL DA CORRIDA ===");

        List<Lebre> resultadosFinais = lebres.stream()
                .filter(l -> l.getDistanciaPercorrida() >= Lebre.distanciaTotal)
                .sorted(Comparator.comparingLong(Lebre::getTempoChegada))
                .toList();

        if (resultadosFinais.isEmpty()) {
            System.out.println("Nenhuma lebre conseguiu terminar a corrida.");
            return;
        }

        int colocacao = 1;

        System.out.println("\n## 🏆 VENCEDORA:");
        String corVencedora = resultadosFinais.get(0).getCorANSI();
        System.out.printf("   - %s%s%s (Tempo de Chegada: %d ms)\n",
                corVencedora, resultadosFinais.get(0).getNome(), ANSIConverter.RESET, resultadosFinais.get(0).getTempoChegada());

        System.out.println("\n## 🏅 COLOCAÇÃO E ESTATÍSTICAS:");
        System.out.println("-------------------------------------------------------");
        System.out.printf("| %-10s | %-12s | %-10s |\n", "Colocação", "Lebre", "Total Pulos");
        System.out.println("-------------------------------------------------------");

        for (Lebre l : resultadosFinais) {
            System.out.printf("| %-10d | %s%-12s%s | %-10d |\n",
                    colocacao++, l.getCorANSI(), l.getNome(), ANSIConverter.RESET, l.getTotalPulos());
        }

        System.out.println("-------------------------------------------------------");
    }
}

