package math.probabilidade.csv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CalculadoraEstatistica {

    // --- CALCULA O RESUMO ESTATÍSTICO DE UMA COLUNA ---
    public ResumoEstatistico calcularResumo(ColunaCsv coluna) {
        if (coluna == null) {
            throw new IllegalArgumentException("A coluna não pode ser nula.");
        }

        List<Double> valores = coluna.getValoresNumericos();

        if (valores.isEmpty()) {
            throw new IllegalArgumentException("A coluna não possui valores numéricos.");
        }

        double          soma = calcularSoma(valores);
        double          media = soma / valores.size();
        double          mediana = calcularMediana(valores);
        List<Double>    modas = calcularModas(valores);
        double          varianciaPopulacional = calcularVarianciaPopulacional(valores, media);
        double          desvioPadraoPopulacional = Math.sqrt(varianciaPopulacional);
        double          desvioPadraoAmostral = calcularDesvioPadraoAmostral(valores, media);
        double          minimo = Collections.min(valores);
        double          maximo = Collections.max(valores);

        return new ResumoEstatistico(coluna.getNome(), valores.size(), coluna.contarValoresAusentes(),
                soma, media, mediana, modas, desvioPadraoPopulacional, desvioPadraoAmostral,
                varianciaPopulacional, minimo, maximo);
    }

    // --- CALCULA A SOMA DOS VALORES ---
    public double calcularSoma(List<Double> valores) {
        validarValores(valores);

        double soma = 0.0;

        for (double valor : valores) {
            soma += valor;
        }

        return soma;
    }

    // --- CALCULA A MÉDIA ARITMÉTICA ---
    public double calcularMedia(List<Double> valores) {
        validarValores(valores);
        return calcularSoma(valores) / valores.size();
    }

    // --- CALCULA A MEDIANA ---
    public double calcularMediana(List<Double> valores) {
        validarValores(valores);

        List<Double> valoresOrdenados = new ArrayList<>(valores);
        Collections.sort(valoresOrdenados);

        int tamanho = valoresOrdenados.size();
        int meio = tamanho / 2;

        if (tamanho % 2 == 0) {
            return (valoresOrdenados.get(meio - 1) + valoresOrdenados.get(meio)) / 2.0;
        }

        return valoresOrdenados.get(meio);
    }

    // --- CALCULA AS MODAS ---
    public List<Double> calcularModas(List<Double> valores) {
        validarValores(valores);

        Map<Double, Integer> frequencias = new LinkedHashMap<>();
        int maiorFrequencia = 0;

        for (Double valor : valores) {
            int frequencia = frequencias.getOrDefault(valor, 0) + 1;
            frequencias.put(valor, frequencia);
            maiorFrequencia = Math.max(maiorFrequencia, frequencia);
        }

        if (maiorFrequencia <= 1) {
            return new ArrayList<>();
        }

        List<Double> modas = new ArrayList<>();

        for (Map.Entry<Double, Integer> entrada : frequencias.entrySet()) {
            if (entrada.getValue() == maiorFrequencia) {
                modas.add(entrada.getKey());
            }
        }

        Collections.sort(modas);
        return modas;
    }

    // --- CALCULA A VARIÂNCIA POPULACIONAL ---
    public double calcularVarianciaPopulacional(List<Double> valores, double media) {
        validarValores(valores);

        double somaDiferencas = 0.0;

        for (double valor : valores) {
            double diferenca = valor - media;
            somaDiferencas += diferenca * diferenca;
        }

        return somaDiferencas / valores.size();
    }

    // --- CALCULA O DESVIO PADRÃO AMOSTRAL ---
    public double calcularDesvioPadraoAmostral(List<Double> valores, double media) {
        validarValores(valores);

        if (valores.size() < 2) {
            return 0.0;
        }

        double somaDiferencas = 0.0;

        for (double valor : valores) {
            double diferenca = valor - media;
            somaDiferencas += diferenca * diferenca;
        }

        return Math.sqrt(somaDiferencas / (valores.size() - 1));
    }

    // --- CALCULA A CORRELAÇÃO DE PEARSON ENTRE DUAS COLUNAS ---
    public ResultadoCorrelacao calcularCorrelacao(ColunaCsv primeiraColuna, ColunaCsv segundaColuna) {
        if (primeiraColuna == null || segundaColuna == null) {
            throw new IllegalArgumentException("As duas colunas devem ser informadas.");
        }

        List<Double> primeirosValores = new ArrayList<>();
        List<Double> segundosValores = new ArrayList<>();
        int quantidadeLinhas = Math.min(primeiraColuna.getQuantidadeValores(), segundaColuna.getQuantidadeValores());

        for (int indice = 0; indice < quantidadeLinhas; indice++) {
            Double primeiroValor = ColunaCsv.converterParaNumero(primeiraColuna.getValores().get(indice));
            Double segundoValor = ColunaCsv.converterParaNumero(segundaColuna.getValores().get(indice));

            if (primeiroValor != null && segundoValor != null) {
                primeirosValores.add(primeiroValor);
                segundosValores.add(segundoValor);
            }
        }

        if (primeirosValores.size() < 2) {
            return new ResultadoCorrelacao(primeiraColuna.getNome(), segundaColuna.getNome(), primeirosValores.size(), null, "Dados insuficientes");
        }

        double mediaPrimeira = calcularMedia(primeirosValores);
        double mediaSegunda = calcularMedia(segundosValores);
        double numerador = 0.0;
        double somaQuadradosPrimeira = 0.0;
        double somaQuadradosSegunda = 0.0;

        for (int indice = 0; indice < primeirosValores.size(); indice++) {
            double diferencaPrimeira = primeirosValores.get(indice) - mediaPrimeira;
            double diferencaSegunda = segundosValores.get(indice) - mediaSegunda;

            numerador += diferencaPrimeira * diferencaSegunda;
            somaQuadradosPrimeira += diferencaPrimeira * diferencaPrimeira;
            somaQuadradosSegunda += diferencaSegunda * diferencaSegunda;
        }

        double denominador = Math.sqrt(somaQuadradosPrimeira * somaQuadradosSegunda);

        if (denominador == 0.0) {
            return new ResultadoCorrelacao(primeiraColuna.getNome(), segundaColuna.getNome(), primeirosValores.size(), null, "Correlação indefinida por ausência de variação");
        }

        double coeficiente = numerador / denominador;
        coeficiente = Math.max(-1.0, Math.min(1.0, coeficiente));

        return new ResultadoCorrelacao(primeiraColuna.getNome(), segundaColuna.getNome(), primeirosValores.size(), coeficiente, interpretarCorrelacao(coeficiente));
    }

    // --- INTERPRETA O COEFICIENTE DE CORRELAÇÃO ---
    public String interpretarCorrelacao(double coeficiente) {
        double valorAbsoluto = Math.abs(coeficiente);
        String intensidade;

        if (valorAbsoluto >= 0.9) {
            intensidade = "muito forte";
        } else if (valorAbsoluto >= 0.7) {
            intensidade = "forte";
        } else if (valorAbsoluto >= 0.5) {
            intensidade = "moderada";
        } else if (valorAbsoluto >= 0.3) {
            intensidade = "fraca";
        } else {
            intensidade = "muito fraca";
        }

        if (coeficiente > 0) {
            return "Correlação positiva " + intensidade;
        }

        if (coeficiente < 0) {
            return "Correlação negativa " + intensidade;
        }

        return "Sem correlação linear";
    }

    // --- VALIDA UMA LISTA DE VALORES ---
    private void validarValores(List<Double> valores) {
        if (valores == null || valores.isEmpty()) {
            throw new IllegalArgumentException("A lista deve possuir pelo menos um valor.");
        }
    }
}