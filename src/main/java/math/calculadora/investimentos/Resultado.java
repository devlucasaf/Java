package math.calculadora.investimentos;

public class Resultado {

    public final double montanteBruto;
    public final double lucro;
    public final double iof;
    public final double impostoRenda;
    public final double montanteLiquido;
    public final double rentabilidadeLiquida;

    public Resultado(double montanteBruto, double lucro, double iof, double impostoRenda,
                     double montanteLiquido, double rentabilidadeLiquida) {
        this.montanteBruto = montanteBruto;
        this.lucro = lucro;
        this.iof = iof;
        this.impostoRenda = impostoRenda;
        this.montanteLiquido = montanteLiquido;
        this.rentabilidadeLiquida = rentabilidadeLiquida;
    }
}

