package math.calculadora.completa.model.conversoes;

public class ConversorTemperatura {

    public static double converter(double valor, String de, String para) {
        double celsius;

        switch (de) {
            case "Celsius":
                celsius = valor;
                break;
            case "Fahrenheit":
                celsius = (valor - 32) * 5.0 / 9.0;
                break;
            case "Kelvin":
                celsius = valor - 273.15;
                break;
            default:
                throw new IllegalArgumentException("Escala de origem desconhecida: " + de);
        }

        switch (para) {
            case "Celsius":
                return celsius;
            case "Fahrenheit":
                return celsius * 9.0 / 5.0 + 32;
            case "Kelvin":
                return celsius + 273.15;
            default:
                throw new IllegalArgumentException("Escala de destino desconhecida: " + para);
        }
    }
}
