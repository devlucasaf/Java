package math.calculadora.completa.model.financas.resultados;

public class ResultadoMargem {
    public final double margemBruta;
    public final double margemLiquida;
    public final double markup;

    public ResultadoMargem(double margemBruta, double margemLiquida, double markup) {
        this.margemBruta = margemBruta;
        this.margemLiquida = margemLiquida;
        this.markup = markup;
    }
}