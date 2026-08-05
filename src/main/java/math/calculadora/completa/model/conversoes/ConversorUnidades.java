package math.calculadora.completa.model.conversoes;

public class ConversorUnidades {

    public static double converter(double valor, double fatorDe, double fatorPara) {
        return valor * (fatorPara / fatorDe);
    }
}
