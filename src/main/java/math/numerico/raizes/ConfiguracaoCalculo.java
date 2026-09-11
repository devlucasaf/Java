package math.numerico.raizes;

public class ConfiguracaoCalculo {

    private final double tolerancia;
    private final int maximoIteracoes;
    private final double limiteValorProximoZero;
    private final Double raizExata;

    public ConfiguracaoCalculo(double tolerancia, int maximoIteracoes) {
        this(tolerancia, maximoIteracoes, 1.0E-14, null);
    }

    public ConfiguracaoCalculo(double tolerancia, int maximoIteracoes, Double raizExata) {
        this(tolerancia, maximoIteracoes, 1.0E-14, raizExata);
    }

    public ConfiguracaoCalculo(double tolerancia, int maximoIteracoes, double limiteValorProximoZero, Double raizExata) {
        if (!Double.isFinite(tolerancia) || tolerancia <= 0) {
            throw new IllegalArgumentException("A tolerância deve ser um número finito maior que zero.");
        }

        if (maximoIteracoes <= 0) {
            throw new IllegalArgumentException("O máximo de iterações deve ser maior que zero.");
        }

        if (!Double.isFinite(limiteValorProximoZero) || limiteValorProximoZero <= 0) {
            throw new IllegalArgumentException("O limite de proximidade de zero deve ser um número finito maior que zero.");
        }

        if (raizExata != null && !Double.isFinite(raizExata)) {
            throw new IllegalArgumentException("A raiz exata deve ser finita.");
        }

        this.tolerancia = tolerancia;
        this.maximoIteracoes = maximoIteracoes;
        this.limiteValorProximoZero = limiteValorProximoZero;
        this.raizExata = raizExata;
    }

    public double getTolerancia() {
        return tolerancia;
    }

    public int getMaximoIteracoes() {
        return maximoIteracoes;
    }

    public double getLimiteValorProximoZero() {
        return limiteValorProximoZero;
    }

    public Double getRaizExata() {
        return raizExata;
    }
}

