package application.utilitarios.mockapi;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ContextoRequisicao {
    private final String                    metodo;
    private final String                    caminho;
    private final String                    corpo;
    private final Map<String, String>       parametrosCaminho;
    private final Map<String, List<String>> parametrosConsulta;
    private final Map<String, List<String>> cabecalhos;

    public ContextoRequisicao(String metodo, String caminho, String corpo, Map<String, String> parametrosCaminho, Map<String, List<String>> parametrosConsulta, Map<String, List<String>> cabecalhos) {
        this.metodo = metodo;
        this.caminho = caminho;
        this.corpo = corpo == null ? "" : corpo;
        this.parametrosCaminho = parametrosCaminho == null ? new LinkedHashMap<>() : new LinkedHashMap<>(parametrosCaminho);
        this.parametrosConsulta = parametrosConsulta == null ? new LinkedHashMap<>() : copiarMapaLista(parametrosConsulta);
        this.cabecalhos = cabecalhos == null ? new LinkedHashMap<>() : copiarMapaLista(cabecalhos);
    }

    // --- COPIA UM MAPA QUE POSSUI LISTAS COMO VALORES ---
    private Map<String, List<String>> copiarMapaLista(Map<String, List<String>> mapaOriginal) {
        Map<String, List<String>> copia = new LinkedHashMap<>();

        for (Map.Entry<String, List<String>> entrada : mapaOriginal.entrySet()) {
            copia.put(entrada.getKey(), entrada.getValue() == null ? List.of() : List.copyOf(entrada.getValue()));
        }

        return copia;
    }

    public String getMetodo() {
        return metodo;
    }

    public String getCaminho() {
        return caminho;
    }

    public String getCorpo() {
        return corpo;
    }

    public Map<String, String> getParametrosCaminho() {
        return Collections.unmodifiableMap(parametrosCaminho);
    }

    public Map<String, List<String>> getParametrosConsulta() {
        return Collections.unmodifiableMap(parametrosConsulta);
    }

    public Map<String, List<String>> getCabecalhos() {
        return Collections.unmodifiableMap(cabecalhos);
    }

    // --- RETORNA UM PARÂMETRO DE CAMINHO ---
    public String getParametroCaminho(String nome) {
        return parametrosCaminho.get(nome);
    }

    // --- RETORNA O PRIMEIRO VALOR DE UM PARÂMETRO DE CONSULTA ---
    public String getParametroConsulta(String nome) {
        List<String> valores = parametrosConsulta.get(nome);
        return valores == null || valores.isEmpty() ? null : valores.get(0);
    }

    // --- RETORNA O PRIMEIRO VALOR DE UM CABEÇALHO ---
    public String getCabecalho(String nome) {
        if (nome == null) {
            return null;
        }

        for (Map.Entry<String, List<String>> entrada : cabecalhos.entrySet()) {
            if (entrada.getKey().equalsIgnoreCase(nome)) {
                return entrada.getValue().isEmpty() ? null : entrada.getValue().get(0);
            }
        }

        return null;
    }
}

