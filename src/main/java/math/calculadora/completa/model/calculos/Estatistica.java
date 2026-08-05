package math.calculadora.completa.model.calculos;

import java.util.*;

public class Estatistica {

    public static ResultadoEstatistico calcular(List<Double> valores) {
        if (valores == null || valores.isEmpty()) {
            throw new IllegalArgumentException("Lista de valores vazia");
        }

        List<Double> sorted = new ArrayList<>(valores);
        Collections.sort(sorted);
        int n = sorted.size();

        double soma = 0;
        for (double v : sorted) {
            soma += v;
        }
        double media = soma / n;

        double mediana;
        if (n % 2 == 0) {
            mediana = (sorted.get(n / 2 - 1) + sorted.get(n / 2)) / 2;
        } else {
            mediana = sorted.get(n / 2);
        }

        Map<Double, Integer> freq = new HashMap<>();
        for (double v : sorted) {
            freq.put(v, freq.getOrDefault(v, 0) + 1);
        }

        int maxFreq = Collections.max(freq.values());
        List<Double> modas = new ArrayList<>();
        for (Map.Entry<Double, Integer> entry : freq.entrySet()) {
            if (entry.getValue() == maxFreq) {
                modas.add(entry.getKey());
            }
        }

        double var = 0;
        for (double v : sorted) {
            var += Math.pow(v - media, 2);
        }
        var /= n;
        double dp = Math.sqrt(var);

        return new ResultadoEstatistico(media, mediana, var, dp, modas);
    }
}