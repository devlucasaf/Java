package math.numerico.sistemaslineares;

public class ResultadoSistemaLinear {

    private final double[]  solucao;
    private final double    determinanteAproximado;

    public ResultadoSistemaLinear(double[] solucao, double determinanteAproximado) {
        if (solucao == null || solucao.length == 0) {
            throw new IllegalArgumentException("A solucao nao pode estar vazia.");
        }
        this.solucao = solucao.clone();
        this.determinanteAproximado = determinanteAproximado;
    }

    public double[] getSolucao() {
        return solucao.clone();
    }

    public double getDeterminanteAproximado() {
        return determinanteAproximado;
    }
}
