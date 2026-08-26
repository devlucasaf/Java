package application.estruturas.grafos;

import java.util.*;

public class Dijkstra {

    public static Resultado calcular(Grafo grafo, String origem, String destino) {
        Map<String, Double> distancias = new HashMap<>();
        Map<String, String> anteriores = new HashMap<>();
        Set<String> visitados = new HashSet<>();

        for (String v : grafo.getVertices()) {
            distancias.put(v, Double.POSITIVE_INFINITY);
        }
        distancias.put(origem, 0.0);

        PriorityQueue<String> fila = new PriorityQueue<>(Comparator.comparingDouble(distancias::get));
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
                double novaDistancia = distancias.get(atual) + aresta.peso;
                if (novaDistancia < distancias.get(aresta.destino)) {
                    distancias.put(aresta.destino, novaDistancia);
                    anteriores.put(aresta.destino, atual);
                    fila.add(aresta.destino);
                }
            }
        }

        if (distancias.get(destino) == Double.POSITIVE_INFINITY) {
            return new Resultado(Collections.emptyList(), -1, visitados.size());
        }

        List<String> caminho = reconstruirCaminho(anteriores, origem, destino);
        return new Resultado(caminho, distancias.get(destino), visitados.size());
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
