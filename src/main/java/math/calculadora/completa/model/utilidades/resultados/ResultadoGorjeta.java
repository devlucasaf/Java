package math.calculadora.completa.model.utilidades.resultados;

public class ResultadoGorjeta {
    public final double totalConta;
    public final double valorGorjeta;
    public final double totalPagar;
    public final double porPessoa;

    public ResultadoGorjeta(double totalConta, double valorGorjeta, double totalPagar, double porPessoa) {
        this.totalConta = totalConta;
        this.valorGorjeta = valorGorjeta;
        this.totalPagar = totalPagar;
        this.porPessoa = porPessoa;
    }
}
