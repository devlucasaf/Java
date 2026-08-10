package math.calculadora.completa.model.conversoes;

public class ConversorEnergia {

    private static final double JOULE = 1.0;
    private static final double CALORIA = 4.184;
    private static final double KWH = 3.6e6;
    private static final double BTU = 1055.06;
    private static final double EV = 1.602176634e-19;

    public static double converter(double valor, String de, String para) {
        double emJoules = paraJoules(valor, de);
        return deJoules(emJoules, para);
    }

    private static double paraJoules(double valor, String unidade) {
        switch (unidade) {
            case "Joule":
                return valor * JOULE;
            case "Caloria":
                return valor * CALORIA;
            case "kWh":
                return valor * KWH;
            case "BTU":
                return valor * BTU;
            case "eV":
                return valor * EV;
            default:
                throw new IllegalArgumentException("Unidade desconhecida: " + unidade);
        }
    }

    private static double deJoules(double joules, String unidade) {
        switch (unidade) {
            case "Joule":
                return joules / JOULE;
            case "Caloria":
                return joules / CALORIA;
            case "kWh":
                return joules / KWH;
            case "BTU":
                return joules / BTU;
            case "eV":
                return joules / EV;
            default:
                throw new IllegalArgumentException("Unidade desconhecida: " + unidade);
        }
    }
}
