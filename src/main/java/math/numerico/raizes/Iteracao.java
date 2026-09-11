package math.numerico.raizes;

public class Iteracao {

    private final int       numero;
    private final Double    limiteInferior;
    private final Double    limiteSuperior;
    private final double    aproximacao;
    private final double    valorFuncao;
    private final Double    erroAbsoluto;
    private final Double    erroRelativo;
    private final Double    erroVerdadeiro;

    public Iteracao(int numero, Double limiteInferior, Double limiteSuperior, double aproximacao, double valorFuncao, Double erroAbsoluto, Double erroRelativo, Double erroVerdadeiro) {
        if (numero <= 0) {
            throw new IllegalArgumentException("O número da iteração deve ser maior que zero.");
        }

        this.numero = numero;
        this.limiteInferior = limiteInferior;
        this.limiteSuperior = limiteSuperior;
        this.aproximacao = aproximacao;
        this.valorFuncao = valorFuncao;
        this.erroAbsoluto = erroAbsoluto;
        this.erroRelativo = erroRelativo;
        this.erroVerdadeiro = erroVerdadeiro;
    }

    public int getNumero() {
        return numero;
    }

    public Double getLimiteInferior() {
        return limiteInferior;
    }

    public Double getLimiteSuperior() {
        return limiteSuperior;
    }

    public double getAproximacao() {
        return aproximacao;
    }

    public double getValorFuncao() {
        return valorFuncao;
    }

    public Double getErroAbsoluto() {
        return erroAbsoluto;
    }

    public Double getErroRelativo() {
        return erroRelativo;
    }

    public Double getErroVerdadeiro() {
        return erroVerdadeiro;
    }
}

