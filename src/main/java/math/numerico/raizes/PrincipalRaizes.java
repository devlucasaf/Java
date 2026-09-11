package math.numerico.raizes;

import java.util.List;
import java.util.Locale;
import java.util.function.DoubleUnaryOperator;

public class PrincipalRaizes {

    public static void main(String[] args) {
        DoubleUnaryOperator funcao = x -> x * x * x - x - 2;
        DoubleUnaryOperator derivada = x -> 3 * x * x - 1;

        double raizExataAproximada = 1.5213797068045676;
        ConfiguracaoCalculo configuracao = new ConfiguracaoCalculo(1.0E-10, 100, raizExataAproximada);
        SolucionadorRaizes solucionador = new SolucionadorRaizes();

        ResultadoRaiz resultadoBissecao = solucionador.bissecao(funcao, 1.0, 2.0, configuracao);
        ResultadoRaiz resultadoNewton = solucionador.newtonRaphson(funcao, derivada, 1.5, configuracao);
        ResultadoRaiz resultadoSecante = solucionador.secante(funcao, 1.0, 2.0, configuracao);

        List<ResultadoRaiz> resultados = List.of(resultadoBissecao, resultadoNewton, resultadoSecante);

        for (ResultadoRaiz resultado : resultados) {
            exibirResultado(resultado);
            exibirHistorico(resultado);
        }

        ComparadorMetodos comparador = new ComparadorMetodos();
        System.out.println(comparador.criarRelatorio(resultados));
    }

    // --- EXIBE O RESULTADO DE UM MÉTODO ---
    private static void exibirResultado(ResultadoRaiz resultado) {
        System.out.println("\n============================================================");
        System.out.println("MÉTODO: " + resultado.getMetodo().getNomeFormatado().toUpperCase());
        System.out.println("============================================================");
        System.out.println("Situação: " + resultado.getSituacao().getNomeFormatado());
        System.out.println("Mensagem: " + resultado.getMensagem());
        System.out.println("Raiz aproximada: " + formatarNumero(resultado.getRaizAproximada()));
        System.out.println("Valor da função: " + formatarNumero(resultado.getValorFuncaoNaRaiz()));
        System.out.println("Iterações: " + resultado.getQuantidadeIteracoes());
        System.out.println("Erro absoluto final: " + formatarErro(resultado.getErroAbsolutoFinal()));
        System.out.println("Erro relativo final: " + formatarErro(resultado.getErroRelativoFinal()));
        System.out.println("Erro verdadeiro final: " + formatarErro(resultado.getErroVerdadeiroFinal()));
        System.out.println("Tempo de execução: " + formatarNumero(resultado.getTempoExecucaoMilissegundos()) + " ms");
    }

    // --- EXIBE O HISTÓRICO DAS ITERAÇÕES ---
    private static void exibirHistorico(ResultadoRaiz resultado) {
        if (resultado.getHistorico().isEmpty()) {
            System.out.println("Nenhuma iteração foi realizada.");
            return;
        }

        System.out.println("\nIterações:");
        System.out.printf("%-6s %-18s %-18s %-18s %-18s%n", "Nº", "Aproximação", "f(x)", "Erro absoluto", "Erro verdadeiro");

        for (Iteracao iteracao : resultado.getHistorico()) {
            System.out.printf("%-6d %-18s %-18s %-18s %-18s%n",
                    iteracao.getNumero(),
                    formatarNumero(iteracao.getAproximacao()),
                    formatarNumero(iteracao.getValorFuncao()),
                    formatarErro(iteracao.getErroAbsoluto()),
                    formatarErro(iteracao.getErroVerdadeiro()));
        }
    }

    // --- FORMATA UM ERRO OPCIONAL ---
    private static String formatarErro(Double erro) {
        return erro == null ? "Não disponível" : formatarNumero(erro);
    }

    // --- FORMATA UM VALOR NUMÉRICO ---
    private static String formatarNumero(double valor) {
        if (!Double.isFinite(valor)) {
            return "Não disponível";
        }

        return String.format(Locale.US, "%.12f", valor);
    }
}

