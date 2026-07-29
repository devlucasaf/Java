package application.utilitarios.motorbusca;

import java.util.HashMap;
import java.util.Map;

public class Documento {

    private final String id;
    private final String titulo;
    private final String conteudo;
    private final Map<String, Integer> frequencias = new HashMap<>();
    private int totalTermos;

    public Documento(String id, String titulo, String conteudo) {
        this.id = id;
        this.titulo = titulo;
        this.conteudo = conteudo;
        indexar();
    }

    private void indexar() {
        for (String palavra : conteudo.toLowerCase().split("[^\\p{L}0-9]+")) {
            if (palavra.isBlank() || palavra.length() < 2) continue;
            frequencias.merge(palavra, 1, Integer::sum);
            totalTermos++;
        }
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public int frequencia(String termo) {
        return frequencias.getOrDefault(termo, 0);
    }

    public boolean contem(String termo) {
        return frequencias.containsKey(termo);
    }

    public double tf(String termo) {
        return totalTermos == 0 ? 0 : (double) frequencia(termo) / totalTermos;
    }

    public Map<String, Integer> getFrequencias() {
        return frequencias;
    }
}

