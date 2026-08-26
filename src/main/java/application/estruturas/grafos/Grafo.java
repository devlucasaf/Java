package application.estruturas.grafos;

import java.util.*;

public class Grafo {

    private final Map<String, List<Aresta>> adjacencias = new HashMap<>();
    private final Map<String, double[]> coordenadas = new HashMap<>();

    public void adicionarVertice(String nome, double x, double y) {
        adjacencias.putIfAbsent(nome, new ArrayList<>());
        coordenadas.put(nome, new double[]{x, y});
    }

    public void adicionarAresta(String origem, String destino, double peso) {
        adjacencias.computeIfAbsent(origem, k -> new ArrayList<>()).add(new Aresta(destino, peso));
        adjacencias.computeIfAbsent(destino, k -> new ArrayList<>()).add(new Aresta(origem, peso));
    }

    public List<Aresta> getVizinhos(String vertice) {
        return adjacencias.getOrDefault(vertice, Collections.emptyList());
    }

    public Set<String> getVertices() {
        return adjacencias.keySet();
    }

    public double[] getCoordenadas(String vertice) {
        return coordenadas.get(vertice);
    }

    public double distanciaEuclidiana(String a, String b) {
        double[] pa = coordenadas.get(a);
        double[] pb = coordenadas.get(b);
        if (pa == null || pb == null) {
            return 0;
        }
        double dx = pa[0] - pb[0];
        double dy = pa[1] - pb[1];
        return Math.sqrt(dx * dx + dy * dy);
    }
}
