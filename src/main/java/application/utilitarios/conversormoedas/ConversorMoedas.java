package application.utilitarios.conversormoedas;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ConversorMoedas {

    private static final String URL_BASE = "https://economia.awesomeapi.com.br/last/";
    private final HttpClient    http = HttpClient.newHttpClient();
    private final Path          historicoArquivo = Path.of("target", "historico-cotacoes.csv");

    public Map<String, Double> consultar(String... pares) throws Exception {
        String url = URL_BASE + String.join(",", pares);
        HttpRequest req = HttpRequest.newBuilder(URI.create(url)).GET().build();
        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
        if (resp.statusCode() != 200) {
            throw new RuntimeException("Erro HTTP: " + resp.statusCode());
        }
        return parsear(resp.body());
    }

    private Map<String, Double> parsear(String json) {
        Map<String, Double> resultado = new HashMap<>();
        int i = 0;
        while (i < json.length()) {
            int codeStart = json.indexOf("\"code\":\"", i);
            if (codeStart == -1) break;
            codeStart += 8;
            int codeEnd = json.indexOf('"', codeStart);
            String code = json.substring(codeStart, codeEnd);

            int codeInStart = json.indexOf("\"codein\":\"", codeEnd);
            codeInStart += 10;
            int codeInEnd = json.indexOf('"', codeInStart);
            String codein = json.substring(codeInStart, codeInEnd);

            int bidStart = json.indexOf("\"bid\":\"", codeInEnd);
            bidStart += 7;
            int bidEnd = json.indexOf('"', bidStart);
            String bid = json.substring(bidStart, bidEnd);

            resultado.put(code + codein, Double.parseDouble(bid));
            i = bidEnd;
        }
        return resultado;
    }

    public void salvarHistorico(Map<String, Double> cotacoes) throws Exception {
        historicoArquivo.getParent().toFile().mkdirs();
        StringBuilder sb = new StringBuilder();
        String data = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        for (var e : cotacoes.entrySet()) {
            sb.append(data).append(',').append(e.getKey()).append(',').append(e.getValue()).append('\n');
        }
        Files.writeString(historicoArquivo, sb.toString(),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    public Path getHistoricoArquivo() {
        return historicoArquivo;
    }
}

