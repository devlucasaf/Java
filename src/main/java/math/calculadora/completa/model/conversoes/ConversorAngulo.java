package math.calculadora.completa.model.conversoes;

public class ConversorAngulo {

    public static double converter(double valor, String de, String para) {
        if (de.equalsIgnoreCase("Graus") && para.equalsIgnoreCase("Radianos")) {
            return Math.toRadians(valor);
        } else if (de.equalsIgnoreCase("Radianos") && para.equalsIgnoreCase("Graus")) {
            return Math.toDegrees(valor);
        } else if (de.equalsIgnoreCase(para)) {
            return valor;
        } else {
            throw new IllegalArgumentException("Unidades desconhecidas: " + de + " ou " + para);
        }
    }
}
