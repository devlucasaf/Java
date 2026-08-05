package math.calculadora.completa.model.calculos;

import java.util.List;

public class ResultadoEstatistico {
    public final double       media;
    public final double       mediana;
    public final double       variancia;
    public final double       desvioPadrao;
    public final List<Double> moda;

    public ResultadoEstatistico(double media, double mediana, double variancia,
                                double desvioPadrao, List<Double> moda) {
        this.media = media;
        this.mediana = mediana;
        this.variancia = variancia;
        this.desvioPadrao = desvioPadrao;
        this.moda = moda;
    }
}