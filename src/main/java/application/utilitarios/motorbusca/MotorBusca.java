package application.utilitarios.motorbusca;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MotorBusca {

    private final List<Documento> documentos = new ArrayList<>();
    private final Map<String, Integer> ocorrenciasPorTermo = new HashMap<>();

    public void adicionar(Documento doc) {
        documentos.add(doc);
        for (String termo : doc.getFrequencias().keySet()) {
            ocorrenciasPorTermo.merge(termo, 1, Integer::sum);
        }
    }

    public double idf(String termo) {
        int ocorrencias = ocorrenciasPorTermo.getOrDefault(termo, 0);
        if (ocorrencias == 0) return 0;
        return Math.log((double) documentos.size() / ocorrencias);
    }

    public double tfIdf(Documento doc, String termo) {
        return doc.tf(termo) * idf(termo);
    }

    public List<Resultado> buscar(String consulta, int topN) {
        String[] termos = consulta.toLowerCase().split("[^\\p{L}0-9]+");
        List<Resultado> resultados = new ArrayList<>();
        for (Documento doc : documentos) {
            double score = 0;
            for (String t : termos) {
                if (t.isBlank()) continue;
                score += tfIdf(doc, t);
            }
            if (score > 0) resultados.add(new Resultado(doc, score));
        }
        resultados.sort(Comparator.comparingDouble((Resultado r) -> r.score).reversed());
        return resultados.subList(0, Math.min(topN, resultados.size()));
    }

    public int getTotal() {
        return documentos.size();
    }

    public int getTamanhoIndice() {
        return ocorrenciasPorTermo.size();
    }

    public static class Resultado {
        public final Documento doc;
        public final double score;

        public Resultado(Documento doc, double score) {
            this.doc = doc;
            this.score = score;
        }
    }
}

