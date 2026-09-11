package math.numerico.raizes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class ComparadorMetodos {

    // --- ORDENA OS RESULTADOS DO MENOR PARA O MAIOR ERRO ---
    public List<ResultadoRaiz> ordenarPorErro(List<ResultadoRaiz> resultados) {
        validarResultados(resultados);

        List<ResultadoRaiz> copia = new ArrayList<>(resultados);
        copia.sort(Comparator.comparingDouble(this::obterErroComparavel));
        return copia;
    }

    // --- ORDENA OS RESULTADOS PELA QUANTIDADE DE ITERAÇÕES ---
    public List<ResultadoRaiz> ordenarPorIteracoes(List<ResultadoRaiz> resultados) {
        validarResultados(resultados);

        List<ResultadoRaiz> copia = new ArrayList<>(resultados);
        copia.sort(Comparator.comparingInt(ResultadoRaiz::getQuantidadeIteracoes));
        return copia;
    }

    // --- RETORNA O RESULTADO COM O MENOR ERRO DISPONÍVEL ---
    public ResultadoRaiz encontrarMaisPreciso(List<ResultadoRaiz> resultados) {
        return ordenarPorErro(resultados).stream().filter(ResultadoRaiz::isConvergiu).findFirst().orElse(null);
    }

    // --- RETORNA O MÉTODO QUE UTILIZOU MENOS ITERAÇÕES ---
    public ResultadoRaiz encontrarMaisRapidoEmIteracoes(List<ResultadoRaiz> resultados) {
        return ordenarPorIteracoes(resultados).stream().filter(ResultadoRaiz::isConvergiu).findFirst().orElse(null);
    }

    // --- CRIA UM RELATÓRIO COMPARATIVO ---
    public String criarRelatorio(List<ResultadoRaiz> resultados) {
        validarResultados(resultados);

        StringBuilder relatorio = new StringBuilder();

        relatorio.append("============================================================\n");
        relatorio.append("                 COMPARAÇÃO DOS MÉTODOS\n");
        relatorio.append("============================================================\n");

        for (ResultadoRaiz resultado : resultados) {
            relatorio.append("\nMétodo: ").append(resultado.getMetodo().getNomeFormatado()).append("\n");
            relatorio.append("Situação: ").append(resultado.getSituacao().getNomeFormatado()).append("\n");
            relatorio.append("Raiz aproximada: ").append(formatarNumero(resultado.getRaizAproximada())).append("\n");
            relatorio.append("f(raiz): ").append(formatarNumero(resultado.getValorFuncaoNaRaiz())).append("\n");
            relatorio.append("Iterações: ").append(resultado.getQuantidadeIteracoes()).append("\n");
            relatorio.append("Erro absoluto final: ").append(formatarErro(resultado.getErroAbsolutoFinal())).append("\n");
            relatorio.append("Erro relativo final: ").append(formatarErro(resultado.getErroRelativoFinal())).append("\n");
            relatorio.append("Erro verdadeiro final: ").append(formatarErro(resultado.getErroVerdadeiroFinal())).append("\n");
            relatorio.append("Tempo: ").append(String.format(Locale.US, "%.6f", resultado.getTempoExecucaoMilissegundos())).append(" ms\n");
        }

        ResultadoRaiz maisPreciso = encontrarMaisPreciso(resultados);
        ResultadoRaiz menosIteracoes = encontrarMaisRapidoEmIteracoes(resultados);

        relatorio.append("\n============================================================\n");

        if (maisPreciso != null) {
            relatorio.append("Menor erro: ").append(maisPreciso.getMetodo().getNomeFormatado()).append("\n");
        }

        if (menosIteracoes != null) {
            relatorio.append("Menos iterações: ").append(menosIteracoes.getMetodo().getNomeFormatado()).append("\n");
        }

        return relatorio.toString();
    }

    // --- RETORNA O ERRO MAIS ADEQUADO PARA COMPARAÇÃO ---
    private double obterErroComparavel(ResultadoRaiz resultado) {
        if (!resultado.isConvergiu()) {
            return Double.POSITIVE_INFINITY;
        }

        if (resultado.getErroVerdadeiroFinal() != null) {
            return resultado.getErroVerdadeiroFinal();
        }

        if (resultado.getErroAbsolutoFinal() != null) {
            return resultado.getErroAbsolutoFinal();
        }

        return Math.abs(resultado.getValorFuncaoNaRaiz());
    }

    // --- FORMATA UM ERRO OPCIONAL ---
    private String formatarErro(Double erro) {
        return erro == null ? "Não disponível" : formatarNumero(erro);
    }

    // --- FORMATA UM NÚMERO ---
    private String formatarNumero(double valor) {
        if (!Double.isFinite(valor)) {
            return "Não disponível";
        }

        return String.format(Locale.US, "%.12f", valor);
    }

    // --- VALIDA A LISTA DE RESULTADOS ---
    private void validarResultados(List<ResultadoRaiz> resultados) {
        if (resultados == null || resultados.isEmpty()) {
            throw new IllegalArgumentException("A lista de resultados não pode estar vazia.");
        }
    }
}

