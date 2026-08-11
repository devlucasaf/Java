package math.calculadora.completa.model.conversoes;

public class ConversorRomanos {

    public static String paraRomano(int n) {
        if (n < 1 || n > 3999) {
            throw new IllegalArgumentException("Número fora do intervalo (1-3999)");
        }
        String[] milhares = {"", "M", "MM", "MMM"};
        String[] centenas = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] dezenas = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] unidades = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        return milhares[n / 1000] + centenas[(n % 1000) / 100] + dezenas[(n % 100) / 10] + unidades[n % 10];
    }

    public static int deRomano(String s) {
        int resultado = 0;
        int valorProcessado = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            int valor = romanoValor(s.charAt(i));
            if (valor < valorProcessado) {
                resultado -= valor;
            } else {
                resultado += valor;
            }
            valorProcessado = valor;
        }

        if (resultado < 1 || resultado > 3999) {
            throw new IllegalArgumentException("Numeral romano inválido");
        }
        return resultado;
    }

    private static int romanoValor(char c) {
        switch (c) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                throw new IllegalArgumentException("Caractere inválido: " + c);
        }
    }
}
