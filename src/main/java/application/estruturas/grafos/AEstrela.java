package application.estruturas.grafos;

import java.util.*;

public class AEstrela {

    public static Resultado calcular(Grafo grafo, String origem, String destino) {
        Map<String, Double> custoReal = new HashMap<>();
        Map<String, String> anteriores = new HashMap<>();
        Set<String> visitados = new HashSet<>();

        for (String v : grafo.getVertices()) {
            custoReal.put(v, Double.POSITIVE_INFINITY);
        }
        custoReal.put(origem, 0.0);

        Map<String, Double> custoEstimado = new HashMap<>();
        custoEstimado.put(origem, grafo.distanciaEuclidiana(origem, destino));

        PriorityQueue<String> fila = new PriorityQueue<>(
                Comparator.comparingDouble(v -> custoEstimado.getOrDefault(v, Double.POSITIVE_INFINITY))
        );
        fila.add(origem);

        while (!fila.isEmpty()) {
            String atual = fila.poll();

            if (visitados.contains(atual)) {
                continue;
            }
            visitados.add(atual);

            if (atual.equals(destino)) {
                break;
            }

            for (Aresta aresta : grafo.getVizinhos(atual)) {
                double novoCusto = custoReal.get(atual) + aresta.peso;
                if (novoCusto < custoReal.get(aresta.destino)) {
                    custoReal.put(aresta.destino, novoCusto);
                    anteriores.put(aresta.destino, atual);
                    custoEstimado.put(aresta.destino, novoCusto + grafo.distanciaEuclidiana(aresta.destino, destino));
                    fila.add(aresta.destino);
                }
            }
        }

        if (custoReal.get(destino) == Double.POSITIVE_INFINITY) {
            return new Resultado(Collections.emptyList(), -1, visitados.size());
        }

        List<String> caminho = reconstruirCaminho(anteriores, origem, destino);
        return new Resultado(caminho, custoReal.get(destino), visitados.size());
    }

    private static List<String> reconstruirCaminho(Map<String, String> anteriores, String origem, String destino) {
        LinkedList<String> caminho = new LinkedList<>();
        String atual = destino;
        while (atual != null && !atual.equals(origem)) {
            caminho.addFirst(atual);
            atual = anteriores.get(atual);
        }
        caminho.addFirst(origem);
        return caminho;
    }
}
