package application.utilitarios.naivebayes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NaiveBayes {

    private final Map<String, Integer>              contagemClasses = new HashMap<>();
    private final Map<String, Map<String, Integer>> contagemPalavras = new HashMap<>();
    private final Set<String>                       vocabulario = new HashSet<>();
    private int                                     totalDocumentos = 0;

    public void treinar(String texto, String classe) {
        contagemClasses.merge(classe, 1, Integer::sum);
        totalDocumentos++;
        Map<String, Integer> mapa = contagemPalavras.computeIfAbsent(classe, k -> new HashMap<>());
        for (String palavra : tokenizar(texto)) {
            vocabulario.add(palavra);
            mapa.merge(palavra, 1, Integer::sum);
        }
    }

    public String classificar(String texto) {
        String[] palavras = tokenizar(texto);
        String melhor = null;
        double melhorProb = Double.NEGATIVE_INFINITY;
        for (String classe : contagemClasses.keySet()) {
            double log = Math.log((double) contagemClasses.get(classe) / totalDocumentos);
            Map<String, Integer> mapa = contagemPalavras.get(classe);
            int totalPalavrasClasse = mapa.values().stream().mapToInt(Integer::intValue).sum();
            int V = vocabulario.size();
            for (String p : palavras) {
                int cont = mapa.getOrDefault(p, 0);
                log += Math.log((cont + 1.0) / (totalPalavrasClasse + V));
            }

            if (log > melhorProb) {
                melhorProb = log;
                melhor = classe;
            }
        }
        return melhor;
    }

    public Map<String, Double> probabilidades(String texto) {
        Map<String, Double> resultado = new HashMap<>();
        String[] palavras = tokenizar(texto);
        double somaExp = 0;
        Map<String, Double> logs = new HashMap<>();
        for (String classe : contagemClasses.keySet()) {
            double log = Math.log((double) contagemClasses.get(classe) / totalDocumentos);
            Map<String, Integer> mapa = contagemPalavras.get(classe);
            int total = mapa.values().stream().mapToInt(Integer::intValue).sum();
            int V = vocabulario.size();

            for (String p : palavras) {
                log += Math.log((mapa.getOrDefault(p, 0) + 1.0) / (total + V));
            }
            logs.put(classe, log);
        }

        double max = logs.values().stream().mapToDouble(Double::doubleValue).max().orElse(0);
        for (var e : logs.entrySet()) {
            double v = Math.exp(e.getValue() - max);
            resultado.put(e.getKey(), v);
            somaExp += v;
        }

        for (var e : resultado.entrySet()) {
            resultado.put(e.getKey(), e.getValue() / somaExp);
        }
        return resultado;
    }

    private String[] tokenizar(String texto) {
        return texto.toLowerCase().replaceAll("[^\\p{L}0-9 ]", " ").trim().split("\\s+");
    }
}

