package math.calculadora.completa.model.datas;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CalendarioPerpetuo {

    public static String diaDaSemana(int dia, int mes, int ano) {
        if (mes == 1) {
            mes = 13;
            ano--;
        } else if (mes == 2) {
            mes = 14;
            ano--;
        }

        int k = ano % 100;
        int j = ano / 100;
        int h = (dia + (13 * (mes + 1)) / 5 + k + (k / 4) + (j / 4) + 5 * j) % 7;

        String[] dias = {"Sábado", "Domingo", "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira"};
        return dias[h];
    }

    public static String diaDaSemanaModerno(int dia, int mes, int ano) {
        LocalDate data = LocalDate.of(ano, mes, dia);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE");
        return formatter.format(data);
    }
}
