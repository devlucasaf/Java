package application.utilitarios.logs;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AnalisadorLogs {

    private final Path arquivo;

    public AnalisadorLogs(Path arquivo) {
        this.arquivo = arquivo;
    }

    public Resultado analisar(Pattern filtro, String nivelFiltro) throws IOException {
        Map<String, Integer> porNivel = new HashMap<>();
        Map<Integer, Integer> porHora = new HashMap<>();
        long totalLinhas = 0;
        long linhasValidas = 0;
        long linhasCasadas = 0;
        LocalDateTime primeira = null;
        LocalDateTime ultima = null;

        try (BufferedReader br = Files.newBufferedReader(arquivo)) {
            String linha;
            while ((linha = br.readLine()) != null) {
                totalLinhas++;
                EntradaLog e = EntradaLog.parse(linha);
                if (e == null) {
                    continue;
                }
                linhasValidas++;

                if (nivelFiltro != null && !nivelFiltro.equalsIgnoreCase(e.getNivel())) {
                    continue;
                }
                
                if (filtro != null && !filtro.matcher(e.getLinhaOriginal()).find()) {
                    continue;
                }

                linhasCasadas++;
                porNivel.merge(e.getNivel(), 1, Integer::sum);
                porHora.merge(e.getInstante().getHour(), 1, Integer::sum);
                if (primeira == null || e.getInstante().isBefore(primeira)) {
                    primeira = e.getInstante();
                }
                
                if (ultima == null || e.getInstante().isAfter(ultima)) {
                    ultima = e.getInstante();
                }
            }
        }

        return new Resultado(totalLinhas, linhasValidas, linhasCasadas,
                ordenar(porNivel), ordenarInt(porHora), primeira, ultima);
    }

    private static Map<String, Integer> ordenar(Map<String, Integer> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue,
                        (a, b) -> a, LinkedHashMap::new));
    }

    private static Map<Integer, Integer> ordenarInt(Map<Integer, Integer> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a, LinkedHashMap::new));
    }

}

