package math.calculadora.completa.model.conversoes;

public class ConversorFrequencia {

    public static double converter(double valor, String de, String para) {
        double emHz = paraHz(valor, de);
        return deHz(emHz, para);
    }

    private static double paraHz(double valor, String unidade) {
        switch (unidade) {
            case "Hz":
                return valor;
            case "kHz":
                return valor * 1e3;
            case "MHz":
                return valor * 1e6;
            case "GHz":
                return valor * 1e9;
            case "RPM":
                return valor / 60.0;
            default:
                throw new IllegalArgumentException("Unidade desconhecida: " + unidade);
        }
    }

    private static double deHz(double valor, String unidade) {
        switch (unidade) {
            case "Hz":
                return valor;
            case "kHz":
                return valor / 1e3;
            case "MHz":
                return valor / 1e6;
            case "GHz":
                return valor / 1e9;
            case "RPM":
                return valor * 60.0;
            default:
                throw new IllegalArgumentException("Unidade desconhecida: " + unidade);
        }
    }
}
