package application.simuladores.ia;

import java.util.List;

public class AnaliseCronologica {

    private final List<AnaliseValores>  historico;
    private final String                resumo;
    private final String                estilo;

    public AnaliseCronologica(List<AnaliseValores> historico, String resumo, String estilo) {
        this.historico = historico;
        this.resumo = resumo;
        this.estilo = estilo;
    }

    public List<AnaliseValores> getHistorico() {
        return historico;
    }

    public String getResumo() {
        return resumo;
    }

    public String getEstilo() {
        return estilo;
    }
}