package math.probabilidade.csv;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResultadoAnaliseCsv {

    private final String                    arquivoAnalisado;
    private final LocalDateTime             dataAnalise;
    private final int                       quantidadeRegistros;
    private final int                       quantidadeColunas;
    private final int                       quantidadeColunasNumericas;
    private final List<String>              colunas;
    private final List<ResumoEstatistico>   resumos;
    private final List<ResultadoCorrelacao> correlacoes;

    public ResultadoAnaliseCsv(String arquivoAnalisado, TabelaCsv tabela, List<ResumoEstatistico> resumos, List<ResultadoCorrelacao> correlacoes) {
        this.arquivoAnalisado = arquivoAnalisado;
        this.dataAnalise = LocalDateTime.now();
        this.quantidadeRegistros = tabela.getQuantidadeRegistros();
        this.quantidadeColunas = tabela.getQuantidadeColunas();
        this.quantidadeColunasNumericas = tabela.getColunasNumericas().size();
        this.colunas = new ArrayList<>();
        this.resumos = new ArrayList<>(resumos);
        this.correlacoes = new ArrayList<>(correlacoes);

        for (ColunaCsv coluna : tabela.getColunas()) {
            colunas.add(coluna.getNome());
        }
    }

    public String getArquivoAnalisado() {
        return arquivoAnalisado;
    }

    public LocalDateTime getDataAnalise() {
        return dataAnalise;
    }

    public int getQuantidadeRegistros() {
        return quantidadeRegistros;
    }

    public int getQuantidadeColunas() {
        return quantidadeColunas;
    }

    public int getQuantidadeColunasNumericas() {
        return quantidadeColunasNumericas;
    }

    public List<String> getColunas() {
        return Collections.unmodifiableList(colunas);
    }

    public List<ResumoEstatistico> getResumos() {
        return Collections.unmodifiableList(resumos);
    }

    public List<ResultadoCorrelacao> getCorrelacoes() {
        return Collections.unmodifiableList(correlacoes);
    }
}

