package math.calculadora.completa.model.conversoes;

public class ConversorRomanos {

    public static String paraRomano(int n) {
        if (n < 1 || n > 3999) {
            throw new IllegalArgumentException("Número fora do intervalo (1-3999)");
        }
        String[] mil = {"", "M", "MM", "MMM"};
        String[] cen = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] dez = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] uni = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        return mil[n / 1000] + cen[(n % 1000) / 100] + dez[(n % 100) / 10] + uni[n % 10];
    }

    public static int deRomano(String s) {
        int resultado = 0;
        int prev = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            int val = romanoValor(s.charAt(i));
            if (val < prev) {
                resultado -= val;
            } else {
                resultado += val;
            }
            prev = val;
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
