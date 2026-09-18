package math.estatistica.regressaolinear;

public class ConfiguracaoRegressao {

    private final boolean   incluirIntercepto;
    private final double    toleranciaSingularidade;

    public ConfiguracaoRegressao() {
        this(true, 1.0E-12);
    }

    public ConfiguracaoRegressao(boolean incluirIntercepto) {
        this(incluirIntercepto, 1.0E-12);
    }

    public ConfiguracaoRegressao(boolean incluirIntercepto, double toleranciaSingularidade) {
        if (!Double.isFinite(toleranciaSingularidade) || toleranciaSingularidade <= 0) {
            throw new IllegalArgumentException("A tolerância de singularidade deve ser finita e maior que zero.");
        }

        this.incluirIntercepto = incluirIntercepto;
        this.toleranciaSingularidade = toleranciaSingularidade;
    }

    public boolean isIncluirIntercepto() {
        return incluirIntercepto;
    }

    public double getToleranciaSingularidade() {
        return toleranciaSingularidade;
    }
}

