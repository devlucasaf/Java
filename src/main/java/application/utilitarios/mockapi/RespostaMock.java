package application.utilitarios.mockapi;

import java.util.LinkedHashMap;
import java.util.Map;

public class RespostaMock {

    private final int                   status;
    private final String                tipoConteudo;
    private final String                corpo;
    private final Map<String, String>   cabecalhos;
    private final long                  atrasoMilissegundos;

    public RespostaMock(int status, String tipoConteudo, String corpo, Map<String, String> cabecalhos, long atrasoMilissegundos) {
        this.status = status;
        this.tipoConteudo = tipoConteudo;
        this.corpo = corpo == null ? "" : corpo;
        this.cabecalhos = cabecalhos == null ? new LinkedHashMap<>() : new LinkedHashMap<>(cabecalhos);
        this.atrasoMilissegundos = atrasoMilissegundos;
    }

    public int getStatus() {
        return status;
    }

    public String getTipoConteudo() {
        return tipoConteudo;
    }

    public String getCorpo() {
        return corpo;
    }

    public Map<String, String> getCabecalhos() {
        return new LinkedHashMap<>(cabecalhos);
    }

    public long getAtrasoMilissegundos() {
        return atrasoMilissegundos;
    }
}

