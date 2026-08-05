package math.calculadora.completa.util;

public class Validador {

    public static boolean isNumero(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }

        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isInteiro(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }

        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidoParaBase(String texto, int base) {
        if (texto == null || texto.isEmpty()) {
            return false;
        }

        for (char c : texto.toUpperCase().toCharArray()) {
            if (Character.digit(c, base) < 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isRomanoValido(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        return s.matches("[IVXLCDM]+");
    }

    public static boolean isDataValida(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        return s.matches("\\d{2}/\\d{2}/\\d{4}");
    }

    public static boolean isHoraValida(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        return s.matches("\\d{2}:\\d{2}");
    }
}
