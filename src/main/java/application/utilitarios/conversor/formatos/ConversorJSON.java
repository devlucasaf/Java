package application.utilitarios.conversor.formatos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ConversorJSON {

    private ConversorJSON() {
    }

    public static void escrever(List<Registro> registros, Path arquivo) throws IOException {
        StringBuilder stringBuilder = new StringBuilder("[\n");
        for (int i = 0; i < registros.size(); i++) {
            stringBuilder.append("  {");
            List<String> partes = new ArrayList<>();
            for (Map.Entry<String, String> e : registros.get(i).getCampos().entrySet()) {
                partes.add("\"" + escapar(e.getKey()) + "\": \"" + escapar(e.getValue()) + "\"");
            }

            stringBuilder.append(String.join(", ", partes));
            stringBuilder.append("}");
            if (i < registros.size() - 1) {
                stringBuilder.append(",");
            }
            stringBuilder.append("\n");
        }
        stringBuilder.append("]\n");
        Files.writeString(arquivo, stringBuilder.toString());
    }

    public static List<Registro> ler(Path arquivo) throws IOException {
        String texto = Files.readString(arquivo).trim();
        if (texto.startsWith("[")) {
            texto = texto.substring(1);
        }

        if (texto.endsWith("]")) {
            texto = texto.substring(0, texto.length() - 1);
        }

        List<Registro> registros = new ArrayList<>();
        int nivel = 0;
        StringBuilder atual = new StringBuilder();
        boolean dentroAspas = false;

        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            if (c == '"' && (i == 0 || texto.charAt(i - 1) != '\\')) {
                dentroAspas = !dentroAspas;
            }

            if (!dentroAspas) {
                if (c == '{') {
                    nivel++;
                } else if (c == '}') {
                    nivel--;
                    if (nivel == 0) {
                        atual.append(c);
                        registros.add(parseObjeto(atual.toString()));
                        atual.setLength(0);
                        continue;
                    }
                }
            }

            if (nivel > 0) {
                atual.append(c);
            }
        }
        return registros;
    }

    private static Registro parseObjeto(String texto) {
        Registro registro = new Registro();
        texto = texto.trim();
        if (texto.startsWith("{")) {
            texto = texto.substring(1);
        }

        if (texto.endsWith("}")) {
            texto = texto.substring(0, texto.length() - 1);
        }

        boolean dentroAspas = false;
        StringBuilder atual = new StringBuilder();
        List<String> pares = new ArrayList<>();
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            if (c == '"' && (i == 0 || texto.charAt(i - 1) != '\\')) {
                dentroAspas = !dentroAspas;
            }

            if (c == ',' && !dentroAspas) {
                pares.add(atual.toString());
                atual.setLength(0);
            } else {
                atual.append(c);
            }
        }
        pares.add(atual.toString());

        for (String par : pares) {
            int sep = par.indexOf(':');
            if (sep < 0) {
                continue;
            }

            String chave = limparString(par.substring(0, sep));
            String valor = limparString(par.substring(sep + 1));
            registro.set(chave, valor);
        }
        return registro;
    }

    private static String limparString(String s) {
        s = s.trim();
        if (s.startsWith("\"")) {
            s = s.substring(1);
        }

        if (s.endsWith("\"")) {
            s = s.substring(0, s.length() - 1);
        }
        return s.replace("\\\"", "\"").replace("\\\\", "\\");
    }

    private static String escapar(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}

