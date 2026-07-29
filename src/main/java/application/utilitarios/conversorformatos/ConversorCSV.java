package application.utilitarios.conversorformatos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class ConversorCSV {

    private ConversorCSV() {
    }

    public static List<Registro> ler(Path arquivo) throws IOException {
        List<String> linhas = Files.readAllLines(arquivo);
        if (linhas.isEmpty()) {
            return List.of();
        }
        String[] cabecalho = separar(linhas.get(0));
        List<Registro> registros = new ArrayList<>();
        for (int i = 1; i < linhas.size(); i++) {
            if (linhas.get(i).isBlank()) {
                continue;
            }

            String[] valores = separar(linhas.get(i));
            Registro r = new Registro();
            for (int j = 0; j < cabecalho.length && j < valores.length; j++) {
                r.set(cabecalho[j], valores[j]);
            }
            registros.add(r);
        }
        return registros;
    }

    public static void escrever(List<Registro> registros, Path arquivo) throws IOException {
        StringBuilder sb = new StringBuilder();
        if (registros.isEmpty()) {
            Files.writeString(arquivo, "");
            return;
        }

        List<String> chaves = new ArrayList<>(registros.get(0).getCampos().keySet());
        sb.append(String.join(",", chaves)).append('\n');
        for (Registro r : registros) {
            List<String> valores = new ArrayList<>();
            for (String c : chaves) {
                valores.add(escapar(r.get(c)));
            }
            sb.append(String.join(",", valores)).append('\n');
        }
        Files.writeString(arquivo, sb.toString());
    }

    private static String[] separar(String linha) {
        List<String> partes = new ArrayList<>();
        StringBuilder atual = new StringBuilder();
        boolean dentroAspas = false;
        for (int i = 0; i < linha.length(); i++) {
            char c = linha.charAt(i);
            if (c == '"') {
                dentroAspas = !dentroAspas;
            } else if (c == ',' && !dentroAspas) {
                partes.add(atual.toString());
                atual.setLength(0);
            } else {
                atual.append(c);
            }
        }
        partes.add(atual.toString());
        return partes.toArray(new String[0]);
    }

    private static String escapar(String valor) {
        if (valor == null) {
            return "";
        }

        if (valor.contains(",") || valor.contains("\"") || valor.contains("\n")) {
            return "\"" + valor.replace("\"", "\"\"") + "\"";
        }
        return valor;
    }
}

