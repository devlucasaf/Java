package math.estatistica.regressaolinear;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class ResultadoRegressao {

    private final boolean           possuiIntercepto;
    private final double            intercepto;
    private final double[]          coeficientes;
    private final String[]          nomesVariaveis;
    private final double[]          valoresReais;
    private final double[]          valoresPrevistos;
    private final double[]          residuos;
    private final MetricasRegressao metricas;

    public ResultadoRegressao(boolean possuiIntercepto, double intercepto, double[] coeficientes, String[] nomesVariaveis, double[] valoresReais, double[] valoresPrevistos) {
        if (coeficientes == null || nomesVariaveis == null || coeficientes.length != nomesVariaveis.length) {
            throw new IllegalArgumentException("Os coeficientes devem corresponder aos nomes das variáveis.");
        }

        if (valoresReais == null || valoresPrevistos == null || valoresReais.length != valoresPrevistos.length) {
            throw new IllegalArgumentException("Os valores reais e previstos devem possuir o mesmo tamanho.");
        }

        this.possuiIntercepto = possuiIntercepto;
        this.intercepto = intercepto;
        this.coeficientes = Arrays.copyOf(coeficientes, coeficientes.length);
        this.nomesVariaveis = Arrays.copyOf(nomesVariaveis, nomesVariaveis.length);
        this.valoresReais = Arrays.copyOf(valoresReais, valoresReais.length);
        this.valoresPrevistos = Arrays.copyOf(valoresPrevistos, valoresPrevistos.length);
        this.residuos = calcularResiduos(this.valoresReais, this.valoresPrevistos);
        this.metricas = new MetricasRegressao(this.valoresReais, this.valoresPrevistos, coeficientes.length, possuiIntercepto);
    }

    // --- REALIZA UMA PREVISÃO COM NOVAS VARIÁVEIS ---
    public double prever(double... variaveis) {
        if (variaveis == null || variaveis.length != coeficientes.length) {
            throw new IllegalArgumentException("A previsão exige " + coeficientes.length + " variável(is).");
        }

        double previsao = possuiIntercepto ? intercepto : 0;

        for (int indice = 0; indice < variaveis.length; indice++) {
            if (!Double.isFinite(variaveis[indice])) {
                throw new IllegalArgumentException("As variáveis da previsão devem ser finitas.");
            }

            previsao += coeficientes[indice] * variaveis[indice];
        }

        return previsao;
    }

    // --- REALIZA PREVISÕES PARA VÁRIAS OBSERVAÇÕES ---
    public double[] prever(double[][] observacoes) {
        if (observacoes == null) {
            throw new IllegalArgumentException("As observações não podem ser nulas.");
        }

        double[] previsoes = new double[observacoes.length];

        for (int linha = 0; linha < observacoes.length; linha++) {
            previsoes[linha] = prever(observacoes[linha]);
        }

        return previsoes;
    }

    // --- CALCULA OS RESÍDUOS DO MODELO ---
    private double[] calcularResiduos(double[] reais, double[] previstos) {
        double[] resultado = new double[reais.length];

        for (int indice = 0; indice < reais.length; indice++) {
            resultado[indice] = reais[indice] - previstos[indice];
        }

        return resultado;
    }

    // --- RETORNA OS COEFICIENTES ASSOCIADOS AOS NOMES DAS VARIÁVEIS ---
    public Map<String, Double> getCoeficientesNomeados() {
        Map<String, Double> resultado = new LinkedHashMap<>();

        if (possuiIntercepto) {
            resultado.put("Intercepto", intercepto);
        }

        for (int indice = 0; indice < coeficientes.length; indice++) {
            resultado.put(nomesVariaveis[indice], coeficientes[indice]);
        }

        return resultado;
    }

    // --- RETORNA A EQUAÇÃO FORMATADA DO MODELO ---
    public String getEquacaoFormatada() {
        StringBuilder equacao = new StringBuilder("y = ");

        if (possuiIntercepto) {
            equacao.append(String.format("%.6f", intercepto));
        } else {
            equacao.append("0");
        }

        for (int indice = 0; indice < coeficientes.length; indice++) {
            double coeficiente = coeficientes[indice];

            if (coeficiente >= 0) {
                equacao.append(" + ");
            } else {
                equacao.append(" - ");
            }

            equacao.append(String.format("%.6f", Math.abs(coeficiente)));
            equacao.append(" * ");
            equacao.append(nomesVariaveis[indice]);
        }

        return equacao.toString();
    }

    public boolean isPossuiIntercepto() {
        return possuiIntercepto;
    }

    public double getIntercepto() {
        return intercepto;
    }

    public double[] getCoeficientes() {
        return Arrays.copyOf(coeficientes, coeficientes.length);
    }

    public String[] getNomesVariaveis() {
        return Arrays.copyOf(nomesVariaveis, nomesVariaveis.length);
    }

    public double[] getValoresReais() {
        return Arrays.copyOf(valoresReais, valoresReais.length);
    }

    public double[] getValoresPrevistos() {
        return Arrays.copyOf(valoresPrevistos, valoresPrevistos.length);
    }

    public double[] getResiduos() {
        return Arrays.copyOf(residuos, residuos.length);
    }

    public MetricasRegressao getMetricas() {
        return metricas;
    }
}

