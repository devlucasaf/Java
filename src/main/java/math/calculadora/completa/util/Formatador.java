package math.calculadora.completa.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Formatador {

    private static final DecimalFormat df = new DecimalFormat("#.##########",
            DecimalFormatSymbols.getInstance(Locale.US));

    public static String formatarNumero(double valor) {
        if (Double.isNaN(valor) || Double.isInfinite(valor)) {
            return "Erro";
        }

        if (valor == (long) valor) {
            return String.valueOf((long) valor);
        }
        return df.format(valor);
    }

    public static String formatarDecimal(double valor, int casas) {
        if (Double.isNaN(valor) || Double.isInfinite(valor)) {
            return "Erro";
        }

        String padrao = "#." + "0".repeat(Math.max(0, casas));
        DecimalFormat df2 = new DecimalFormat(padrao, DecimalFormatSymbols.getInstance(Locale.US));
        return df2.format(valor);
    }

    public static String formatarMinutosParaHora(int totalMinutos) {
        int horas = totalMinutos / 60;
        int minutos = totalMinutos % 60;
        return String.format("%02d:%02d", horas, minutos);
    }

    public static String formatarBinario(long valor) {
        return "0b" + Long.toBinaryString(valor);
    }

    public static String formatarHexadecimal(long valor) {
        return "0x" + Long.toHexString(valor).toUpperCase();
    }
}
