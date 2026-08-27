package math.probabilidade.csv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResumoEstatistico {

    private final String        nomeColuna;
    private final int           quantidadeValores;
    private final int           quantidadeAusentes;
    private final double        soma;
    private final double        media;
    private final double        mediana;
    private final List<Double>  modas;
    private final double        desvioPadraoPopulacional;
    private final double        desvioPadraoAmostral;
    private final double        varianciaPopulacional;
    private final double        minimo;
    private final double        maximo;
    private final double        amplitude;

    public ResumoEstatistico(String nomeColuna, int quantidadeValores, int quantidadeAusentes, double soma, double media, double mediana, List<Double> modas, double desvioPadraoPopulacional, double desvioPadraoAmostral, double varianciaPopulacional, double minimo, double maximo) {
        this.nomeColuna = nomeColuna;
        this.quantidadeValores = quantidadeValores;
        this.quantidadeAusentes = quantidadeAusentes;
        this.soma = soma;
        this.media = media;
        this.mediana = mediana;
        this.modas = modas == null ? new ArrayList<>() : new ArrayList<>(modas);
        this.desvioPadraoPopulacional = desvioPadraoPopulacional;
        this.desvioPadraoAmostral = desvioPadraoAmostral;
        this.varianciaPopulacional = varianciaPopulacional;
        this.minimo = minimo;
        this.maximo = maximo;
        this.amplitude = maximo - minimo;
    }

    public String getNomeColuna() {
        return nomeColuna;
    }

    public int getQuantidadeValores() {
        return quantidadeValores;
    }

    public int getQuantidadeAusentes() {
        return quantidadeAusentes;
    }

    public double getSoma() {
        return soma;
    }

    public double getMedia() {
        return media;
    }

    public double getMediana() {
        return mediana;
    }

    public List<Double> getModas() {
        return Collections.unmodifiableList(modas);
    }

    public double getDesvioPadraoPopulacional() {
        return desvioPadraoPopulacional;
    }

    public double getDesvioPadraoAmostral() {
        return desvioPadraoAmostral;
    }

    public double getVarianciaPopulacional() {
        return varianciaPopulacional;
    }

    public double getMinimo() {
        return minimo;
    }

    public double getMaximo() {
        return maximo;
    }

    public double getAmplitude() {
        return amplitude;
    }
}

