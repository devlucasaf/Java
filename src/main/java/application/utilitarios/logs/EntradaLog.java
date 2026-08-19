package application.utilitarios.logs;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EntradaLog {

    private static final Pattern PADRAO =
            Pattern.compile("\\[(.*?)\\]\\s+(\\w+)\\s+(.*)");
    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final LocalDateTime instante;
    private final String nivel;
    private final String mensagem;
    private final String linhaOriginal;

    public EntradaLog(LocalDateTime instante, String nivel, String mensagem, String original) {
        this.instante = instante;
        this.nivel = nivel;
        this.mensagem = mensagem;
        this.linhaOriginal = original;
    }

    public LocalDateTime getInstante() {
        return instante;
    }

    public String getNivel() {
        return nivel;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getLinhaOriginal() {
        return linhaOriginal;
    }

    public static EntradaLog parse(String linha) {
        Matcher m = PADRAO.matcher(linha);
        if (!m.find()) return null;
        try {
            LocalDateTime dt = LocalDateTime.parse(m.group(1), FMT);
            return new EntradaLog(dt, m.group(2).toUpperCase(), m.group(3), linha);
        } catch (Exception e) {
            return null;
        }
    }
}

