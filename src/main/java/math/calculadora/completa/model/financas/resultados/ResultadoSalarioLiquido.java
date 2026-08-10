package math.calculadora.completa.model.financas.resultados;

public class ResultadoSalarioLiquido {
    public double salarioBruto;
    public double descontoINSS;
    public double descontoIRRF;
    public double salarioLiquido;

    public ResultadoSalarioLiquido(double bruto, double inss, double irrf, double liquido) {
        this.salarioBruto = bruto;
        this.descontoINSS = inss;
        this.descontoIRRF = irrf;
        this.salarioLiquido = liquido;
    }
}