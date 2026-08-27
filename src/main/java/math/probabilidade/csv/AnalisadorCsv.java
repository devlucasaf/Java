package math.probabilidade.csv;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AnalisadorCsv {

    private final LeitorCsv                 leitorCsv;
    private final CalculadoraEstatistica    calculadora;

    public AnalisadorCsv() {
        this.leitorCsv = new LeitorCsv();
        this.calculadora = new CalculadoraEstatistica();
    }

    // --- ANALISA TODAS AS COLUNAS NUMÉRICAS DE UM ARQUIVO CSV ---
    public ResultadoAnaliseCsv analisar(String caminhoArquivo) throws IOException {
        if (caminhoArquivo == null || caminhoArquivo.trim().isEmpty()) {
            throw new IllegalArgumentException("O caminho do arquivo não pode estar vazio.");
        }

        TabelaCsv tabela = leitorCsv.ler(caminhoArquivo);
        List<ResumoEstatistico> resumos = calcularResumos(tabela);
        List<ResultadoCorrelacao> correlacoes = calcularCorrelacoes(tabela);

        return new ResultadoAnaliseCsv(Path.of(caminhoArquivo).toAbsolutePath().toString(), tabela, resumos, correlacoes);
    }

    // --- CALCULA OS RESUMOS DAS COLUNAS NUMÉRICAS ---
    private List<ResumoEstatistico> calcularResumos(TabelaCsv tabela) {
        List<ResumoEstatistico> resumos = new ArrayList<>();

        for (ColunaCsv coluna : tabela.getColunasNumericas()) {
            resumos.add(calculadora.calcularResumo(coluna));
        }

        return resumos;
    }

    // --- CALCULA TODAS AS COMBINAÇÕES DE CORRELAÇÃO ---
    private List<ResultadoCorrelacao> calcularCorrelacoes(TabelaCsv tabela) {
        List<ResultadoCorrelacao> correlacoes = new ArrayList<>();
        List<ColunaCsv> colunasNumericas = tabela.getColunasNumericas();

        for (int primeiroIndice = 0; primeiroIndice < colunasNumericas.size(); primeiroIndice++) {
            for (int segundoIndice = primeiroIndice + 1; segundoIndice < colunasNumericas.size(); segundoIndice++) {
                ColunaCsv primeiraColuna = colunasNumericas.get(primeiroIndice);
                ColunaCsv segundaColuna = colunasNumericas.get(segundoIndice);
                correlacoes.add(calculadora.calcularCorrelacao(primeiraColuna, segundaColuna));
            }
        }

        return correlacoes;
    }
}

