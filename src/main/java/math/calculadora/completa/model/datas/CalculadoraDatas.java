package math.calculadora.completa.model.datas;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class CalculadoraDatas {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static long diferencaDias(String data1, String data2) {
        LocalDate d1 = LocalDate.parse(data1, FORMATTER);
        LocalDate d2 = LocalDate.parse(data2, FORMATTER);
        return Math.abs(ChronoUnit.DAYS.between(d1, d2));
    }

    public static Period diferencaPeriodo(String data1, String data2) {
        LocalDate d1 = LocalDate.parse(data1, FORMATTER);
        LocalDate d2 = LocalDate.parse(data2, FORMATTER);
        if (d1.isAfter(d2)) {
            return Period.between(d2, d1);
        } else {
            return Period.between(d1, d2);
        }
    }

    public static String adicionarDias(String data, long dias) {
        LocalDate d = LocalDate.parse(data, FORMATTER);
        LocalDate nova = d.plusDays(dias);
        return nova.format(FORMATTER);
    }
}
