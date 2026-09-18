package math.estatistica.regressaolinear;

import java.util.Map;

public class PrincipalRegressaoLinear {

    public static void main(String[] args) {
        double[][] variaveis = {
                {50, 1, 20},
                {60, 2, 15},
                {70, 2, 10},
                {80, 3, 8},
                {90, 3, 5},
                {100, 3, 3},
                {110, 4, 2},
                {120, 4, 1},
                {65, 2, 18},
                {85, 3, 12},
                {95, 3, 7},
                {105, 4, 4}
        };

        double[] precos = {
                193000,
                247500,
                284000,
                343000,
                381500,
                420500,
                479000,
                518500,
                260000,
                355000,
                400000,
                455000
        };

        String[] nomesVariaveis = {"Área", "Quartos", "Idade"};
        DadosRegressao dados = new DadosRegressao(variaveis, precos, nomesVariaveis);
        ConfiguracaoRegressao configuracao = new ConfiguracaoRegressao(true);
        RegressaoLinearMultipla regressao = new RegressaoLinearMultipla(configuracao);

        try {
            ResultadoRegressao resultado = regressao.treinar(dados);
            exibirResultado(resultado);
            exibirPrevisoes(resultado);

            double novaPrevisao = resultado.prever(75, 2, 6);

            System.out.println("\n============================================================");
            System.out.println("                     NOVA PREVISÃO");
            System.out.println("============================================================");
            System.out.println("Área: 75");
            System.out.println("Quartos: 2");
            System.out.println("Idade: 6");
            System.out.printf("Preço previsto: R$ %.2f%n", novaPrevisao);
        } catch (IllegalArgumentException excecao) {
            System.out.println("Não foi possível treinar o modelo: " + excecao.getMessage());
        }
    }

    // --- EXIBE OS COEFICIENTES E AS MÉTRICAS DO MODELO ---
    private static void exibirResultado(ResultadoRegressao resultado) {
        MetricasRegressao metricas = resultado.getMetricas();

        System.out.println("============================================================");
        System.out.println("              REGRESSÃO LINEAR MÚLTIPLA");
        System.out.println("============================================================");
        System.out.println("Equação:");
        System.out.println(resultado.getEquacaoFormatada());

        System.out.println("\nCOEFICIENTES");

        for (Map.Entry<String, Double> coeficiente : resultado.getCoeficientesNomeados().entrySet()) {
            System.out.printf("%s: %.6f%n", coeficiente.getKey(), coeficiente.getValue());
        }

        System.out.println("\nMÉTRICAS");
        System.out.printf("R²: %.6f%n", metricas.getRQuadrado());

        if (Double.isFinite(metricas.getRQuadradoAjustado())) {
            System.out.printf("R² ajustado: %.6f%n", metricas.getRQuadradoAjustado());
        } else {
            System.out.println("R² ajustado: não disponível");
        }

        System.out.printf("MAE: %.6f%n", metricas.getErroAbsolutoMedio());
        System.out.printf("MSE: %.6f%n", metricas.getErroQuadraticoMedio());
        System.out.printf("RMSE: %.6f%n", metricas.getRaizErroQuadraticoMedio());
        System.out.printf("Soma dos quadrados dos resíduos: %.6f%n", metricas.getSomaQuadradosResiduos());
        System.out.printf("Soma total dos quadrados: %.6f%n", metricas.getSomaQuadradosTotal());
    }

    // --- EXIBE OS VALORES REAIS, PREVISTOS E RESÍDUOS ---
    private static void exibirPrevisoes(ResultadoRegressao resultado) {
        double[] valoresReais = resultado.getValoresReais();
        double[] valoresPrevistos = resultado.getValoresPrevistos();
        double[] residuos = resultado.getResiduos();

        System.out.println("\n============================================================");
        System.out.println("              PREVISÕES DO CONJUNTO DE TREINO");
        System.out.println("============================================================");
        System.out.printf("%-12s %-18s %-18s %-18s%n", "Observação", "Valor real", "Valor previsto", "Resíduo");

        for (int indice = 0; indice < valoresReais.length; indice++) {
            System.out.printf("%-12d %-18.6f %-18.6f %-18.6f%n", indice + 1, valoresReais[indice], valoresPrevistos[indice], residuos[indice]);
        }
    }
}

