package application.utilitarios.conversorformatos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ConversorXML {

    private ConversorXML() {
    }

    public static void escrever(List<Registro> registros, Path arquivo) throws IOException {
        StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<registros>\n");
        for (Registro r : registros) {
            sb.append("  <registro>\n");
            for (Map.Entry<String, String> e : r.getCampos().entrySet()) {
                String chave = sanitizarTag(e.getKey());
                sb.append("    <").append(chave).append(">")
                        .append(escapar(e.getValue()))
                        .append("</").append(chave).append(">\n");
            }
            sb.append("  </registro>\n");
        }
        sb.append("</registros>\n");
        Files.writeString(arquivo, sb.toString());
    }

    public static List<Registro> ler(Path arquivo) throws IOException {
        String texto = Files.readString(arquivo);
        Pattern padraoRegistro = Pattern.compile("<registro>(.*?)</registro>", Pattern.DOTALL);
        Pattern padraoCampo = Pattern.compile("<(\\w+)>(.*?)</\\1>", Pattern.DOTALL);
        List<Registro> registros = new ArrayList<>();
        Matcher mReg = padraoRegistro.matcher(texto);
        while (mReg.find()) {
            Registro r = new Registro();
            Matcher mCampo = padraoCampo.matcher(mReg.group(1));
            while (mCampo.find()) {
                r.set(mCampo.group(1), desescapar(mCampo.group(2).trim()));
            }
            registros.add(r);
        }
        return registros;
    }

    private static String sanitizarTag(String s) {
        return s.replaceAll("[^a-zA-Z0-9_]", "_");
    }

    private static String escapar(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;");
    }

    private static String desescapar(String s) {
        return s.replace("&quot;", "\"").replace("&gt;", ">")
                .replace("&lt;", "<").replace("&amp;", "&");
    }
}

