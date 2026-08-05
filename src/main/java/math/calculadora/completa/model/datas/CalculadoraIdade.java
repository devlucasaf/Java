package math.calculadora.completa.model.datas;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class CalculadoraIdade {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static Period calcularIdade(String dataNascimento) {
        LocalDate nascimento = LocalDate.parse(dataNascimento, FORMATTER);
        LocalDate hoje = LocalDate.now();

        if (nascimento.isAfter(hoje)) {
            throw new IllegalArgumentException("Data de nascimento não pode ser futura");
        }

        return Period.between(nascimento, hoje);
    }

    public static int calcularIdadeEmAnos(String dataNascimento) {
        return calcularIdade(dataNascimento).getYears();
    }

    public static String formatarIdade(String dataNascimento) {
        Period periodo = calcularIdade(dataNascimento);
        return String.format("%d anos, %d meses, %d dias",
                periodo.getYears(), periodo.getMonths(), periodo.getDays());
    }
}
