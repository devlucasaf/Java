package math.estatistica.regressaolinear;

public class MetricasRegressao {

    private final int       quantidadeObservacoes;
    private final int       quantidadeVariaveis;
    private final double    rQuadrado;
    private final double    rQuadradoAjustado;
    private final double    erroAbsolutoMedio;
    private final double    raizErroQuadraticoMedio;
    private final double    erroQuadraticoMedio;
    private final double    somaQuadradosResiduos;
    private final double    somaQuadradosTotal;

    public MetricasRegressao(double[] valoresReais, double[] valoresPrevistos, int quantidadeVariaveis, boolean possuiIntercepto) {
        validarDados(valoresReais, valoresPrevistos, quantidadeVariaveis);

        this.quantidadeObservacoes = valoresReais.length;
        this.quantidadeVariaveis = quantidadeVariaveis;

        double mediaReal = calcularMedia(valoresReais);
        double somaErrosAbsolutos = 0;
        double somaErrosQuadrados = 0;
        double somaTotal = 0;

        for (int indice = 0; indice < valoresReais.length; indice++) {
            double residuo = valoresReais[indice] - valoresPrevistos[indice];
            double diferencaMedia = valoresReais[indice] - mediaReal;

            somaErrosAbsolutos += Math.abs(residuo);
            somaErrosQuadrados += residuo * residuo;
            somaTotal += diferencaMedia * diferencaMedia;
        }

        this.somaQuadradosResiduos = somaErrosQuadrados;
        this.somaQuadradosTotal = somaTotal;
        this.erroAbsolutoMedio = somaErrosAbsolutos / quantidadeObservacoes;
        this.erroQuadraticoMedio = somaErrosQuadrados / quantidadeObservacoes;
        this.raizErroQuadraticoMedio = Math.sqrt(erroQuadraticoMedio);
        this.rQuadrado = calcularRQuadrado(somaErrosQuadrados, somaTotal);
        this.rQuadradoAjustado = calcularRQuadradoAjustado(rQuadrado, quantidadeObservacoes, quantidadeVariaveis, possuiIntercepto);
    }

    // --- CALCULA A MÉDIA DE UM VETOR ---
    private double calcularMedia(double[] valores) {
        double soma = 0;

        for (double valor : valores) {
            soma += valor;
        }

        return soma / valores.length;
    }

    // --- CALCULA O COEFICIENTE DE DETERMINAÇÃO ---
    private double calcularRQuadrado(double somaResiduos, double somaTotal) {
        if (somaTotal == 0.0) {
            return somaResiduos == 0.0 ? 1.0 : 0.0;
        }

        return 1.0 - somaResiduos / somaTotal;
    }

    // --- CALCULA O COEFICIENTE DE DETERMINAÇÃO AJUSTADO ---
    private double calcularRQuadradoAjustado(double rQuadrado, int observacoes, int variaveis, boolean possuiIntercepto) {
        int quantidadeParametros = variaveis + (possuiIntercepto ? 1 : 0);
        int grausLiberdadeResiduais = observacoes - quantidadeParametros;

        if (grausLiberdadeResiduais <= 0) {
            return Double.NaN;
        }

        return 1.0 - (1.0 - rQuadrado) * (observacoes - 1.0) / grausLiberdadeResiduais;
    }

    // --- VALIDA OS VETORES UTILIZADOS NO CÁLCULO ---
    private void validarDados(double[] valoresReais, double[] valoresPrevistos, int quantidadeVariaveis) {
        if (valoresReais == null || valoresPrevistos == null || valoresReais.length == 0) {
            throw new IllegalArgumentException("Os vetores de valores não podem estar vazios.");
        }

        if (valoresReais.length != valoresPrevistos.length) {
            throw new IllegalArgumentException("Os vetores reais e previstos devem possuir o mesmo tamanho.");
        }

        if (quantidadeVariaveis <= 0) {
            throw new IllegalArgumentException("A quantidade de variáveis deve ser maior que zero.");
        }

        for (int indice = 0; indice < valoresReais.length; indice++) {
            if (!Double.isFinite(valoresReais[indice]) || !Double.isFinite(valoresPrevistos[indice])) {
                throw new IllegalArgumentException("Os valores reais e previstos devem ser finitos.");
            }
        }
    }

    public int getQuantidadeObservacoes() {
        return quantidadeObservacoes;
    }

    public int getQuantidadeVariaveis() {
        return quantidadeVariaveis;
    }

    public double getRQuadrado() {
        return rQuadrado;
    }

    public double getRQuadradoAjustado() {
        return rQuadradoAjustado;
    }

    public double getErroAbsolutoMedio() {
        return erroAbsolutoMedio;
    }

    public double getRaizErroQuadraticoMedio() {
        return raizErroQuadraticoMedio;
    }

    public double getErroQuadraticoMedio() {
        return erroQuadraticoMedio;
    }

    public double getSomaQuadradosResiduos() {
        return somaQuadradosResiduos;
    }

    public double getSomaQuadradosTotal() {
        return somaQuadradosTotal;
    }
}

