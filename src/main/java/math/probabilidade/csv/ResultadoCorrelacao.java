package math.probabilidade.csv;

public class ResultadoCorrelacao {

    private final String    primeiraColuna;
    private final String    segundaColuna;
    private final int       quantidadePares;
    private final Double    coeficiente;
    private final String    interpretacao;

    public ResultadoCorrelacao(String primeiraColuna, String segundaColuna, int quantidadePares, Double coeficiente, String interpretacao) {
        this.primeiraColuna = primeiraColuna;
        this.segundaColuna = segundaColuna;
        this.quantidadePares = quantidadePares;
        this.coeficiente = coeficiente;
        this.interpretacao = interpretacao;
    }

    public String getPrimeiraColuna() {
        return primeiraColuna;
    }

    public String getSegundaColuna() {
        return segundaColuna;
    }

    public int getQuantidadePares() {
        return quantidadePares;
    }

    public Double getCoeficiente() {
        return coeficiente;
    }

    public String getInterpretacao() {
        return interpretacao;
    }
}

