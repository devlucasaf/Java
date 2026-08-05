package math.calculadora.completa.model.conversoes;

public class ConversorRomanos {

    public static String toRoman(int n) {
        if (n < 1 || n > 3999) {
            throw new IllegalArgumentException("Número fora do intervalo (1-3999)");
        }
        String[] mil = {"", "M", "MM", "MMM"};
        String[] cen = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] dez = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] uni = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        return mil[n / 1000] + cen[(n % 1000) / 100] + dez[(n % 100) / 10] + uni[n % 10];
    }

    public static int fromRoman(String s) {
        int result = 0;
        int prev = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            int val = romanValue(s.charAt(i));
            if (val < prev) {
                result -= val;
            } else {
                result += val;
            }
            prev = val;
        }

        if (result < 1 || result > 3999) {
            throw new IllegalArgumentException("Numeral romano inválido");
        }
        return result;
    }

    private static int romanValue(char c) {
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
